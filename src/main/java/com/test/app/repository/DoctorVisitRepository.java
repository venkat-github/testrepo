package com.test.app.repository;

import java.util.List;

import com.test.app.domain.Doctor;
import com.test.app.domain.DoctorVisit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the DoctorVisitDTO entity.
 */
public interface DoctorVisitRepository extends MongoRepository<DoctorVisit,String> {
	Page<DoctorVisit> findByDoctorId(String doctorId, Pageable page);
}
