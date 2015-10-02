package com.health.app.domain;

public class DoctorPrescriptionMedicines {
	String medicineName;
	String drugName;
	int  noOfDaysOfUsage;
	TypeOfMedicine type;
	MedicineUsagePerDay usagePerDay;
	String otherComments;
	
	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public int getNoOfDaysOfUsage() {
		return noOfDaysOfUsage;
	}

	public void setNoOfDaysOfUsage(int noOfDaysOfUsage) {
		this.noOfDaysOfUsage = noOfDaysOfUsage;
	}

	public TypeOfMedicine getType() {
		return type;
	}

	public void setType(TypeOfMedicine type) {
		this.type = type;
	}

	public MedicineUsagePerDay getUsagePerDay() {
		return usagePerDay;
	}

	public void setUsagePerDay(MedicineUsagePerDay usagePerDay) {
		this.usagePerDay = usagePerDay;
	}

	public String getOtherComments() {
		return otherComments;
	}

	public void setOtherComments(String otherComments) {
		this.otherComments = otherComments;
	}

	public DoctorPrescriptionMedicines() {

	}
	
}
