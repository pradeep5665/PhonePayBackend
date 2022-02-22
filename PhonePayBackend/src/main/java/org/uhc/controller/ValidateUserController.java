/* 
 * ===========================================================================
 * File Name ValidateUserController.java
 * 
 * Created on March 06, 2019
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.envelop.request.ValidateUserRequest;
import org.uhc.service.ValidateUserService;

@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class ValidateUserController {

	private static final Logger LOGGER = LogManager.getLogger(ValidateUserController.class);

	@Autowired
	private ValidateUserService validateUserService;

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return Object
	 * @param validateUserRequest
	 * @return validating user if he/she is already logged in to UHC web app or not.
	 */
	@PostMapping(value = "/validateUser")
	public Object validateUserAPI(@RequestBody ValidateUserRequest validateUserRequest) {
		LOGGER.info("Validate user API called for : {}", validateUserRequest);
		return validateUserService.validateUser(validateUserRequest);
	}

}