/* 
 * ===========================================================================
 * File Name GetScheduledPaymentListService.java
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
 * $Log: GetScheduledPaymentListService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.envelop.response.GetScheduledPaymentListResponse;

/**
 * @author nehas3
 * @date April 9, 2019
 */
public interface GetScheduledPaymentListService {

	/**
	 * @author nehas3
	 * @date April 9, 2019
	 * @return GetScheduledPaymentListResponse
	 * @Description : this is the getScheduledPaymentList service method created to get the list of
	 *              scheduled payment
	 */
	 GetScheduledPaymentListResponse getScheduledPaymentList();
}
