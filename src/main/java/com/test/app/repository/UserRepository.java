package com.test.app.repository;

import com.test.app.domain.HospitalDoctorConsultaion;
import com.test.app.domain.User;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the User entity.
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(DateTime dateTime);

    User findOneByResetKey(String resetKey);

    User findOneByLogin(String login);

    User findOneByEmail(String email);

    User findOneByMobileno(String mobileno);
    
	User findOneByName(String name);
	
	Page<User> findByName(String name, Pageable page);
	User findOneById(String Id);
}
