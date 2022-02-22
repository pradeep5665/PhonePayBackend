/**
 * 
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;

/**
 * @author nehas3
 *
 */
public class FailedProcessPaymentsDto {

	private int paymentID;
	private long loanNumber;
	private BigDecimal paymentTotal;
	private String reasonFailed;

	/**
	 * @return the paymentId
	 */
	public int getPaymentId() {
		return paymentID;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(int paymentID) {
		this.paymentID = paymentID;
	}

	/**
	 * @return the loanNumber
	 */
	public long getLoanNumber() {
		return loanNumber;
	}

	/**
	 * @param loanNumber the loanNumber to set
	 */
	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	/**
	 * @return the paymentTotal
	 */
	public BigDecimal getPaymentTotal() {
		return paymentTotal;
	}

	/**
	 * @param paymentTotal the paymentTotal to set
	 */
	public void setPaymentTotal(BigDecimal paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	/**
	 * @return the reasonFailed
	 */
	public String getReasonFailed() {
		return reasonFailed;
	}

	/**
	 * @param reasonFailed the reasonFailed to set
	 */
	public void setReasonFailed(String reasonFailed) {
		this.reasonFailed = reasonFailed;
	}

}
