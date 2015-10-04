package com.test.app.repository;

import org.elasticsearch.common.joda.time.LocalDateTime;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.health.app.domain.OneTimePassword;
import com.health.app.domain.OneTimePasswordStore;
import com.test.app.domain.User;

@Service
public class OneTimePasswordStoreHelperFunctions {
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	UniqueIdRepositoryHelper urh;

	@Autowired
	HospitalRepository hr;

	@Autowired
	OneTimePasswordStoreRepository otpr;

	@Autowired
	UserRepository userRepo;
	
	@SuppressWarnings("deprecation")
	public String generateStoreOTP(String mobileNo) {
		
		OneTimePasswordStore otps = new OneTimePasswordStore();
		
		String otp = OneTimePassword.generateOtp(4);
		otps.setOtp(otp);
		User user = userRepo.findOneByMobileno(mobileNo);
		
		otps.setUserId(user.getId());
		otps.setMobileNo(mobileNo);
		
		
		LocalDateTime ldt = new LocalDateTime();
		ldt.plusMinutes(15);
		
		otps.setValidity(ldt);
		
		mongoTemplate.save(otps);
		return otp;
	}

	public void deleteOTP(String mobileNo) {
		otpr.deleteByMobileNo(mobileNo);
	}

	public boolean verifOTP(String mobileNo, String otp) {
		try {
			OneTimePasswordStore usr = otpr.findOneByMobileNo(mobileNo);
			LocalDateTime dt = new LocalDateTime();
			
			if (otp.equals(usr.getOtp())) {
				if (usr.getValidity().isAfter(dt)) {
					deleteOTP(mobileNo);
					return true;
				}
			}
		} catch(Throwable e) {
		}
		return false;
	}

}
