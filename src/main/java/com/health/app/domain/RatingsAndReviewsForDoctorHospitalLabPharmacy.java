package com.health.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document()
public class RatingsAndReviewsForDoctorHospitalLabPharmacy {
	@Id
	String id;

	String userId;
	double avgRating;
	long totalNumberOfRatingsTillDate;

	List<ReviewRatings> reviewRatings = new ArrayList<ReviewRatings>();

	public RatingsAndReviewsForDoctorHospitalLabPharmacy() {

	}

	public String getUserId() {
		return userId;
	}

	public void setDoctorId(String userId) {
		this.userId = userId;
	}

	public List<ReviewRatings> getReviewRatings() {
		return reviewRatings;
	}

	public void setReviewRatings(List<ReviewRatings> reviewRatings) {
		this.reviewRatings = reviewRatings;
	}
}
