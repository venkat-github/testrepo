package com.test.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


public class LabTestDto implements Serializable {

	private String id;

    String name;
    
    String code;
    
    String description;
    
    int price;
    
    int daysForReport;
    
    int hoursForReport;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LabTestDto ratingDTO = (LabTestDto) o;

        if ( ! Objects.equals(id, ratingDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
                "id=" + id +
                '}';
    }
}
