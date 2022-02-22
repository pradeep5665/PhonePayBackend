/**
 * 
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author nehas3
 *
 */
public class PaymentsInProcessActualList {
	private long loanNumber;
	private List<Long> associatedLoanAccounts;
	private boolean advice;
	private String payorName;
	private String payorType;
	private boolean isAdviceCompleted;
	private boolean isCancelled;
	private String dateTime;
	private BigDecimal totalPayment;
	private String collector;

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public List<Long> getAssociatedLoanAccounts() {
		return associatedLoanAccounts;
	}

	public void setAssociatedLoanAccounts(List<Long> associatedLoanAccounts) {
		this.associatedLoanAccounts = associatedLoanAccounts;
	}

	public boolean isAdvice() {
		return advice;
	}

	public void setAdvice(boolean advice) {
		this.advice = advice;
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

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

}
