package com.health.app.domain;

import java.util.Random;

public class OneTimePassword {
	public static String generateOtp(int size) {
		String otp = "";
		Random randomGenerator = new Random();
		for (int i = 0; i < size; i++) {
			int randomInt = randomGenerator.nextInt(10);
			otp = otp + randomInt;
		}
		return otp;
	}
}
