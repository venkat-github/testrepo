package com.test.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
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

    
    public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public Set<String> getDoctorIds() {
		return doctorIds;
	}

	public void setDoctorIds(Set<String> doctorIds) {
		this.doctorIds = doctorIds;
	}

	public Set<String> getAdminIds() {
		return adminIds;
	}

	public void setAdminIds(Set<String> adminIds) {
		this.adminIds = adminIds;
	}

	public Set<String> getEmergencyNos() {
		return emergencyNos;
	}

	public void setEmergencyNos(Set<String> emergencyNos) {
		this.emergencyNos = emergencyNos;
	}

	public Set<Facility> getFacilities() {
		return facilities;
	}

	public void setFacilities(Set<Facility> facilities) {
		this.facilities = facilities;
	}

	public Set<HospitalService> getEmergencyServices() {
		return emergencyServices;
	}

	public void setEmergencyServices(Set<HospitalService> emergencyServices) {
		this.emergencyServices = emergencyServices;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Set<String> getPhotoIds() {
		return photoIds;
	}

	public void setPhotoIds(Set<String> photoIds) {
		this.photoIds = photoIds;
	}

	public Set<String> getNurseIds() {
		return nurseIds;
	}

	public void setNurseIds(Set<String> nurseIds) {
		this.nurseIds = nurseIds;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Set<WorkingDay> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(Set<WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}

	public long getLangitude() {
		return langitude;
	}

	public void setLangitude(long langitude) {
		this.langitude = langitude;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
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

	public void setAdminIds(HashSet<String> adminIds2) {
		adminIds = adminIds2;
		
	}
}
