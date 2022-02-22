/**
 * 
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author nehas3
 *
 */
public class ResearchPaymentActualListDto {
	private int paymentId;
	private int userId;
	private long loanNumber;
	private String borrowerName;
	private String coBorrowerName;
	private String paymentDate;
	private String totalAmount;
	private String paymentStatus;
	private String monthlyPayment;
	private String canceledBy;
	private String canceledDate;
	private int phonePayFee;
	private boolean lateProcess;
	private PaymentAdviceDto paymentAdvice;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public BigDecimal getTotalAmountForTotal() {
		return (new BigDecimal(totalAmount));
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.totalAmount = df.format(totalAmount);
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(String monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public int getPhonePayFee() {
		return phonePayFee;
	}

	public void setPhonePayFee(int phonePayFee) {
		this.phonePayFee = phonePayFee;
	}

	public boolean isLateProcess() {
		return lateProcess;
	}

	public void setLateProcess(boolean lateProcess) {
		this.lateProcess = lateProcess;
	}

	public PaymentAdviceDto getPaymentAdvice() {
		return paymentAdvice;
	}

	public void setPaymentAdvice(PaymentAdviceDto paymentAdvice) {
		this.paymentAdvice = paymentAdvice;
	}

	public String getCanceledBy(){return canceledBy;}

	public void setCanceledBy(String canceledBy){this.canceledBy = canceledBy;}

	public String getCanceledDate(){return canceledDate;}

	public void setCanceledDate(String canceledDate){this.canceledDate = canceledDate;}

	
}
