package com.health.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document()
public class Doctor {
	@Id
	private String id;
	// DoctorId = UserId
	@JsonIgnore
	String doctorId;
	String doctorName;
	String userName;
	List<String> doctorDegrees = new ArrayList<String>();
	Sex sex;
	List<Languages> languages = new ArrayList<Languages>();
	String primaryPhoneNumber;
	int age;
	int yearOfExperience;
	List<Speciality> speciality = new ArrayList<Speciality>();
	List<String> hospitalIds = new ArrayList<String>();
	boolean isActivated;
	public List<String> getHospitalIds() {
		return hospitalIds;
	}

	public void setHospitalIds(List<String> hospitalIds) {
		this.hospitalIds = hospitalIds;
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

	int availableThroughPhone;// 1:for yes, 0 : no, default no

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

	public int getAvailableThroughPhone() {
		return availableThroughPhone;
	}

	public void setAvailableThroughPhone(int availableThroughPhone) {
		this.availableThroughPhone = availableThroughPhone;
	}

	public List<Speciality> getSpeciality() {
		return speciality;
	}

	public void setSpeciality(List<Speciality> speciality) {
		this.speciality = speciality;
	}

	public Doctor(String doctorId, String doctorName,
			List<String> doctorDegrees, Sex sex, List<Languages> languages,
			String primaryPhoneNumber) {

		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.sex = sex;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.availableThroughPhone = 0;// default value
		this.doctorDegrees.addAll(doctorDegrees);
		this.languages.addAll(languages);
	}

	public Doctor(String doctorId, String doctorName,
			List<String> doctorDegrees, Sex sex, List<Languages> languages,
			String primaryPhoneNumber, int availableThroughPhone) {
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.sex = sex;
		this.languages = languages;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.availableThroughPhone = availableThroughPhone;
		this.doctorDegrees.addAll(doctorDegrees);
		this.languages.addAll(languages);
	}

	public Doctor(String doctorName, List<String> doctorDegrees, Sex sex,
			List<Languages> languages, String primaryPhoneNumber,
			int availableThroughPhone, int age) {
		// TODO
		this.doctorId = doctorName + String.valueOf(age);
		this.doctorName = doctorName;
		this.doctorDegrees.addAll(doctorDegrees);
		this.sex = sex;
		this.languages.addAll(languages);
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.availableThroughPhone = availableThroughPhone;
	}

	public Doctor() {

	}

	public Doctor(String id, String doctorId, String doctorName,
			List<String> doctorDegrees, Sex sex, List<Languages> languages,
			String primaryPhoneNumber, int age, int yearOfExperience,
			List<Speciality> speciality, int availableThroughPhone) {
		this.id = id;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.doctorDegrees = doctorDegrees;
		this.sex = sex;
		this.languages = languages;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.age = age;
		this.yearOfExperience = yearOfExperience;
		this.speciality = speciality;
		this.availableThroughPhone = availableThroughPhone;
	}

	public Doctor(String doctorName, List<String> doctorDegrees, Sex sex,
			int age, int yearOfExperience, List<Languages> languages,
			List<Speciality> speciality) {
		this.doctorId = doctorName + String.valueOf(age);
		this.doctorName = doctorName;
		this.doctorDegrees.addAll(doctorDegrees);
		this.sex = sex;
		this.age = age;
		this.yearOfExperience = yearOfExperience;
		this.languages.addAll(languages);
		this.speciality.addAll(speciality);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	

}
