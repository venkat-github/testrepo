package com.health.app.domain;

public class UserDoctor {
	UserTable user;
	Doctor doctor;

	public UserTable getUser() {
		return user;
	}

	public void setUser(UserTable user) {
		this.user = user;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

}
