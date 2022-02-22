/**
 * 
 */
package org.uhc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.envelop.request.GetNamesRequest;
import org.uhc.envelop.response.GetNamesResponse;
import org.uhc.service.GetNamesService;

/**
 * @author nehas3
 *
 */
@Service
public class GetNamesServiceImpl implements GetNamesService {

	private static final Logger LOGGER = LogManager.getLogger(GetNamesServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	@Override
	public GetNamesResponse getLastNamesList(String name) {
		LOGGER.info("Entering into getNamesList method");
		GetNamesResponse getNamesResponse = new GetNamesResponse();
		try {
			List<String> names = userDao.getLastNamesList(name.toUpperCase());
			if (names != null && !names.isEmpty()) {
				getNamesResponse.setIsSuccessful(true);
				getNamesResponse.setMessage("Got list of names");
				getNamesResponse.setNames(names);
			} else {
				getNamesResponse.setIsSuccessful(false);
				getNamesResponse.setMessage("Could not get list of names");
			}

		} catch (Exception exp) {
			getNamesResponse.setIsSuccessful(false);
			getNamesResponse.setMessage("Could not get list of names");
			LOGGER.error("Could not get list of names because of exception", exp);
		}

		return getNamesResponse;
	}
	
	
	@Override
	public GetNamesResponse getFirstNamesList(String name) {
		LOGGER.info("Entering into getNamesList method");
		GetNamesResponse getNamesResponse = new GetNamesResponse();
		try {
			List<String> names = userDao.getFirstNamesList(name.toUpperCase());
			if (names != null && !names.isEmpty()) {
				getNamesResponse.setIsSuccessful(true);
				getNamesResponse.setMessage("Got list of names");
				getNamesResponse.setNames(names);
			} else {
				getNamesResponse.setIsSuccessful(false);
				getNamesResponse.setMessage("Could not get list of names");
			}

		} catch (Exception exp) {
			getNamesResponse.setIsSuccessful(false);
			getNamesResponse.setMessage("Could not get list of names");
			LOGGER.error("Could not get list of names because of exception", exp);
		}

		return getNamesResponse;
	}


	@Override
	public GetNamesResponse getFirstNamesListByFirstAndLastName(GetNamesRequest getNamesRequest) {
		LOGGER.info("Entering into getFirstNamesListByFirstAndLastName method");
		GetNamesResponse getNamesResponse = new GetNamesResponse();
		try {
			List<String> names = userDao.getFirstNamesListForFirstAndLastName(getNamesRequest);
			if (names != null && !names.isEmpty()) {
				getNamesResponse.setIsSuccessful(true);
				getNamesResponse.setMessage("Got list of names");
				getNamesResponse.setNames(names);
			} else {
				getNamesResponse.setIsSuccessful(false);
				getNamesResponse.setMessage("Could not get list of names");
			}

		} catch (Exception exp) {
			getNamesResponse.setIsSuccessful(false);
			getNamesResponse.setMessage("Could not get list of names");
			LOGGER.error("Could not get list of names because of exception", exp);
		}

		return getNamesResponse;
	}


	@Override
	public GetNamesResponse getLastNamesListByFirstAndLastName(GetNamesRequest getNamesRequest) {
		LOGGER.info("Entering into getLastNamesListByFirstAndLastName method");
		GetNamesResponse getNamesResponse = new GetNamesResponse();
		try {
			List<String> names = userDao.getLastNamesListForFirstAndLastName(getNamesRequest);
			if (names != null && !names.isEmpty()) {
				getNamesResponse.setIsSuccessful(true);
				getNamesResponse.setMessage("Got list of names");
				getNamesResponse.setNames(names);
			} else {
				getNamesResponse.setIsSuccessful(false);
				getNamesResponse.setMessage("Could not get list of names");
			}

		} catch (Exception exp) {
			getNamesResponse.setIsSuccessful(false);
			getNamesResponse.setMessage("Could not get list of names");
			LOGGER.error("Could not get list of names because of exception", exp);
		}

		return getNamesResponse;
	}


	@Override
	public GetNamesResponse getFirstAndLastNamesListForAutocomplete(String name) {
		LOGGER.info("Entering into getNamesList method");
		GetNamesResponse getNamesResponse = new GetNamesResponse();
		try {
			List<String> names = userDao.getNamesList(name);
			if (names != null && !names.isEmpty()) {
				getNamesResponse.setIsSuccessful(true);
				getNamesResponse.setMessage("Got list of names");
				getNamesResponse.setNames(names);
			} else {
				getNamesResponse.setIsSuccessful(false);
				getNamesResponse.setMessage("Could not get list of names");
			}

		} catch (Exception exp) {
			getNamesResponse.setIsSuccessful(false);
			getNamesResponse.setMessage("Could not get list of names");
			LOGGER.error("Could not get list of names because of exception", exp);
		}

		return getNamesResponse;
	
	}

}
