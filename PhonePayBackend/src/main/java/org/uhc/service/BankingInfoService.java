/* 
 * ===========================================================================
 * File Name BankingInfoService.java
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
 * $Log: BankingInfoService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.envelop.request.BankingInfoRequest;
import org.uhc.envelop.response.BankingInfoResponse;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
public interface BankingInfoService {

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param bankingInfoRequest
	 * @return BankingInfoResponse
	 * @Description : this is the banking info service method created to get the
	 *              banking information of user.
	 */
	 BankingInfoResponse getBankingInfo(BankingInfoRequest bankingInfoRequest);
}
