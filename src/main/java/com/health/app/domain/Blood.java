package com.health.app.domain;

import java.util.Date;

public class Blood {
	BloodGroup bloodGroup;
	Date lastBloodDonationDate;
	/* In case of an emrgency */
	boolean readyToDonateBlood;
	TypeOfCommunication typeOfCommunication;
	boolean shareYourIdentity;

	public BloodGroup getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(BloodGroup bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Date getLastBloodDonationDate() {
		return lastBloodDonationDate;
	}

	public void setLastBloodDonationDate(Date lastBloodDonationDate) {
		this.lastBloodDonationDate = lastBloodDonationDate;
	}

	public boolean isReadyToDonateBlood() {
		return readyToDonateBlood;
	}

	public void setReadyToDonateBlood(boolean readyToDonateBlood) {
		this.readyToDonateBlood = readyToDonateBlood;
	}

	public TypeOfCommunication getTypeOfCommunication() {
		return typeOfCommunication;
	}

	public void setTypeOfCommunication(TypeOfCommunication typeOfCommunication) {
		this.typeOfCommunication = typeOfCommunication;
	}

	public boolean isShareYourIdentity() {
		return shareYourIdentity;
	}

	public void setShareYourIdentity(boolean shareYourIdentity) {
		this.shareYourIdentity = shareYourIdentity;
	}

	public Blood() {

	}

}
