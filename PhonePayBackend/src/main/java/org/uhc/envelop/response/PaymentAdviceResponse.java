
package org.uhc.envelop.response;

import java.util.List;

import org.uhc.dao.dto.PaymentAdviceDetailsDto;

public class PaymentAdviceResponse {

	private Boolean isSuccessful;
	private String message;
	private List<PaymentAdviceDetailsDto> paymentAdviceDetailsDto;

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

	public List<PaymentAdviceDetailsDto> getPaymentAdviceDetailsDto() {
		return paymentAdviceDetailsDto;
	}

	public void setPaymentAdviceDetailsDto(List<PaymentAdviceDetailsDto> paymentAdviceDetailsDto) {
		this.paymentAdviceDetailsDto = paymentAdviceDetailsDto;
	}

}
