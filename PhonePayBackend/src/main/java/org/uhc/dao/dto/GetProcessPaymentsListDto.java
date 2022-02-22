/**
 * 
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.uhc.util.Constants;

/**
 * @author nehas3
 *
 */
public class GetProcessPaymentsListDto {

	private int paymentId;
	private long loanNumber;
	private BigDecimal monthlyAmount;
	private BigDecimal additionalPrincipal;
	private BigDecimal additionalEscrow = new BigDecimal(0);
	private BigDecimal lateFee;
	private BigDecimal nsfFee;
	private String createDate;
	private String confirmationNumber;
	private String nameOnAccount;
	private String agentName;
	private String accountNumber;
	private String routingNumber;
	private String accountType;
	private BigDecimal phonePayFee = new BigDecimal(0);
	private BigDecimal corporateAdvance;
	private BigDecimal otherTypePayments;
	private boolean advice;
	private int adviceType;
	private String adviceNote;
	private String emails;
	private String line1;
	private String city;
	private String state;
	private String zip; 
	private boolean sendLetter;

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

	public String getEncryptedLoanNumber() {
		StringBuilder encryptedLoanNumber = new StringBuilder();
		String loanNum = String.valueOf(this.loanNumber);
		if (loanNum.startsWith("9")) {
			encryptedLoanNumber.append(loanNum.substring(0, 5));
			for (int index = 4; index < loanNum.length(); index++) {
				encryptedLoanNumber.append("*");
			}
		} else {
			encryptedLoanNumber.append(loanNum.substring(0, 4));
			for (int index = 3; index < loanNum.length(); index++) {
				encryptedLoanNumber.append("*");
			}
		}
		return encryptedLoanNumber.toString();
	}

	public BigDecimal getMonthlyAmount() {
		return monthlyAmount;
	}

	public void setMonthlyAmount(BigDecimal monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
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
		if (additionalEscrow != null) {
			this.additionalEscrow = additionalEscrow;
		} else {
			this.additionalEscrow = new BigDecimal(0);
		}

	}

	public BigDecimal getLateFee() {
		return lateFee;
	}

	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}

	public BigDecimal getNsfFee() {
		return nsfFee;
	}

	public void setNsfFee(BigDecimal nsfFee) {
		this.nsfFee = nsfFee;
	}

	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		if (createDate != null) {
			this.createDate = createDate;
		} else {
			this.createDate = null;
		}
	}

	public void setCreateDate(LocalDateTime createDate) {
		if (createDate != null) {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern(Constants.DateFormat.NEW_DATETIME_FORMAT.getValue());
			this.createDate = sdf.format(createDate);
		} else {
			this.createDate = null;
		}
	}

	public String getConfirmationNumber() {
		return confirmationNumber.trim();
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public String getNameOnAccount() {
		return nameOnAccount;
	}

	public void setNameOnAccount(String nameOnAccount) {
		this.nameOnAccount = nameOnAccount;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	/*
	 * public void setAccountNumber(String accountNumber) { if (accountNumber !=
	 * null && accountNumber.length()>4) { this.accountNumber =
	 * accountNumber.substring(accountNumber.length()-4); } else {
	 * this.accountNumber = accountNumber; }
	 * 
	 * }
	 */
	public void setAccountNumber(String accountNumber) {
		if(accountNumber!=null) {
		this.accountNumber = accountNumber;
		}
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

	public BigDecimal getPhonePayFee() {
		return phonePayFee;
	}

	public void setPhonePayFee(BigDecimal phonePayFee) {
		if (phonePayFee != null) {
			this.phonePayFee = phonePayFee;
		} else {
			this.phonePayFee = new BigDecimal(0);
		}
	}

	public BigDecimal getCorporateAdvance() {
		return corporateAdvance;
	}

	public void setCorporateAdvance(BigDecimal corporateAdvance) {
		this.corporateAdvance = corporateAdvance;
	}

	public BigDecimal getOtherTypePayments() {
		return otherTypePayments;
	}

	public void setOtherTypePayments(BigDecimal otherTypePayments) {
		this.otherTypePayments = otherTypePayments;
	}

	public boolean isAdvice() {
		return advice;
	}

	public void setAdvice(boolean advice) {
		this.advice = advice;
	}

	public int getAdviceType() {
		return adviceType;
	}

	public void setAdviceType(int adviceType) {
		this.adviceType = adviceType;
	}

	public String getAdviceNote() {
		return adviceNote;
	}

	public void setAdviceNote(String adviceNote) {
		if (adviceNote != null) {
			this.adviceNote = adviceNote;
		} else {
			this.adviceNote = "N/A";
		}
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}


	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	

	public boolean isSendLetter() {
		return sendLetter;
	}

	public void setSendLetter(boolean sendLetter) {
		this.sendLetter = sendLetter;
	}
	
}
