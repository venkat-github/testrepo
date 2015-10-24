package com.test.app.repository;
import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.test.app.domain.OneTimePasswordStore;



public interface OneTimePasswordStoreRepository 
	extends MongoRepository<OneTimePasswordStore, String> {
	 String deleteByMobileNo(String mobileNo);
	 OneTimePasswordStore findOneByMobileNo(String mobileNo);
	 
	 OneTimePasswordStore findOneByMobileNoAndOtp(String mobileNo, String otp);
	 
}
