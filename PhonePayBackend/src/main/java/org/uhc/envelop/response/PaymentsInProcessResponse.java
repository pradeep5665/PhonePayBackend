/**
 * 
 */
package org.uhc.envelop.response;

import java.util.List;

import org.uhc.dao.dto.PaymentsInProcessActualList;

/**
 * @author nehas3
 *
 */
public class PaymentsInProcessResponse {

	private Boolean isSuccessful;
	private String message;
	private List<PaymentsInProcessActualList> paymentInProcessList;

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
	
	public List<PaymentsInProcessActualList> getPaymentInProcessList() {
		return paymentInProcessList;
	}

	public void setPaymentInProcessList(List<PaymentsInProcessActualList> paymentInProcessList) {
		this.paymentInProcessList = paymentInProcessList;
	}

}
