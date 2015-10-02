package com.test.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


public class UserDoctorVisitRecordDto implements Serializable {

    private String id;

    private String userId;
    
    private String visitId;
    
    private String symptoms;
    
    private String diagnosis;
    
    private String prescription;

    private String tests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDoctorVisitRecordDto userRecordDTO = (UserDoctorVisitRecordDto) o;

        if ( ! Objects.equals(id, userRecordDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserRecordDTO{" +
                "id=" + id +
                ", userId='" + userId + "'" +
                ", visitId='" + visitId + "'" +
                ", symptoms='" + symptoms + "'" +
                ", diagnosis='" + diagnosis + "'" +
                ", prescription='" + prescription + "'" +
                ", tests='" + tests + "'" +
                '}';
    }
}
