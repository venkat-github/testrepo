package com.health.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document()
public class MedicalPrescriptionRecord {
	@Id
	private String id;
	
	@JsonIgnore
	String transactionId;
	String userName;
	@JsonIgnore
	String userId;
	String doctorId;
	String hospitalId;
	Date date;
	String doctorName;
	Date prescribedDate;
	boolean isVaccine;
	String vaccineName;
	Date nextVisitingDate;
	DoctorPrescriptionGeneralComments generalSymptoms;
	List<LabTest> labTests = new ArrayList<LabTest>();
	List<DoctorPrescriptionMedicines> medicines = new ArrayList<DoctorPrescriptionMedicines>();
	String generalComments;
	String dietPlan;
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public Date getPrescribedDate() {
		return prescribedDate;
	}

	public void setPrescribedDate(Date prescribedDate) {
		this.prescribedDate = prescribedDate;
	}

	public boolean isVaccine() {
		return isVaccine;
	}

	public void setVaccine(boolean isVaccine) {
		this.isVaccine = isVaccine;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public Date getNextVisitingDate() {
		return nextVisitingDate;
	}

	public void setNextVisitingDate(Date nextVisitingDate) {
		this.nextVisitingDate = nextVisitingDate;
	}

	public DoctorPrescriptionGeneralComments getGeneralSymptoms() {
		return generalSymptoms;
	}

	public void setGeneralSymptoms(DoctorPrescriptionGeneralComments generalSymptoms) {
		this.generalSymptoms = generalSymptoms;
	}

	public List<LabTest> getLabTests() {
		return labTests;
	}

	public void setLabTests(List<LabTest> labTests) {
		this.labTests = labTests;
	}

	public List<DoctorPrescriptionMedicines> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<DoctorPrescriptionMedicines> medicines) {
		this.medicines = medicines;
	}

	public String getGeneralComments() {
		return generalComments;
	}

	public void setGeneralComments(String generalComments) {
		this.generalComments = generalComments;
	}

	public String getDietPlan() {
		return dietPlan;
	}

	public void setDietPlan(String dietPlan) {
		this.dietPlan = dietPlan;
	}
	
	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public MedicalPrescriptionRecord() {
	}
}
