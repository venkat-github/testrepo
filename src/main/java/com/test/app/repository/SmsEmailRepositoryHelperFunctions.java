package com.test.app.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
/*
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;*/
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.health.app.domain.DatabaseDateWithoutime;
import com.health.app.domain.DoctorConsultation;
import com.health.app.domain.DoctorFeildsForSearch;
import com.health.app.domain.Languages;
import com.health.app.domain.Sex;
import com.health.app.domain.Speciality;
import com.health.app.domain.UserTable;

@Service
public class SmsEmailRepositoryHelperFunctions {
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	DoctorRepository dr;
	
	/*
	@Autowired
	UserTableRepository utr;
	
	@Autowired
	UserTableRepositoryHelperFunctions utrhf;
	
	@Autowired
	UniqueIdRepositoryHelper uth;
	public void sendSms(String from, String to, String body) {
		
	}
	public void sendEmail(String from, String to, String header, String body) {
		
	}
	
*/
}
