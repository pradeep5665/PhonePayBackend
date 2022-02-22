/**
 * 
 */
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BorrowerInfoDto;
import org.uhc.dao.dto.GetResearchPaymentDto;
import org.uhc.dao.dto.GetScheduledPaymentListDto;
import org.uhc.dao.dto.LoanInfoDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.dao.dto.PaymentAdviceDto;
import org.uhc.dao.dto.PropertyDto;
import org.uhc.envelop.request.GetResearchPaymentRequest;
import org.uhc.envelop.response.GetResearchPaymentResponse;
import org.uhc.service.GetResearchPaymentDetailsService;

/**
 * @author nehas3
 *
 */
@Service
public class GetResearchPaymentDetailsServiceImpl implements GetResearchPaymentDetailsService{
	private static final Logger LOGGER = LogManager.getLogger(GetResearchPaymentDetailsServiceImpl.class.getName());
	
	@Autowired
	UserDao userDao;
	
	@Override
	public GetResearchPaymentResponse getResearchPaymentDetailsById(GetResearchPaymentRequest getResearchPaymentRequest) {
		LOGGER.info("Entering into getResearchPaymentDetailsById method");
		GetResearchPaymentResponse getResearchPaymentResponse = new GetResearchPaymentResponse();
		try {
			GetResearchPaymentDto getResearchPaymentDto = new GetResearchPaymentDto();
			List<GetScheduledPaymentListDto> getResearchPaymentDtoList = userDao.getResearchPaymentDetails(getResearchPaymentRequest.getPaymentId());
			LOGGER.info("Payment Details: ", getResearchPaymentDtoList );
			if(getResearchPaymentDtoList!=null && !getResearchPaymentDtoList.isEmpty()) {
				for(GetScheduledPaymentListDto researchPaymentDetail: getResearchPaymentDtoList) {
					PropertyDto propertyInfo = userDao.getPropertyInfoByLoanNum(researchPaymentDetail.getLoanNumber());
					if(propertyInfo!=null) {
						getResearchPaymentDto.setPropertyInfo(propertyInfo);	
					}
					List<Long> loanNumbers = new ArrayList<>();
					List<Long> loanNums = userDao.getLoanAccountsByLoanNumber(researchPaymentDetail.getLoanNumber());
					if(loanNums != null && !loanNums.isEmpty()) {
						for (Long loanNum : loanNums) {
							LoanInfoDto loanInfo = userDao.getLoanAccountDetailsByLoanNum(loanNum);
						 if(loanInfo != null) {
							 if (loanInfo.getUnpaidPrincipalBalance().intValue() != 0) {
								 loanNumbers.add(loanInfo.getLoanNumber());
								}
						 }
						}
						getResearchPaymentDto.setLoanNumbers(loanNumbers);
					}
					
					List<BorrowerInfoDto> borrowerInfoList = userDao.getBorroweListByLoanNumber(researchPaymentDetail.getLoanNumber());
					PaymentAdviceDto paymentAdvice = userDao.getPaymentAdvice(researchPaymentDetail.getPaymentId());
					LOGGER.info("Payment Advice: ", paymentAdvice );

					if (paymentAdvice != null /**&& paymentAdvice.isCompleted() != true **/) {
						String adviceType = userDao.getPaymentAdviceType(paymentAdvice.getAdviceId());
						if(adviceType != null) {
							researchPaymentDetail.setPaymentAdvice(paymentAdvice);
						}
					} else {
						LOGGER.info("Could not get advice details - Research");
					}
					
					for (BorrowerInfoDto borrowerInfo : borrowerInfoList) {
						if (borrowerInfo.getSequence() == 1) {
							getResearchPaymentDto.setBorrowerName(borrowerInfo.getFirstName() + " " + borrowerInfo.getLastName());
						} else if (borrowerInfo.getSequence() == 2) {
							getResearchPaymentDto.setCoBorrowerName(borrowerInfo.getFirstName() + " " + borrowerInfo.getLastName());
						}
					}
					List<OtherFeeDto> feeList = userDao.getOtherFeeListForPayment(getResearchPaymentRequest.getPaymentId());
					BigDecimal totalFeeAmount = new BigDecimal(0);
					if (!feeList.isEmpty()) {
						for (OtherFeeDto feeDetail : feeList) {
							String feeName = userDao.getFeeNameByFeeId(feeDetail.getFeeCode());
							if (feeName != null) {
								feeDetail.setFeeName(feeName);
								totalFeeAmount = totalFeeAmount.add(feeDetail.getFeeAmount());
							}
						}
						researchPaymentDetail.setOtherFeeList(feeList);
					} else {
						LOGGER.info("No other fee selected while making payment");
					}
				
					researchPaymentDetail.setTotalPayment(totalFeeAmount);
				}
				
				getResearchPaymentDto.setPaymentList(getResearchPaymentDtoList);
				getResearchPaymentResponse.setIsSuccessful(true);
				getResearchPaymentResponse.setMessage("Research data found successfully");
				getResearchPaymentResponse.setResearchPaymentDto(getResearchPaymentDto);
			}else {
				getResearchPaymentResponse.setIsSuccessful(false);
				getResearchPaymentResponse.setMessage("Research payment records not found..");
			}
		}catch(Exception exp){
			getResearchPaymentResponse.setIsSuccessful(false);
			getResearchPaymentResponse.setMessage("Could not get research payment records..");
		}
		return getResearchPaymentResponse;
	}

}
