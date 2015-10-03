package com.test.app.repository;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.app.domain.HospitalDoctorConsultaion;

/**
 * Spring Data MongoDB repository for the HospitalDoctorDTO4 entity.
 */
public interface HospitalDoctorConsultaionRepository extends MongoRepository<HospitalDoctorConsultaion,String> {

	List<HospitalDoctorConsultaion> findBySpecialityOrLocation(String speciality, String location);
	List<HospitalDoctorConsultaion> findBySpecialityAndLocation(String speciality, String location);
	List<HospitalDoctorConsultaion> findBySpecialityAndLocationAndDate(String speciality, String location, LocalDate date);
	List<HospitalDoctorConsultaion> findByLocationAndDate(String location, LocalDate date);
	List<HospitalDoctorConsultaion> findBySpecialityAndDate(String speciality, LocalDate date);
	Page<HospitalDoctorConsultaion> findBySpecialityAndDate(String speciality, LocalDate date, Pageable page);
	List<HospitalDoctorConsultaion> findByDoctorName(String doctorName);
	
}
