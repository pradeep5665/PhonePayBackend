/* 
 * ===========================================================================
 * File Name PhonepayCheckService.java
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
 * $Log: PhonepayCheckService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.envelop.response.PhonepayCheckResponse;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
public interface PhonepayCheckService {

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return PhonepayCheckResponse
	 * @Description : this is the get borrowers info service method created to get the
	 *              all the necessary information of borrower that is required in making payment.
	 */
	 PhonepayCheckResponse getBorrowersInfo(long loanNumber);
}
