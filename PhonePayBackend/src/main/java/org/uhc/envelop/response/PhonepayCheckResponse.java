package org.uhc.envelop.response;

import java.util.List;

import org.uhc.dao.dto.GetScheduledPaymentListDto;
import org.uhc.dao.dto.LoanInfoDto;
import org.uhc.dao.dto.MailingAddressDto;
import org.uhc.dao.dto.PhonePayFeeDto;
import org.uhc.dao.dto.PropertyDto;

public class PhonepayCheckResponse {

	private Boolean isSucessfull;
	private Boolean isPaymentScheduled;
	private String message;
	private String borrowerName;
	private String borrowerLastName;
	private String borrowerEmail;
	private String coBorrower;

	private String coBorrowerLastName;
	private String coBorrowerEmail;
	private String phoneNumber;
	private List<LoanInfoDto> loanInfoList;
	private List<GetScheduledPaymentListDto> schedulePaymentList;
	private PropertyDto propertyInfo;
	private MailingAddressDto mailingAddress;
	private List<PhonePayFeeDto> phonePayFeeList;

	public Boolean getIsSucessfull() {
		return isSucessfull;
	}

	public void setIsSucessfull(Boolean isSucessfull) {
		this.isSucessfull = isSucessfull;
	}

	public Boolean getIsPaymentScheduled() {
		return isPaymentScheduled;
	}

	public void setIsPaymentScheduled(Boolean isPaymentScheduled) {
		this.isPaymentScheduled = isPaymentScheduled;
	}

	/*public Boolean getIsStopFile() {
		return isStopFile;
	}*/

	/*public void setIsStopFile(Boolean isStopFile) {
		this.isStopFile = isStopFile;
	}*/

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getCoBorrower() {
		return coBorrower;
	}

	public String getBorrowerLastName() {
		return borrowerLastName;
	}

	public void setBorrowerLastName(String borrowerLastName) {
		this.borrowerLastName = borrowerLastName;
	}

	public String getBorrowerEmail() {
		return borrowerEmail;
	}

	public void setBorrowerEmail(String borrowerEmail) {
		this.borrowerEmail = borrowerEmail;
	}

	public String getCoBorrowerEmail() {
		return coBorrowerEmail;
	}

	public void setCoBorrowerEmail(String coBorrowerEmail) {
		this.coBorrowerEmail = coBorrowerEmail;
	}

	public String getCoBorrowerLastName() {
		return coBorrowerLastName;
	}

	public void setCoBorrowerLastName(String coBorrowerLastName) {
		this.coBorrowerLastName = coBorrowerLastName;
	}

	public void setCoBorrower(String coBorrower) {
		this.coBorrower = coBorrower;
	}

	/*
	 * public List<LoanAccounts> getLoanAccounts() { return loanAcconts; }
	 * 
	 * public void setLoanAccounts(List<LoanAccounts> loanAmount) { this.loanAcconts
	 * = loanAmount; }
	 */

	public List<LoanInfoDto> getLoanInfoList() {
		return loanInfoList;
	}

	public void setLoanInfoList(List<LoanInfoDto> loanInfoList) {
		this.loanInfoList = loanInfoList;
	}

	public PropertyDto getPropertyInfo() {
		return propertyInfo;
	}

	public void setPropertyInfo(PropertyDto propertyInfo) {
		this.propertyInfo = propertyInfo;
	}

	public MailingAddressDto getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(MailingAddressDto mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<PhonePayFeeDto> getPhonePayFeeList() {
		return phonePayFeeList;
	}

	public void setPhonePayFeeList(List<PhonePayFeeDto> phonePayFeeList) {
		this.phonePayFeeList = phonePayFeeList;
	}

	public List<GetScheduledPaymentListDto> getSchedulePaymentList() {
		return schedulePaymentList;
	}

	public void setSchedulePaymentList(List<GetScheduledPaymentListDto> schedulePaymentList) {
		this.schedulePaymentList = schedulePaymentList;
	}

}
