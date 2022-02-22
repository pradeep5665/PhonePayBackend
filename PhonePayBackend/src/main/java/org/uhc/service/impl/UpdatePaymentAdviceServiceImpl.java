/**
 * 
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.envelop.request.UpdatePaymentAdviceReq;
import org.uhc.envelop.response.UpdatePaymentAdviceRes;
import org.uhc.service.UpdatePaymentAdviceService;

@Service
public class UpdatePaymentAdviceServiceImpl implements UpdatePaymentAdviceService {

	private static final Logger LOGGER = LogManager.getLogger(UpdatePaymentAdviceServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	/**
	 * @author nehas3
	 * @date May 15, 2019
	 * @param updatePaymentAdviceReq
	 * @return UpdatePaymentAdviceRes
	 * @Description : this is updatePayment advice service when it gets updated by
	 *              service agent
	 */
	@Override
	public UpdatePaymentAdviceRes updatePaymentAdvice(UpdatePaymentAdviceReq updatePaymentAdviceReq) {

		UpdatePaymentAdviceRes updatePaymentAdviceRes = new UpdatePaymentAdviceRes();
		try {
			boolean isPaymentAdviceUpdated = userDao.updatePaymentAdvice(updatePaymentAdviceReq);
			if (isPaymentAdviceUpdated) {
				updatePaymentAdviceRes.setIsSuccessfull(true);
				updatePaymentAdviceRes.setMessage("Payment advice updated successfully");
				LOGGER.info("Payment advice updated successfully");
			} else {
				updatePaymentAdviceRes.setIsSuccessfull(false);
				updatePaymentAdviceRes.setMessage("Payment advice could not be updated");
				LOGGER.info("Payment advice could not be updated");
			}

		} catch (Exception exp) {
			updatePaymentAdviceRes.setIsSuccessfull(false);
			updatePaymentAdviceRes.setMessage("Payment advice could not be updated");
			LOGGER.error("Payment advice could not be updated because of exception", exp);
		}

		return updatePaymentAdviceRes;
	}

}
