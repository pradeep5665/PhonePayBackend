/* 
 * ===========================================================================
 * File Name LoanDto.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: LoanDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public class LoanInfoDto {
	private long loanNumber;
	private BigDecimal unpaidPrincipalBalance;
	private BigDecimal interestRate;
	private String monthlyPayment;
	private String escrow;
	private String principalAndInterest;
	private String nextDue;
	private long totalPrincipalAmount;
	private String lateFees;
	private String nsfFees;
	private BigDecimal escrowBalance;
	private BigDecimal escrowAdvance;
	private boolean stopPayment;
	private String stopDesc;
	private String confirmationNumber;

	public long getLoanNumber() {
		return loanNumber;
	}

	public String getEncryptedLoanNumber() {
		StringBuilder encryptedLoanNumber = new StringBuilder();

		String loanNum = new Long(this.loanNumber).toString();
		encryptedLoanNumber.append(loanNum.substring(0, 4));
		for (int index = 4; index < loanNum.length(); index++) {
			encryptedLoanNumber.append("*");
		}
		return encryptedLoanNumber.toString();
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public BigDecimal getUnpaidPrincipalBalance() {
		return unpaidPrincipalBalance;
	}

	public void setUnpaidPrincipalBalance(BigDecimal unpaidPrincipalBalance) {
		this.unpaidPrincipalBalance = unpaidPrincipalBalance;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public BigDecimal getInterestRatePercent() {
		return interestRate.multiply(new BigDecimal(100)).stripTrailingZeros();
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public String getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(BigDecimal monthlyPayment) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.monthlyPayment = df.format(monthlyPayment);
	}

	public String getEscrow() {
		return escrow;
	}

	public void setEscrow(BigDecimal escrow) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.escrow = df.format(escrow);
	}

	public String getPrincipalAndInterest() {
		return principalAndInterest;
	}

	public void setPrincipalAndInterest(BigDecimal principalAndInterest) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.principalAndInterest = df.format(principalAndInterest);
	}

	public String getNextDue() {
		return nextDue;
	}

	public void setNextDue(Date nextDue) {
		if (nextDue != null) {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			this.nextDue = sdf.format(nextDue);
		} else {
			this.nextDue = null;
		}
	}

	public long getTotalPrincipalAmount() {
		return totalPrincipalAmount;
	}

	public void setTotalPrincipalAmount(long totalPrincipalAmount) {
		this.totalPrincipalAmount = totalPrincipalAmount;
	}

	public String getLateFees() {
		return lateFees;
	}

	public void setLateFees(BigDecimal lateFees) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.lateFees = df.format(lateFees);
	}

	public String getNsfFees() {
		return nsfFees;
	}

	public void setNsfFees(BigDecimal nsfFees) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.nsfFees = df.format(nsfFees);
	}

	public BigDecimal getEscrowBalance() {
		return escrowBalance;
	}

	public void setEscrowBalance(BigDecimal escrowBalance) {
		this.escrowBalance = escrowBalance;
	}

	public BigDecimal getEscrowAdvance() {
		return escrowAdvance;
	}

	public void setEscrowAdvance(BigDecimal escrowAdvance) {
		this.escrowAdvance = escrowAdvance;
	}

	public boolean isStopPayment() {
		return stopPayment;
	}

	public void setStopPayment(boolean stopPayment) {
		this.stopPayment = stopPayment;
	}

	public String getStopDesc() {return stopDesc; }

	public void setStopDesc(String stopDesc){ this.stopDesc = stopDesc; }

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

}
