/**
 * 
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.uhc.util.Constants;
import org.uhc.util.Constants.PaymentStatus;

public class ResearchPaymentDto {

	private int paymentId;
	private int userId;
	private long loanNumber;
	private String lateFeesPaid;
	private String nsfFeesPaid;
	private String extraPrincipal;
	private String extraEscrow;
	private String createDate;
	private String processedDate;
	private String canceledDate;
	private String canceledBy;
	private String monthlyPayment;
	private String amount;
	private String firstName;
	private String lastName;
	private boolean canceled;
	private PaymentStatus paymentStatus;
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

	public String getLateFeesPaid() {
		return lateFeesPaid;
	}

	public BigDecimal getLateFeesPaidForTotal() {
		return new BigDecimal(lateFeesPaid);
	}

	public void setLateFeesPaid(BigDecimal lateFeesPaid) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.lateFeesPaid = df.format(lateFeesPaid);
	}

	public String getNsfFeesPaid() {
		return nsfFeesPaid;
	}

	public BigDecimal getNsfFeesPaidForTotal() {
		return (new BigDecimal(nsfFeesPaid));
	}

	public void setNsfFeesPaid(BigDecimal nsfFeesPaid) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.nsfFeesPaid = df.format(nsfFeesPaid);
	}

	public String getExtraPrincipal() {
		return extraPrincipal;
	}

	public BigDecimal getExtraPrincipalForTotal() {
		return (new BigDecimal(extraPrincipal));
	}

	public void setExtraPrincipal(BigDecimal extraPrincipal) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.extraPrincipal = df.format(extraPrincipal);
	}

	public String getExtraEscrow() {
		return extraEscrow;
	}

	public BigDecimal getExtraEscrowForTotal() {
		return (new BigDecimal(extraEscrow));
	}

	public void setExtraEscrow(BigDecimal extraEscrow) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.extraEscrow = df.format(extraEscrow);
	}

	public String getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		if (processedDate != null) {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			this.processedDate = sdf.format(processedDate);
		} else {
			this.processedDate = null;
		}
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		if (createDate != null) {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern(Constants.DateFormat.DATE_FORMAT.getValue());
			this.createDate = sdf.format(createDate);
		} else {
			this.createDate = null;
		}
	}

	public String getCanceledDate() {
		return canceledDate;
	}

	public void setCanceledDate(String canceledDate){this.canceledDate = canceledDate;}

	public void setDateTime(LocalDateTime canceledDate) {
		if (canceledDate != null) {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern(Constants.DateFormat.DATE_FORMAT.getValue());
			this.canceledDate = sdf.format(canceledDate);
		} else {
			this.canceledDate = null;
		}
	}

	public String getMonthlyPayment() {
		return monthlyPayment;
	}

	public BigDecimal getMonthlyPaymentForTotal() {
		return new BigDecimal(monthlyPayment);
	}

	public void setMonthlyPayment(BigDecimal monthlyPayment) {
		if (monthlyPayment != null) {
			DecimalFormat df = new DecimalFormat("0.00");
			this.monthlyPayment = df.format(monthlyPayment);
		} else {
			this.monthlyPayment = null;
		}

	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public PaymentAdviceDto getPaymentAdvice() {
		return paymentAdvice;
	}

	public void setPaymentAdvice(PaymentAdviceDto paymentAdvice) {
		this.paymentAdvice = paymentAdvice;
	}

	public String getCanceledBy() { return canceledBy;}

	public void setCanceledBy(String canceledBy){this.canceledBy = canceledBy;}
	
}
