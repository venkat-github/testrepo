package com.health.app.domain;

import java.util.ArrayList;
import java.util.List;

public class DoctorFeildsForSearch {
	String doctorId;
	String doctorName;
	List<String> doctorDegrees = new ArrayList<String>();
	Sex sex;
	List<Languages> languages = new ArrayList<Languages>();
	String primaryPhoneNumber;
	int age;
	int yearOfExperience;
	List<Speciality> speciality = new ArrayList<Speciality>();

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public List<String> getDoctorDegrees() {
		return doctorDegrees;
	}

	public void setDoctorDegrees(List<String> doctorDegrees) {
		this.doctorDegrees = doctorDegrees;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public List<Languages> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Languages> languages) {
		this.languages = languages;
	}

	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}

	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getYearOfExperience() {
		return yearOfExperience;
	}

	public void setYearOfExperience(int yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}

	public List<Speciality> getSpeciality() {
		return speciality;
	}

	public void setSpeciality(List<Speciality> speciality) {
		this.speciality = speciality;
	}

	public DoctorFeildsForSearch() {
	}

}
