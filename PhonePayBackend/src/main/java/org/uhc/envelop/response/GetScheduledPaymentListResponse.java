/**
 * 
 */
package org.uhc.envelop.response;

import java.util.List;

import org.uhc.dao.dto.GetScheduledPaymentListDto;

/**
 * @author nehas3
 *
 */
public class GetScheduledPaymentListResponse {

	private Boolean isSuccessFull;
	private String message;
	private List<GetScheduledPaymentListDto> scheduledPaymentList;

	public Boolean getIsSuccessFull() {
		return isSuccessFull;
	}

	public void setIsSuccessFull(Boolean isSuccessFull) {
		this.isSuccessFull = isSuccessFull;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<GetScheduledPaymentListDto> getScheduledPaymentList() {
		return scheduledPaymentList;
	}

	public void setScheduledPaymentList(List<GetScheduledPaymentListDto> scheduledPaymentList) {
		this.scheduledPaymentList = scheduledPaymentList;
	}

}
