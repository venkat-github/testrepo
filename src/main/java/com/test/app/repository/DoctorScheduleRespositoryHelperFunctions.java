package com.test.app.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;
import com.test.app.domain.DoctorSchedule;
import com.test.app.domain.HospitalDoctorConsultaion;

@Service
public class DoctorScheduleRespositoryHelperFunctions {
	@Inject
	HospitalDoctorConsultaionRepository hdcr;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Inject
	DoctorScheduleRepository dsr;
	
	public DoctorSchedule findDoctorScheduleByDoctorIdHospitalIdLocalDate (String doctorId, String hospitalId,
			LocalDate startDate, int days) {
		Criteria criteria = where("doctorId").is(doctorId).and("startDate").
				lte(startDate.plusDays(days)).and("endDate").gte(startDate.plusDays(days));
		if(hospitalId != null) {
			criteria = criteria.and("hospitalId").is(hospitalId);
		}
		DoctorSchedule dc =  mongoTemplate.findOne(query(criteria), DoctorSchedule.class);
		return dc;
	}

	public DoctorSchedule findDoctorScheduleByDateAndSpecialityAndLocation (
			String doctorId, String hospitalId,
			LocalDate startDate, int days) {
		Criteria criteria = where("doctorId").is(doctorId).and("startDate").
				lte(startDate.plusDays(days)).and("endDate").gte(startDate.plusDays(days));

		if(hospitalId != null) {
			criteria = criteria.and("hospitalId").is(hospitalId);
		}
		DoctorSchedule dc =  mongoTemplate.findOne(query(criteria), DoctorSchedule.class);
	
		return dc;
	}

}
