package org.uhc.envelop.response;

import org.uhc.dao.dto.BankingInfoDto;

public class BankingInfoResponse {

	private Boolean isSuccessful;
	private String message;
	private BankingInfoDto bankingInfo;
	
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
	public BankingInfoDto getBankingInfo() {
		return bankingInfo;
	}
	public void setBankingInfo(BankingInfoDto bankingInfo) {
		this.bankingInfo = bankingInfo;
	}
	
}
