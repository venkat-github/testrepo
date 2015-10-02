package com.test.app.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.health.app.domain.DatabaseDateWithoutime;
import com.health.app.domain.UniqueId;

@Service
public class UniqueIdRepositoryHelper {

	@Autowired
	UniqueIdRepository uir;

	@Autowired
	MongoTemplate mongoTemplate;

	public void uniqueIdTableInit() {
		UniqueId uid = new UniqueId();
		uid.setUuid("aaaaaa");
		uid.setValue(0);
		uid.setUserName("userId");
		mongoTemplate.save(uid);
		UniqueId uid1 = new UniqueId();
		uid1.setUuid(new DatabaseDateWithoutime(new Date())
				.getDateInSpecificFormat("yyyy/MM/dd"));
		uid1.setValue(0);
		uid1.setUserName("trasactionId");
		mongoTemplate.save(uid1);
		
		UniqueId uid2 = new UniqueId();
		uid2.setUuid(new DatabaseDateWithoutime(new Date())
				.getDateInSpecificFormat("yyyyMMdd"));
		uid2.setValue(0);
		uid2.setUserName("familyId");
		mongoTemplate.save(uid2);
	}

	public String getNextUserId() {
		String retUuid;
		Query query = query(where("userName").is("userId".toString()));
		UniqueId uid = mongoTemplate.findOne(query, UniqueId.class);
		if (uid == null)
			return null;
		Update update = new Update();
		int value = uid.getValue();
		value++;
		String uuid = uid.getUuid();
		retUuid = uuid + value;
		if (value % 10000 == 0) {
			int size = uuid.length();
			char array[] = new char[size];
			array = uuid.toCharArray();
			while (array[size] != 'z') {
				size--;
			}
			array[size] = (char) (array[size] + 1);
			update.set("uuid", array.toString());
			update.set("value", value % 10000);
		} else {
			update.set("uuid", uid.getUuid());
			update.set("value", value % 10000);
		}
		mongoTemplate.findAndModify(query, update, UniqueId.class);
		return retUuid;

	}

	public String getNextTrasactionId() {
		Query query = query(where("userName").is("transactionId".toString()));
		UniqueId uid = mongoTemplate.findOne(query, UniqueId.class);
		if (uid == null)
			return null;
		Update update = new Update();
		int value = uid.getValue();
		value++;
		update.set("value", value);
		mongoTemplate.findAndModify(query, update, UniqueId.class);
		return uid.getUuid() + uid.getValue();
	}

	public String getNextFamilyId() {
		Query query = query(where("userName").is("familyId".toString()));
		UniqueId uid = mongoTemplate.findOne(query, UniqueId.class);
		if (uid == null)
			return null;
		Update update = new Update();
		int value = uid.getValue();
		value++;
		update.set("value", value);
		mongoTemplate.findAndModify(query, update, UniqueId.class);
		return "F"+uid.getUuid() + uid.getValue();
	}
	
}
