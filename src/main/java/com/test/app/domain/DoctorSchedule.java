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
@Document(collection = "DOCTOR_SCHEDULE")
public class DoctorSchedule implements Serializable {

    @Id
    private String id;

    private String doctorName;
    
    private String doctorId;
    
    private String hospitalName;
    
    private String hospitalId;
    
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("startdate")
    private LocalDate startDate;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("enddate")
    private LocalDate endDate;

    int fees;
    
    int slotDuration;
    
    String startTime;
    String breakStartTime;
    String breakEndTime;
    String endTime;
    
    Set<WorkingDay> workingDays;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DoctorSchedule hospitalDoctorDTO4 = (DoctorSchedule) o;

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
                "id=" + id ;
    }
}
