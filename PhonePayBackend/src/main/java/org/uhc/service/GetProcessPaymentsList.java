/**
 * 
 */
package org.uhc.service;

import org.uhc.bean.ProcessingPaymentsNotificationResponse;
import org.uhc.envelop.request.ProcessPaymentPrintLetterReq;
import org.uhc.envelop.request.ProcessPaymentSendEmailReq;
import org.uhc.envelop.response.ProcessPaymentsResponse;

/**
 * @author nehas3
 *
 */
public interface GetProcessPaymentsList {
	ProcessPaymentsResponse getProcessPaymentsList();
	ProcessingPaymentsNotificationResponse getProcessPaymentsNotificationListAPI(ProcessPaymentSendEmailReq processPaymentSendEmailReq);
	ProcessingPaymentsNotificationResponse getProcessPaymentsLetterNotificationList(ProcessPaymentPrintLetterReq paymentPrintLetterReq);
}


