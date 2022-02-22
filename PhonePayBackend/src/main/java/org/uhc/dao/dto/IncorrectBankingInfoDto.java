package org.uhc.dao.dto;

public class IncorrectBankingInfoDto {

	private int userId;
	private String routingNum;
	private String accountNum;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRoutingNum() {
		return routingNum;
	}

	public void setRoutingNum(String routingNum) {
		this.routingNum = routingNum;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

}
