/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.request.UpdatePaymentAdviceReq;
import org.uhc.envelop.response.UpdatePaymentAdviceRes;

/**
 * @author nehas3
 *
 */
public interface UpdatePaymentAdviceService {

	 UpdatePaymentAdviceRes updatePaymentAdvice(UpdatePaymentAdviceReq updatePaymentAdviceReq);
}
