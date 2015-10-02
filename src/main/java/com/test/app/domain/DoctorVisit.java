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
import java.util.Objects;


/**
 * A DoctorVisitDTO.
 */
@Document(collection = "DOCTOR_VISIT")
public class DoctorVisit implements Serializable {

    @Id
    String id;
    
    String name;
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Field("user_id")
    String userId;

    String userMobileNo;
    String userName;
    
    @Field("doctor_id")
    String doctorId;
    
    @Field("hospital_id")
    String hospitalId;
    
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("date")
    LocalDate date;

    @Field("slot")
    String slot;

    @Field("consult_id")
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

        DoctorVisit doctorVisitDTO = (DoctorVisit) o;

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
