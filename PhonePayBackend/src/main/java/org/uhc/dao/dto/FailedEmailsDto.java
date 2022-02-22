package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.util.List;

public class FailedEmailsDto {
	
	private long loanNumber;
	private BigDecimal totalFee = BigDecimal.ZERO;
	private String payerName;
	
	public long getLoanNumber() {
		return loanNumber;
	}
	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	
	
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

}
