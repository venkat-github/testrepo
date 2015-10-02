package com.health.app.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class UniqueId {
	@Id
	private String id;
	String userName;
	String uuid;
	int value;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public UniqueId() {
	}

	public UniqueId(String uuid, int value) {
		this.uuid = uuid;
		this.value = value;
		this.userName = "admin";
	}
}
