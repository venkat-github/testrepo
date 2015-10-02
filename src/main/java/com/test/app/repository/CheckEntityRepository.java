package com.test.app.repository;

import com.test.app.domain.CheckEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CheckEntity entity.
 */
public interface CheckEntityRepository extends MongoRepository<CheckEntity,String> {

}
