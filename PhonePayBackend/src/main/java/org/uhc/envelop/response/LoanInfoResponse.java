package org.uhc.envelop.response;

import java.util.List;

import org.uhc.dao.dto.LoanInfoDto;

public class LoanInfoResponse {

	private Boolean isSuccessful;
	private String message;
	private List<LoanInfoDto> loanInfoList;

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

	public List<LoanInfoDto> getLoanInfoList() {
		return loanInfoList;
	}

	public void setLoanInfoList(List<LoanInfoDto> loanInfoList) {
		this.loanInfoList = loanInfoList;
	}

}
