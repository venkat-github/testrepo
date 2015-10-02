package com.health.app.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LabTestCode {
	String labTestName;
	String description;
	String labTestCode;
	// per each test we give a unique id 
	int testNo;
	public int getTestNo() {
		return testNo;
	}
	public void setTestNo(int testNo) {
		this.testNo = testNo;
	}
	public String getLabTestName() {
		return labTestName;
	}
	public void setLabTestName(String labTestName) {
		this.labTestName = labTestName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLabTestCode() {
		return labTestCode;
	}
	public void setLabTestCode(String labTestCode) {
		this.labTestCode = labTestCode;
	}
	public LabTestCode(String labTestName, String description,
			String labTestCode, int testNo) {
	
		this.labTestName = labTestName;
		this.description = description;
		this.labTestCode = labTestCode;
		this.testNo = testNo;
	}

}
