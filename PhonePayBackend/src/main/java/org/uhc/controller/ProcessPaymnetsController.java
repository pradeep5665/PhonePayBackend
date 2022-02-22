/**
 * 
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
import org.uhc.envelop.request.ProcessPaymentPrintLetterReq;
import org.uhc.envelop.request.ProcessPaymentSendEmailReq;
import org.uhc.service.GetProcessPaymentsList;

/**
 * @author nehas3
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class ProcessPaymnetsController {

	private static final Logger LOGGER = LogManager.getLogger(ProcessPaymnetsController.class);

	@Autowired
	private GetProcessPaymentsList getProcessPaymentsList;

	/**
	 * @author nehas3
	 * @date Sept 13, 2019
	 * @return Object
	 * @param paymentsInProcessRequest
	 * @return paymentsInProcess response got from service
	 */
	@PostMapping(value = "/getProcessPaymentsList")
	public Object getProcessPaymentsListAPI() {
		LOGGER.info("get process payments list API called");
		return getProcessPaymentsList.getProcessPaymentsList();
	}
	
	/**
	 * @author pradeepy
	 * @date June 15, 2021
	 * @return Object
	 * @param paymentsInProcessRequest
	 * @return paymentsInProcess response got from service
	 */
	@PostMapping(value = "/getProcessPaymentsNotificationList")
	public Object getProcessPaymentsNotificationListAPI(@RequestBody ProcessPaymentSendEmailReq processPaymentSendEmailReq) {
		LOGGER.info("get process payments Notification list API called");
		return getProcessPaymentsList.getProcessPaymentsNotificationListAPI(processPaymentSendEmailReq);
	}
	
	/**
	 * @author pradeepy
	 * @date June 21, 2021
	 * @return Object
	 * @param paymentsInProcessRequest
	 * @return paymentsInProcess response got from service
	 */
	@PostMapping(value = "/getProcessPaymentsLetterNotificationList")
	public Object getProcessPaymentsLetterNotificationListAPI(@RequestBody ProcessPaymentPrintLetterReq paymentPrintLetterReq) {
		LOGGER.info("get process payments letter Notification list API called");
		return getProcessPaymentsList.getProcessPaymentsLetterNotificationList(paymentPrintLetterReq);
	}

}
