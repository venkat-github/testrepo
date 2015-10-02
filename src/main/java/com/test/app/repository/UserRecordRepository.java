package com.test.app.repository;

import com.test.app.domain.UserDoctorVisitRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the UserRecordDTO entity.
 */
public interface UserRecordRepository extends MongoRepository<UserDoctorVisitRecord,String> {

	Page<UserDoctorVisitRecord> findByUserId(String userId, Pageable generatePageRequest);

}
