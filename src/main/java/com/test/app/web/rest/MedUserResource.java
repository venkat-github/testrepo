package com.test.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.app.domain.DoctorSchedule;
import com.test.app.domain.DoctorVisit;
import com.test.app.domain.Hospital;
import com.test.app.domain.HospitalDoctorConsultaion;
import com.test.app.domain.User;
import com.test.app.domain.UserDoctorVisitRecord;
import com.test.app.domain.util.CustomLocalDateSerializer;
import com.test.app.domain.util.ISO8601LocalDateDeserializer;
import com.test.app.repository.DoctorScheduleRepository;
import com.test.app.repository.DoctorScheduleRespositoryHelperFunctions;
import com.test.app.repository.DoctorVisitRepository;
import com.test.app.repository.HospitalDoctorConsultaionRepository;
import com.test.app.repository.HospitalDoctorConsultationHelperFunctions;
import com.test.app.repository.HospitalRepository;
import com.test.app.repository.UserRecordRepository;
import com.test.app.repository.UserRepository;
import com.test.app.security.SecurityUtils;
import com.test.app.service.UserService;
import com.test.app.web.rest.dto.HospitalDto;
import com.test.app.web.rest.util.HeaderUtil;
import com.test.app.web.rest.util.PaginationUtil;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * REST controller for managing UserDTO.
 */
@RestController
@RequestMapping("/api")
public class MedUserResource {

    private final Logger log = LoggerFactory.getLogger(MedUserResource.class);


    @Inject
    private UserRepository userRepository;

    @Inject
    private HospitalRepository hospitalRepository;

    @Inject
    private HospitalDoctorConsultaionRepository hospitalDoctorConsultaionRepository;

    @Inject
    private DoctorVisitRepository doctorVisitRepository;

    @Inject
    private UserRecordRepository userRecordRepository;
    
    @Inject
    private DoctorScheduleRepository doctorScheduleRepository;
    
	@RequestMapping(value = "/userDTOs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<User> updateUser(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to update UserDTO : {}", user);
        User userToStore = userRepository.findOneById(user.getId());
        userToStore.setAge(user.getAge());
        userToStore.setLogin(user.getLogin());
        userToStore.setEmail(user.getEmail());
        userToStore.setEmailId(user.getEmailId());
        userToStore.setLocation(user.getLocation());
        userToStore.setCity(user.getCity());
        
