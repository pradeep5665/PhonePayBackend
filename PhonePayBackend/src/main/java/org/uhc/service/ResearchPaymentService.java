/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.request.ResearchPaymentRequest;
import org.uhc.envelop.response.ResearchPaymentResponse;

/**
 * @author nehas3
 *
 */
public interface ResearchPaymentService {

	ResearchPaymentResponse getResearchPaymentInfo(ResearchPaymentRequest researchPaymentRequest);
}
