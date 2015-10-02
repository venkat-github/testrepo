package com.health.app.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Hospital {
	@Id
	private String id;

	String hospitalName;
	String hospitalId;

	Address address;
	List<String> adminUsers = new ArrayList<String>();
	List<String> doctors = new ArrayList<String>();
	List<String> nonAdminUsers = new ArrayList<String>();
	List<Speciality> specialities = new ArrayList<Speciality>();

	String hospitalRegId;

	/* TODO logo */
	String caption;
	int noHoursOfWorkingPerDay;
	int noofDaysPerWeek;
	List<String> phoneNumbers = new ArrayList<String>();
	List<String> emergencyContactNumbers = new ArrayList<String>();
	List<String> emergencyServices = new ArrayList<String>();
	List<String> listOfDoctors = new ArrayList<String>();
	/* TODO list of nurses */
	List<String> listOfNurses = new ArrayList<String>();

	public List<String> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<String> doctors) {
		this.doctors = doctors;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public List<String> getAdminUsers() {
		return adminUsers;
	}

	public void setAdminUsers(List<String> adminUsers) {
		this.adminUsers = adminUsers;
	}

	public List<String> getNonAdminUsers() {
		return nonAdminUsers;
	}

	public void setNonAdminUsers(List<String> nonAdminUsers) {
		this.nonAdminUsers = nonAdminUsers;
	}

	public List<Speciality> getSpecialities() {
		return specialities;
	}

	public void setSpecialities(List<Speciality> specialities) {
		this.specialities = specialities;
	}

	public String getHospitalRegId() {
		return hospitalRegId;
	}

	public void setHospitalRegId(String hospitalRegId) {
		this.hospitalRegId = hospitalRegId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getNoHoursOfWorkingPerDay() {
		return noHoursOfWorkingPerDay;
	}

	public void setNoHoursOfWorkingPerDay(int noHoursOfWorkingPerDay) {
		this.noHoursOfWorkingPerDay = noHoursOfWorkingPerDay;
	}

	public int getNoofDaysPerWeek() {
		return noofDaysPerWeek;
	}

	public void setNoofDaysPerWeek(int noofDaysPerWeek) {
		this.noofDaysPerWeek = noofDaysPerWeek;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<String> getEmergencyContactNumbers() {
		return emergencyContactNumbers;
	}

	public void setEmergencyContactNumbers(List<String> emergencyContactNumbers) {
		this.emergencyContactNumbers = emergencyContactNumbers;
	}

	public List<String> getEmergencyServices() {
		return emergencyServices;
	}

	public void setEmergencyServices(List<String> emergencyServices) {
		this.emergencyServices = emergencyServices;
	}

	public List<String> getListOfDoctors() {
		return listOfDoctors;
	}

	public void setListOfDoctors(List<String> listOfDoctors) {
		this.listOfDoctors = listOfDoctors;
	}

	public List<String> getListOfNurses() {
		return listOfNurses;
	}

	public void setListOfNurses(List<String> listOfNurses) {
		this.listOfNurses = listOfNurses;
	}

	public Hospital() {

	}

	public Hospital(Address address, String hospitalName, String hospitalRegId,
			int noHoursOfWorkingPerDay, int noofDaysPerWeek,
			List<String> phoneNumbers, List<String> emergencyContactNumbers,
			List<String> emergencyServices) {
		this.address = address;
		this.hospitalName = hospitalName;
		this.hospitalId = hospitalName;
		this.hospitalRegId = hospitalRegId;
		this.noHoursOfWorkingPerDay = noHoursOfWorkingPerDay;
		this.noofDaysPerWeek = noofDaysPerWeek;
		this.phoneNumbers = phoneNumbers;
		this.emergencyContactNumbers = emergencyContactNumbers;
		this.emergencyServices = emergencyServices;
	}

	public Hospital(Address address, String hospitalName, String hospitalId,
			List<Speciality> specialities, String hospitalRegId) {
		this.address = address;
		this.hospitalName = hospitalName;
		this.hospitalId = hospitalName;
		this.specialities = specialities;
		this.hospitalRegId = hospitalRegId;
	}

	public Hospital(String hospitalName, Address address) {

		this.hospitalName = hospitalName;
		this.address = address;
		this.hospitalId = hospitalName;
	}

}
