/**
 * 
 */
package org.uhc.dao.dto;

/**
 * @author nehas3
 *
 */
public class PhonePayBankDetailsDto {

	private int Id;
	private int schedulePaymentId;
	private String accountNumber;
	private String routingNumber;
	private String emails;
	private String payorName;
	private String payorType;
	private String payorAddress;
	private String payorPhone;
	private String enteredBy;
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getSchedulePaymentId() {
		return schedulePaymentId;
	}

	public void setSchedulePaymentId(int schedulePaymentId) {
		this.schedulePaymentId = schedulePaymentId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getPayorName() {
		return payorName;
	}

	public void setPayorName(String payorName) {
		this.payorName = payorName;
	}

	public String getPayorType() {
		return payorType;
	}

	public void setPayorType(String payorType) {
		this.payorType = payorType;
	}

	public String getPayorAddress() {
		return payorAddress;
	}

	public void setPayorAddress(String payorAddress) {
		this.payorAddress = payorAddress;
	}

	public String getPayorPhone() {
		return payorPhone;
	}

	public void setPayorPhone(String payorPhone) {
		this.payorPhone = payorPhone;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

}
