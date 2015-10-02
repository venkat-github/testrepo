package com.test.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SmsEmailRepositoryHelperFunctions {
	@Autowired
	MongoTemplate mongoTemplate;
	
	
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
