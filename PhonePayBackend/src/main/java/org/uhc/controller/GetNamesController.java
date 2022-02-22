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
import org.uhc.envelop.request.GetNamesRequest;
import org.uhc.service.GetNamesService;

@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class GetNamesController {

	private static final Logger LOGGER = LogManager.getLogger(GetNamesController.class);

	@Autowired
	private GetNamesService getNamesService;

	/**
	 * @author nehas3
	 * @date Dec 30, 2019
	 * @return Object
	 * @param bankingInfoRequest
	 * @return bankingInfo response got from service
	 */
	@PostMapping(value = "/getLastNames")
	public Object getLastNamesListAPI(@RequestBody GetNamesRequest getNamesRequest) {
		LOGGER.info("get Last Names API called: {}", getNamesRequest.getLastName());
		return getNamesService.getLastNamesList(getNamesRequest.getLastName());
	}
	
	@PostMapping(value = "/getFirstNames")
	public Object getFirstNamesListAPI(@RequestBody GetNamesRequest getNamesRequest) {
		LOGGER.info("get First Names API called: {}" , getNamesRequest.getFirstName());
		return getNamesService.getFirstNamesList(getNamesRequest.getFirstName());
	}
	
	@PostMapping(value = "/getFirstNamesForFirstAndLastName")
	public Object getFirstNamesListByFirstAndLastNameAPI(@RequestBody GetNamesRequest getNamesRequest) {
		LOGGER.info("get getFirstNamesForFirstAndLastName  API called: {}", getNamesRequest);
		return getNamesService.getFirstNamesListByFirstAndLastName(getNamesRequest);
	}
	
	@PostMapping(value = "/getLastNamesForFirstAndLastName")
	public Object getLastNamesListByFirstAndLastNameAPI(@RequestBody GetNamesRequest getNamesRequest) {
		LOGGER.info("get getLastNamesForFirstAndLastName API called: {}", getNamesRequest);
		return getNamesService.getLastNamesListByFirstAndLastName(getNamesRequest);
	}
	
	@PostMapping(value = "/getFirstAndLastNamesListForAutocomplete")
	public Object getFirstAndLastNamesListForAutocompleteAPI(@RequestBody GetNamesRequest getNamesRequest) {
		LOGGER.info("get getFirstAndLastNamesListForAutocompleteAPI API called: {}", getNamesRequest);
		return getNamesService.getFirstAndLastNamesListForAutocomplete(getNamesRequest.getName());
	}
}
