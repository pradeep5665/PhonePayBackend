/**
 * 
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.envelop.request.ValidateUserRequest;
import org.uhc.envelop.response.ValidateUserResponse;
import org.uhc.service.ValidateUserService;

/**
 * @author nehas3
 *
 */
@Service
public class ValidateUserServiceImpl implements ValidateUserService{
	
	private static final Logger LOGGER = LogManager.getLogger(ValidateUserServiceImpl.class.getName());
	
	@Autowired
	UserDao userDao;

	@Override
	public ValidateUserResponse validateUser(ValidateUserRequest validateUserRequest) {
		LOGGER.info("Entering into validateUser method" , validateUserRequest);
		ValidateUserResponse validateUserResponse = new ValidateUserResponse();
		try {
			if(validateUserRequest.getUserId() == 287) {
				validateUserResponse.setIsSuccessful(true);
				validateUserResponse.setMessage("User is authorized to access Phone-Pay");
				LOGGER.info("User is authorized to access Phone-Pay");
			}else {
				validateUserResponse.setIsSuccessful(false);
				validateUserResponse.setMessage("User is not authorized to access Phone-Pay");
				LOGGER.info("User is not authorized to access Phone-Pay");
			}
			
		}catch(Exception exp) {
			validateUserResponse.setIsSuccessful(false);
			validateUserResponse.setMessage("User could not be validated");
			LOGGER.error("User could not be validated because of exception", exp);
		}
		
		return validateUserResponse;
	}
}
