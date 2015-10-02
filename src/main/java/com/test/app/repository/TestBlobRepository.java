package com.test.app.repository;

import com.test.app.domain.TestBlob;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the TestBlob entity.
 */
public interface TestBlobRepository extends MongoRepository<TestBlob,String> {

}
