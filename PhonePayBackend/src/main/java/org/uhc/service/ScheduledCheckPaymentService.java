/* 
 * ===========================================================================
 * File Name ScheduledCheckPaymentService.java
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
 * $Log: ScheduledCheckPaymentService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.envelop.request.CheckPaymentRequest;
import org.uhc.envelop.response.CheckPaymentResponse;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
public interface ScheduledCheckPaymentService {

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param checkPaymentRequest
	 * @return CheckPaymentResponse
	 * @Description : this is scheduleCheckPayment method created to make service agents schedule payment for borrowers.
	 */
	 CheckPaymentResponse scheduleCheckPayment(CheckPaymentRequest checkPaymentRequest);
	
}
