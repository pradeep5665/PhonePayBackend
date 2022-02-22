/**
 * 
 */
package org.uhc.envelop.response;

import java.util.List;

import org.uhc.dao.dto.ResearchPaymentActualListDto;

/**
 * @author nehas3
 *
 */
public class ResearchPaymentResponse {
	private Boolean isSuccessful;
	private String message;
	private List<ResearchPaymentActualListDto> researchPaymentDto;
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

	public List<ResearchPaymentActualListDto> getResearchPaymentDto() {
		return researchPaymentDto;
	}

	public void setResearchPaymentDto(List<ResearchPaymentActualListDto> researchPaymentDto) {
		this.researchPaymentDto = researchPaymentDto;
	}
	
	
}
