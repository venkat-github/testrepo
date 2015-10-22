package com.test.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.app.domain.BookingStatsTable;

public interface BookingStatsTableRepository extends MongoRepository<BookingStatsTable, String> {

}
