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
import org.uhc.envelop.request.GetResearchPaymentRequest;
import org.uhc.envelop.request.ResearchPaymentRequest;
import org.uhc.service.GetResearchPaymentDetailsService;
import org.uhc.service.ResearchPaymentService;

@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class ResearchPaymentController {
	private static final Logger LOGGER = LogManager.getLogger(ResearchPaymentController.class);

	@Autowired
	private ResearchPaymentService researchPaymentService;
	
	@Autowired
	private GetResearchPaymentDetailsService getResearchPaymentService;

	/**
	 * @author nehas3
	 * @date Oct 10, 2019
	 * @return Object
	 * @param researchPaymentRequest
	 * @return researchPayment response got from service
	 */
	@PostMapping(value = "/researchPayment")
	public Object researchPaymentInfoAPI(@RequestBody ResearchPaymentRequest researchPaymentRequest) {
		LOGGER.info("ResearchPayment Info API called: {}", researchPaymentRequest.toString());
		return researchPaymentService.getResearchPaymentInfo(researchPaymentRequest);
	}
	
	@PostMapping(value = "/getResearchPaymentDetails")
	public Object getResearchPaymentInfoAPI(@RequestBody GetResearchPaymentRequest getResearchPaymentRequest) {
		LOGGER.info("GetResearchPaymentInfoAPI Info API called: {}", getResearchPaymentRequest.toString());
		return getResearchPaymentService.getResearchPaymentDetailsById(getResearchPaymentRequest);
	}
}
