/* 
 * ===========================================================================
 * File Name PhonepayCheckServiceImpl.java
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
 * $Log: PhonepayCheckServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BorrowerInfoDto;
import org.uhc.dao.dto.GetScheduledPaymentListDto;
import org.uhc.dao.dto.LoanInfoDto;
import org.uhc.dao.dto.MailingAddressDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.dao.dto.PaymentAdviceDto;
import org.uhc.dao.dto.PhonePayFeeDto;
import org.uhc.dao.dto.PropertyDto;
import org.uhc.dao.dto.UserDto1;
import org.uhc.envelop.response.PhonepayCheckResponse;
import org.uhc.service.PhonepayCheckService;
import org.uhc.util.ConfirmationNumberHelper;
import org.uhc.util.Constants.ScheduledPaymentType;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
@Service
public class PhonepayCheckServiceImpl implements PhonepayCheckService {

	private static final Logger LOGGER = LogManager.getLogger(PhonepayCheckServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	private String paymentSource;
	private List<GetScheduledPaymentListDto> schedulePaymentList;
	List<OtherFeeDto> feeList;
	boolean isStopFile = false;

	public static final int MAXIMUM_RE_TRY = 20;

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return PhonepayCheckResponse
	 * @Description : this is the get borrowers info service method created to get
	 *              the all the necessary information of borrower that is required
	 *              in making payment.
	 */
	@Override
	public PhonepayCheckResponse getBorrowersInfo(long loanNumber) {
		LOGGER.info("Entering into getBorrowersInfo method with loan number : {}", loanNumber);
		PhonepayCheckResponse phonePayCheckRes = new PhonepayCheckResponse();
		List<LoanInfoDto> loanAccList = new ArrayList<>();
		schedulePaymentList = new ArrayList<>();
		try {
			List<BorrowerInfoDto> borrowerInfoList = userDao.getBorroweListByLoanNumber(loanNumber);
			List<UserDto1> user = userDao.getUserByLoanNumber(loanNumber);
			if (borrowerInfoList != null && !(borrowerInfoList.isEmpty())) {
				for (BorrowerInfoDto borrowerInfo : borrowerInfoList) {
					if (borrowerInfo.getSequence() == 1) {
						phonePayCheckRes.setBorrowerName(borrowerInfo.getFirstName());
						phonePayCheckRes.setBorrowerLastName(borrowerInfo.getLastName());
						phonePayCheckRes.setPhoneNumber(borrowerInfo.getPhoneNumber());
						if (user != null && !user.isEmpty()) {
							phonePayCheckRes.setBorrowerEmail(user.get(0).getEmail());
						}
					} else if (borrowerInfo.getSequence() == 2) {
						phonePayCheckRes.setCoBorrower(borrowerInfo.getFirstName());
						phonePayCheckRes.setCoBorrowerLastName(borrowerInfo.getLastName());
						if (user != null && !user.isEmpty() && user.size()>1) {
							phonePayCheckRes.setCoBorrowerEmail(user.get(1).getEmail());
						}
					}
				}
				PropertyDto propertyInfo = userDao.getPropertyInfoByLoanNum(loanNumber);
				List<Long> loanNumbers = userDao.getLoanAccountsByLoanNumber(loanNumber);
				String paymentConfirmationNumber = null;
				int count = 0;
				if (loanNumbers != null && !loanNumbers.isEmpty()) {
					MailingAddressDto mailingAddress = userDao.getMailingAddressByLoanNum(loanNumbers.get(0));
					for (Long loanNum : loanNumbers) {
						LoanInfoDto loanInfo = userDao.getLoanAccountDetailsByLoanNum(loanNum);
						if (isThereAnActiveTodayPayment(ScheduledPaymentType.TODAY, loanNum, schedulePaymentList)) {
							count++;
							if (count == 1) {
								phonePayCheckRes.setMessage(
										"There is a pending payment for loan number "
												+ loanNum + "." + " Would you like to proceed?");
							} else if (count == 2) {
								phonePayCheckRes.setMessage("There is a pending payment for loan numbers " + loanNumbers.get(0) + ",  " + loanNumbers.get(1)
										+ "." + " Would you like to proceed?");
							}
						}
						if (user != null && !user.isEmpty()) {
							paymentConfirmationNumber = buildPaymentConfirmationNumber(user.get(0).getUserId());
						} else {
							paymentConfirmationNumber = buildPaymentConfirmationNumber(0);
						}
						String formattedConfirmationNum = ConfirmationNumberHelper
								.formatConfirmationNumber(paymentConfirmationNumber);
						loanInfo.setConfirmationNumber(formattedConfirmationNum);
						if (loanInfo.getUnpaidPrincipalBalance().intValue() != 0) {
							loanAccList.add(loanInfo);
						}
					}
					if (count > 0) {
						LOGGER.info("There is an active today-type payment.");
						phonePayCheckRes.setIsPaymentScheduled(true);
						for (GetScheduledPaymentListDto schedulePayment : schedulePaymentList) {
							feeList = userDao.getOtherFeeListForPayment(schedulePayment.getPaymentId());
							if (feeList != null && !feeList.isEmpty()) {
								for (OtherFeeDto feeDto : feeList) {
									String feeName = userDao.getFeeNameByFeeId(feeDto.getFeeCode());
									feeDto.setFeeName(feeName);
								}
								schedulePayment.setOtherFeeList(feeList);
							}
							PaymentAdviceDto paymentAdvice = userDao.getPaymentAdvice(schedulePayment.getPaymentId());
							if (paymentAdvice != null) {
								schedulePayment.setPaymentAdvice(paymentAdvice);
							} else {
								LOGGER.info("Could not get advice details");
							}
						}
					} else {
						LOGGER.info("No active today-type payment found.");
						phonePayCheckRes.setIsPaymentScheduled(false);
					}
					List<PhonePayFeeDto> phonePayFeeList = userDao.getPhonePayFeeList();
					if (phonePayFeeList != null) {
						phonePayCheckRes.setPhonePayFeeList(phonePayFeeList);
					}
					phonePayCheckRes.setIsSucessfull(true);
					if (phonePayCheckRes.getMessage() == null) {
						phonePayCheckRes.setMessage("successfully got borrower's info");
					}
					phonePayCheckRes.setSchedulePaymentList(schedulePaymentList);
					phonePayCheckRes.setLoanInfoList(loanAccList);
					phonePayCheckRes.setPropertyInfo(propertyInfo);
					phonePayCheckRes.setMailingAddress(mailingAddress);
				} else {
					phonePayCheckRes.setIsSucessfull(false);
					phonePayCheckRes.setMessage("invalid loan number");
				}
			} else {
				phonePayCheckRes.setIsSucessfull(false);
				phonePayCheckRes.setMessage("Please enter a valid loan number.");
			}
		} catch (Exception exp) {
			phonePayCheckRes.setIsSucessfull(false);
			phonePayCheckRes.setMessage("Could not get information");
			LOGGER.error("Could not get Borrowers information becasuse of exp", exp);
		}
		LOGGER.info("Exit from getBorrowersInfo method");
		return phonePayCheckRes;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param userId
	 * @return String
	 * @description this is buildPaymentConfirmationNumber method to generate unique
	 *              and numeric confirmation number
	 */
	private String buildPaymentConfirmationNumber(int userId) {
		LOGGER.info("Entering into buildPaymentConfirmationNumber method");
		String confirmationNumber;
		boolean collided = false;

		int i = 0;
		do {
			confirmationNumber = ConfirmationNumberHelper.generateConfirmationNumber();
			if (userId != 0) {
				collided = userDao.doesConfirmationNumberExistForUser(confirmationNumber, userId);
			}
		} while (collided && i++ < MAXIMUM_RE_TRY);

		if (collided) {
			throw new IllegalStateException("Unable to build payment confirmation number.");
		}

		return confirmationNumber;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param checkPayment
	 * @return boolean
	 * @description this method is created to check if there is an active today
	 *              payment available.
	 */
	public boolean isThereAnActiveTodayPayment(ScheduledPaymentType paymentType, long loanNumber,
			List<GetScheduledPaymentListDto> schedulePaymentList) {
		LOGGER.info("Entering into isThereAnActiveTodayPayment method");
		List<GetScheduledPaymentListDto> result = null;
		int count = 0;
		result = userDao.getActivePaymentByPaymentType(paymentType, loanNumber);
		if (result != null && !result.isEmpty()) {
			schedulePaymentList.add(result.get(0));
			count++;
		}
		if (count == 0) {
			LOGGER.info("No active today-type payment found.");
			return false;
		}

		LOGGER.info("There is an active today-type payment.");
		LOGGER.info("Exit From isThereAnActiveTodayPayment method");
		return true;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}

}
