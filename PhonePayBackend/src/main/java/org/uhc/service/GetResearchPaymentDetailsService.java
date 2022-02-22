/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.request.GetResearchPaymentRequest;
import org.uhc.envelop.response.GetResearchPaymentResponse;

/**
 * @author nehas3
 *
 */
public interface GetResearchPaymentDetailsService {

	GetResearchPaymentResponse getResearchPaymentDetailsById(GetResearchPaymentRequest getResearchPaymentRequest);
}
