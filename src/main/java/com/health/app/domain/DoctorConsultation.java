package com.health.app.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class DoctorConsultation {

	@Id
	private String id;

	Address address;
	DoctorFeildsForSearch doctor;
	String hospitalId;

	String date;
	int startHour;
	int endHour;
	int slotDurationInMins;
	int totalSlots;
	int usedSlots;
	long setSlotBits;

	int breakTimeHour;
	int breakTimeMinute;
	int breakDurationInMins;
	int consultationFee;
	double[] location = new double[2];

	public String getHospitalId() {
		return hospitalId;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public DoctorFeildsForSearch getDoctor() {
		return doctor;
	}

	public String getHospital() {
		return hospitalId;
	}

	public void setHospital(String hospitalId) {
		this.hospitalId = hospitalId;

	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getSlotDurationInMins() {
		return slotDurationInMins;
	}

	public void setSlotDurationInMins(int slotDurationInMins) {
		this.slotDurationInMins = slotDurationInMins;
	}

	public int getTotalSlots() {
		return totalSlots;
	}

	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	public int getUsedSlots() {
		return usedSlots;
	}

	public void setUsedSlots(int usedSlots) {
		this.usedSlots = usedSlots;
	}

	public long getSetSlotBits() {
		return setSlotBits;
	}

	public void setSetSlotBits(long setSlotBits) {
		this.setSlotBits = setSlotBits;
	}

	public int getBreakTimeHour() {
		return breakTimeHour;
	}

	public void setBreakTimeHour(int breakTimeHour) {
		this.breakTimeHour = breakTimeHour;
	}

	public int getBreakTimeMinute() {
		return breakTimeMinute;
	}

	public void setBreakTimeMinute(int breakTimeMinute) {
		this.breakTimeMinute = breakTimeMinute;
	}

	public int getBreakDurationInMins() {
		return breakDurationInMins;
	}

	public void setBreakDurationInMins(int breakDurationInMins) {
		this.breakDurationInMins = breakDurationInMins;
	}

	public int getConsultationFee() {
		return consultationFee;
	}

	public void setConsultationFee(int consultationFee) {
		this.consultationFee = consultationFee;
	}

	public DoctorConsultation(DoctorFeildsForSearch doctor, String hospitalId,
			Date date, int startHour, int endHour, int slotDurationInMins,
			int totalSlots, int usedSlots, long setSlotBits, int breakTimeHour,
			int breakTimeMinute, int breakDurationInMins, int consultationFee) {

		DatabaseDateWithoutime localDate = new DatabaseDateWithoutime(date);
		this.date = localDate.getDateInSpecificFormat("yyyy/MM/dd");
		this.setDoctor(doctor);
		this.setHospitalId(hospitalId);
		this.startHour = startHour;
		this.endHour = endHour;
		this.slotDurationInMins = slotDurationInMins;
		this.totalSlots = totalSlots;
		this.usedSlots = usedSlots;
		this.setSlotBits = setSlotBits;
		this.breakTimeHour = breakTimeHour;
		this.breakTimeMinute = breakTimeMinute;
		this.breakDurationInMins = breakDurationInMins;
		this.consultationFee = consultationFee;
	}

	public DoctorConsultation(DoctorFeildsForSearch doctor, Date date,
			String hospitalId, int startHour, int endHour,
			int slotDurationInMins, int totalSlots, int usedSlots,
			long setSlotBits, int consultationFee) {
		DatabaseDateWithoutime localDate = new DatabaseDateWithoutime(date);
		this.date = localDate.getDateInSpecificFormat("yyyy/MM/dd");
		this.setDoctor(doctor);
		this.setHospitalId(hospitalId);
		this.startHour = startHour;
		this.endHour = endHour;
		this.slotDurationInMins = slotDurationInMins;
		this.totalSlots = totalSlots;
		this.usedSlots = usedSlots;
		this.setSlotBits = setSlotBits;
		this.consultationFee = consultationFee;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public DoctorConsultation(DoctorFeildsForSearch doctor, String hospital,
			int totalSlots, int consultationFee, Date date) {
		DatabaseDateWithoutime localDate = new DatabaseDateWithoutime(date);
		this.date = localDate.getDateInSpecificFormat("yyyy/MM/dd");
		this.setDoctor(doctor);
		this.setHospitalId(hospital);
		this.totalSlots = totalSlots;
		this.consultationFee = consultationFee;
	}

	public DoctorConsultation() {

	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
		this.location[0] = address.location[0];
		this.location[1] = address.location[1];
	}

	public void setDate(Date date) {
		DatabaseDateWithoutime localDate = new DatabaseDateWithoutime(date);
		this.date = localDate.getDateInSpecificFormat("yyyy/MM/dd");
	}

	public void setDoctor(DoctorFeildsForSearch doctor2) {
		this.doctor = doctor2;
	}

}
