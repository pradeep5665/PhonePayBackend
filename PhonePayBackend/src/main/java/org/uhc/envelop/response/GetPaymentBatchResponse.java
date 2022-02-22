/**
 * 
 */
package org.uhc.envelop.response;

import org.uhc.bean.ProcessingPaymentsResponse;

/**
 * @author nehas3
 *
 */
public class GetPaymentBatchResponse {
	private Boolean isSuccessful;
	private String message;
	private ProcessingPaymentsResponse ppsResponse;

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

}
