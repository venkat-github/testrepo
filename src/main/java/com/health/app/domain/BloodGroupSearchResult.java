package com.health.app.domain;

public class BloodGroupSearchResult {
	String name;
	String mobileNumber;
	String mailId;
	BloodGroup bg;
	TypeOfCommunication toc;
	public BloodGroupSearchResult(String name, String mobileNumber,
			String mailId, BloodGroup bg, TypeOfCommunication toc) {
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.mailId = mailId;
		this.bg = bg;
		this.toc = toc;
	}
	public BloodGroupSearchResult() {

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public BloodGroup getBg() {
		return bg;
	}
	public void setBg(BloodGroup bg) {
		this.bg = bg;
	}
	public TypeOfCommunication getToc() {
		return toc;
	}
	public void setToc(TypeOfCommunication toc) {
		this.toc = toc;
	}
	
	
}
