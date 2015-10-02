package com.health.app.domain;

public class Address {

	String city;

	String area;

	String pincode;

	String street;

	String houseNo;

	double location[] = new double[2];

	public Address() {

	}

	public Address(String city, String area, String pincode, String street,
			String houseNo, double[] location) {
		this.city = city.toLowerCase();
		this.area = area.toLowerCase();
		this.pincode = pincode.toLowerCase();
		this.street = street.toLowerCase();
		this.houseNo = houseNo.toLowerCase();
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city.toLowerCase();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area.toLowerCase();
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode.toLowerCase();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street.toLowerCase();
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo.toLowerCase();
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
	}
}
