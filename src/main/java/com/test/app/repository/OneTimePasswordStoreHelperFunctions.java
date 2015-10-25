package com.test.app.repository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.ReadablePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.test.app.domain.OneTimePassword;
import com.test.app.domain.OneTimePasswordStore;
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
		try {
		OneTimePasswordStore usr = otpr.findOneByMobileNo(mobileNo);
		String otp = OneTimePassword.generateOtp(4);
		System.out.println(otp);
		LocalDateTime ldt = new LocalDateTime();
		ldt.plusMinutes(15);

		if(usr == null ) {
			usr = new OneTimePasswordStore();
			usr.setMobileNo(mobileNo);
		}
		usr.setOtp(otp);
		usr.setValidity(ldt);
		mongoTemplate.save(usr);
		return otp;
		} catch (Throwable e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "1234";
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
				return true;
			}
		} catch (Throwable e) {
		}
		return false;
	}
}
