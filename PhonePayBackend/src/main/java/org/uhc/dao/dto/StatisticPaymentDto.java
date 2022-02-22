/**
 * 
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;

/**
 * @author nehas3
 *
 */
public class StatisticPaymentDto {

	private String borrowerName;
	private String borrowerLastName;
	private String coBorrowerName;
	private String coBorrowerLastName;
	private long loanNumber;
	private BigDecimal totalPayment;
	private String agentName;
	private String adviceName;
	private BigDecimal waiveFee;
	private String dateAgent;
	public BigDecimal getWaiveFee() {
		return waiveFee;
	}

	public void setWaiveFee(BigDecimal waiveFee) {
		this.waiveFee = waiveFee;
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

	public String getBorrowerLastName() {
		return borrowerLastName;
	}

	public void setBorrowerLastName(String borrowerLastName) {
		this.borrowerLastName = borrowerLastName;
	}

	public String getCoBorrowerLastName() {
		return coBorrowerLastName;
	}

	public void setCoBorrowerLastName(String coBorrowerLastName) {
		this.coBorrowerLastName = coBorrowerLastName;
	}

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAdviceName() {
		return adviceName;
	}

	public void setAdviceName(String adviceName) {
		this.adviceName = adviceName;
	}

	public String getDateAgent() {
		return dateAgent;
	}

	public void setDateAgent(String dateAgent) {
		if (dateAgent != null) {
			this.dateAgent = dateAgent.substring(0, dateAgent.length()-9);
		} else {
			this.dateAgent = null;
		}
	}
	

}
