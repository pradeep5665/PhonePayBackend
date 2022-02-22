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
public class GetStatisticDetailsDto {
	private String date;
	private int paymentCount;
	private BigDecimal totalPayment;
	private String dateAgent;
	List<StatisticPaymentDto> paymentDto;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(int paymentCount) {
		this.paymentCount = paymentCount;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public List<StatisticPaymentDto> getPaymentDto() {
		return paymentDto;
	}

	public void setPaymentDto(List<StatisticPaymentDto> paymentDto) {
		this.paymentDto = paymentDto;
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

	@Override
	public String toString() {
		return "GetStatisticDetailsDto [date=" + date + ", paymentCount=" + paymentCount + ", totalPayment="
				+ totalPayment + ", dateAgent=" + dateAgent + ", paymentDto=" + paymentDto + "]";
	}

	
}
