/**
 * 
 */
package org.uhc.service.impl;

import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.envelop.response.CheckStopFileExistanceResponse;
import org.uhc.service.CheckStopFileExistanceService;

/**
 * @author nehas3
 * @date July 09, 2019
 */
@Service
public class CheckStopFileExistanceServiceImpl implements CheckStopFileExistanceService {

	private static final Logger LOGGER = LogManager.getLogger(CheckStopFileExistanceServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;

	/**
	 * @author nehas3
	 * @date July 09, 2019
	 * @param loanNumber
	 * @return CheckStopFileExistanceResponse
	 * @Description : this is isLoanAccountInStopFile method to check if loan is in stop file list.
	 */
	@Override
	public CheckStopFileExistanceResponse isLoanAccountInStopFile(long loanNumber) {
		LOGGER.info("Entering into isLoanAccountInStopFile method with loan number : {}", loanNumber);
		
		CheckStopFileExistanceResponse checkStopFileExistanceResponse = new CheckStopFileExistanceResponse();
		DecimalFormat df = new DecimalFormat("0000000000");
		String formattedLoanNumber = df.format(loanNumber);
	    boolean	isStopFile = userDao.isLoanInStopFile(formattedLoanNumber);
	   if(isStopFile) {
		   checkStopFileExistanceResponse.setIsStopFile(true);
	   }else {
		   checkStopFileExistanceResponse.setIsStopFile(false);
	   }
		return checkStopFileExistanceResponse;
	}

}
