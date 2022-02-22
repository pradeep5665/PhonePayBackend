/**
 * 
 */
package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.service.GetPaymentBatchService;

/**
 * @author nehas3
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class GetPaymentBatchController {
	private static final Logger LOGGER = LogManager.getLogger(GetPaymentBatchController.class);
	
	@Autowired
	private GetPaymentBatchService paymentBatchService;

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return Object
	 * @param bankingInfoRequest
	 * @return bankingInfo response got from service
	 */
	@PostMapping(value = "/paymentBatch")
	public Object getPaymentBatchAPI() {
		LOGGER.info("get payment batch API called");
		return paymentBatchService.getPaymentBatchDetails();
	}
}
