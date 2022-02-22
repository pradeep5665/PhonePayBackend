package org.uhc.dao.dto;

import java.math.BigDecimal;

public class OtherFeeDto {
	private int id;
	private int schedulePaymentId;
	private int feeCode;
	private BigDecimal feeAmount;
	private String feeName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSchedulePaymentId() {
		return schedulePaymentId;
	}

	public void setSchedulePaymentId(int schedulePaymentId) {
		this.schedulePaymentId = schedulePaymentId;
	}

	public int getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(int feeCode) {
		this.feeCode = feeCode;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		if(feeAmount==null) {
			this.feeAmount = new BigDecimal(0.00); 
		}else {
		this.feeAmount = feeAmount;
		}
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

}
