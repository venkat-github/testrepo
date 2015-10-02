package com.test.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;


/**
 * A RatingDTO.
 */
@Document(collection = "RATING")
public class Rating implements Serializable {

    @Id
    private String id;


    
    @Field("user_id")
    private String userId;


    
    @Field("doctor_id")
    private String doctorId;


    
    @Field("comments")
    private String comments;


    
    @Field("rating")
    private Integer rating;

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Rating ratingDTO = (Rating) o;

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
                ", userId='" + userId + "'" +
                ", doctorId='" + doctorId + "'" +
                ", comments='" + comments + "'" +
                ", rating='" + rating + "'" +
                '}';
    }
}
