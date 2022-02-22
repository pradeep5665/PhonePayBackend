/* 
 * ===========================================================================
 * File Name ScheduledCheckPaymentController.java
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
 * $Log: ScheduledCheckPaymentController.java,v $
 * ===========================================================================
 */
package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.envelop.request.CheckPaymentRequest;
import org.uhc.service.ScheduledCheckPaymentService;

@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class ScheduledCheckPaymentController {

	private static final Logger LOGGER = LogManager.getLogger(ScheduledCheckPaymentController.class);
	
	@Autowired
	private ScheduledCheckPaymentService scheduledCheckPaymentService;

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return Object
	 * @param checkPaymentRequest
	 * @return response whether payment scheduled or not
	 */
	@PostMapping(value = "/scheduleCheckPayment")
	public Object scheduleCheckPaymentAPI(@RequestBody CheckPaymentRequest checkPaymentRequest) throws Exception {
		LOGGER.info("scheduleCheckPayment API called: {}", checkPaymentRequest);
		return scheduledCheckPaymentService.scheduleCheckPayment(checkPaymentRequest);
	}
}
