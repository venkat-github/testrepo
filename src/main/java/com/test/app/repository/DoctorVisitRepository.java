package com.test.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.app.domain.DoctorVisit;

/**
 * Spring Data MongoDB repository for the DoctorVisitDTO entity.
 */
public interface DoctorVisitRepository extends MongoRepository<DoctorVisit,String> {
	Page<DoctorVisit> findByDoctorId(String doctorId, Pageable page);
}
