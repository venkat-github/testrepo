package com.test.app.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
@Document(collection="REVIEW_RATING_FOR_ALL_ENTITIES")
public class ReviewAndRatingsForDoctorHospitalLadPharmacy {
		@Id
		BigInteger id;
		
		String userId;
		double avgRating;
		long totalNumberOfRatingsTillDate;
		int totalRecommendations;
		List<ReviewRatings> reviewRatings = new ArrayList<ReviewRatings>();

		public ReviewAndRatingsForDoctorHospitalLadPharmacy() {

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