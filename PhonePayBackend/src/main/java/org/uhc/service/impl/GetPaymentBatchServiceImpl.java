/**
 * 
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.bean.ProcessingPaymentsResponse;
import org.uhc.dao.UserDao;
import org.uhc.envelop.response.GetPaymentBatchResponse;
import org.uhc.service.GetPaymentBatchService;

/**
 * @author nehas3
 *
 */
@Service
public class GetPaymentBatchServiceImpl implements GetPaymentBatchService{
	private static final Logger LOGGER = LogManager.getLogger(GetPaymentBatchServiceImpl.class.getName());

	@Autowired
	UserDao userDao;
	
	@Override
	public GetPaymentBatchResponse getPaymentBatchDetails() {
		LOGGER.info("Entering into getPaymentBatchDetails method");
		
		GetPaymentBatchResponse paymentBatchResponse = new GetPaymentBatchResponse();
		ProcessingPaymentsResponse paymentBatch = null;
		try {
			paymentBatch = userDao.getPaymentBatchDetails();
			if(paymentBatch!=null) {
				paymentBatchResponse.setIsSuccessful(true);
				paymentBatchResponse.setMessage("Got payment batch successfully");
				paymentBatchResponse.setPpsResponse(paymentBatch);
			}else {
				paymentBatchResponse.setIsSuccessful(false);
				paymentBatchResponse.setMessage("Payment batch does not exist");	
			}
			
		}catch(Exception exp) {
			LOGGER.error("Exception occured while getting payment batch", exp);
			paymentBatchResponse.setIsSuccessful(false);
			paymentBatchResponse.setMessage("Could not get records to process payments");
		}
		LOGGER.info("Exit from getPaymentBatchDetails method");
		return paymentBatchResponse;
	}

}
