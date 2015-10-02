package com.test.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


/**
 * A HospitalDTO.
 */
@Document(collection = "HOSPITAL")
public class Hospital implements Serializable {

    @Id
    String id;
    
    @Field("name")
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

        Hospital hospitalDTO = (Hospital) o;

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
