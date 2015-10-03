package com.test.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.app.domain.Hospital;
import com.test.app.domain.User;

/**
 * Spring Data MongoDB repository for the HospitalDTO entity.
 */
public interface HospitalRepository extends MongoRepository<Hospital,String> {
	List<Hospital> findByNameAndLocation(String name, String location);
	Hospital findOneById(String Id);
}
