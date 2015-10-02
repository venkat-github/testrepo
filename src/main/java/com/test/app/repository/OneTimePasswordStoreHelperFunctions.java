package com.test.app.repository;

	
	import java.util.Date;








import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;







/*
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.health.app.domain.OneTimePassword;
import com.health.app.domain.OneTimePasswordStore;
import com.health.app.domain.ReturnCodes;
import com.health.app.domain.UserTable;

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
		/*
		
		@SuppressWarnings("deprecation")
		public String generateStoreOTP(String userName) {
			OneTimePasswordStore otps = new OneTimePasswordStore();
			String userId =  utrh.getUserIdFromUserName(userName);
			String otp = new OneTimePassword().generateOtp(4);
			otps.setOtp(otp);
			otps.setUserId(userId);
			otps.setUserName(userName);
			otps.setValidTill(new Date().getDate()+10);
			mongoTemplate.save(otps);
			return otp;
		}
		public String generateStoreOTP1(String userName) {
			OneTimePasswordStore otps = new OneTimePasswordStore();
			String userId =  utrh.getUserIdFromUserName(userName);
			String otp = new OneTimePassword().generateOtp(4);
			otps.setOtp(otp);
			otps.setUserId(userId);
			otps.setUserName(userName);
			otps.setValidTill(new Date().getDate()+10);
			mongoTemplate.save(otps);
			return otp;
		}
		@SuppressWarnings("deprecation")
		public void deleteOTP(String userName){
			Criteria criteria = where("userName").is(userName);
			mongoTemplate.findAllAndRemove(query(criteria), OneTimePasswordStore.class);
		}
		@SuppressWarnings("deprecation")
		public  void verifOTP(String userName, String otp, Date date){
			Criteria criteria = where("userName").is(userName);
			criteria = criteria.and("otp").is(otp);
			criteria = criteria.and("validTill").lte(date.getDate());
			OneTimePasswordStore otps = mongoTemplate.findOne(query(criteria), OneTimePasswordStore.class);
			if (otps != null) {
				utrh.activeUserProfileUserName(userName);
				deleteOTP(userName);
			}else {
				return;
			}
		}
		public void sendOTP(String userName, String mobileNo){
			String otp = generateStoreOTP(userName);
			//TODO send sms to the registered
			return;
			
		}
		public ReturnCodes sendOTP(String userName){

	Criteria criteria = where("userName").is(userName);
	UserTable user = mongoTemplate.findOne(query(criteria), UserTable.class);
	if(user != null ) {
		sendOTP(userName, user.getMobileNo());
		return ReturnCodes.OTP_SEND_FAILED;
	}
	return ReturnCodes.OTP_SEND_SUCCESS;
		}
		
*/
}
