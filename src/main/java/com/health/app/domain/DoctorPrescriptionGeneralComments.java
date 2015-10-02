package com.health.app.domain;

import java.util.ArrayList;
import java.util.List;

public class DoctorPrescriptionGeneralComments {
	int age;
	int weight;
	int BP;
	boolean IsHavingFever;
	int temparature;
	TemparatureMeasuringSytem system;
	List<String > sysmptoms = new ArrayList<String>();
	String otherComments;
	String conclusion;
}
