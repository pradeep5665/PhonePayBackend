/* 
 * ===========================================================================
 * File Name CancelPaymentService.java
 * 
 * Created on Jul 11, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: CancelPaymentService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.envelop.request.CancelPaymentRequest;
import org.uhc.envelop.response.CancelPaymentResponse;

/**
 * @author nehas3
 * @date June 06, 2019
 */
public interface CancelPaymentService {

	/**
	 * @author nehas3
	 * @date June 06, 2019
	 * @return CancelPaymentResponse 
	 * @param cancelPaymentRequest
	 * @Description : this is cancelPayment method that is used to cancel the scheduled payment on basis of requested parameters.
	 */
	CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest);
}
