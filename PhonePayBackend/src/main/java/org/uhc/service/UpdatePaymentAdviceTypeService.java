/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.request.UpdatePaymentAdviceTypeReq;
import org.uhc.envelop.response.UpdatePaymentAdviceTypeRes;

/**
 * @author nehas3
 *
 */
public interface UpdatePaymentAdviceTypeService {
	UpdatePaymentAdviceTypeRes updatePaymentTypeAdvice(UpdatePaymentAdviceTypeReq updatePaymentAdviceTypeReq);
}
