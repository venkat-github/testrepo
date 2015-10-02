package com.test.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


public class RatingDto implements Serializable {

    private String id;

    private String userId;

    private String doctorId;

    private String comments;

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

        RatingDto ratingDTO = (RatingDto) o;

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
