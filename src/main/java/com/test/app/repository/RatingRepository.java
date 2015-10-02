package com.test.app.repository;

import com.test.app.domain.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the RatingDTO entity.
 */
public interface RatingRepository extends MongoRepository<Rating,String> {

}
