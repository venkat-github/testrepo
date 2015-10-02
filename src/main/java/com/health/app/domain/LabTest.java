package com.health.app.domain;

import com.health.app.domain.TotalListTestCodesNames.TotalListOFLabTestCode;

public class LabTest {
	
	TotalListOFLabTestCode testNo;
	//same day or 1 day or 2 day or ... 
	String reportReadyOn;
	String sampleToReachBefore;
	int price;
	
	
	public TotalListOFLabTestCode getTestNo() {
		return testNo;
	}
	public void setTestNo(TotalListOFLabTestCode testNo) {
		this.testNo = testNo;
	}
	public String getReportReadyOn() {
		return reportReadyOn;
	}
	public void setReportReadyOn(String reportReadyOn) {
		this.reportReadyOn = reportReadyOn;
	}
	public String getSampleToReachBefore() {
		return sampleToReachBefore;
	}
	public void setSampleToReachBefore(String sampleToReachBefore) {
		this.sampleToReachBefore = sampleToReachBefore;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
