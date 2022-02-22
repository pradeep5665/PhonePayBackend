package org.uhc.envelop.request;

public class BankingInfoRequest {

	private String routingNum;

	public String getRoutingNum() {
		return routingNum;
	}

	public void setRoutingNum(String routingNum) {
		this.routingNum = routingNum;
	}
	@Override
	public String toString() {
		return "BankingInfoRequest [routingNum=" + routingNum + "]";
	}

}
