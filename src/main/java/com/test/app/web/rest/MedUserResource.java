package com.test.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
import com.test.app.repository.DoctorVisitRepository;
import com.test.app.repository.HospitalDoctorConsultaionRepository;
import com.test.app.repository.HospitalRepository;
import com.test.app.repository.UserRecordRepository;
import com.test.app.repository.UserRepository;
import com.test.app.service.UserService;
import com.test.app.web.rest.dto.HospitalDto;
import com.test.app.web.rest.util.HeaderUtil;
import com.test.app.web.rest.util.PaginationUtil;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

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
    
    
	@RequestMapping(value = "/userDTOs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<User> update(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to update UserDTO : {}", user);
        user = userRepository.save(user);
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
    
    public ResponseEntity<List<User>> findDoctor(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit, String name)
        throws URISyntaxException {
    	Page<User> page = userRepository.findByName(name, PaginationUtil.generatePageRequest(offset, limit));
    	
    	List<User> result = page.getContent();
    	
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userDTOs", offset, limit);
        return new ResponseEntity<List<User>>(result, headers, HttpStatus.OK);

    }
    
    @Inject
    MongoTemplate mongoTemplate;
    
    List<Hospital> getHospitals(User user) {
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
    	List<Hospital> result = getHospitals(loggedinUser);
    	
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(1,7, "/api/hospitals", offset, limit);
        return new ResponseEntity<List<Hospital>>(result, headers, HttpStatus.OK);

    }

    
    @RequestMapping(value="/cities", method = RequestMethod.GET)
	@ResponseBody
	Map<String,String> cities() {
		HashMap<String, String>	cities = new HashMap<String, String>();
		cities.put("koramanagala","koramanagala");
		cities.put("marathalli","marathalli");
		return cities;
	}


    @RequestMapping(value = "/doctors",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<List<HospitalDoctorConsultaion>> getAllDoctors(
    	    @RequestParam(value = "date", required = false)
    	    @JsonSerialize(using = CustomLocalDateSerializer.class)
    	    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    	    LocalDate date,
    		@RequestParam(value = "speciality" , required = false) String speciality,
    		@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	List<HospitalDoctorConsultaion> result = new ArrayList<HospitalDoctorConsultaion>();
    	Page<HospitalDoctorConsultaion> page = hospitalDoctorConsultaionRepository.findBySpecialityAndDate(speciality, date, PaginationUtil.generatePageRequest(offset, limit));
    	result = page.getContent();
    	
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctors", offset, limit);
        return new ResponseEntity<List<HospitalDoctorConsultaion>>(result, headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/userRecords",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<List<UserDoctorVisitRecord>> getAllUserRecords(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
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
    
    public ResponseEntity<List<DoctorVisit>> getAllUserVisits(
    	    @RequestParam(value = "date", required = false)
    	    @JsonSerialize(using = CustomLocalDateSerializer.class)
    	    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    	    LocalDate date,
    		@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	User user = userService.getUserWithAuthorities();
    	date = new LocalDate(2015,8,1);
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

    @RequestMapping(value = "/find_doctor",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<User> findDoctor(@RequestParam("name") String name, 
    		@RequestParam("practionerid") String practionerId, HttpServletResponse response) {
        //TODO
    	//return new ResponseEntity<>(null, HttpStatus.OK);
    	return null;
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
    	    @JsonSerialize(using = CustomLocalDateSerializer.class)
    	    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    	    LocalDate date,
    		@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	
        Page<DoctorSchedule> page = doctorScheduleRepo.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctorSchedules", offset, limit);
        return new ResponseEntity<List<DoctorSchedule>>(page.getContent(), headers, HttpStatus.OK);
    }

    int getEndDate(int year, int month) {
    	switch(month) {
    	case 1:
    	case 3:
    	case 5:
    	case 7:
    	case 8:
    	case 10:
    	case 12:
    		return 31;
    	case 2:
    		return 28;
    	case 4:
    	case 6:
    	case 9:
    	case 11:
    		return 30;
    	}
    	return 30;
    }

    @RequestMapping(value = "/doctorSchedules",
            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorSchedule> createDoctorSchedule(@RequestBody DoctorSchedule dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userDTO cannot already have an ID").body(null);
        }
        Hospital hospital = hospitalRepository.findOne(dto.getHospitalId());
        dto.setHospitalName(hospital.getName());
        
        DoctorSchedule result = doctorScheduleRepo.save(dto);
        
        
        for (int month = dto.getStartDate().getMonthOfYear() ; 
        		month < dto.getEndDate().getMonthOfYear(); month++) {
        	
        	int startDate = dto.getStartDate().getDayOfMonth();
        	int endDate = getEndDate(dto.getStartDate().getYear(), dto.getStartDate().getMonthOfYear());
        	
			for (int day = startDate; day < endDate; day++) {
				
				HospitalDoctorConsultaion dto = new HospitalDoctorConsultaion();
				List<String> freeSlots = new ArrayList<String>();
				freeSlots.add("8:00");
				freeSlots.add("8:15");
				freeSlots.add("8:30");
				freeSlots.add("8:45");

				freeSlots.add("9:00");
				freeSlots.add("9:15");
				freeSlots.add("9:30");
				freeSlots.add("9:45");
	
				freeSlots.add("10:00");
				freeSlots.add("10:15");
				freeSlots.add("10:30");
				freeSlots.add("10:45");
	
				freeSlots.add("11:00");
				freeSlots.add("11:15");
				freeSlots.add("11:30");
				freeSlots.add("11:45");
	
				LocalDate date = new LocalDate(2015, month, day);
				
				
				dto.setLocation(dto.getLocation());
				dto.setDoctorName(dto.getDoctorName());
				HashSet<String> degrees = new HashSet<String>();
				degrees.add("md");
				
				dto.setDate(date);
				dto.setDoctorId(dto.getDoctorId());
				dto.setExperience(dto.getExperience());
				dto.setFreeSlots(freeSlots);
				dto.setOccupiedSlots(new ArrayList<String>());
				dto.setHospitalId(hospital.getId());
				dto.setSpeciality(dto.getSpeciality());
				hospitalDoctorConsultaionRepository.save(dto);
				
				
			}
        }
        
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
}
