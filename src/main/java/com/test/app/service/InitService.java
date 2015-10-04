package com.test.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.app.domain.Authority;
import com.test.app.domain.DoctorSchedule;
import com.test.app.domain.DoctorVisit;
import com.test.app.domain.Hospital;
import com.test.app.domain.HospitalDoctorConsultaion;
import com.test.app.domain.User;
import com.test.app.domain.UserDoctorVisitRecord;
import com.test.app.domain.WorkingDay;
import com.test.app.domain.enumeration.Sex;
import com.test.app.domain.enumeration.Speciality;
import com.test.app.repository.AuthorityRepository;
import com.test.app.repository.DoctorScheduleRepository;
import com.test.app.repository.DoctorVisitRepository;
import com.test.app.repository.HospitalDoctorConsultaionRepository;
import com.test.app.repository.HospitalDoctorConsultationHelperFunctions;
import com.test.app.repository.HospitalRepository;
import com.test.app.repository.UserRecordRepository;
import com.test.app.repository.UserRepository;
import com.test.app.web.rest.util.PaginationUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;


@Service
public class InitService {

    final Logger log = LoggerFactory.getLogger(InitService.class);

    @Inject
    UserRepository userRepository;

    @Inject
    HospitalRepository hospitalRepository;
    
    @Inject
    UserService userService;
    
	@Inject
    AuthorityRepository authorityRepository;

    @Inject
    DoctorVisitRepository doctorVisitRepository;
    
    @Inject
    UserRecordRepository userRecordDTORepository;

    @Inject
    HospitalDoctorConsultaionRepository hospitalDoctorConsultaionRepository;
    @Inject
    DoctorScheduleRepository doctorScheduleRepository;
    
    @Inject
    HospitalDoctorConsultationHelperFunctions hospitalDoctorConsultationHelperFunctions;
    
    @Inject
    MongoTemplate mongoTemplate;
    
    @PostConstruct
    public  void init() {
    	try {
    		userRepository.deleteAll();
    		userRecordDTORepository.deleteAll();
    		doctorScheduleRepository.deleteAll();
    		hospitalDoctorConsultaionRepository.deleteAll();
    		doctorVisitRepository.deleteAll();
    		hospitalRepository.deleteAll();
    		userRepository.deleteAll();
    		
	    	createUsers();
	    	createDoctors();
	    	createHospitals();
	    	addDoctorsToHospitals();
	    	bookAppointments();

	    	List result = hospitalDoctorConsultaionRepository.findBySpecialityAndDate("DENTIST", new LocalDate());
	    	User user = userRepository.findOneByName("hosp");
	    	
	    	Query query = Query.query(Criteria.where("adminIds").in(user.getId()));
	    	List<Hospital> doctor = mongoTemplate.find(query , Hospital.class);
	    	System.out.println("found "+doctor.size()+" no of hospitals ");

	    	
    	} catch (javax.validation.ConstraintViolationException ex1) {
    		System.out.println("constrant name "+ex1.getConstraintViolations());
    		for (ConstraintViolation x : ex1.getConstraintViolations()) {
    			System.out.println(x.getPropertyPath());
    			System.out.println(x.getInvalidValue());
    			System.out.println(x.getMessage());
    		}
    	} catch (Throwable e) {
    		e.printStackTrace();
    	}
    }
    
