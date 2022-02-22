/**
 * 
 */
package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.service.CheckStopFileExistanceService;

/**
 *  @author nehas3
 *  @date July 09, 2019
 *  @Description : This is CheckStopFileExistanceController class to check if loan account in stop file
 */
@RestController
@RequestMapping(value = "/")
public class CheckStopFileExistanceController {
	
private static final Logger LOGGER = LogManager.getLogger(CheckStopFileExistanceController.class);
	
	@Autowired
	private CheckStopFileExistanceService checkStopFileExistanceService;
	
	/**
	 * @author nehas3
	 * @date June 06, 2019
	 * @return Object 
	 * @param cancelPaymentRequest
	 * @return cancelPaymentResponse
	 * @Description This is Cancel Payment service API to cancel the payment
	 */
	@PostMapping(value = "/isLoanExistStopFile/{loanNumber}")
	public Object isLoanAccountInStopFileAPI(@PathVariable long loanNumber) {
		LOGGER.info("Cancel Payment Service API is called: {}", loanNumber);
		return checkStopFileExistanceService.isLoanAccountInStopFile(loanNumber);
	}

}
