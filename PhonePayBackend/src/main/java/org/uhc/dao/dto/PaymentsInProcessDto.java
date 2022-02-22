/**
 * 
 */
package org.uhc.dao.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.uhc.util.Constants;

/**
 * @author nehas3
 *
 */
public class PaymentsInProcessDto {

	private int paymentId;
	private long loanNumber;
	private String dateTime;
	private int advicePaymentId;
	private String payorName;
	private String payorType;
	private boolean isAdviceCompleted;
	private boolean isCancelled;
	private String enteredBy;

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

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		if (dateTime != null) {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern(Constants.DateFormat.DATETIME_HOUR.getValue());
			this.dateTime = sdf.format(dateTime);
		} else {
			this.dateTime = null;
		}
	}

	public int getAdvicePaymentId() {
		return advicePaymentId;
	}

	public void setAdvicePaymentId(int advicePaymentId) {
		this.advicePaymentId = advicePaymentId;
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

	public boolean isAdviceCompleted() {
		return isAdviceCompleted;
	}

	public void setAdviceCompleted(boolean isAdviceCompleted) {
		this.isAdviceCompleted = isAdviceCompleted;
	}

	/**
	 * @return the isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * @param isCancelled the isCancelled to set
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

}
