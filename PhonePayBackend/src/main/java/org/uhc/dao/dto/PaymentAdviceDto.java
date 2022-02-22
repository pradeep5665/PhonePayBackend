
package org.uhc.dao.dto;

public class PaymentAdviceDto {

	private int id;
	private int schedulePaymentId;
	private int adviceId;
	private boolean completed;
	private String adviceNotes;
	private String processedBy;
	private String batchCode;

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

	public int getAdviceId() {
		return adviceId;
	}

	public void setAdviceId(int adviceId) {
		this.adviceId = adviceId;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getAdviceNotes() {
		return adviceNotes;
	}

	public void setAdviceNotes(String adviceNotes) {
		this.adviceNotes = adviceNotes;
	}

	public String getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

}
