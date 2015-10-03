package com.test.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.app.domain.DoctorSchedule;

public interface DoctorScheduleRepository extends MongoRepository<DoctorSchedule,String>{

}