        user = userRepository.save(userToStore);
        User result = user;
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("userDTO", user.getId().toString()))
                .body(result);
    }

    /**
     * GET  /userDTOs -> get all the userDTOs.
     */
    @RequestMapping(value = "/userDTOs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<List<User>> getAllUsers(
    		@RequestParam(value = "name" , required = false) String name,
    		@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	Page<User> page = null;
    	if (name != null) {
    		page = userRepository.findByName(name, PaginationUtil.generatePageRequest(offset, limit));
    	} else {
    		page = userRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
    	}
    	
    	List<User> result = page.getContent();
    	
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userDTOs", offset, limit);
        return new ResponseEntity<List<User>>(result, headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/doctor",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<List<User>> findDoctorByName(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit, String name)
        throws URISyntaxException {
    	Page<User> page = userRepository.findByName(name, PaginationUtil.generatePageRequest(offset, limit));
    	
    	List<User> result = page.getContent();
    	
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userDTOs", offset, limit);
        return new ResponseEntity<List<User>>(result, headers, HttpStatus.OK);

    }
    
    @Inject
    MongoTemplate mongoTemplate;
    
    List<Hospital> getHospitalsForUser(User user) {
    	Query query = Query.query(Criteria.where("adminIds").in(user.getId()));
    	List<Hospital> doctors = mongoTemplate.find(query , Hospital.class);
    	return doctors;
    }
    
    @RequestMapping(value = "/hospitals",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<List<Hospital>> getAllHospitals(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	User loggedinUser = userService.getUserWithAuthorities();
    	List<Hospital> result = getHospitalsForUser(loggedinUser);
    	
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(1,7, "/api/hospitals", offset, limit);
        return new ResponseEntity<List<Hospital>>(result, headers, HttpStatus.OK);

    }

    
    @RequestMapping(value="/cities", method = RequestMethod.GET)
	@ResponseBody
	Map<String,String> getCities() {
		HashMap<String, String>	cities = new HashMap<String, String>();
		cities.put("koramanagala","koramanagala");
		cities.put("marathalli","marathalli");
		return cities;
	}
    
    @Inject
    DoctorScheduleRespositoryHelperFunctions doctorScheduleFunctions;

    @RequestMapping(value = "/consultation",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HospitalDoctorConsultaion> getFreeSlots(
    	    @RequestParam(value = "date", required = true)
    	    @DateTimeFormat(pattern="yyyy-MM-dd")
    	    LocalDate date,
    		@RequestParam(value = "doctorId" , required = true) String doctorId
    		)
        throws URISyntaxException {
    	HospitalDoctorConsultaion result = null;
    	HttpHeaders headers = new HttpHeaders();
    	if (doctorId != null) {
    		result = hospitalDoctorConsultaionRepository.findOneByDoctorIdAndDate(doctorId, date);
    	}
        return new ResponseEntity<HospitalDoctorConsultaion>(result, headers, HttpStatus.OK);

    }
    
    @RequestMapping(value = "/doctors",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HospitalDoctorConsultaion>> searchConsultations(
    	    @RequestParam(value = "date", required = false)
    	    @DateTimeFormat(pattern="yyyy-MM-dd")
    	    LocalDate date,
    		@RequestParam(value = "speciality" , required = false) String speciality,
    		@RequestParam(value = "speciality" , required = false) String doctorId,
    		@RequestParam(value = "page" , required = false) Integer offset,
    		@RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	List<HospitalDoctorConsultaion> result = new ArrayList<HospitalDoctorConsultaion>();
    	Page<HospitalDoctorConsultaion> page = null;
    	HttpHeaders headers = null;
		page = hospitalDoctorConsultaionRepository.findBySpecialityAndDate(speciality, date, 
			PaginationUtil.generatePageRequest(offset, limit));
		result = page.getContent();
		headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctors", offset, limit);
        return new ResponseEntity<List<HospitalDoctorConsultaion>>(result, headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/userRecords",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<List<UserDoctorVisitRecord>> getAllUserRecords(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	User user = userService.getUserWithAuthorities();
    	List<UserDoctorVisitRecord> result = new ArrayList<UserDoctorVisitRecord>();
    	Page<UserDoctorVisitRecord> page = userRecordRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
    	result = page.getContent();
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userRecords", offset, limit);
        return new ResponseEntity<List<UserDoctorVisitRecord>>(result, headers, HttpStatus.OK);
    }

    @Inject
    UserService userService;

    @RequestMapping(value = "/patients",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<List<DoctorVisit>> getDoctorSchedule(
    	    @RequestParam(value = "date", required = false)
    	    LocalDate date,//TODO
    		@RequestParam(value = "page" , required = false) Integer offset,
    		@RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	User user = userService.getUserWithAuthorities();
    	if (date == null) {
    		date = new LocalDate();
    		date = new LocalDate(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
    	}
        Page<DoctorVisit> page = doctorVisitRepository.findByDoctorIdAndDate(user.getId(), date, PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctorVisitDTOs", offset, limit);
        return new ResponseEntity<List<DoctorVisit>>(page.getContent(), headers, HttpStatus.OK);
    }

    public ResponseEntity<List<User>>  get(int offset, int limit) throws URISyntaxException {
    	Page<User> page = userRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
    	
    	List<User> result = page.getContent();
    	
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userDTOs", offset, limit);
        return new ResponseEntity<List<User>>(result, headers, HttpStatus.OK);

    }
    
    @RequestMapping(value = "/userDTOs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<User> getUser(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get UserDTO : {}", id);
        User userDTO = userRepository.findOne(id);
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/hospitals/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<Hospital> getHospital(@PathVariable String id, HttpServletResponse response) {
        Hospital result = hospitalRepository.findOne(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    
    @RequestMapping(value = "/doctors/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HospitalDoctorConsultaion> getDoctorConsultation(@PathVariable String id, HttpServletResponse response) {
    	HospitalDoctorConsultaion result = hospitalDoctorConsultaionRepository.findOne(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    
    @RequestMapping(value = "/userRecords/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<UserDoctorVisitRecord> getUserRecord(@PathVariable String id, HttpServletResponse response) {
    	UserDoctorVisitRecord result = userRecordRepository.findOne(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    
    @RequestMapping(value = "/patients/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<DoctorVisit> getUserDoctorVisit(@PathVariable String id, HttpServletResponse response) {
    	DoctorVisit result = doctorVisitRepository.findOne(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    
    @RequestMapping(value = "/hospitals",
            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        User loggedinUser = userService.getUserWithAuthorities();
        Set<String> adminIds = new HashSet<String>();
        adminIds.add(loggedinUser.getId());
		dto.setAdminIds(adminIds);
        Hospital result = hospitalRepository.save(dto);
        return ResponseEntity.created(new URI("/api/hospitals/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("hospitals", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/doctors",
            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HospitalDoctorConsultaion> createDoctorConsultaion(@RequestBody HospitalDoctorConsultaion dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        HospitalDoctorConsultaion result = hospitalDoctorConsultaionRepository.save(dto);
        return ResponseEntity.created(new URI("/api/doctors/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("doctors", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/userRecords",
            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDoctorVisitRecord> createUserRecord(@RequestBody UserDoctorVisitRecord dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        UserDoctorVisitRecord result = userRecordRepository.save(dto);
        return ResponseEntity.created(new URI("/api/userRecords/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("userRecords", result.getId().toString()))
                .body(result);
    }

    @Inject
    DoctorScheduleRepository doctorScheduleRepo;
    
    @RequestMapping(value = "/doctorSchedules/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<DoctorSchedule> getSchedule(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get UserDTO : {}", id);
        DoctorSchedule userDTO = doctorScheduleRepo.findOne(id);
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctorSchedules",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<List<DoctorSchedule>> getSchedules(
    	    @RequestParam(value = "date", required = false)
    	    LocalDate date,//TODO
    		@RequestParam(value = "page" , required = false) Integer offset,
    		@RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	
        Page<DoctorSchedule> page = doctorScheduleRepo.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctorSchedules", offset, limit);
        return new ResponseEntity<List<DoctorSchedule>>(page.getContent(), headers, HttpStatus.OK);
    }

    @Inject
    HospitalDoctorConsultationHelperFunctions hospitalDoctorConsultationHelperFunctions;

    @RequestMapping(value = "/doctorSchedules",
            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorSchedule> createDoctorSchedule(@RequestBody DoctorSchedule dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        Hospital hospital = hospitalRepository.findOne(dto.getHospitalId());
        dto.setHospitalName(hospital.getName());
        
        DoctorSchedule result = doctorScheduleRepo.save(dto);
        
        hospitalDoctorConsultationHelperFunctions.
        addNextDayConsultationRecordForDoctorAndHospital(dto.getDoctorId(),
        		dto.getHospitalId(), dto.getStartDate(), dto.getEndDate());
        
        return ResponseEntity.created(new URI("/api/doctorSchedules/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("doctorSchedules", result.getId().toString()))
                .body(result);
    }
    
    @RequestMapping(value = "/patients",
            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorVisit> createDoctorVisit(@RequestBody DoctorVisit dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        HospitalDoctorConsultaion consultation = hospitalDoctorConsultaionRepository.findOne(dto.getConsultId());
        consultation.getFreeSlots().remove(dto.getSlot());
        hospitalDoctorConsultaionRepository.save(consultation);
        
        DoctorVisit result = doctorVisitRepository.save(dto);
        
        return ResponseEntity.created(new URI("/api/patients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("patients", result.getId().toString()))
                .body(result);
    }

    
    @RequestMapping(value = "/hospitals",
            method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hospital> updateHospital(@RequestBody Hospital dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        Hospital result = hospitalRepository.save(dto);
        return ResponseEntity.created(new URI("/api/hospitals/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("hospitals", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/doctors",
            method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HospitalDoctorConsultaion> updateDoctorConsultaion(@RequestBody HospitalDoctorConsultaion dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        HospitalDoctorConsultaion result = hospitalDoctorConsultaionRepository.save(dto);
        return ResponseEntity.created(new URI("/api/doctors/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("doctors", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/userRecords",
            method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDoctorVisitRecord> updateUserRecord(@RequestBody UserDoctorVisitRecord dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        UserDoctorVisitRecord result = userRecordRepository.save(dto);
        return ResponseEntity.created(new URI("/api/userRecords/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("userRecords", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/patients",
            method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorVisit> updateDoctorVisit(@RequestBody DoctorVisit dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        DoctorVisit result = doctorVisitRepository.save(dto);
        return ResponseEntity.created(new URI("/api/patients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("patients", result.getId().toString()))
                .body(result);
    }
    @RequestMapping(value = "/doctor_schedule",
            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorSchedule> createDoctorConsultaion(@RequestBody DoctorSchedule dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new doctorSchdule cannot already have an ID").body(null);
        }
        DoctorSchedule result = doctorScheduleRepository.save(dto);
        return ResponseEntity.created(new URI("/api/doctors/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("doctors", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/doctor_schedule",
            method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorSchedule> updateDoctorConsultaion(@RequestBody DoctorSchedule dto) throws URISyntaxException {
        if (dto.getId() == null) {
            return ResponseEntity.badRequest().header("Failure", "Cannot update doctorSchedule with out ID").body(null);
        }
        DoctorSchedule result = doctorScheduleRepository.save(dto);
        return ResponseEntity.created(new URI("/api/doctors/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("doctors", result.getId().toString()))
                .body(result);
    }
}
