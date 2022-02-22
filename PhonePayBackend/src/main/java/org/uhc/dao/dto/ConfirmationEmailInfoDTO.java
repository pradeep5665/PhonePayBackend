/**
 * 
 */
package org.uhc.dao.dto;

import java.util.List;

/**
 * @author nehas3
 *
 */
public class ConfirmationEmailInfoDTO {
	private String bankAccountEnding;
	private List<String> emailList;
	private List<LoanPaymentDto> loanPaymentList;
	private String nameOfException;
	private String status;
	private String transactionDate;

	public String getBankAccountEnding() {
		return bankAccountEnding;
	}

	public void setBankAccountEnding(String bankAccountEnding) {
		this.bankAccountEnding = bankAccountEnding;
	}

	public List<String> getEmailList() {
		return emailList;
	}

	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
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

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}


}
