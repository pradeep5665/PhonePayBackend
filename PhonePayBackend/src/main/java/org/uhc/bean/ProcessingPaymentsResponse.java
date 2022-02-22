/**
 * 
 */
package org.uhc.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.uhc.dao.dto.FailedProcessPaymentsDto;
import org.uhc.util.Constants;

/**
 * @author nehas3
 *
 */
public class ProcessingPaymentsResponse {

	private List<Integer> successfulPayments;
	private List<FailedProcessPaymentsDto> failedPayments;

	private String status;
	private int paymentBatchId;
	private String date;

	private int numOfSuccessfulPayments;
	private BigDecimal totalSuccessfulPayments;
	private int numNonAdvicePayments;
	private BigDecimal totalNonAdvicePayments;
	private int numCashiering;
	private BigDecimal totalCashiering;
	private int numCollections;
	private BigDecimal totalCollections;
	private int numBankruptcy;
	private BigDecimal totalBankruptcy;
	private int numLossMit;
	private BigDecimal totalLossMit;
	private int numZionsTel;
	private BigDecimal totalZionsTel;
	private int numZionsTad;
	private BigDecimal totalZionsTad;
	private int numBk071;
	private BigDecimal totalBk071;
	private int numBk073;
	private BigDecimal totalBk073;

	public List<Integer> getSuccessfulPayments() {
		return successfulPayments;
	}

	public void setSuccessfulPayments(List<Integer> successfulPayments) {
		this.successfulPayments = successfulPayments;
	}

	/**
	 * @return the failedPayments
	 */
	public List<FailedProcessPaymentsDto> getFailedPayments() {
		return failedPayments;
	}

	/**
	 * @param failedPayments the failedPayments to set
	 */
	public void setFailedPayments(List<FailedProcessPaymentsDto> failedPayments) {
		this.failedPayments = failedPayments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPaymentBatchId() {
		return paymentBatchId;
	}

	public void setPaymentBatchId(int paymentBatchId) {
		this.paymentBatchId = paymentBatchId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		if (date != null) {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern(Constants.DateFormat.DATETIME_HOUR.getValue());
			this.date = sdf.format(date);
		} else {
			this.date = null;
		}
	}

	public int getNumOfSuccessfulPayments() {
		return numOfSuccessfulPayments;
	}

	public void setNumOfSuccessfulPayments(int numOfSuccessfulPayments) {
		this.numOfSuccessfulPayments = numOfSuccessfulPayments;
	}

	public BigDecimal getTotalSuccessfulPayments() {
		return totalSuccessfulPayments;
	}

	public void setTotalSuccessfulPayments(BigDecimal totalSuccessfulPayments) {
		this.totalSuccessfulPayments = totalSuccessfulPayments;
	}

	public int getNumNonAdvicePayments() {
		return numNonAdvicePayments;
	}

	public void setNumNonAdvicePayments(int numNonAdvicePayments) {
		this.numNonAdvicePayments = numNonAdvicePayments;
	}

	public BigDecimal getTotalNonAdvicePayments() {
		return totalNonAdvicePayments;
	}

	public void setTotalNonAdvicePayments(BigDecimal totalNonAdvicePayments) {
		this.totalNonAdvicePayments = totalNonAdvicePayments;
	}

	public int getNumCashiering() {
		return numCashiering;
	}

	public void setNumCashiering(int numCashiering) {
		this.numCashiering = numCashiering;
	}

	public BigDecimal getTotalCashiering() {
		return totalCashiering;
	}

	public void setTotalCashiering(BigDecimal totalCashiering) {
		this.totalCashiering = totalCashiering;
	}

	public int getNumCollections() {
		return numCollections;
	}

	public void setNumCollections(int numCollections) {
		this.numCollections = numCollections;
	}

	public BigDecimal getTotalCollections() {
		return totalCollections;
	}

	public void setTotalCollections(BigDecimal totalCollections) {
		this.totalCollections = totalCollections;
	}

	public int getNumBankruptcy() {
		return numBankruptcy;
	}

	public void setNumBankruptcy(int numBankruptcy) {
		this.numBankruptcy = numBankruptcy;
	}

	public BigDecimal getTotalBankruptcy() {
		return totalBankruptcy;
	}

	public void setTotalBankruptcy(BigDecimal totalBankruptcy) {
		this.totalBankruptcy = totalBankruptcy;
	}

	public int getNumLossMit() {
		return numLossMit;
	}

	public void setNumLossMit(int numLossMit) {
		this.numLossMit = numLossMit;
	}

	public BigDecimal getTotalLossMit() {
		return totalLossMit;
	}

	public void setTotalLossMit(BigDecimal totalLossMit) {
		this.totalLossMit = totalLossMit;
	}

	public int getNumZionsTel() {
		return numZionsTel;
	}

	public void setNumZionsTel(int numZionsTel) {
		this.numZionsTel = numZionsTel;
	}

	public BigDecimal getTotalZionsTel() {
		return totalZionsTel;
	}

	public void setTotalZionsTel(BigDecimal totalZionsTel) {
		this.totalZionsTel = totalZionsTel;
	}

	public int getNumZionsTad() {
		return numZionsTad;
	}

	public void setNumZionsTad(int numZionsTad) {
		this.numZionsTad = numZionsTad;
	}

	public BigDecimal getTotalZionsTad() {
		return totalZionsTad;
	}

	public void setTotalZionsTad(BigDecimal totalZionsTad) {
		this.totalZionsTad = totalZionsTad;
	}

	public int getNumBk071() {
		return numBk071;
	}

	public void setNumBk071(int numBk071) {
		this.numBk071 = numBk071;
	}

	public BigDecimal getTotalBk071() {
		return totalBk071;
	}

	public void setTotalBk071(BigDecimal totalBk071) {
		this.totalBk071 = totalBk071;
	}

	public int getNumBk073() {
		return numBk073;
	}

	public void setNumBk073(int numBk073) {
		this.numBk073 = numBk073;
	}

	public BigDecimal getTotalBk073() {
		return totalBk073;
	}

	public void setTotalBk073(BigDecimal totalBk073) {
		this.totalBk073 = totalBk073;
	}

}
