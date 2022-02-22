/**
 * 
 */
package org.uhc.envelop.response;

import java.util.List;

import org.uhc.bean.ProcessingPaymentsResponse;
import org.uhc.dao.dto.GetProcessPaymentsListDto;

/**
 * @author nehas3
 *
 */
public class ProcessPaymentsResponse {

	private Boolean isSuccessful;
	private String message;
	private ProcessingPaymentsResponse ppsResponse;
	
	private List<Integer> successfulIds;
	private boolean updatedForNoti;
	List<GetProcessPaymentsListDto> processPaymentsList;

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

	public ProcessingPaymentsResponse getPpsResponse() {
		return ppsResponse;
	}

	public void setPpsResponse(ProcessingPaymentsResponse ppsResponse) {
		this.ppsResponse = ppsResponse;
	}

	public List<Integer> getSuccessfulIds() {
		return successfulIds;
	}

	public void setSuccessfulIds(List<Integer> successfulIds) {
		this.successfulIds = successfulIds;
	}
	public boolean isUpdatedForNoti() {
		return updatedForNoti;
	}

	public void setUpdatedForNoti(boolean updatedForNoti) {
		this.updatedForNoti = updatedForNoti;
	}

	public List<GetProcessPaymentsListDto> getProcessPaymentsList() {
		return processPaymentsList;
	}

	public void setProcessPaymentsList(List<GetProcessPaymentsListDto> processPaymentsList) {
		this.processPaymentsList = processPaymentsList;
	}
	
	

}
