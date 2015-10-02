package com.test.app.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.health.app.domain.DatabaseDateWithoutime;
import com.health.app.domain.DoctorConsultation;
import com.health.app.domain.PatientDoctorHospitalAppiontment;
import com.health.app.domain.Sex;
import com.health.app.domain.Speciality;

@Service
public class DoctorConsultationHelperFunctions {

	private static final Logger log = LoggerFactory
			.getLogger(DoctorConsultationHelperFunctions.class);

	@Autowired
	MongoTemplate mongoTemplate;

	
	public void bookAppointment(PatientDoctorHospitalAppiontment aps) {
		Criteria criteria = where("doctor.doctorId").is(aps.getDoctorId())
				.and("date").is(aps.getDate());

		if (aps.getHospitalId() != null) {
			criteria = criteria.and("hospitalId").is(aps.getHospitalId());
		}

		List<DoctorConsultation> dc = mongoTemplate.find(query(criteria),
				DoctorConsultation.class);
		System.out.println("sachin tendulkar*************************slot doctorid  "
				+ aps.getDoctorId() + "date " + aps.getDate()
				+ "hospitalId is " + aps.getHospitalId());
		if (dc.size() == 0) {
			System.out.println("Rama chandhra ");
			return;
		}
		if (dc.get(0).getUsedSlots() < dc.get(0).getTotalSlots()) {
			long bitfeild = dc.get(0).getSetSlotBits();

			if ((bitfeild & (1L << aps.getSlot())) != 0) {
				System.out.println("Slot has been already filled");
			} else {
				bitfeild = bitfeild & 1 << aps.getSlot();
				Update update = new Update();
				update.inc("usedSlots", 1);
				update.set("setSlotBits", bitfeild);
				DoctorConsultation p = mongoTemplate.findAndModify(
						query(criteria), update, DoctorConsultation.class);
				if (p == null){ 
					System.out.println("updating doctor consultation has failed");
				} else {
					System.out.println("Book appointment has been failed");
				//patientDoctorHospitalAppiontmentRepository.save(aps);
				}
			}
		} else
			System.out
					.println("*******************************************slotsfilled ! ");
	}

	public List<PatientDoctorHospitalAppiontment> displayListOfAppointmentsForDoctorAndHospitalBasedOnDate(
			PatientDoctorHospitalAppiontment input) {
		Query query = query(where("doctor.doctorId").is(input.getDoctorId())
				.and("hospitalId").is(input.getHospitalId()).and("date")
				.is(input.getDate()));
		List<PatientDoctorHospitalAppiontment> dc = mongoTemplate.find(query,
				PatientDoctorHospitalAppiontment.class);
		for (int i = 0; i < dc.size(); i++) {
			System.out
					.println("*************************************PatientDoctorHospitalAppiontment**************"
							+ dc.get(i).getDoctorId());
		}
		return dc;
	}

	public List<PatientDoctorHospitalAppiontment> displayListOfAppointmentsHospitalBasedOnDate(
			PatientDoctorHospitalAppiontment input) {
		Query query = query(where("hospitalId").is(input.getHospitalId())
				.and("date").is(input.getDate()));
		List<PatientDoctorHospitalAppiontment> dc = mongoTemplate.find(query,
				PatientDoctorHospitalAppiontment.class);
		for (int i = 0; i < dc.size(); i++) {
			System.out
					.println("*************************************PatientDoctorHospitalAppiontment**************"
							+ dc.get(i).getDoctorId());
		}
		return dc;
	}

	public List<DoctorConsultation> findConsultations(String city,
			Speciality speciality, List<String> locations, double langitude,
			double latitude, int startTime, int endTime, Date date,
			int lowFee,// TODO
			int highFee, Sex sex, int yearsOfExperienceLow,
			int yearsOfExperienceHigh) {

		// NearQuery nearQuery = NearQuery.near(langitude, latitude,
		// Metrics.KILOMETERS);

		Criteria criteria = where("address.city").is(city);

		criteria = criteria.and("doctor.speciality").in(speciality);

		if (locations != null && locations.size() > 0) {
			criteria = criteria.and("address.area").in(locations);
		}

		if (sex != null) {
			criteria = criteria.and("doctor.sex").is(sex);
		}
		if (date != null) {
			DatabaseDateWithoutime dateWithoutTime = new DatabaseDateWithoutime(
					date);
			criteria = criteria.and("date").is(
					dateWithoutTime.getDateInSpecificFormat("yyyy/MM/dd"));
		}
		if (lowFee > 0 && highFee > 0 && lowFee >= highFee) {
			criteria = criteria.and("consultationFee").gte(lowFee).lte(highFee);
		}
		if (yearsOfExperienceLow >= 0 && yearsOfExperienceHigh > 0
				&& yearsOfExperienceLow < yearsOfExperienceHigh) {
			criteria = criteria.and("yearOfExperience")
					.gte(yearsOfExperienceLow).lte(yearsOfExperienceHigh);
		}
		// nearQuery.query(query(criteria));
		List<DoctorConsultation> consul = mongoTemplate.find(query(criteria),
				DoctorConsultation.class);
		/*
		 * GeoResults<DoctorConsultation> consultations =
		 * mongoTemplate.geoNear(nearQuery, DoctorConsultation.class);
		 * List<GeoResult<DoctorConsultation>> consul =
		 * consultations.getContent();
		 */

		for (int i = 0; i < consul.size(); i++) {
			System.out.println("+++++++++++++++++++++++++++++++++++"
					+ consul.get(i));
		}

		// TODO
		return null;
	}
}
