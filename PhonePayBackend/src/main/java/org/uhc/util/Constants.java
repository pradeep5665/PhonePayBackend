package org.uhc.util;

import java.util.concurrent.TimeUnit;

public class Constants {
	public static final String HEADER_NAME = "X-Auth-Token";
	public static final String JWT_SECRET = "9gag-is-awesome-isnt-it";
	public static final String BEARER = "Bearer: ";
	public static final Long JWT_TOKEN_DURATION = TimeUnit.HOURS.toMillis(5);
	
	
	
	public static enum DateFormat {
		DATE_FORMAT("MM/dd/yyyy"), START_DATE("/01/"), DATETIME_FORMAT("MM/dd/yyyy HH:mm.ss"), NEW_DATETIME_FORMAT("yyyy-MM-dd HH:mm:ss"),
		DATETIME_HOUR("MM/dd/yyyy HH:mm a"), EMAIL_DATE_FORMAT("yyyy-MM-dd"), DATETIME_HOUR_12HR("MM/dd/yyyy hh:mm a");

		private final String DATEVALUE;

		DateFormat(String dateValue) {
			this.DATEVALUE = dateValue;
		}

		public static DateFormat constructDateFormat(String dateValue) {
			switch (dateValue) {
			case "MM/dd/yyyy":
				return DATE_FORMAT;
			case "MM/dd/yyyy HH:mm.ss":
				return DATETIME_FORMAT;
			case "yyyy-MM-dd HH:mm.ss":
				return NEW_DATETIME_FORMAT;
			case "/01/":
				return START_DATE;
			case "MM/dd/yyyy HH:mm a":
				return DATETIME_HOUR;
			case "yyyy-MM-dd":
				return EMAIL_DATE_FORMAT;	
			default:
				return null;
			}
		}

		public String getValue() {
			return this.DATEVALUE;
		}
	}
	
	public static enum PaymentSource {
		TEL("TEL"), MBL("MBL"), WEB("WEB");

		private final String desc;

		PaymentSource(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public static PaymentSource getPaymentSource(String desc) {
			switch (desc.toUpperCase()) {

			case "TEL":
				return TEL;
				
			case "MBL":
				return MBL;
				
			case "WEB":
				return WEB;

			default:
				return null;
			}
		}
	}
	
	/**
	 * @Description : making enum to define the scheduled type of a payment
	 */
	public static enum ScheduledPaymentType {
		TODAY("Today");

		private final String desc;

		ScheduledPaymentType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public static ScheduledPaymentType getScheduledPaymentType(String desc) {
			switch (desc.toUpperCase()) {
			case "TODAY":
				return TODAY;
			default:
				return null;
			}
			//return desc.equalsIgnoreCase("TODAY") ? TODAY : null;
		}
	}
	
	/**
	 * @Description : making enum to define the status of a payment
	 */
	public enum PaymentStatus {
		CANCELED('C'), PROCESSED('P'), PENDING('S');

		private final char dbValue;

		private PaymentStatus(char dbValue) {
			this.dbValue = dbValue;
		}

		public char getDbValue() {
			return dbValue;
		}

		public static PaymentStatus constructPaymentStatus(char dbValue) {
			switch (dbValue) {
			case 'C':
				return CANCELED;
			case 'P':
				return PROCESSED;
			case 'S':
				return PENDING;
			default:
				return null;
			}
		}
	}

}
