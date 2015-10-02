package com.health.app.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * this table is used to store doctor specific prescriptions, tests, symptoms,  and 
 * we are using this table to give more adaptive "auto complete" 
 * 
 */
@Document()
public class DoctorCache {
	@Id
	private BigInteger id;

	String doctorID;
	Speciality speciality;
	List<String> frequentlyUsedTablets = new ArrayList<String>();
	List<String> frequentlyPrescribedTests = new ArrayList<String>();
	List<String> generalSymptons = new ArrayList<String>();

	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public void setFrequentlyUsedTablets(List<String> frequentlyUsedTablets) {
		this.frequentlyUsedTablets = frequentlyUsedTablets;
	}

	public void setFrequentlyPrescribedTests(
			List<String> frequentlyPrescribedTests) {
		this.frequentlyPrescribedTests = frequentlyPrescribedTests;
	}

	public void setGeneralSymptons(List<String> generalSymptons) {
		this.generalSymptons = generalSymptons;
	}

}
