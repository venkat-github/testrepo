package com.test.app.repository;

import java.util.List;

import com.test.app.domain.Doctor;
import com.test.app.domain.Hospital;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the HospitalDTO entity.
 */
public interface HospitalRepository extends MongoRepository<Hospital,String> {
	List<Hospital> findByNameAndLocation(String name, String location);
}
