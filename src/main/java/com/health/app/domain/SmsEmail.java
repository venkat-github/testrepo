package com.health.app.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class SmsEmail {
	@Id
	private String id;
	String userId;
	EventToNotify event;
	TypeOfCommunication toc;
	String source;
	String destination;
	String msg;
	String body;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public EventToNotify getEvent() {
		return event;
	}

	public void setEvent(EventToNotify event) {
		this.event = event;
	}

	public TypeOfCommunication getToc() {
		return toc;
	}

	public void setToc(TypeOfCommunication toc) {
		this.toc = toc;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public SmsEmail() {

	}

}
