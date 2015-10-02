package com.health.app.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class PatientDoctorHospitalAppiontment {
	@Id
	private String id;

	String patientId;
	String doctorId;
	String hospitalId;
	String date;
	int slot;
	PaymentTypes paymentMode;// payment done online or needs to do at hospitals
	PaymentStatus paymentStatus;// useful in case payment the payment is online
	int visitedDoctor;
	String transactionId;

	public int getVisitedDoctor() {
		return visitedDoctor;
	}

	public void setVisitedDoctor(int visitedDoctor) {
		this.visitedDoctor = visitedDoctor;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PatientDoctorHospitalAppiontment(String patientId, String doctorId,
			String hospitalId, Date date, int slot, PaymentTypes paymentMode,
			PaymentStatus paymentStatus) {
		DatabaseDateWithoutime dd = new DatabaseDateWithoutime(date);
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.hospitalId = hospitalId;
		this.date = dd.getDateInSpecificFormat("yyyy/MM/dd");
		this.slot = slot;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.visitedDoctor = 0;
	}

	public PatientDoctorHospitalAppiontment(String patientId, String doctorId,
			String hospitalId, Date date, int slot) {
		DatabaseDateWithoutime dd = new DatabaseDateWithoutime(date);
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.hospitalId = hospitalId;
		this.date = dd.getDateInSpecificFormat("yyyy/MM/dd");
		this.slot = slot;
		this.paymentMode = PaymentTypes.OFFLINE;
		this.paymentStatus = PaymentStatus.OFFLINE_PAYMENT;
		this.visitedDoctor = 0;
	}

	public PatientDoctorHospitalAppiontment(String patientId, String doctorId,
			String hospitalId, String date, int slot) {
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.hospitalId = hospitalId;
		this.date = date;
		this.slot = slot;
		this.paymentMode = PaymentTypes.OFFLINE;
		this.paymentStatus = PaymentStatus.OFFLINE_PAYMENT;
		this.visitedDoctor = 0;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public PaymentTypes getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentTypes paymentMode) {
		this.paymentMode = paymentMode;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public PatientDoctorHospitalAppiontment() {

	}

}
