/**
 * 
 */
package org.uhc.dao.dto;

import java.util.List;

/**
 * @author nehas3
 *
 */
public class GetResearchPaymentDto {
	PropertyDto propertyInfo;
	List<Long> loanNumbers;
	private String borrowerName;
	private String borrowerLastName;
	private String coBorrowerName;
	private String coBorrowerLastName;

	List<GetScheduledPaymentListDto> paymentList;

	public PropertyDto getPropertyInfo() {
		return propertyInfo;
	}

	public void setPropertyInfo(PropertyDto propertyInfo) {
		this.propertyInfo = propertyInfo;
	}

	public List<Long> getLoanNumbers() {
		return loanNumbers;
	}

	public void setLoanNumbers(List<Long> loanNumbers) {
		this.loanNumbers = loanNumbers;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerLastName() {
		return borrowerLastName;
	}

	public void setBorrowerLastName(String borrowerLastName) {
		this.borrowerLastName = borrowerLastName;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}

	public String getCoBorrowerLastName() {
		return coBorrowerLastName;
	}

	public void setCoBorrowerLastName(String coBorrowerLastName) {
		this.coBorrowerLastName = coBorrowerLastName;
	}

	public List<GetScheduledPaymentListDto> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<GetScheduledPaymentListDto> paymentList) {
		this.paymentList = paymentList;
	}

}
