package org.uhc.envelop.request;

import java.math.BigDecimal;
import java.util.List;

import org.uhc.dao.dto.OtherFeeDto;

public class ScheduledCheckPaymentRequest {

	private int paymentId;
	private long loanNumber;
	private String confirmationNumber;
	private List<OtherFeeDto> feeList;
	private List<Integer> deleteFeeList;
	private BigDecimal phonePayServiceFee = BigDecimal.ZERO;
	private String paymentAdviceType;
	private String paymentAdviceNotes;
	private boolean printedConfirmation;

	private String accountNumber;
	private String routingNumber;
	private String accountType;
	private String emails;
	private String payorName;
	private String payorType;
	private String payorStreetAddress;
	private String payorCity;
	private String payorState;
	private String payorZip;
	private String payorPhone;
	private String enteredBy;

	public ScheduledCheckPaymentRequest() {
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

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public List<OtherFeeDto> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<OtherFeeDto> feeList) {
		this.feeList = feeList;
	}

	/**
	 * @return the deleteFeeList
	 */
	public List<Integer> getDeleteFeeList() {
		return deleteFeeList;
	}

	/**
	 * @param deleteFeeList the deleteFeeList to set
	 */
	public void setDeleteFeeList(List<Integer> deleteFeeList) {
		this.deleteFeeList = deleteFeeList;
	}

	public BigDecimal getPhonePayServiceFee() {
		return phonePayServiceFee;
	}

	public void setPhonePayServiceFee(BigDecimal phonePayServiceFee) {
		this.phonePayServiceFee = phonePayServiceFee;
	}

	public String getPaymentAdviceType() {
		return paymentAdviceType;
	}

	public void setPaymentAdviceType(String paymentAdviceType) {
		this.paymentAdviceType = paymentAdviceType;
	}

	public String getPaymentAdviceNotes() {
		return paymentAdviceNotes;
	}

	public void setPaymentAdviceNotes(String paymentAdviceNotes) {
		this.paymentAdviceNotes = paymentAdviceNotes;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
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

	public String getPayorStreetAddress() {
		return payorStreetAddress;
	}

	public void setPayorStreetAddress(String payorStreetAddress) {
		this.payorStreetAddress = payorStreetAddress;
	}

	public String getPayorCity() {
		return payorCity;
	}

	public void setPayorCity(String payorCity) {
		this.payorCity = payorCity;
	}

	public String getPayorState() {
		return payorState;
	}

	public void setPayorState(String payorState) {
		this.payorState = payorState;
	}

	public String getPayorZip() {
		return payorZip;
	}

	public void setPayorZip(String payorZip) {
		this.payorZip = payorZip;
	}

	public String getPayorPhone() {
		return payorPhone;
	}

	public void setPayorPhone(String payorPhone) {
		this.payorPhone = payorPhone;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	public boolean isPrintedConfirmation() {
		return printedConfirmation;
	}

	public void setPrintedConfirmation(boolean printedConfirmation) {
		this.printedConfirmation = printedConfirmation;
	}

}
