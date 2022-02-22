package org.uhc.util;

import java.security.SecureRandom;

public class ConfirmationNumberHelper {

	private static final int LENGTH = 6;
	private static final int GROUP_SIZE = 3;
	private static final String DIGITS = "0123456789";
	private static final String ALPHANUM = DIGITS;
	
	

	private ConfirmationNumberHelper() {
		
	}

	public static String formatConfirmationNumber(String confirmationNumber) {
		if (confirmationNumber == null) {
			return null;
		}
		int confNumLength = confirmationNumber.length();

		if (confirmationNumber.length() > GROUP_SIZE + 1) {
			return confirmationNumber.substring(0, 3) + "-" + confirmationNumber.substring(GROUP_SIZE, confNumLength);
		} else {
			return confirmationNumber;
		}
	}

	public static String generateConfirmationNumber() {
		SecureRandom random = new SecureRandom();
		char[] buf = new char[LENGTH];
		char[] symbols = ALPHANUM.toCharArray();

		for (int i = 0; i < LENGTH; i++) {
			buf[i] = symbols[random.nextInt(symbols.length)];
		}

		return new String(buf);
	}
}
