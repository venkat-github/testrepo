package com.health.app.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OneTimePasswordStore {
@Id
private String id;
String userId;
String userName;
String otp;
int  validTill;
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getOtp() {
	return otp;
}
public void setOtp(String otp) {
	this.otp = otp;
}
public int getValidTill() {
	return validTill;
}
public void setValidTill(int i) {
	this.validTill = i;
}
public OneTimePasswordStore() {
}
}
