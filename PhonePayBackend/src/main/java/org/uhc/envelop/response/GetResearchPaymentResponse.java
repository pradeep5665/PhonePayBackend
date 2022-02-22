/**
 * 
 */
package org.uhc.envelop.response;

import org.uhc.dao.dto.GetResearchPaymentDto;

public class GetResearchPaymentResponse {
	private Boolean isSuccessful;
	private String message;
	private GetResearchPaymentDto getResearchPaymentDto;

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

	public GetResearchPaymentDto getResearchPaymentDto() {
		return getResearchPaymentDto;
	}

	public void setResearchPaymentDto(GetResearchPaymentDto researchPaymentDto) {
		this.getResearchPaymentDto = researchPaymentDto;
	}

}
