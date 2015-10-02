package com.health.app.domain;

import java.util.Comparator;


public class MedicalPrescriptionRecordComparator implements Comparator<MedicalPrescriptionRecord> {
	    public int compare(MedicalPrescriptionRecord m1, MedicalPrescriptionRecord m2) {
	       //possibly check for nulls to avoid NullPointerException
		if (m1 == null || m2 == null) {
			return 0;
		}
	       return m1.getDate().compareTo(m2.getDate());
	    }
	}

