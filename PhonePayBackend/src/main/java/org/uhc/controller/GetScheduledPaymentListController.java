/* 
 * ===========================================================================
 * File Name GetScheduledPaymentListController.java
 * 
 * Created on April 9, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentListController.java,v $
 * ===========================================================================
 */
package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.service.GetScheduledPaymentListService;

/**
 * @author nehas3
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class GetScheduledPaymentListController {

	private static final Logger LOGGER = LogManager.getLogger(GetScheduledPaymentListController.class);
	
	@Autowired
	private GetScheduledPaymentListService getScheduledPaymentListService;
	
	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return Object
	 * @param loanNumber
	 * @return phone-pay check response got on basis of loan number
	 */
	@PostMapping(value = "/getScheduledPaymentList")
	public Object getScheduledPaymentListAPI() {
		LOGGER.info("getScheduledPaymentList API called for :");
		return getScheduledPaymentListService.getScheduledPaymentList();
	}
}
