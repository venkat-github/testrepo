package com.test.app.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="BOOKING_STATS_TABLE")
public class BookingStatsTable {
	String hospitalId;
	String doctorId;
	int year;
	BookingStats[] stats = new  BookingStats[367];
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public BookingStats[] getStats() {
		return stats;
	}
	public void setStats(BookingStats[] stats) {
		this.stats = stats;
	}
	public BookingStatsTable() {
		super();
	}

	public void updateStats(int index, StatsUpdate update, int value) {
		switch (update) {
		case TOTAL_SLOTS:
			stats[index].totalSlots = stats[index].totalSlots + value;
			break;
		case BOOKED_SLOTS:
			stats[index].bookedSlots = stats[index].bookedSlots + value;
			break;
		case ATTENDED_SLOTS:
			stats[index].attendedSlots = stats[index].attendedSlots + value;
			break;
		case ATTENDED_FOLLOWUP_SLOTS:
			stats[index].attendedFollowupSlots = stats[index].attendedFollowupSlots
					+ value;
			break;
		case ATTENDED_FRESH_SLOTS:
			stats[index].attendedFreshBookingSlots = stats[index].attendedFreshBookingSlots
					+ value;
			break;
		case ATTENDED_SLOTS_WITHOUT_BOOKING:
			stats[index].attendedSLotsWithoutBooking = stats[index].attendedSLotsWithoutBooking
					+ value;

			break;
		}
	}

	public BookingStats getStatsByIndex(int index) {
		if (index > 0 && index <= 366) {
			return stats[index];
		}
		return null;
	}
}