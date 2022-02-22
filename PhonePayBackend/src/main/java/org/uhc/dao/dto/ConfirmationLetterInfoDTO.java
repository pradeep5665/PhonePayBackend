package org.uhc.dao.dto;

import java.util.List;



public class ConfirmationLetterInfoDTO {
	private String processorName;
	private String payerName;
	private Address address;
	private String transactionDate;
	private String bankName;
	private String bankAccountEnding;
	private String nameOfException;
	private String status;
	private String accountType;
	private List<LoanPaymentDto> loanPaymentList;
	private long failedEmailLoanNum;
	public String getProcessorName() {
		return processorName;
	}
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountEnding() {
		return bankAccountEnding;
	}
	public void setBankAccountEnding(String bankAccountEnding) {
		this.bankAccountEnding = bankAccountEnding;
	}
	public List<LoanPaymentDto> getLoanPaymentList() {
		return loanPaymentList;
	}
	public void setLoanPaymentList(List<LoanPaymentDto> loanPaymentList) {
		this.loanPaymentList = loanPaymentList;
	}
	public String getNameOfException() {
		return nameOfException;
	}
	public void setNameOfException(String nameOfException) {
		this.nameOfException = nameOfException;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	
	public long getFailedEmailLoanNum() {
		return failedEmailLoanNum;
	}
	public void setFailedEmailLoanNum(long failedEmailLoanNum) {
		this.failedEmailLoanNum = failedEmailLoanNum;
	}
}
