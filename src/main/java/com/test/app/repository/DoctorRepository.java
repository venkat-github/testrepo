package com.test.app.repository;

import java.util.List;

import com.test.app.domain.Doctor;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the DoctorDTO entity.
 */
public interface DoctorRepository extends MongoRepository<Doctor,String> {
	List<Doctor> findByName(String name);
}
