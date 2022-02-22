/* 
 * ===========================================================================
 * File Name BankingInfoDto.java
 * 
 * Created on Feb 25, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: BankingInfoDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
public class BankingInfoDto {
	private String routingNumber;
	private String bankName;
	private String bankAddress;
	private String bankCity;
	private String bankCountry;
	private String bankZip;
	private String bankPhoneNumber;

	public String getRoutingNumber() {
		return routingNumber.trim();
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getBankName() {
		return bankName.trim();
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddress() {
		return bankAddress.trim();
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBankCity() {
		return bankCity.trim();
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankCountry() {
		return bankCountry.trim();
	}

	public void setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
	}

	public String getBankZip() {
		return bankZip.trim();
	}

	public void setBankZip(String bankZip) {
		this.bankZip = bankZip;
	}

	public String getBankPhoneNumber() {
		return bankPhoneNumber.trim();
	}

	public void setBankPhoneNumber(String bankPhoneNumber) {
		this.bankPhoneNumber = bankPhoneNumber;
	}

}
