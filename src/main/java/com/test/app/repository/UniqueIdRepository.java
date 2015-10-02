package com.test.app.repository;

import com.health.app.domain.UniqueId;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface  UniqueIdRepository extends 
	MongoRepository<UniqueId, String>{
	
}
