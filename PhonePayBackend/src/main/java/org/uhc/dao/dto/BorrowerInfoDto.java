package org.uhc.dao.dto;

public class BorrowerInfoDto {

	private long loanNumber;
	private int sequence;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String homeArea;
	private String homePrefix;
	private String homeNumber;

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getFirstName() {
		return firstName.trim();
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName.trim();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPhoneNumber() {
		return phoneNumber = this.homeArea.concat(this.homePrefix).concat(this.homeNumber);
	}

	
	public String getHomeArea() {
		return homeArea;
	}

	public void setHomeArea(int homeArea) {
		this.homeArea = Integer.toString(homeArea);
	}

	public String getHomePrefix() {
		return homePrefix;
	}

	public void setHomePrefix(int homePrefix) {
		this.homePrefix = Integer.toString(homePrefix);
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(int homeNumber) {
		if(Integer.toString(homeNumber).length()<4) {
			this.homeNumber = String.format("%04d",homeNumber);
		}else {
		this.homeNumber = Integer.toString(homeNumber);
		}
	}

}
