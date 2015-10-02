package com.test.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;


/**
 * A RatingDTO.
 */
@Document(collection = "LAB_TEST")
public class LabTest implements Serializable {
    @Id
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

        LabTest ratingDTO = (LabTest) o;

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
