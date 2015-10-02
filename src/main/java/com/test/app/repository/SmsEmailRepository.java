package com.test.app.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.health.app.domain.SmsEmail;

public interface SmsEmailRepository  extends 
	MongoRepository<SmsEmail, String> {

}
