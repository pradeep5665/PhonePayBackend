/* 
 * ===========================================================================
 * File Name BankingInfoServiceImpl.java
 * 
 * Created on Feb 25, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: BankingInfoServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.envelop.request.BankingInfoRequest;
import org.uhc.envelop.response.BankingInfoResponse;
import org.uhc.service.BankingInfoService;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
@Service
public class BankingInfoServiceImpl implements BankingInfoService{
	
	private static final Logger LOGGER = LogManager.getLogger(BankingInfoServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param bankingInfoRequest
	 * @return BankingInfoResponse
	 * @Description : this is the banking info service method created to get the
	 *              banking information of user.
	 */
	@Override
	public BankingInfoResponse getBankingInfo(BankingInfoRequest bankingInfoRequest) {
		LOGGER.info("Entering into getBankingInfo method");
		
		BankingInfoResponse bankingInfoResponse = new BankingInfoResponse();
		try {
			boolean isRoutingNumberWhiteListed = userDao.isRoutingNumberWhiteListed(bankingInfoRequest.getRoutingNum());
			if(isRoutingNumberWhiteListed) {
				BankingInfoDto bankingInfoDto = userDao.getBankingInfoByRoutingNum(bankingInfoRequest.getRoutingNum());
				if(bankingInfoDto != null) {
					bankingInfoResponse.setIsSuccessful(true);
					bankingInfoResponse.setMessage("successfully got the banking info");
					bankingInfoResponse.setBankingInfo(bankingInfoDto);
				}else{
					bankingInfoResponse.setIsSuccessful(false);
					bankingInfoResponse.setMessage("routing number does not exists");
				}
				
			}else {
				bankingInfoResponse.setIsSuccessful(false);
				bankingInfoResponse.setMessage("invalid routing number");
			}
			
		}catch(Exception exp) {
			bankingInfoResponse.setMessage("could not get banking info");
			bankingInfoResponse.setIsSuccessful(false);
			LOGGER.error("could not get banking info because of exception", exp);
		}
		LOGGER.info("Exit from getBankingInfo method");
		return bankingInfoResponse;
	}	
}
