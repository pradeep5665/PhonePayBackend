/**
 * 
 */
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BorrowerInfoDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.dao.dto.PaymentAdviceDetailsDto;
import org.uhc.envelop.response.PaymentAdviceResponse;
import org.uhc.service.AllPaymentAdviceService;

/**
 * @author nehas3
 * @date May 8, 2019
 */
@Service
public class AllPaymentAdviceServiceImpl implements AllPaymentAdviceService {

	private static final Logger LOGGER = LogManager.getLogger(AllPaymentAdviceServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	/**
	 * @author nehas3
	 * @date May 8, 2019
	 * @return PaymentAdviceResponse
	 * @Description : this is the payment advice service method created to get list
	 *              of payment records that are under payment advice
	 */
	@Override
	public PaymentAdviceResponse getAllPaymentAdviceDetails() {
		LOGGER.info("Entering into getAllPaymentAdviceDetails method");

		PaymentAdviceResponse paymentAdviceResponse = new PaymentAdviceResponse();
		try {
			List<PaymentAdviceDetailsDto> paymentAdviceDetailsDtoList = userDao.getAllPaymentAdviceDetails();

			if (paymentAdviceDetailsDtoList != null && !paymentAdviceDetailsDtoList.isEmpty()) {

				for (PaymentAdviceDetailsDto paymentAdviceDetail : paymentAdviceDetailsDtoList) {
					List<BorrowerInfoDto> borrowerInfoList = userDao.getBorroweListByLoanNumber(paymentAdviceDetail.getLoanNumber());
					if (borrowerInfoList != null && !(borrowerInfoList.isEmpty())) {
						for (BorrowerInfoDto borrowerInfo : borrowerInfoList) {
							if (borrowerInfo.getSequence() == 1) {
								paymentAdviceDetail.setBorrowerName(borrowerInfo.getFirstName());
								paymentAdviceDetail.setBorrowerLastName(borrowerInfo.getLastName());
							} else if (borrowerInfo.getSequence() == 2) {
								paymentAdviceDetail.setCoBorrowerName(borrowerInfo.getFirstName());
								paymentAdviceDetail.setCoBorrowerLastName(borrowerInfo.getLastName());
							}
						}
					}
					
					List<OtherFeeDto> feeList = userDao.getOtherFeeListForPayment(paymentAdviceDetail.getPaymentId());
					BigDecimal totalFeeAmount = new BigDecimal(0);
					if (!feeList.isEmpty()) {
						for (OtherFeeDto feeDetail : feeList) {
							String feeName = userDao.getFeeNameByFeeId(feeDetail.getFeeCode());
							if (feeName != null && feeDetail.getFeeAmount()!=null ) {
								feeDetail.setFeeName(feeName);
								totalFeeAmount = totalFeeAmount.add(feeDetail.getFeeAmount());
							}
						}
					} else {
						LOGGER.info("No other fee selected while making payment");
					}
					paymentAdviceDetail.setTotalPayment(totalFeeAmount);
					paymentAdviceDetail.setOtherFeeList(feeList);
				}
				paymentAdviceResponse.setIsSuccessful(true);
				paymentAdviceResponse.setMessage("Successfully got payment advice details");
				paymentAdviceResponse.setPaymentAdviceDetailsDto(paymentAdviceDetailsDtoList);
			} else {
				paymentAdviceResponse.setIsSuccessful(false);
				paymentAdviceResponse.setMessage("No Payment Advice Found");
				LOGGER.error("could not get payment advice details");
			}

		} catch (Exception exp) {
			paymentAdviceResponse.setIsSuccessful(false);
			paymentAdviceResponse.setMessage("could not get payment advice details because of exception");
			LOGGER.error("could not get payment advice details because of exception", exp);
		}
		return paymentAdviceResponse;
	}
}
