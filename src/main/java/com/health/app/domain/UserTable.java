package com.health.app.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document()
public class UserTable {
	@Id
	private String id;

	String userName;
	//@JsonIgnore
	//should be invisible ot the user
	String userId;
	String userFullName;
	@JsonIgnore
	String passWord;
	String uuid;
	Sex sex;
	Date dateOfBirth;

	Date createOrPasswordUpdated;
	Date passWordExpiryDate;

	List<Role> roles = new ArrayList<Role>();

	String mobileNo;
	String emailId;
	Blood blood;
	boolean isFamilyGuaridan;
	List<String> familyMembers = new ArrayList<String>();
	String guardian;
	List<String> writePermittedDoctors = new ArrayList<String>();
	TypeOfCommunication toc;
	List<String> hospitalIds = new ArrayList<String>();
	List<String> labIds = new ArrayList<String>();

	IdProofs idProof;
	String identityProofValue;
	Address address;
	boolean accountIsActivated;
	
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isAccountIsActivated() {
		return accountIsActivated;
	}

	public void setAccountIsActivated(boolean accountIsActivated) {
		this.accountIsActivated = accountIsActivated;
	}

	public UserTable(String userName, String userId, String userFullName,
			String passWord, String uuid, Sex sex,
			Date createOrPasswordUpdated, List<Role> roles, String mobileNo,
			String emailId, Blood blood, boolean isFamilyGuaridan,
			List<String> familyMembers, TypeOfCommunication toc,
			IdProofs idProof, String identityProofValue, Address address) {
		this.userName = userName;
		this.userId = userId;
		this.userFullName = userFullName;
		this.passWord = passWord;
		this.uuid = uuid;
		this.sex = sex;
		this.createOrPasswordUpdated = createOrPasswordUpdated;
		this.roles = roles;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.blood = blood;
		this.isFamilyGuaridan = isFamilyGuaridan;
		this.familyMembers = familyMembers;
		this.toc = toc;
		this.idProof = idProof;
		this.identityProofValue = identityProofValue;
		this.address = address;
	}

	public UserTable() {
	}

	public String getGuardian() {
		return guardian;
	}

	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public IdProofs getIdProof() {
		return idProof;
	}

	public void setIdProof(IdProofs idProof) {
		this.idProof = idProof;
	}

	public String getIdentityProofValue() {
		return identityProofValue;
	}

	public void setIdentityProofValue(String identityProofValue) {
		this.identityProofValue = identityProofValue;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public List<String> getHospitalIds() {
		return hospitalIds;
	}

	public void setHospitalIds(List<String> hospitalIds) {
		this.hospitalIds = hospitalIds;
	}

	public List<String> getLabIds() {
		return labIds;
	}

	public void setLabIds(List<String> labIds) {
		this.labIds = labIds;
	}

	public TypeOfCommunication getToc() {
		return toc;
	}

	public void setToc(TypeOfCommunication toc) {
		this.toc = toc;
	}

	public Blood getBlood() {
		return blood;
	}

	public void setBlood(Blood blood) {
		this.blood = blood;
		
	}
	

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setCommunicationOption(TypeOfCommunication communicationOption) {
		this.blood.typeOfCommunication = communicationOption;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreateOrPasswordUpdated() {
		return createOrPasswordUpdated;
	}

	public void setCreateOrPasswordUpdated(Date createOrPasswordUpdated) {
		this.createOrPasswordUpdated = createOrPasswordUpdated;
	}

	public Date getPassWordExpiryDate() {
		return passWordExpiryDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setPassWordExpiryDate(Date passWordExpiryDate) {
		this.passWordExpiryDate = passWordExpiryDate;
	}

	public void setBlooodGroup(BloodGroup bg) {
		this.blood.bloodGroup = bg;

	}

	public void setLastBloodDonationDate(Date date) {
		this.blood.lastBloodDonationDate = new Date();
	}

	public boolean isFamilyGuaridan() {
		return isFamilyGuaridan;
	}

	public void setFamilyGuaridan(boolean isFamilyGuaridan) {
		this.isFamilyGuaridan = isFamilyGuaridan;
	}

	public List<String> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<String> familyMembers) {
		this.familyMembers = familyMembers;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public UserTable(String userName, String passWord, String uuid,
			List<Role> role) {

		this.userName = userName;
		this.passWord = passWord;
		this.uuid = uuid;
		this.roles = role;
	}

	public UserTable(String userName, String passWord, List<Role> role) {
		this.userName = userName;
		this.passWord = passWord;
		this.roles = role;
		/* TODO needs to add the location */
		this.uuid = String.valueOf(new Date().getTime());
	}

	public List<String> getWritePermittedDoctors() {
		return writePermittedDoctors;
	}

	public void setWritePermittedDoctors(List<String> writePermittedDoctors) {
		this.writePermittedDoctors = writePermittedDoctors;
	}	
	
}
