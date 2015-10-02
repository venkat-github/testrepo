package com.health.app.domain;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DatabaseDateWithoutime {
	Date date;

	public DatabaseDateWithoutime(java.util.Date date) {
		this.date = date;
	}
	public String getDateInSpecificFormat(String dateFormat){
		DateFormat outputFormatter = new SimpleDateFormat(dateFormat);
		String output = outputFormatter.format(date);
		return output;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
