/**
 * 
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.envelop.request.UpdatePaymentAdviceTypeReq;
import org.uhc.envelop.response.UpdatePaymentAdviceTypeRes;
import org.uhc.service.UpdatePaymentAdviceTypeService;

/**
 * @author nehas3
 *
 */
@Service
public class UpdatePaymentAdviceTypeServiceImpl implements UpdatePaymentAdviceTypeService{
	
	private static final Logger LOGGER = LogManager.getLogger(UpdatePaymentAdviceTypeServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	/**
	 * @author nehas3
	 * @date Jul 03, 2019
	 * @param updatePaymentAdviceReq
	 * @return UpdatePaymentAdviceRes
	 * @Description : this is updatePayment advice service when it gets updated by
	 *              service agent
	 */
	@Override
	public UpdatePaymentAdviceTypeRes updatePaymentTypeAdvice(UpdatePaymentAdviceTypeReq updatePaymentAdviceTypeReq) {
		UpdatePaymentAdviceTypeRes updatePaymentAdviceTypeRes = new UpdatePaymentAdviceTypeRes();
		try {
			int paymentAdviceId = userDao.getpaymentAdviceIdByName(updatePaymentAdviceTypeReq.getAdviceType());
			if(paymentAdviceId != 0) {
			boolean isPaymentAdviceUpdated = userDao.updatePaymentAdviceType(updatePaymentAdviceTypeReq, paymentAdviceId);
			if (isPaymentAdviceUpdated) {
				updatePaymentAdviceTypeRes.setIsSuccessfull(true);
				updatePaymentAdviceTypeRes.setMessage("Payment advice type updated successfully");
				LOGGER.info("Payment advice type updated successfully");
			} else {
				updatePaymentAdviceTypeRes.setIsSuccessfull(false);
				updatePaymentAdviceTypeRes.setMessage("Payment advice type could not be updated");
				LOGGER.info("Payment advice type could not be updated");
			}
			}else {
				updatePaymentAdviceTypeRes.setIsSuccessfull(false);
				updatePaymentAdviceTypeRes.setMessage("Payment advice type not found");
			}
		} catch (Exception exp) {
			updatePaymentAdviceTypeRes.setIsSuccessfull(false);
			updatePaymentAdviceTypeRes.setMessage("Payment advice type could not be updated");
			LOGGER.error("Payment advice type could not be updated because of exception", exp);
		}

		return updatePaymentAdviceTypeRes;
	}

}
