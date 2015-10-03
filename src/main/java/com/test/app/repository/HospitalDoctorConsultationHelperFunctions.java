package com.test.app.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.health.app.domain.DoctorConsultation;
import com.mongodb.WriteResult;
import com.test.app.domain.DoctorSchedule;
import com.test.app.domain.Hospital;
import com.test.app.domain.HospitalDoctorConsultaion;
import com.test.app.domain.User;
@Service
public class HospitalDoctorConsultationHelperFunctions {
	@Inject
	HospitalDoctorConsultaionRepository hdcr;
	@Inject
	DoctorScheduleRepository dsr;
	@Inject
	UserRepository ur;

	@Inject
	MongoTemplate mongoTemplate;
	@Inject
	DoctorScheduleRespositoryHelperFunctions dshrf;
	
	@Inject
	HospitalRepository hr;
	
	WriteResult RemoveAllPastRecordsByLocalDateBasedOnDoctorId(String doctorId) {
		
		Criteria criteria = where("doctorId").is(doctorId)
				.and("date").lte(new LocalDate().minusDays(1));

		WriteResult dc =  mongoTemplate.remove(query(criteria), HospitalDoctorConsultaion.class);
	
		return dc;
	}
	/*
   List<HospitalDoctorConsultaion> RemoveAllPastRecordsByLocalDate() {
		
		Criteria criteria = where("date".);

		List<HospitalDoctorConsultaion> dc = mongoTemplate.find(query(criteria),
				HospitalDoctorConsultaion.class);
	
		return dc;
	}*/
	@SuppressWarnings("deprecation")
	private int GetTimeFromString(String time) {
		int result = 0; 
		int temp = 0 ;
		int i = 0;
		String parts[] = time.split(":");
		return Integer.parseInt(parts[0])*60 + Integer.parseInt(parts[1]);		
	}
	public void AddNextDayConsultationRecordForDoctorAndHospital(String doctorId, String hospitalId, int days) {

		List<String> slots = new ArrayList<String>();;
		int startTime;
		int endTime;
		int startBreakTime;
		int endBreakTime;
		Criteria criteria = where("doctorId").is(doctorId)
				.and("date").lte(new LocalDate().minusDays(1));
		mongoTemplate.findAndRemove(query(criteria), HospitalDoctorConsultaion.class);
		DoctorSchedule ds = dshrf.FindDoctorScheduleByDoctorIdHospitalIdLocalDate(doctorId, hospitalId, days);
		if (ds == null ) {
			System.out.println(" Error: not able to find a schedule");
			return; 
		}
		HospitalDoctorConsultaion  hdc = new HospitalDoctorConsultaion();
		hdc.setDoctorId(doctorId);
		hdc.setHospitalId(hospitalId);
		hdc.setDoctorName(ds.getDoctorName());
		startTime = GetTimeFromString(ds.getStartTime());
		endTime = GetTimeFromString(ds.getEndTime());
		startBreakTime = GetTimeFromString(ds.getBreakStartTime());
		endBreakTime = GetTimeFromString(ds.getBreakEndTime());
		
		while( startTime < startBreakTime  ) {
			String temp = (int) startTime/60 + ":" + (int )startTime%60;
			slots.add(temp);
			startTime = startTime + ds.getSlotDuration();
		}
		startTime = endBreakTime;
		while( startTime < endTime  ) {
			String temp = (int) startTime/60 + ":" + (int )startTime%60;
			slots.add(temp);
			startTime = startTime + ds.getSlotDuration();
		}		
		hdc.setFreeSlots(slots);
		User user =  ur.findOneById(ds.getDoctorId());
		if (ds.getHospitalId() != null) {
			
			Hospital hospital = hr.findOneById(ds.getHospitalId());
			hdc.setLocation(hospital.getLocation());
			hdc.setHospitalName(hospital.getName());
			hdc.setCity(hospital.getCity());
		} else {
			hdc.setLocation(user.getLocation());
			hdc.setCity(user.getCity());
		}
		hdc.setName(user.getFullname());
		hdc.setSpeciality(user.getSpecialities());
		hdc.setExperience(user.getExperience());
		mongoTemplate.save(hdc);
	}
}
