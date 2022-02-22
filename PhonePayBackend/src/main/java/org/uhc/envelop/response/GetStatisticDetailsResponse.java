/**
 * 
 */
package org.uhc.envelop.response;

import java.util.List;

import org.uhc.dao.dto.GetStatisticDetailsDto;

/**
 * @author nehas3
 *
 */
public class GetStatisticDetailsResponse {
	private Boolean isSuccessful;
	private String message;
	private List<GetStatisticDetailsDto> statisticPaymentList;

	public Boolean getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<GetStatisticDetailsDto> getStatisticPaymentList() {
		return statisticPaymentList;
	}

	public void setStatisticPaymentList(List<GetStatisticDetailsDto> statisticPaymentList) {
		this.statisticPaymentList = statisticPaymentList;
	}

}
