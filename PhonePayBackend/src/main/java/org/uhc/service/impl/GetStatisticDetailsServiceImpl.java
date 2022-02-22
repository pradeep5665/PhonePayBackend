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
import org.uhc.bean.ProcessingPaymentsResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BorrowerInfoDto;
import org.uhc.dao.dto.GetProcessPaymentsListDto;
import org.uhc.dao.dto.GetStatisticDetailsDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.dao.dto.StatisticPaymentDto;
import org.uhc.envelop.request.GetStatisticDetailsRequest;
import org.uhc.envelop.response.GetStatisticDetailsResponse;
import org.uhc.service.GetStatisticDetailsService;

/**
 * @author nehas3
 *
 */
@Service
public class GetStatisticDetailsServiceImpl implements GetStatisticDetailsService {
	
	private static final Logger LOGGER = LogManager.getLogger(GetStatisticDetailsServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	@Override
	public GetStatisticDetailsResponse getStatisticDetails(GetStatisticDetailsRequest statisticDetailsRequest) {
		GetStatisticDetailsResponse getStatisticDetailsResponse = new GetStatisticDetailsResponse();
		List<ProcessingPaymentsResponse> paymentBatchList = null;
		List<GetProcessPaymentsListDto> processedPaymentList = null;
		List<GetStatisticDetailsDto> statisticDetailList = new ArrayList<>();
		try {
			paymentBatchList = userDao.getPaymentBatchDetailsBetweenTwoDates(statisticDetailsRequest);
			if(paymentBatchList!=null && !paymentBatchList.isEmpty()) {
				for(ProcessingPaymentsResponse paymentBatch : paymentBatchList) {
					GetStatisticDetailsDto statisticDetail = new GetStatisticDetailsDto();
					processedPaymentList = userDao.getProcessedPaymentsListByPaymentBatch(paymentBatch.getPaymentBatchId());
					if(processedPaymentList!=null && !processedPaymentList.isEmpty()) {
						List<StatisticPaymentDto> statisticPaymentList = new ArrayList<>();
						for(GetProcessPaymentsListDto paymentDetail : processedPaymentList) {
							StatisticPaymentDto statisticPayment = new StatisticPaymentDto();
							statisticPayment.setLoanNumber(paymentDetail.getLoanNumber());
							statisticPayment.setAgentName(paymentDetail.getAgentName());
							if(paymentDetail.getAdviceType()>0) {
								statisticPayment.setAdviceName(userDao.getPaymentAdviceType(paymentDetail.getAdviceType()));	
							}else {
								statisticPayment.setAdviceName("Regular Payment");
							}
							
							List<OtherFeeDto> feeList = userDao.getOtherFeeListForPayment(paymentDetail.getPaymentId());
							BigDecimal totalFeeAmount = new BigDecimal(0);
							BigDecimal waiveFee = new BigDecimal(0);
							if (!feeList.isEmpty()) {
								for (OtherFeeDto feeDetail : feeList) {
									String feeName = userDao.getFeeNameByFeeId(feeDetail.getFeeCode());
									if (feeName != null) {
										feeDetail.setFeeName(feeName);
										totalFeeAmount = totalFeeAmount.add(feeDetail.getFeeAmount());
										if(feeName.equalsIgnoreCase("PhonePay Fee") && feeDetail.getFeeAmount().compareTo(BigDecimal.ZERO) != 0) {
											waiveFee = feeDetail.getFeeAmount();
										}
									}
								}
							} else {
								LOGGER.info("No other fee selected while making payment");
							}
							statisticPayment.setTotalPayment(totalFeeAmount);
							statisticPayment.setWaiveFee(waiveFee);
							statisticPayment.setDateAgent(paymentBatch.getDate());
							List<BorrowerInfoDto> borrowerInfoList = userDao.getBorroweListByLoanNumber(paymentDetail.getLoanNumber());
							if (borrowerInfoList != null && !(borrowerInfoList.isEmpty())) {
								for (BorrowerInfoDto borrowerInfo : borrowerInfoList) {
									if (borrowerInfo.getSequence() == 1) {
										statisticPayment.setBorrowerName(borrowerInfo.getFirstName());
										statisticPayment.setBorrowerLastName(borrowerInfo.getLastName());
									} else if (borrowerInfo.getSequence() == 2) {
										statisticPayment.setCoBorrowerName(borrowerInfo.getFirstName());
										statisticPayment.setCoBorrowerLastName(borrowerInfo.getLastName());
									}
								}
							}
						
							statisticPaymentList.add(statisticPayment);
						}
						
						statisticDetail.setPaymentDto(statisticPaymentList);
						
					}
					statisticDetail.setPaymentCount(paymentBatch.getNumOfSuccessfulPayments());
					statisticDetail.setTotalPayment(paymentBatch.getTotalSuccessfulPayments());
					statisticDetail.setDate(paymentBatch.getDate());
					statisticDetail.setDateAgent(paymentBatch.getDate());
					statisticDetailList.add(statisticDetail);
				}
				
				if(statisticDetailList!=null && !statisticDetailList.isEmpty()) {
					getStatisticDetailsResponse.setIsSuccessful(true);
					getStatisticDetailsResponse.setMessage("Got statistic details successfully");
					getStatisticDetailsResponse.setStatisticPaymentList(statisticDetailList);
				}else {
					getStatisticDetailsResponse.setIsSuccessful(false);
					getStatisticDetailsResponse.setMessage("Statistic details is not available");
				}
				
			}else {
				getStatisticDetailsResponse.setIsSuccessful(false);
				getStatisticDetailsResponse.setMessage("Statistic details is not available for selected date range");
			}
			
		}catch(Exception exp) {
			getStatisticDetailsResponse.setIsSuccessful(false);
			getStatisticDetailsResponse.setMessage("Could not get statistic details");
			LOGGER.error("Could not get statistic details due to exception", exp);
		}
		
		return getStatisticDetailsResponse;
	}

}
