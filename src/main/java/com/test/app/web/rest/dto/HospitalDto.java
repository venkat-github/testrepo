package com.test.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.test.app.domain.Hospital;
import com.test.app.domain.HospitalService;
import com.test.app.domain.WorkingDay;
import com.test.app.domain.enumeration.Facility;


public class HospitalDto implements Serializable {

    String id;
    
    String name;
    
    String mobileNo;

    String emailId;

    String registrationId;

    String city;

    String location;

    String landmark;

    String zipcode;

    Set<String> doctorIds;
    Set<String> adminIds;
    Set<String> emergencyNos;

    Set<Facility> facilities;
    Set<HospitalService> emergencyServices;
    
    String caption;
    
    Set<String> photoIds;
    
    Set<String>	nurseIds;
    
    String startTime;
    
    String endTime;
    
    Set<WorkingDay>	workingDays;
    
    long langitude;
    long latitude;
    
    public HospitalDto() {
    	
    }
    public HospitalDto(Hospital user) {
		this.id = user.getId();
		this.name = user.getName();
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HospitalDto hospitalDTO = (HospitalDto) o;

        if ( ! Objects.equals(id, hospitalDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HospitalDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", mobileNo='" + mobileNo + "'" +
                ", emailId='" + emailId + "'" +
                ", city='" + city + "'" +
                ", location='" + location + "'" +
                ", zipcode='" + zipcode + "'" +
                '}';
    }
}
