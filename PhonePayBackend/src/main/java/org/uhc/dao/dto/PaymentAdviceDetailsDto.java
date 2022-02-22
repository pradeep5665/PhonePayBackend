
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.uhc.util.Constants;

public class PaymentAdviceDetailsDto {

	private String borrowerName;
	private String borrowerLastName;
	private String coBorrowerName;
	private String coBorrowerLastName;
	private String payorName;
	private String payorType;

	private int paymentId;
	private long loanNumber;
	private String scheduledDate;
	private String confirmationNumber;
	private String paymentAdviceType;
	private String paymentAdviceMessage;
	private boolean paymentAdviceStatus;
	private String batchCode;
	private String processedBy;
	private String enteredBy;
	private BigDecimal totalPayment;
	private List<OtherFeeDto> otherFeeList;
	private boolean isDateCalcelled;
	private boolean removedFromBK;
	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerLastName() {
		return borrowerLastName;
	}

	public void setBorrowerLastName(String borrowerLastName) {
		this.borrowerLastName = borrowerLastName;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}

	public String getCoBorrowerLastName() {
		return coBorrowerLastName;
	}

	public void setCoBorrowerLastName(String coBorrowerLastName) {
		this.coBorrowerLastName = coBorrowerLastName;
	}

	public String getPayorName() {
		return payorName;
	}

	public void setPayorName(String payorName) {
		this.payorName = payorName;
	}

	public String getPayorType() {
		return payorType;
	}

	public void setPayorType(String payorType) {
		this.payorType = payorType;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDateTime scheduledDate) {
		if (scheduledDate != null) {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern(Constants.DateFormat.DATETIME_HOUR.getValue());
			this.scheduledDate = sdf.format(scheduledDate);
		} else {
			this.scheduledDate = null;
		}
	}

	public String getConfirmationNumber() {
		return confirmationNumber.trim();
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public String getPaymentAdviceType() {
		return paymentAdviceType;
	}

	public void setPaymentAdviceType(String paymentAdviceType) {
		this.paymentAdviceType = paymentAdviceType;
	}

	public String getPaymentAdviceMessage() {
		return paymentAdviceMessage;
	}

	public void setPaymentAdviceMessage(String paymentAdviceMessage) {
		this.paymentAdviceMessage = paymentAdviceMessage;
	}

	public boolean isPaymentAdviceStatus() {
		return paymentAdviceStatus;
	}

	public void setPaymentAdviceStatus(boolean paymentAdviceStatus) {
		this.paymentAdviceStatus = paymentAdviceStatus;
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

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public List<OtherFeeDto> getOtherFeeList() {
		return otherFeeList;
	}

	public void setOtherFeeList(List<OtherFeeDto> otherFeeList) {
		this.otherFeeList = otherFeeList;
	}

	/**
	 * @return the isDateCalcelled
	 */
	public boolean isDateCalcelled() {
		return isDateCalcelled;
	}

	/**
	 * @param isDateCalcelled the isDateCalcelled to set
	 */
	public void setDateCalcelled(boolean isDateCalcelled) {
		this.isDateCalcelled = isDateCalcelled;
	}

	public boolean isRemovedFromBK() {
		return removedFromBK;
	}

	public void setRemovedFromBK(boolean removedFromBK) {
		this.removedFromBK = removedFromBK;
	}
	
	

}
