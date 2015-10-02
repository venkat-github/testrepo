package com.test.app.repository;

import com.test.app.domain.AddressDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AddressDTO entity.
 */
public interface AddressDTORepository extends MongoRepository<AddressDTO,String> {

}
