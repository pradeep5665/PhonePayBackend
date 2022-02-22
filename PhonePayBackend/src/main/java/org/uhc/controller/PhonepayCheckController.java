/* 
 * ===========================================================================
 * File Name PhonepayCheckController.java
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
 * $Log: PhonepayCheckController.java,v $
 * ===========================================================================
 */
package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.service.PhonepayCheckService;

@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class PhonepayCheckController {

	private static final Logger LOGGER = LogManager.getLogger(PhonepayCheckController.class);

	@Autowired
	private PhonepayCheckService phonepayCheckService;
	
	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return Object
	 * @param loanNumber
	 * @return phone-pay check response got on basis of loan number
	 */
	@PostMapping(value = "/phonepayCheck/{loanNumber}")
	public Object phonepayCheckAPI(@PathVariable long loanNumber) {
		LOGGER.info("Phone-Pay Check API called for : {}", loanNumber);
		return phonepayCheckService.getBorrowersInfo(loanNumber);
	}
	
}