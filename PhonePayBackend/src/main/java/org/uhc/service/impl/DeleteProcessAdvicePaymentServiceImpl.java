/**
 * 
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.envelop.request.DeleteProcessAdvicePaymentReq;
import org.uhc.envelop.response.DeleteProcessAdvicePaymentRes;
import org.uhc.service.DeleteProcessAdvicePaymentService;

/**
 * @author pradeepy
 *
 */
@Service
public class DeleteProcessAdvicePaymentServiceImpl implements  DeleteProcessAdvicePaymentService {

	private static final Logger LOGGER = LogManager.getLogger(DeleteProcessAdvicePaymentServiceImpl.class.getName());
	
	@Autowired
	UserDao userDao;
	
	@Override
	public DeleteProcessAdvicePaymentRes deleteProcessAdvicePayment(DeleteProcessAdvicePaymentReq deleteProcessAdvicePaymentReq) {
		
		DeleteProcessAdvicePaymentRes deleteProcessAdvicePaymentRes = new DeleteProcessAdvicePaymentRes();
		try {
			boolean isPaymentAdviceDeleted = userDao.deleteProcessAdvicePayment(deleteProcessAdvicePaymentReq);
			if (isPaymentAdviceDeleted) {
				deleteProcessAdvicePaymentRes.setIsSuccessfull(true);
				deleteProcessAdvicePaymentRes.setMessage("Delete processed advice successfully");
				LOGGER.info("Delete processed advice updated successfully");
			} else {
				deleteProcessAdvicePaymentRes.setIsSuccessfull(false);
				deleteProcessAdvicePaymentRes.setMessage("Delete processed advice could not be updated");
				LOGGER.info("Delete processed advice could not be updated");
			}

		} catch (Exception exp) {
			deleteProcessAdvicePaymentRes.setIsSuccessfull(false);
			deleteProcessAdvicePaymentRes.setMessage("Delete processed advice could not be updated");
			LOGGER.error("processed advice could not be delete because of exception", exp);
		}

		return deleteProcessAdvicePaymentRes;
	}

}
