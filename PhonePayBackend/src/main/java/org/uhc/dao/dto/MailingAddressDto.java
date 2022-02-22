package org.uhc.dao.dto;

public class MailingAddressDto {

	private String mailingaddress;
	private String mailingCity;
	private String mailingState;
	private String mailingZip;

	public String getMailingaddress() {
		return mailingaddress.trim();
	}

	public void setMailingaddress(String mailingaddress) {
		this.mailingaddress = mailingaddress;
	}

	public String getMailingCity() {
		return mailingCity.trim();
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

	public String getMailingState() {
		return mailingState.trim();
	}

	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}

	public String getMailingZip() {
		return mailingZip.trim();
	}

	public void setMailingZip(String mailingZip) {
		this.mailingZip = mailingZip.substring(0, 5);
	}

}
