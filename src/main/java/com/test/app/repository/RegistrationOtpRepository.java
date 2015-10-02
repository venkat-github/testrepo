package com.test.app.repository;

import com.test.app.domain.RegistrationOtp;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the RegistrationOtp entity.
 */
public interface RegistrationOtpRepository extends MongoRepository<RegistrationOtp,String> {

}
