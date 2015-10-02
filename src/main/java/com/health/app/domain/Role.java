package com.health.app.domain;

public enum Role {
		HOSPITAL,
		HOSPITAL_ADMIN,//ADMIN can update the hospital page and create user 
		HOSPITAL_USER,//User has just read permossion there no write permissions
		
		LAB,
		LAB_ADMIN,
		LAB_USER,
		
		PATIENT,// Can read contents  or upload file
		DOCTOR,// can access patient profile and update based permissions provided by the patient
		PHARMACY_ADMIN,
		PHARMACY_USER,
		FAMILY_GUARDIAN,
		ADMIN// for the website
}

