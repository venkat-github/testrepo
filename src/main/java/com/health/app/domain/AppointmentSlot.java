package com.health.app.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class AppointmentSlot {

	@Override
	public String toString() {
		return "AppointmentSlot [doctorId=" + doctorId + ", hospitalId="
				+ hospitalId + "]";
	}

	@Id
	private String id;

	UserTable user;
	
	String doctorId;
	 
	String hospitalId;
	
	Date time;
	
	int slotNumber;
	
	public AppointmentSlot() {
		
	}

	public UserTable getUser() {
		return user;
	}

	public void setUser(UserTable user) {
		this.user = user;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}
	
	
}
