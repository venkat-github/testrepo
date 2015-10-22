package com.test.app.domain;

public class BookingStats {
	int totalSlots;
	int bookedSlots;
	int attendedSlots;
	int attendedFollowupSlots;
	int attendedFreshBookingSlots;
	int attendedSLotsWithoutBooking;
	public int getTotalSlots() {
		return totalSlots;
	}
	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}
	public int getBookedSlots() {
		return bookedSlots;
	}
	public void setBookedSlots(int bookedSlots) {
		this.bookedSlots = bookedSlots;
	}
	public int getAttendedSlots() {
		return attendedSlots;
	}
	public void setAttendedSlots(int attendedSlots) {
		this.attendedSlots = attendedSlots;
	}
	public int getAttendedFollowupSlots() {
		return attendedFollowupSlots;
	}
	public void setAttendedFollowupSlots(int attendedFollowupSlots) {
		this.attendedFollowupSlots = attendedFollowupSlots;
	}
	public int getAttendedFreshBookingSlots() {
		return attendedFreshBookingSlots;
	}
	public void setAttendedFreshBookingSlots(int attendedFreshBookingSlots) {
		this.attendedFreshBookingSlots = attendedFreshBookingSlots;
	}
	public int getAttendedSLotsWithoutBooking() {
		return attendedSLotsWithoutBooking;
	}
	public void setAttendedSLotsWithoutBooking(int attendedSLotsWithoutBooking) {
		this.attendedSLotsWithoutBooking = attendedSLotsWithoutBooking;
	}
}
