package com.test.app.repository;

import java.util.List;

import com.test.app.domain.Doctor;
import com.test.app.domain.MedUser;
import com.test.app.domain.User;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the UserDTO entity.
 */
public interface MedUserRepository extends MongoRepository<MedUser,String> {
	
}
