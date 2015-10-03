package com.test.app.repository;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.app.domain.DoctorSchedule;
import com.test.app.domain.DoctorVisit;

/**
 * Spring Data MongoDB repository for the DoctorVisitDTO entity.
 */
public interface DoctorScheduleRepository extends MongoRepository<DoctorSchedule,String> {
	
}
