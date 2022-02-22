
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanInfoDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.dao.dto.PaymentsInProcessActualList;
import org.uhc.dao.dto.PaymentsInProcessDto;
import org.uhc.envelop.response.PaymentsInProcessResponse;
import org.uhc.service.PaymentsInProcessService;

/**
 * @author nehas3
 * @date Sept 16, 2019
 */
@Service
public class PaymentsInProcessServiceImpl implements PaymentsInProcessService {
	private static final Logger LOGGER = LogManager.getLogger(PaymentsInProcessServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	/**
	 * @author nehas3
	 * @date Sept 16, 2019
	 * @return PaymentsInProcessResponse
	 * @Description : this is the payment in process service method created to get
	 *              list of payment records that is in process
	 */
	@Override
	public PaymentsInProcessResponse getPaymentsInProcessList() {
		LOGGER.info("Entering into getPaymentsInProcessList method");
		PaymentsInProcessResponse paymentsInProcessResponse = new PaymentsInProcessResponse();
		List<PaymentsInProcessActualList> paymentsInProcessList = new ArrayList<>();
		try {
		List<PaymentsInProcessDto> paymentsInProcessListDto = userDao.getPaymentsInProcessList();	
		if(paymentsInProcessListDto != null && !paymentsInProcessListDto.isEmpty() ) {
			for(PaymentsInProcessDto paymentInProcess: paymentsInProcessListDto) {
				List<Long> loanAccountList = new ArrayList<Long>();
				List<Long> associatedLoan = new ArrayList<Long>();
				loanAccountList = userDao.getLoanAccountsByLoanNumber(paymentInProcess.getLoanNumber());
				if(loanAccountList!= null && !loanAccountList.isEmpty()) {
				for(Long loanNum: loanAccountList) {
					LoanInfoDto loanInfo = userDao.getLoanAccountDetailsByLoanNum(loanNum);
					if (loanInfo != null && loanInfo.getUnpaidPrincipalBalance().intValue() != 0) {
						associatedLoan.add(loanInfo.getLoanNumber());
					}
				}
				}
				PaymentsInProcessActualList paymentsInProcessActualList = new PaymentsInProcessActualList();
				
				if(associatedLoan!= null && !associatedLoan.isEmpty()) {
					paymentsInProcessActualList.setAssociatedLoanAccounts(associatedLoan);
				} 
				paymentsInProcessActualList.setAdvice(paymentInProcess.getAdvicePaymentId() != 0 ? true : false);
				paymentsInProcessActualList.setLoanNumber(paymentInProcess.getLoanNumber());
				paymentsInProcessActualList.setDateTime(paymentInProcess.getDateTime());
				paymentsInProcessActualList.setCollector(paymentInProcess.getEnteredBy());
				paymentsInProcessActualList.setPayorName(paymentInProcess.getPayorName());
				paymentsInProcessActualList.setPayorType(paymentInProcess.getPayorType());
				paymentsInProcessActualList.setAdviceCompleted(paymentInProcess.isAdviceCompleted());
				paymentsInProcessActualList.setCancelled(paymentInProcess.isCancelled());
				List<OtherFeeDto> feeList = userDao.getOtherFeeListForPayment(paymentInProcess.getPaymentId());
				BigDecimal totalFeeAmount = new BigDecimal(0);
				if(!feeList.isEmpty()) {
					for(OtherFeeDto feeDetail: feeList) {
						String feeName = userDao.getFeeNameByFeeId(feeDetail.getFeeCode());
						if(feeName != null && feeDetail.getFeeAmount() != null) {
							feeDetail.setFeeName(feeName);
							totalFeeAmount = totalFeeAmount.add(feeDetail.getFeeAmount());
						}
					}
				}else {
					LOGGER.info("No other fee selected while making payment");
				}
				paymentsInProcessActualList.setTotalPayment(totalFeeAmount);
				
				paymentsInProcessList.add(paymentsInProcessActualList);
			}
			
			if(!paymentsInProcessList.isEmpty() && paymentsInProcessList!= null) {
				paymentsInProcessResponse.setIsSuccessful(true);
				paymentsInProcessResponse.setMessage("successfully got payments in process list");
				paymentsInProcessResponse.setPaymentInProcessList(paymentsInProcessList);
			}else {
				paymentsInProcessResponse.setIsSuccessful(false);
				paymentsInProcessResponse.setMessage("could not get payments in process list");
			}
		}else {
			paymentsInProcessResponse.setIsSuccessful(false);
			paymentsInProcessResponse.setMessage("no pending payments are available");
		}	
		}catch(Exception exp) {
			paymentsInProcessResponse.setIsSuccessful(false);
			paymentsInProcessResponse.setMessage("could not get payments in process list");
			LOGGER.error("could not get payments in process list because of exception", exp);
		}
		
		LOGGER.info("Exit from getPaymentsInProcessList method");
		return paymentsInProcessResponse;
	}

}
