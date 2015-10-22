package com.test.app.repository;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.boon.criteria.Update;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.test.app.domain.BookingStats;
import com.test.app.domain.BookingStatsTable;
import com.test.app.domain.enumeration.StatsUpdate;


@Service
public class BookingStatsTableRepositoryHelperFunctions {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	boolean updateAllowed(LocalDate date) {
		int dayOfTheYear = date.getDayOfYear();
		int currentDayOfTheYear = new LocalDate().getDayOfYear();
		if (dayOfTheYear < currentDayOfTheYear) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateRecordDoctor(String hospitalId, String doctorId,
			LocalDate date, StatsUpdate update, boolean increment, int value) {

		if (doctorId == null || (updateAllowed(date) == false)) {
			return false;
		}
		Criteria criteria = where("doctorId").is(doctorId).and("year")
				.is(date.getYear());

		if (hospitalId == null) {
			hospitalId = doctorId;
		}
		int valueToBeincremented = 0;
		criteria = criteria.and("hospitalId").is(hospitalId);
		int dayOfTheYear = date.getDayOfYear();
		if (increment == true) {
			valueToBeincremented = value;
		} else {
			valueToBeincremented = -value;
		}
		List<BookingStatsTable> result = mongoTemplate.find(query(criteria), BookingStatsTable.class);

		if (result.size() != 1) {
			return false;
		}
		result.get(0).updateStats(dayOfTheYear, update, valueToBeincremented);
		mongoTemplate.save(result.get(0));
		return true;
	}

	public List<BookingStats> getBooksStatsDoctor(String hospitalId,
			String doctorId, LocalDate startDate, LocalDate endDate) {
		int index = 0;
		List<BookingStats> result = new ArrayList<BookingStats>();

		if (doctorId == null) {
			return null;
		}

		if (hospitalId == null) {
			hospitalId = doctorId;
		}
		Criteria criteria = where("doctorId").is(doctorId).and("hospitalId")
				.is(hospitalId);

		int fromYear = startDate.getYear();
		int toYear = endDate.getYear();
		for (int i = fromYear; i <= toYear; i++) {
			criteria = criteria.and("year").is(i);
			List<BookingStatsTable> res = mongoTemplate.find(query(criteria),
					BookingStatsTable.class);
			int startDay = 1;
			int endDay = 366;

			if (i == startDate.getYear()) {
				startDay = startDate.getDayOfYear();
			} else if (i == endDate.getYear()) {
				endDay = endDate.getDayOfYear();
			}
			if (res.size() != 1) {
				return null;
			}
			for (int j = startDay; j < endDay; j++) {
				result.add(index, res.get(0).getStatsByIndex(j));
				index++;
			}
		}
		return result;
	}

	public List<BookingStats> getBooksStatsHospital(String hospitalId,
			LocalDate startDate, LocalDate endDate) {
		int index = 0;
		List<BookingStats> result = new ArrayList<BookingStats>();
		if (hospitalId == null) {
			return null;
		}
		Criteria criteria = where("hosiptalId").is(hospitalId);
		int fromYear = startDate.getYear();
		int toYear = endDate.getYear();
		for (int i = fromYear; i <= toYear; i++) {
			criteria = criteria.and("year").is(i);
			List<BookingStatsTable> res = mongoTemplate.find(query(criteria),
					BookingStatsTable.class);
			int startDay = 1;
			int endDay = 366;

			if (i == startDate.getYear()) {
				startDay = startDate.getDayOfYear();
			} else if (i == endDate.getYear()) {
				endDay = endDate.getDayOfYear();
			}
			for (int j = startDay; j < endDay; j++) {
				BookingStats stats = new BookingStats();
				for (int k = 0; k < res.size(); k++) {
					stats.setTotalSlots(stats.getTotalSlots() +  res.get(k).getStatsByIndex(j).getTotalSlots());
					stats.setBookedSlots(stats.getBookedSlots() +  res.get(k).getStatsByIndex(j).getBookedSlots());
					stats.setAttendedSlots(stats.getAttendedSlots() +  res.get(k).getStatsByIndex(j).getAttendedSlots());
					stats.setAttendedFollowupSlots(stats.getAttendedFollowupSlots() +  res.get(k).getStatsByIndex(j).getAttendedFollowupSlots());
					stats.setAttendedFreshBookingSlots(stats.getAttendedFreshBookingSlots() +  res.get(k).getStatsByIndex(j).getAttendedFreshBookingSlots());
					stats.setAttendedSLotsWithoutBooking(stats.getAttendedSLotsWithoutBooking() +  res.get(k).getStatsByIndex(j).getAttendedSLotsWithoutBooking());
				}
				result.add(index, stats);
				index++;
			}
		}
		return result;
	}
}