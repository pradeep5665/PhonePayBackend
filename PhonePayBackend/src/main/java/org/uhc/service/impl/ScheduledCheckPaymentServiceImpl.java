/* 
 * ===========================================================================
 * File Name ScheduledCheckPaymentServiceImpl.java
 * 
 * Created on Feb 25, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledCheckPaymentServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanInfoDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.dao.dto.PaymentAdviceDto;
import org.uhc.dao.dto.UserDto1;
import org.uhc.envelop.request.CheckPaymentRequest;
import org.uhc.envelop.request.ScheduledCheckPaymentRequest;
import org.uhc.envelop.response.CheckPaymentResponse;
import org.uhc.service.ScheduledCheckPaymentService;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
@Service
public class ScheduledCheckPaymentServiceImpl implements ScheduledCheckPaymentService {
	private static final Logger LOGGER = LogManager.getLogger(ScheduledCheckPaymentServiceImpl.class);
	@Autowired
	UserDao userDao;

	List<ScheduledCheckPaymentRequest> scheduledCheckPaymentRequestList = new ArrayList<>();

	private String paymentSource;

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param checkPaymentRequest
	 * @return CheckPaymentResponse
	 * @Description : this is scheduleCheckPayment method created to make service
	 *              agents schedule payment for borrowers.
	 */
	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
	@Override
	public CheckPaymentResponse scheduleCheckPayment(CheckPaymentRequest checkPaymentRequest) {
		CheckPaymentResponse checkPaymentResponse = new CheckPaymentResponse();
		List<OtherFeeDto> otherFeeList = new ArrayList<>();
		try {
			synchronized (this) {
				List<UserDto1> user = userDao.getUserByLoanNumber(checkPaymentRequest.getLoanNumber());
				if (user != null && !user.isEmpty()) {
					LOGGER.info("User is registred");
				} else {
					LOGGER.info("User is not registred");
				}
				List<LoanInfoDto> loanAccList = new ArrayList<>();
				boolean isPaymentScheduled = false;
				int schedulePaymentId = 0;
				scheduledCheckPaymentRequestList = checkPaymentRequest.getScheduledPayments();
				if (checkPaymentRequest.isPay()) {
					for (ScheduledCheckPaymentRequest scheduledCheckPaymentRequest : scheduledCheckPaymentRequestList) {
						String emails = "";
						LoanInfoDto loan = userDao
								.getLoanAccountDetailsByLoanNum(scheduledCheckPaymentRequest.getLoanNumber());
						loanAccList.add(loan);
						if (!checkPaymentRequest.getEmailAddressList().isEmpty()
								&& checkPaymentRequest.getEmailAddressList().size() > 1) {
							for (String email : checkPaymentRequest.getEmailAddressList()) {
								email = email.trim();
								emails = emails + email.concat(";");
							}
							String lastChar = emails.substring(emails.length() -1);
							if(lastChar.equals(";")){
								emails = emails.substring(0, emails.length() -1);
							}
						} else if (!checkPaymentRequest.getEmailAddressList().isEmpty()
								&& checkPaymentRequest.getEmailAddressList().size() == 1) {
							emails = checkPaymentRequest.getEmailAddressList().get(0).trim();
							String lastChar = emails.substring(emails.length() -1);
							if(lastChar.equals(";")){
								emails = emails.substring(0, emails.length() -1);
							}
						}

						scheduledCheckPaymentRequest.setEmails(emails);
						scheduledCheckPaymentRequest
								.setPrintedConfirmation(checkPaymentRequest.isPrintedConfirmation());
						if (scheduledCheckPaymentRequest.getPaymentId() == 0) {
							isPaymentScheduled = userDao.scheduleCheckPayment(scheduledCheckPaymentRequest);
						} else {
						}

						if (isPaymentScheduled) {
							otherFeeList = scheduledCheckPaymentRequest.getFeeList();
							schedulePaymentId = userDao.getLastUpdatedSchedulePaymentIdByLoanNumber(
									scheduledCheckPaymentRequest.getLoanNumber());
							if (!otherFeeList.isEmpty()) {
								if (schedulePaymentId > 0) {
									boolean isFeeTableUpdated = false;
									List<Integer> deleteFeeIds = scheduledCheckPaymentRequest.getDeleteFeeList();
									if (deleteFeeIds != null && !deleteFeeIds.isEmpty()) {
										for (int id : deleteFeeIds) {
											Integer paymentItemID = userDao.getOtherFeeListForPaymentByID(id);
											if (paymentItemID > 0) {
												boolean isFeeDeleted = userDao.deleteOtherFeeByID(paymentItemID);
												if (isFeeDeleted) {
													LOGGER.info("Fee deleted successfully");
												}
											}
										}
									}
									for (OtherFeeDto feeDto: otherFeeList) {
										if (scheduledCheckPaymentRequest.getPaymentId() == 0) {
											feeDto.setSchedulePaymentId(schedulePaymentId);
											isFeeTableUpdated = userDao.insertFeeTableBySchedulePaymentId(feeDto);
										} else { 
											List<OtherFeeDto> feeRecords = userDao.getOtherFeeListForPayment(schedulePaymentId);
											feeDto.setSchedulePaymentId(scheduledCheckPaymentRequest.getPaymentId());
											if (!feeRecords.isEmpty()) {
												boolean isFeeUpdated = false;
												for (OtherFeeDto feeRec : feeRecords) {
													if (feeRec.getId() == (feeDto.getId())) {
														
														String secloanNumber = String.valueOf(scheduledCheckPaymentRequest.getLoanNumber());
														if(scheduledCheckPaymentRequestList.size()>1) {
															
															int feeCode = feeDto.getFeeCode();
															if(secloanNumber.startsWith("9") && feeCode==17 ) {
																int paymentId = scheduledCheckPaymentRequestList.get(1).getPaymentId();
																List<OtherFeeDto> feeRecords1 = userDao.getOtherFeeListForPayment(paymentId);
																for(OtherFeeDto dt : feeRecords) {
																	if(dt.getFeeCode()==17) {
																		dt.setFeeAmount(BigDecimal.valueOf(0.00));
																		isFeeTableUpdated = userDao.updateFeeTableBySchedulePaymentId(dt);
																		isFeeUpdated = true;
																	}
																}
																
															}else {
															isFeeTableUpdated = userDao.updateFeeTableBySchedulePaymentId(feeDto);
															isFeeUpdated = true;
															}
														}
														
													}
												}
												if (!isFeeUpdated) {
													isFeeTableUpdated = userDao.insertFeeTableBySchedulePaymentId(feeDto);
												}
											} else {
												isFeeTableUpdated = userDao.insertFeeTableBySchedulePaymentId(feeDto);
											}
										}
										if (isFeeTableUpdated) {
											LOGGER.info("Fee table details inserted successfully");
										} else {
											LOGGER.info("Fee table details could not inserted");
										}
									}
								}
							} else {
								LOGGER.info("No fee is selected with payment");
							}
							if (scheduledCheckPaymentRequest.getPaymentAdviceType() != null
									&& !scheduledCheckPaymentRequest.getPaymentAdviceType().equals("")) {
								int paymentAdviceCodeId = userDao
										.getpaymentAdviceIdByName(scheduledCheckPaymentRequest.getPaymentAdviceType());
								if (paymentAdviceCodeId > 0) {
									PaymentAdviceDto paymentAdviceDto = new PaymentAdviceDto();
									paymentAdviceDto.setAdviceId(paymentAdviceCodeId);
									paymentAdviceDto.setSchedulePaymentId(schedulePaymentId);
									paymentAdviceDto.setCompleted(false);
									paymentAdviceDto
											.setAdviceNotes(scheduledCheckPaymentRequest.getPaymentAdviceNotes());
									boolean isPaymentAdviceUpdated = false;
									if (scheduledCheckPaymentRequest.getPaymentId() == 0) {
										isPaymentAdviceUpdated = userDao.addPaymentAdviceDetails(paymentAdviceDto);
									} else {
										PaymentAdviceDto paymentAdviceDetails = userDao
												.getPaymentAdvice(scheduledCheckPaymentRequest.getPaymentId());
										if (paymentAdviceDetails != null) {
											isPaymentAdviceUpdated = userDao
													.updatePaymentAdviceDetails(paymentAdviceDto);
										} else {
											isPaymentAdviceUpdated = userDao.addPaymentAdviceDetails(paymentAdviceDto);
										}
									}
									if (isPaymentAdviceUpdated) {
										LOGGER.info("Payment Advice details updated successfully");
									} else {
										LOGGER.error("Payment Advice details could not updated");
									}
								}
							}
						}
					}
					if (isPaymentScheduled) {
						checkPaymentResponse.setIsSuccessful(true);
						checkPaymentResponse.setMessage("Payment scheduled successfully");

					} else {
						checkPaymentResponse.setIsSuccessful(false);
						checkPaymentResponse.setMessage("Payment could not be scheduled");
					}
				}

			}

		} catch (RuntimeException runTimeExp) {
			LOGGER.info("Exception occured while scheduling payement because of RuntimeException", runTimeExp);
			throw runTimeExp;
		} catch (Exception exp) {
			LOGGER.info("Exception occured while scheduling payement because of ", exp);
			throw exp;
		}

		return checkPaymentResponse;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}
}
