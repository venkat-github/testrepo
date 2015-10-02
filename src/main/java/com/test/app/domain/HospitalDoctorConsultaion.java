package com.test.app.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.app.domain.util.CustomLocalDateSerializer;
import com.test.app.domain.util.ISO8601LocalDateDeserializer;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * A HospitalDoctorDTO4.
 */
@Document(collection = "CONSULTATION")
public class HospitalDoctorConsultaion implements Serializable {

    @Id
    private String id;

    String name;
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
    @Field("doctor_name")
    private String doctorName;

    private String hospitalName;

    @Field("speciality")
    private String speciality;

    @Field("experience")
    private Integer experience;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("date")
    private LocalDate date;

    @Field("free_slots")
    private List<String> freeSlots;

    @Field("occupied_slots")
    private List<String> occupiedSlots;
    
    @Field("degrees")
    private Set<String> degrees;
    
    @Field("doctor_id")
    private String doctorId;

    @Field("hospital_id")
    private String hospitalId;
    
    @Field("location")
    private String location;

    long langitude;
    long latitude;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String> getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(List<String> freeSlots) {
        this.freeSlots = freeSlots;
    }

    public List<String> getOccupiedSlots() {
        return occupiedSlots;
    }

    public void setOccupiedSlots(List<String> occupiedSlots) {
        this.occupiedSlots = occupiedSlots;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HospitalDoctorConsultaion hospitalDoctorDTO4 = (HospitalDoctorConsultaion) o;

        if ( ! Objects.equals(id, hospitalDoctorDTO4.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HospitalDoctorDTO4{" +
                "id=" + id +
                ", doctorName='" + doctorName + "'" +
                ", speciality='" + speciality + "'" +
                ", experience='" + experience + "'" +
                ", date='" + date + "'" +
                ", freeSlots='" + freeSlots + "'" +
                ", occupiedSlots='" + occupiedSlots + "'" +
                ", doctorId='" + doctorId + "'" +
                ", hospitalId='" + hospitalId + "'" +
                ", location='" + location + "'" +
                '}';
    }
}
