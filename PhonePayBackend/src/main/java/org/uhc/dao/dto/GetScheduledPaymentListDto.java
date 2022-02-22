/**
 * 
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.uhc.util.ConfirmationNumberHelper;
import org.uhc.util.Constants;

/**
 * @author nehas3
 *
 */
public class GetScheduledPaymentListDto {
	private int paymentId;
	private long loanNumber;
	private String processedDate;
	private String scheduledDate;
	private String confNum;
	private String totalPayment;
	private String accountNumber;
	private String routingNumber;
	private String accountType;
	private String emails;
	private String payorName;
	private String payorType;
	private String payorAddress;
	private String payorZip;
	private String payorState;
	private String payorCity;
	private String payorPhone;
	private String enteredBy;
	private List<OtherFeeDto> otherFeeList;
	private PaymentAdviceDto paymentAdvice;
	private boolean printConfirmation;
	private String canceledBy;
	private String canceledDate;


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

	public String getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(LocalDateTime processedDate) {
		if (processedDate != null) {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern(Constants.DateFormat.DATE_FORMAT.getValue());
			this.processedDate = sdf.format(processedDate);
		} else {
			this.processedDate = null;
		}
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDateTime scheduledDate) {
		if (scheduledDate != null) {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern(Constants.DateFormat.DATETIME_HOUR_12HR.getValue());
			this.scheduledDate = sdf.format(scheduledDate);
		} else {
			this.scheduledDate = null;
		}
	}
	public String getConfNum() {
		return confNum.trim();
	}

	public void setConfNum(String confNum) {
		confNum = ConfirmationNumberHelper.formatConfirmationNumber(confNum);
		this.confNum = confNum;
	}

	public String getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.totalPayment = df.format(totalPayment);
	}

	public List<OtherFeeDto> getOtherFeeList() {
		return otherFeeList;
	}

	public void setOtherFeeList(List<OtherFeeDto> otherFeeList) {
		this.otherFeeList = otherFeeList;
	}

	public PaymentAdviceDto getPaymentAdvice() {
		return paymentAdvice;
	}

	public void setPaymentAdvice(PaymentAdviceDto paymentAdvice) {
		this.paymentAdvice = paymentAdvice;
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

	public String getPayorAddress() {
		return payorAddress;
	}

	public void setPayorAddress(String payorAddress) {
		this.payorAddress = payorAddress;
	}

	/**
	 * @return the payorZip
	 */
	public String getPayorZip() {
		return payorZip;
	}

	/**
	 * @param payorZip the payorZip to set
	 */
	public void setPayorZip(String payorZip) {
		this.payorZip = payorZip;
	}

	/**
	 * @return the payorState
	 */
	public String getPayorState() {
		return payorState;
	}

	/**
	 * @param payorState the payorState to set
	 */
	public void setPayorState(String payorState) {
		this.payorState = payorState;
	}

	/**
	 * @return the payorCity
	 */
	public String getPayorCity() {
		return payorCity;
	}

	/**
	 * @param payorCity the payorCity to set
	 */
	public void setPayorCity(String payorCity) {
		this.payorCity = payorCity;
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

	public boolean isPrintConfirmation() {
		return printConfirmation;
	}

	public void setPrintConfirmation(boolean printConfirmation) {
		this.printConfirmation = printConfirmation;
	}

	public String getCanceledBy(){return canceledBy;}

	public void setCanceledBy(String canceledBy){this.canceledBy = canceledBy;}

	public String getCanceledDate(){return canceledDate;}

	public void setCanceledDate(String canceledDate){this.canceledDate = canceledDate;}
	
}
