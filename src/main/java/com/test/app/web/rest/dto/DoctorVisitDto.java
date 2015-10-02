package com.test.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.app.domain.util.CustomLocalDateSerializer;
import com.test.app.domain.util.ISO8601LocalDateDeserializer;


public class DoctorVisitDto implements Serializable {

    String id;
    
    String userId;

    String userMobileNo;
    String userName;
    
    String doctorId;
    
    String hospitalId;
    
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    LocalDate date;

    String slot;

    String consultId;
    
    //TODO payment status
    
    public String getConsultId() {
		return consultId;
	}

	public void setConsultId(String consultId) {
		this.consultId = consultId;
	}

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DoctorVisitDto doctorVisitDTO = (DoctorVisitDto) o;

        if ( ! Objects.equals(id, doctorVisitDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DoctorVisitDTO{" +
                "id=" + id +
                ", userId='" + userId + "'" +
                ", doctorId='" + doctorId + "'" +
                ", hospitalId='" + hospitalId + "'" +
                ", date='" + date + "'" +
                '}';
    }
}
