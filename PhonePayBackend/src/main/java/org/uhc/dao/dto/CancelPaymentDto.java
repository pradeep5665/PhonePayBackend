package org.uhc.dao.dto;

public class CancelPaymentDto {

	private String processedByEmail;
	private String batchCode;
	public String getProcessedByEmail() {
		return processedByEmail;
	}
	public void setProcessedByEmail(String processedByEmail) {
		this.processedByEmail = processedByEmail;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	
	
}
