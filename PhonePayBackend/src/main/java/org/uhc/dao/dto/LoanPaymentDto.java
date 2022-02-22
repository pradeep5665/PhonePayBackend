/**
 * 
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;

/**
 * @author nehas3
 *
 */
public class LoanPaymentDto {

	private String maskedLoanNumber;
	private String confirmationNumber;
	private BigDecimal monthlyPayment;
	private BigDecimal additionalPrincipal;
	private BigDecimal additionalEscrow = new BigDecimal(0);
	private BigDecimal lateCharge;
	private BigDecimal nsfFee;
	private BigDecimal corporateAdvanceFee;
	private BigDecimal other;
	private BigDecimal transactionFee = new BigDecimal(0);
	private long loanNumber;
	private String payerName;
	

	public String getMaskedLoanNumber() {
		return maskedLoanNumber;
	}

	public void setMaskedLoanNumber(String maskedLoanNumber) {
		this.maskedLoanNumber = maskedLoanNumber;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public BigDecimal getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(BigDecimal monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public BigDecimal getAdditionalPrincipal() {
		return additionalPrincipal;
	}

	public void setAdditionalPrincipal(BigDecimal additionalPrincipal) {
		this.additionalPrincipal = additionalPrincipal;
	}

	public BigDecimal getAdditionalEscrow() {
		return additionalEscrow;
	}

	public void setAdditionalEscrow(BigDecimal additionalEscrow) {
		this.additionalEscrow = additionalEscrow;
	}

	public BigDecimal getLateCharge() {
		return lateCharge;
	}

	public void setLateCharge(BigDecimal lateCharge) {
		this.lateCharge = lateCharge;
	}

	public BigDecimal getNsfFee() {
		return nsfFee;
	}

	public void setNsfFee(BigDecimal nsfFee) {
		this.nsfFee = nsfFee;
	}

	public BigDecimal getCorporateAdvanceFee() {
		return corporateAdvanceFee;
	}

	public void setCorporateAdvanceFee(BigDecimal corporateAdvanceFee) {
		this.corporateAdvanceFee = corporateAdvanceFee;
	}

	public BigDecimal getOther() {
		return other;
	}

	public void setOther(BigDecimal other) {
		this.other = other;
	}

	public BigDecimal getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(BigDecimal transactionFee) {
		this.transactionFee = transactionFee;
	}
	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}
	
	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	
}
