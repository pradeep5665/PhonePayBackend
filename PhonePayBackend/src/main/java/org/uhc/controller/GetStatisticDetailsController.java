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
import org.uhc.envelop.request.GetStatisticDetailsRequest;
import org.uhc.service.GetStatisticDetailsService;

/**
 * @author nehas3
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class GetStatisticDetailsController {
	private static final Logger LOGGER = LogManager.getLogger(GetStatisticDetailsController.class);

	@Autowired
	private GetStatisticDetailsService statisticDetailsService;

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return Object
	 * @param bankingInfoRequest
	 * @return bankingInfo response got from service
	 */
	@PostMapping(value = "/getStatisticDetails")
	public Object getStatisticDetailsAPI(@RequestBody GetStatisticDetailsRequest statisticDetailsRequest) {
		LOGGER.info("getStatisticDetails API called: {}", statisticDetailsRequest);
		return statisticDetailsService.getStatisticDetails(statisticDetailsRequest);
	}
}
