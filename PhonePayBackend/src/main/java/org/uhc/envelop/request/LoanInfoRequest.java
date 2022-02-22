package org.uhc.envelop.request;

import java.util.List;

public class LoanInfoRequest {

	private List<Long> loanNumbers;

	public List<Long> getLoanNumbers() {
		return loanNumbers;
	}

	public void setLoanNumbers(List<Long> loanNumbers) {
		this.loanNumbers = loanNumbers;
	}

	@Override
	public String toString() {
		return "LoanInfoRequest [loanNumbers=" + loanNumbers + "]";
	}

}
