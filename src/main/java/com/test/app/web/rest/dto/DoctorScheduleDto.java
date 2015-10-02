package com.test.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.app.domain.WorkingDay;
import com.test.app.domain.util.CustomLocalDateSerializer;
import com.test.app.domain.util.ISO8601LocalDateDeserializer;


public class DoctorScheduleDto implements Serializable {

    private String id;

    private String doctorName;
    
    private String doctorId;
    
    private String hospitalName;
    
    private String hospitalId;
    
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
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

        DoctorScheduleDto hospitalDoctorDTO4 = (DoctorScheduleDto) o;

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
