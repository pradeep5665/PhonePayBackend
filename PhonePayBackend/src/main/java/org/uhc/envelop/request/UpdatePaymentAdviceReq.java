/**
 * 
 */
package org.uhc.envelop.request;

public class UpdatePaymentAdviceReq {

	private int schedulePaymentId;
	private String batchCode;
	private String processedBy;
	private String processedByEmail;

	public int getSchedulePaymentId() {
		return schedulePaymentId;
	}

	public void setSchedulePaymentId(int schedulePaymentId) {
		this.schedulePaymentId = schedulePaymentId;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}

	public String getProcessedByEmail() { return processedByEmail; }

	public void setProcessedByEmail(String processedByEmail) { this.processedByEmail = processedByEmail; }

}
