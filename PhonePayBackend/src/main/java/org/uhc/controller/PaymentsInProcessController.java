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
import org.uhc.service.PaymentsInProcessService;

/**
 * @author nehas3
 * @Date: Sept 13, 2019
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class PaymentsInProcessController {
	private static final Logger LOGGER = LogManager.getLogger(PaymentsInProcessController.class);
	
	@Autowired
	private PaymentsInProcessService paymentsInProcessService;

	/**
	 * @author nehas3
	 * @date Sept 13, 2019
	 * @return Object
	 * @param paymentsInProcessRequest
	 * @return paymentsInProcess response got from service
	 */
	@PostMapping(value = "/getPaymentsInProcess")
	public Object getPaymentsInProcessAPI() {
		LOGGER.info("get payments in process API called");
		return paymentsInProcessService.getPaymentsInProcessList();
	}
}
