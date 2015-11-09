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
		if (time == null || time.trim().length() == 0  || !time.contains(":")) {
			return 1200;
		}
		int result = 0; 
		int temp = 0 ;
		int i = 0;
		String parts[] = time.split(":");
		return Integer.parseInt(parts[0])*60 + Integer.parseInt(parts[1]);		
	}
	public void addNextDayConsultationRecordForDoctorAndHospital(String doctorId, String hospitalId, 
			LocalDate startDate, LocalDate endDate) {
		
		int days = endDate.getDayOfYear() - startDate.getDayOfYear();//TODO
		for (int day = 0; day < days; day++) {
			addNextDayConsultationRecordForDoctorAndHospital(doctorId, hospitalId, startDate, day);
		}
	}
	
	@Inject
	UserRepository userRepo;
	
	public void addNextDayConsultationRecordForDoctorAndHospital(String doctorId, String hospitalId, 
			LocalDate startDate, int days) {

		List<String> slots = new ArrayList<String>();;
		int startTime;
		int endTime;
		int startBreakTime;
		int endBreakTime;
		Criteria criteria = where("doctorId").is(doctorId)
				.and("date").lte(new LocalDate().minusDays(1));
		mongoTemplate.findAndRemove(query(criteria), HospitalDoctorConsultaion.class);
		DoctorSchedule ds = dshrf.findDoctorScheduleByDoctorIdHospitalIdLocalDate(doctorId, hospitalId, startDate, days);
		if (ds == null ) {
			System.out.println(" Error: not able to find a schedule");
			return; 
		}
		HospitalDoctorConsultaion  hdc = new HospitalDoctorConsultaion();
		hdc.setDate(startDate.plusDays(days));
		hdc.setDoctorId(doctorId);
		hdc.setDoctorPhotoId(userRepo.findOneById(doctorId).getPhotoId());
		hdc.setDoctorName(ds.getDoctorName());
		hdc.setHospitalId(hospitalId);
		hdc.setDoctorName(ds.getDoctorName());
		startTime = GetTimeFromString(ds.getStartTime());
		endTime = GetTimeFromString(ds.getEndTime());
		startBreakTime = GetTimeFromString(ds.getBreakStartTime());
		endBreakTime = GetTimeFromString(ds.getBreakEndTime());
		
		if (startBreakTime == 1200) {
			startBreakTime = endTime;
		}
		while( startTime < startBreakTime  ) {
			String temp = (int) startTime/60 + ":" + (int )startTime%60;
			slots.add(temp);
			startTime = startTime + ds.getSlotDuration();
		}
		if (endBreakTime == 1200) {
			endBreakTime = endTime;
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
