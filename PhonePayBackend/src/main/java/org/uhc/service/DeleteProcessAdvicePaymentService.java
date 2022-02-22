/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.request.DeleteProcessAdvicePaymentReq;
import org.uhc.envelop.response.DeleteProcessAdvicePaymentRes;

/**
 * @author pradeepy
 *
 */
public interface DeleteProcessAdvicePaymentService {
	
	 DeleteProcessAdvicePaymentRes deleteProcessAdvicePayment(DeleteProcessAdvicePaymentReq deletProcessAdvicePaymentReq);

}
