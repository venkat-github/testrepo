package com.health.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Lab {
	String labName;
	Address address;
	String labId;
	List<LabTest>  labtest = new ArrayList<LabTest>();
	//list of lab technicians who can access patient data
	List<String> technicians = new ArrayList<String>();
	
	public String getLabId() {
		return labId;
	}
	public void setLabId(String labId) {
		this.labId = labId;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<LabTest> getLabtest() {
		return labtest;
	}
	public void setLabtest(List<LabTest> labtest) {
		this.labtest = labtest;
	}
	public List<String> getTechnicians() {
		return technicians;
	}
	public void setTechnicians(List<String> technicians) {
		this.technicians = technicians;
	}
	public Lab(String labName, Address address, List<LabTest> labtest,
			List<String> technicians) {

		this.labName = labName;
		this.address = address;
		this.labtest = labtest;
		this.technicians = technicians;
	}
	public Lab() {

	}
	
	
}
