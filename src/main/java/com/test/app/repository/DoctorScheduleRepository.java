package com.test.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.app.domain.DoctorSchedule;

/**
 * Spring Data MongoDB repository for the DoctorVisitDTO entity.
 */
public interface DoctorScheduleRepository extends MongoRepository<DoctorSchedule,String> {
	
}
