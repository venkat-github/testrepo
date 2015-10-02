package com.test.app.repository;
import static org.springframework.data.mongodb.core.query.Criteria.where;
/*
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;*/
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.health.app.domain.Doctor;
import com.health.app.domain.ReturnCodes;
import com.health.app.domain.Role;
import com.health.app.domain.UserDoctor;
import com.health.app.domain.UserTable;
@Service
public class DoctorRepositoryHelperFunctions {
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	DoctorRepository dr;
	
	@Autowired
	UniqueIdRepositoryHelper uth;
	/*
	public ReturnCodes addDoctor(UserDoctor userDoctor){
		
		//first add user
		List<Role> role = new ArrayList<>();
		UserTable user = userDoctor.getUser();
		role.add(0, Role.PATIENT);
		role.add(1, Role.DOCTOR);
		user.setRoles(role);
		if(utrhf.addUser(user) == false){
			return ReturnCodes.FAILED;	
		}
	
		//then add into doctor feilds 
		String userId = utrhf.getUserIdFromUserName(user.getUserName());
		Doctor doctor = userDoctor.getDoctor();
		doctor.setDoctorId(userId);
		return ReturnCodes.SUCCESS;
	}
	
	public ReturnCodes addDoctor(Doctor doc) {
		Query query = query(where("doctorId").is(doc.getDoctorId()));
		Doctor doctor = mongoTemplate.findOne(query , Doctor.class);
		if(doctor != null)
			return ReturnCodes.DOCTOR_ADD_FAILED;
		mongoTemplate.save(doc);
		return ReturnCodes.DOCTOR_ADD_SUCCESS;
	}
	public ReturnCodes registerDoctor(String userName, String mobileNumber, String password){
		//creating the user  and saves in the table
		utrhf.registerUser(userName, mobileNumber, password);
		utrhf.addRole(userName, Role.DOCTOR);
		String userId = utrhf.getUserIdFromUserName(userName);
		if (userId == null){
			return ReturnCodes.DOCTOR_ADD_FAILED;
		}
		Doctor doc = new Doctor();
		doc.setDoctorId(userId);
		doc.setActivated(false);
		doc.setUserName(userName);
		addDoctor(doc);
		return ReturnCodes.DOCTOR_ADD_SUCCESS;
	}
	public ReturnCodes updateDoctorProfile(Doctor doc){
		
		return ReturnCodes.SUCCESS;
	}
	*/
}
