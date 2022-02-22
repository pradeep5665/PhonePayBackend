package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.envelop.request.DeleteProcessAdvicePaymentReq;
import org.uhc.envelop.request.PaymentAdviceReq;
import org.uhc.envelop.request.UpdatePaymentAdviceReq;
import org.uhc.envelop.request.UpdatePaymentAdviceTypeReq;
import org.uhc.service.AllPaymentAdviceService;
import org.uhc.service.DeleteProcessAdvicePaymentService;
import org.uhc.service.PaymentAdviceService;
import org.uhc.service.UpdatePaymentAdviceService;
import org.uhc.service.UpdatePaymentAdviceTypeService;

@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class PaymentAdviceController {
	private static final Logger LOGGER = LogManager.getLogger(PaymentAdviceController.class);

	@Autowired
	private PaymentAdviceService paymentAdviceService;
	
	@Autowired
	private AllPaymentAdviceService allPaymentAdviceService;
	
	@Autowired
	private UpdatePaymentAdviceService updatePaymentAdviceService;
	
	@Autowired
	private UpdatePaymentAdviceTypeService updatePaymentAdviceTypeService;
	
	@Autowired
	private DeleteProcessAdvicePaymentService deleteProcessAdvicePaymentService;

	/**
	 * @author nehas3
	 * @date May 8, 2019
	 * @return Object
	 * @param paymentAdviceReq
	 * @return paymentAdvice details in response
	 */
	@PostMapping(value = "/allPaymentAdvice")
	public Object allPaymentAdviceAPI() {
		LOGGER.info("allPaymentAdviceAPI advice API called");
		return allPaymentAdviceService.getAllPaymentAdviceDetails();
	}
	
	/**
	 * @author nehas3
	 * @date May 8, 2019
	 * @return Object
	 * @param paymentAdviceReq
	 * @return paymentAdvice details in response
	 */
	@PostMapping(value = "/paymentAdvice")
	public Object paymentAdviceAPI(@RequestBody PaymentAdviceReq paymentAdviceReq) {
		LOGGER.info("payment advice API called");
		return paymentAdviceService.getPaymentAdviceDetails(paymentAdviceReq);
	}

	/**
	 * @author nehas3
	 * @date May 14, 2019
	 * @return Object
	 * @param paymentAdviceReq
	 * @return paymentAdvice details in response
	 */
	@PostMapping(value = "/updatePaymentAdvice")
	public Object updatePaymentAdviceAPI(@RequestBody UpdatePaymentAdviceReq updatePaymentAdviceReq) {
		LOGGER.info("update payment advice API called");
		return updatePaymentAdviceService.updatePaymentAdvice(updatePaymentAdviceReq);
	}

	/**
	 * @author nehas3
	 * @date May 15, 2019
	 * @return Object
	 * @param updatePaymentAdviceTypeReq
	 * @return true or false by updating payment advice table.
	 */
	@PostMapping(value = "/updatePaymentAdviceType")
	public Object updatePaymentAdviceTypeAPI(@RequestBody UpdatePaymentAdviceTypeReq updatePaymentAdviceTypeReq) {
		LOGGER.info("update payment advice API called");
		return updatePaymentAdviceTypeService.updatePaymentTypeAdvice(updatePaymentAdviceTypeReq);
	}
	
	/**
	 * @author pradeepy
	 * @date Dec 10, 2020
	 * @return Object
	 * @param DeletProcessAdvicePaymentReq
	 * @return true or false by delete processAdvicePayment advice table.
	 */
	@PostMapping(value = "/deletProcessAdvicePayment")
	public Object deletProcessAdvicePaymentAPI(@RequestBody DeleteProcessAdvicePaymentReq deletProcessAdvicePaymentReq) {
		LOGGER.info("Delete arocess advice payment API called.");
		return deleteProcessAdvicePaymentService.deleteProcessAdvicePayment(deletProcessAdvicePaymentReq);
	}
	
}
