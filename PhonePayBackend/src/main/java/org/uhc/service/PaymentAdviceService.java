
package org.uhc.service;

import org.uhc.envelop.request.PaymentAdviceReq;
import org.uhc.envelop.response.PaymentAdviceResponse;

public interface PaymentAdviceService {

	 PaymentAdviceResponse getPaymentAdviceDetails(PaymentAdviceReq updatePaymentAdviceReq);
}