    void bookAppointments() {
    	bookAppointment("user", "doc1", "8:15");
    	bookAppointment("user1", "doc1","8:30");
    	bookAppointment("user2", "doc1","8:45");

    	bookAppointment("user", "doc2","9:15");
    	bookAppointment("user1", "doc2","9:30");
    	bookAppointment("user2", "doc2","9:45");
	}

    
	void bookAppointment(String userName, String docName, String slot) {
    	User userDto = userRepository.findOneByName(userName);
		HospitalDoctorConsultaion consultDto = findConsultation("koramanagala", "DENTIST", docName);
		DoctorVisit visitDto = bookAppointment(consultDto, userDto.getId(), slot);

    	
		LocalDate date = new LocalDate();
		date = new LocalDate(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
	
        Page<DoctorVisit> page = doctorVisitRepository.findByDoctorIdAndDate(consultDto.getDoctorId(), 
        		date, PaginationUtil.generatePageRequest(0, 5));

        System.out.println(page.getContent().size());;
        
		UserDoctorVisitRecord userRecord = new UserDoctorVisitRecord();
		userRecord.setSymptoms("hand pain");
		userRecord.setPrescription("use moov pain relief balm");
		userRecord.setVisitId(visitDto.getId());
		userRecord.setUserId(userDto.getId());
		
		userRecordDTORepository.save(userRecord);
    }
	
    DoctorVisit bookAppointment(HospitalDoctorConsultaion consultDto, String userId,
    		String slot) {
    	DoctorVisit dto = new DoctorVisit();
    	dto.setConsultId(consultDto.getId());
    	dto.setDoctorId(consultDto.getDoctorId());
    	dto.setHospitalId(consultDto.getHospitalId());
    	dto.setDate(consultDto.getDate());
    	dto.setUserId(userId);
    	dto.setSlot(slot);
    	return doctorVisitRepository.save(dto);
	}

	HospitalDoctorConsultaion findConsultation(String location, String speciality, String doctorName) {
		return hospitalDoctorConsultaionRepository.findByDoctorName(doctorName).get(0);
	}

	void createUsers() {
		HashSet<Authority> admins = new HashSet<Authority>();
		HashSet<Authority> users = new HashSet<Authority>();
		HashSet<Authority> hosps = new HashSet<Authority>();
		
		Authority adminAuth = authorityRepository.findOne("ROLE_ADMIN");
		admins.add(adminAuth);
        Authority hospAuth = authorityRepository.findOne("ROLE_HOSPITAL_ADMIN");
        hosps.add(hospAuth);
        Authority userAuth = authorityRepository.findOne("ROLE_USER");
        users.add(userAuth);
        
        createUser("hosp@gmail.com", "admin", "hosp", "hosp","hosp@gmail.com","en", hosps);
        createUser("admin@gmail.com", "admin", "admin", "admin","admin@gmail.com","en", admins);
        createUser("user@gmail.com", "admin", "user", "user","user@gmail.com","en", users);
        createUser("user1@gmail.com", "admin", "user1", "user","user1@gmail.com","en", users);
        createUser("user2@gmail.com", "admin", "user2", "user","user2@gmail.com","en", users);
        createUser("user3@gmail.com", "admin", "user3", "user","user3@gmail.com","en", users);
        createUser("user4@gmail.com", "admin", "user4", "user","user4@gmail.com","en", users);
	}

	String createUser(String name, String password, String first, String last, String email, 
			String lang, HashSet<Authority> auths) {
		
		RestTemplate restTemplate = new RestTemplate();
        
		User user = userService.createUserInformation(name, password , first, last,
        		email, lang, auths);
        return user.getId();
	}
	
	void findDoctor(String location, String speciality) {
		for (HospitalDoctorConsultaion dto : hospitalDoctorConsultaionRepository.findBySpecialityAndLocation(speciality, location) ) {
			dto.getFreeSlots().remove("8:30");
			dto.getOccupiedSlots().add("8:30");
			
			dto.getFreeSlots().remove("8:00");
			dto.getOccupiedSlots().add("8:00");
			
			dto.getFreeSlots().remove("8:15");
			dto.getOccupiedSlots().add("8:15");
			
			
			dto.getFreeSlots().remove("8:45");
			dto.getOccupiedSlots().add("8:45");
			
			hospitalDoctorConsultaionRepository.save(dto);
			
		}
		
		for (HospitalDoctorConsultaion dto : hospitalDoctorConsultaionRepository.findBySpecialityAndLocation(speciality, location) ) {
			System.out.println("dto name "+dto.getDoctorName());
		}
		
	}

	void addDoctorsToHospitals() {
		addDoctorToHospital("koramanagala", "manipal", "doc1");
		LocalDate dt = new LocalDate(2015,8,1);
		for (HospitalDoctorConsultaion dto : 
			hospitalDoctorConsultaionRepository.findBySpecialityAndDate(Speciality.DENTIST.toString(), dt) ) {
			System.out.println("dto name "+dto.getDoctorName());
		}
		addDoctorToHospital("koramanagala", "manipal", "doc2");
		
		addDoctorToHospital("koramanagala", "apollo", "doc3");
		addDoctorToHospital("koramanagala", "apollo", "doc4");
		
		addDoctorToHospital("koramanagala", "mallya", "doc5");
		addDoctorToHospital("koramanagala", "mallya", "doc6");
		
		addDoctorToHospital("koramanagala", "sakra", "doc7");
		addDoctorToHospital("koramanagala", "sakra", "doc8");
		
		addDoctorToHospital("marathalli", "jeevika", "doc9");
		addDoctorToHospital("marathalli", "jeevika", "doc10");
		
		addDoctorToHospital("marathalli", "vims", "doc11");
		addDoctorToHospital("marathalli", "vims", "doc12");
		
		addDoctorToHospital("marathalli", "yashomati", "doc13");
		addDoctorToHospital("marathalli", "yashomati", "doc14");
		
		addDoctorToHospital("koramanagala", "manipal", "gyn1");
		addDoctorToHospital("koramanagala", "manipal", "gyn2");
		
		addDoctorToHospital("koramanagala", "apollo", "gyn3");
		addDoctorToHospital("koramanagala", "apollo", "gyn4");
		
		addDoctorToHospital("koramanagala", "mallya", "gyn5");
		addDoctorToHospital("koramanagala", "mallya", "gyn6");
		
		addDoctorToHospital("koramanagala", "sakra", "gyn7");
		addDoctorToHospital("koramanagala", "sakra", "gyn8");
		
		addDoctorToHospital("marathalli", "jeevika", "gyn9");
		addDoctorToHospital("marathalli", "jeevika", "gyn10");
		
		addDoctorToHospital("marathalli", "vims", "gyn11");
		addDoctorToHospital("marathalli", "vims", "gyn12");
		
		addDoctorToHospital("marathalli", "yashomati", "gyn13");
		addDoctorToHospital("marathalli", "yashomati", "gyn14");
		
		
	}

	void addDoctorToHospital(String location, String hospitalName, String doctorName) {
		User doc = userRepository.findOneByName(doctorName);
		Hospital hospital = hospitalRepository.findByNameAndLocation(hospitalName, location).get(0);
		Set<WorkingDay> workingdays = null;
		DoctorSchedule doctorSchedule = new DoctorSchedule();
		doctorSchedule.setDoctorId(doc.getId());
		doctorSchedule.setDoctorName(doc.getName());
		doctorSchedule.setFees(500);
		doctorSchedule.setBreakStartTime("12:30");
		doctorSchedule.setBreakEndTime("13:30");
		doctorSchedule.setStartDate(new LocalDate());
		doctorSchedule.setEndDate(new LocalDate().plusDays(30));
		doctorSchedule.setStartTime("8:00");
		doctorSchedule.setEndTime("17:00");
		doctorSchedule.setHospitalId(hospital.getId());
		doctorSchedule.setHospitalName(hospital.getName());
		doctorSchedule.setSlotDuration(15);
		doctorSchedule.setWorkingDays(workingdays);
		doctorScheduleRepository.save(doctorSchedule);
		
		hospitalDoctorConsultationHelperFunctions.
		addNextDayConsultationRecordForDoctorAndHospital(doc.getId(), hospital.getId(),
				new LocalDate(), new LocalDate().plusDays(30));
		
		/*
		HospitalDoctorConsultaion dto = new HospitalDoctorConsultaion();
		
		for (int month = 8 ; month < 9; month++) {
			for (int day = 1; day < 2; day++) {
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
				
				
				dto.setLocation(location);
				dto.setDoctorName(doctorName);
				HashSet<String> degrees = new HashSet<String>();
				degrees.add("md");
				
				dto.setDate(date);
				dto.setDoctorId(doc.getId());
				dto.setExperience(doc.getExperience());
				dto.setFreeSlots(freeSlots);
				dto.setOccupiedSlots(new ArrayList<String>());
				dto.setHospitalId(hospital.getId());
				dto.setSpeciality(doc.getSpecialities());
				hospitalDoctorConsultaionRepository.save(dto);
				
				
			}
		}
		*/
	}

	void createHospitals() {
		createHospital("bangalore", "test@gmail.com", "koramanagala", "123456789", "manipal");
		createHospital("bangalore", "test@gmail.com", "koramanagala", "123456789", "apollo");
		createHospital("bangalore", "test@gmail.com", "koramanagala", "123456789", "mallya");
		createHospital("bangalore", "test@gmail.com", "koramanagala", "123456789", "sakra");
		
		createHospital("bangalore", "test@gmail.com", "marathalli", "123456789", "jeevika");
		createHospital("bangalore", "test@gmail.com", "marathalli", "123456789", "vims");
		createHospital("bangalore", "test@gmail.com", "marathalli", "123456789", "yashomati");
	}
	
	Hospital createHospital(String city, String emailId,
			String location, String mobileno, String name) {
		Hospital hospitalDto = new Hospital();
		hospitalDto.setCity(city);
		hospitalDto.setEmailId(emailId);
		hospitalDto.setLocation(location);
		hospitalDto.setMobileNo(mobileno);
		hospitalDto.setName(name);
		
		HashSet<String> adminIds = new HashSet<String>();
		
		User doc = userRepository.findOneByName("hosp");
		adminIds.add(doc.getId());
		hospitalDto.setAdminIds(adminIds);
		
		hospitalRepository.save(hospitalDto);
		return hospitalDto;	
	}

	User createDoctor(String name, int age, Speciality speciality,
			String degree, String emailId, int experience, String mobileno, Sex sex) {
		HashSet<Authority> auths = new HashSet<Authority>();
		Authority docAuth = authorityRepository.findOne("ROLE_DOCTOR");
		Authority hospAuth = authorityRepository.findOne("ROLE_HOSPITAL_ADMIN");
        Authority userAuth = authorityRepository.findOne("ROLE_USER");
        
        HashSet<Speciality> specialities = new HashSet<Speciality>();
        specialities.add(speciality);
        
        HashSet<String> degrees = new HashSet<String>();
        degrees.add(degree);
        
        //auths.add(userAuth);
        //auths.add(hospAuth);
        auths.add(docAuth);
        
		User user = userService.createUserInformation(name, "admin" , name, name,
				emailId, "en", auths);
        
		User doctorDto = user;
		doctorDto.setLogin(name);
		doctorDto.setName(name);
		doctorDto.setAge(age);
		doctorDto.setSpecialities(specialities);;
		doctorDto.setDegrees(degrees);
		doctorDto.setEmail(emailId);
		doctorDto.setExperience(experience);
		doctorDto.setMobileno(mobileno);
		doctorDto.setSex(sex);
		doctorDto.setId(user.getId());
		userRepository.save(doctorDto);
		
		return doctorDto;
	}
	
	
	void createDoctors() {
		createDoctor("doc1",40, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc1",45, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc2",41, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc3",42, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc4",43, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc5",44, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);

		createDoctor("doc6",40, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc7",41, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc8",42, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc9",43, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc10",44, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);

		createDoctor("doc11",40, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc12",41, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc13",42, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc14",43, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc15",44, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);

		createDoctor("doc16",40, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc17",41, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc18",42, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc19",43, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("doc20",44, Speciality.DENTIST, "md", "test@gmail.com", 10, "123456789", Sex.MALE);

		
		createDoctor("gyn1",40, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn2",41, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn3",42, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn4",43, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn5",44, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);

		createDoctor("gyn6",40, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn7",41, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn8",42, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn9",43, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn10",44, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);

		createDoctor("gyn11",40, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn12",41, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn13",42, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn14",43, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn15",44, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);

		createDoctor("gyn16",40, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn17",41, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn18",42, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn19",43, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);
		createDoctor("gyn20",44, Speciality.GYNIC, "md", "test@gmail.com", 10, "123456789", Sex.MALE);

	}

}
