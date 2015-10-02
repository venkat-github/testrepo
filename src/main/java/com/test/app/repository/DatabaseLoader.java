package com.test.app.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoader {

	@Autowired
	UniqueIdRepositoryHelper uirh;
	
	@PostConstruct
	private void initDatabase() {
		uirh.uniqueIdTableInit();
	}
}
