/* 
 * ===========================================================================
 * File Name UserDao.java
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
 * $Log: UserDao.java,v $
 * ===========================================================================
 */
package org.uhc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.uhc.bean.ProcessingPaymentsResponse;
import org.uhc.dao.dto.*;
import org.uhc.envelop.request.*;
import org.uhc.util.Constants.ScheduledPaymentType;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
@Repository("userDao")
public interface UserDao {

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return UserDto
	 * @param username
	 * @return UserDto
	 * @Description : this is getUserByUsername method to get user-name on basis of
	 *              entered user name.
	 */
	 UserDto1 getUserByUsername(String username);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return UserDto1
	 * @param userId
	 * @return UserDto1
	 * @Description : this is getUserByUserId method to get user on basis of entered
	 *              userId.
	 */
	 UserDto1 getUserByUserId(int userId);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return UserDto1
	 * @param loanNumber
	 * @return UserDto1
	 * @Description : this is getUserByLoanNumber method to get user on basis of
	 *              entered loan number.
	 */
	 List<UserDto1> getUserByLoanNumber(long loanNumber);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return List<BorrowerInfoDto>
	 * @Description : this is getBorroweListByLoanNumber method to get list of
	 *              borrowers on basis of entered loan number.
	 */
	 List<BorrowerInfoDto> getBorroweListByLoanNumber(long loanNumber);

	 List<BorrowerInfoDto> getBorroweList(long loanNumber, String lastName);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param userId
	 * @return List<LoanInfoDto>
	 * @Description : this is getLoanAccountsByUserId method to get list of loan
	 *              accounts on basis of entered userId.
	 */
	 List<LoanInfoDto> getLoanAccountsByUserId(int userId);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return LoanInfoDto
	 * @Description : this is getLoanAccountDetailsByLoanNum method to get loan
	 *              information on basis of entered loanNumber.
	 */
	 LoanInfoDto getLoanAccountDetailsByLoanNum(long loanNumber);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param userName
	 * @return List<Long>
	 * @Description : this is getLoanAccountsByUserName method to get list of loan
	 *              numbers on basis of entered userName.
	 */
	 List<Long> getLoanAccountsByUserName(String userName);

	/**
	 * @author nehas3
	 * @date July 01, 2019
	 * @param userName
	 * @return List<Long>
	 * @Description : this is getLoanAccountsByLoanNumber method to get list of loan
	 *              numbers on basis of entered loanNumber.
	 */
	 List<Long> getLoanAccountsByLoanNumber(long loanNumber);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return List<PhonePayFeeDto>
	 * @Description : this is getPhonePayFeeList method to get list of fee and its
	 *              corresponding code on basis of entered.
	 */
	 List<PhonePayFeeDto> getPhonePayFeeList();

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return PropertyDto
	 * @Description : this is getPropertyInfoByLoanNum method to get property
	 *              information of borrowers on basis of entered loanNumber.
	 */
	 PropertyDto getPropertyInfoByLoanNum(long loanNumber);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return MailingAddressDto
	 * @Description : this is getMailingAddressByLoanNum method to get mailing
	 *              address of borrowers on basis of entered loanNumber.
	 */
	 MailingAddressDto getMailingAddressByLoanNum(long loanNumber);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param routingNum
	 * @return BankingInfoDto
	 * @Description : this is getBankingInfoByRoutingNum method to get banking
	 *              information of borrowers on basis of entered routingNum.
	 */
	 BankingInfoDto getBankingInfoByRoutingNum(String routingNum);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param routingNum
	 * @return boolean
	 * @Description : this is isRoutingNumberWhiteListed method to check if routing
	 *              number whitelisted
	 */
	 boolean isRoutingNumberWhiteListed(String routingNumber);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param confirmationNumber, userId
	 * @return boolean
	 * @Description : this is doesConfirmationNumberExistForUser method to check if
	 *              confirmation number already exists
	 */
	 boolean doesConfirmationNumberExistForUser(String confirmationNumber, int userId);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param paymentType, loanNumber, canceled
	 * @return List<Integer>
	 * @Description : this is getActivePaymentByPaymentType method to check if
	 *              payment already exists before scheduling new payment
	 */
	 List<Integer> getActivePaymentByPaymentType(ScheduledPaymentType paymentType, long loanNumber, int canceled);

	 List<GetScheduledPaymentListDto> getActivePaymentByPaymentType(ScheduledPaymentType paymentType,
			long loanNumber);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param userId, scheduledCheckPaymentRequest, paymentSource, sceduleType
	 * @return boolean
	 * @Description : this is scheduleCheckPayment method to schedule payment on
	 *              basis of requested parameters
	 */
	 boolean scheduleCheckPayment(ScheduledCheckPaymentRequest scheduledCheckPaymentRequest);

	 boolean updateScheduleCheckPayment(ScheduledCheckPaymentRequest scheduledCheckPaymentRequest);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param paymentSource, loanNumber
	 * @return int
	 * @Description : this is getLastUpdatedSchedulePaymentIdByLoanNumber to get
	 *              last scheduled payment on basis of payment source and loan
	 *              number
	 */
	 int getLastUpdatedSchedulePaymentIdByLoanNumber(long loanNumber);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param paymentSource, loanNumber
	 * @return boolean
	 * @Description : this is insertFeeTableBySchedulePaymentId to insert selected
	 *              fees in fee table while scheduling payment
	 */
	 boolean insertFeeTableBySchedulePaymentId(OtherFeeDto otherFeeDto);

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param paymentSource, loanNumber
	 * @return boolean
	 * @Description : this is updateFeeTableBySchedulePaymentId to update selected
	 *              fees in fee table while scheduling payment
	 */
	 boolean updateFeeTableBySchedulePaymentId(OtherFeeDto otherFeeDto);

	/**
	 * @author nehas3
	 * @date Feb 26, 2019
	 * @return String
	 * @param schedulePaymentId
	 * @Description : this is getPaymentSourceOfScheduledPayment to get payment
	 *              source of already scheduled payment
	 */
	 String getPaymentSourceOfScheduledPayment(int schedulePaymentId);

	/**
	 * @author nehas3
	 * @date March 8, 2019
	 * @return String
	 * @param feeId
	 * @Description : this is getPaymentSourceOfScheduledPayment to get payment
	 *              source of already scheduled payment
	 */
	 String getFeeNameByFeeId(int feeId);

	/**
	 * @author nehas3
	 * @date March 14, 2019
	 * @return PhonePayFeeDto
	 * @Description : this is getFeeDetailsByFeeName to get fee details for
	 *              phone-pay
	 */
	 PhonePayFeeDto getFeeDetailsByFeeName();

	 String getPaymentAdviceType(int adviceID);

	 boolean isLoanInStopFile(String loanNumber);

	 PhonePayBankDetailsDto getPhonepayUserBankingDetails(int scheduledPaymentId);

	 boolean addPhonepayUserBankingDetails(PhonePayBankDetailsDto phonePayBankDetails);

	 boolean updatePhonepayUserBankingDetails(PhonePayBankDetailsDto phonePayBankDetails);

	 List<GetScheduledPaymentListDto> getScheduledPaymentList();

	 PaymentAdviceDto getPaymentAdvice(int scheduledPaymentId);

	 int getpaymentAdviceIdByName(String paymentAdviceType);

	 boolean addPaymentAdviceDetails(PaymentAdviceDto paymentAdviceDto);

	 boolean updatePaymentAdviceDetails(PaymentAdviceDto paymentAdviceDto);

	 List<PaymentAdviceDetailsDto> getAllPaymentAdviceDetails();

	 List<PaymentsInProcessDto> getPaymentsInProcessList();

	 List<PaymentAdviceDetailsDto> getPaymentAdviceDetails(String paymentAdviceType);

	 List<GetScheduledPaymentListDto> getResearchPaymentDetails(int paymentId);

	 List<OtherFeeDto> getOtherFeeListForPayment(int schedulePaymentId);
	
	 Integer getOtherFeeListForPaymentByID(int id);
	
	 boolean deleteOtherFeeByID(int id);

	 boolean updatePaymentAdvice(UpdatePaymentAdviceReq updatePaymentAdviceReq);

	 boolean updatePaymentAdviceType(UpdatePaymentAdviceTypeReq updatePaymentAdviceTypeReq, int adviceId);

	/**
	 * @author nehas3
	 * @date June 06, 2019
	 * @return ScheduledPaymentDto
	 * @param paymentId
	 * @return ScheduledPayment payments
	 * @Description : This is getScheduledPaymentByPaymentId method to get scheduled
	 *              payments by payment id
	 */
	GetScheduledPaymentListDto getScheduledPaymentByPaymentId(int paymentId);

	/**
	 * 
	 * @author nehas3
	 * @date June 4, 2019
	 * @return boolean
	 * @param paymentId
	 * @return true or false
	 * @Description : Checking if scheduled payments have been moved to payment
	 *              table.
	 */
	boolean hasPaymentMoved(int paymentId);

	/**
	 * @author nehas3
	 * @date Jun 4, 2018
	 * @return boolean
	 * @param paymentId
	 * @return true or false
	 * @Description : This is cancelPaymentInPaymentTableByPaymentId method to
	 *              cancel payment in payment table.
	 */
	boolean cancelPaymentInPaymentTableByPaymentId(int paymentId);

	/**
	 * @author nehas3
	 * @date Jun 4, 2018
	 * @return boolean
	 * @param paymentId
	 * @param userId
	 * @return true or false
	 * @Description : This is cancelPaymentByPaymentId method to cancel payments in
	 *              scheduled_payment table.
	 */
	boolean cancelPaymentByPaymentId(int paymentId, String canceledBy);
	
	boolean insertProcessedPaymentBatch(ProcessingPaymentsResponse paymentBatch);
	
	boolean updateProcessedPaymentsDate(List<Integer> paymentIds, int batchId);
	
	ProcessingPaymentsResponse getPaymentBatchDetails();
	
	List<ProcessingPaymentsResponse> getPaymentBatchDetailsBetweenTwoDates(GetStatisticDetailsRequest  statisticRequest);
	
	List<GetProcessPaymentsListDto> getProcessPaymentsList();
	
	List<GetProcessPaymentsListDto> getProcessedPaymentsListByPaymentBatch(int paymentBatchId);

	List<ResearchPaymentDto> getResearchPaymentInfo(ResearchPaymentRequest researchPaymentRequest);

	List<String> getLastNamesList(String name);

	List<String> getLastNamesListForFirstAndLastName(GetNamesRequest getNamesRequest);

	List<String> getFirstNamesList(String name);

	List<String> getFirstNamesListForFirstAndLastName(GetNamesRequest getNamesRequest);
	
	 int getLastUpdatedPaymentBatchId();
	
	 boolean deleteProcessAdvicePayment(DeleteProcessAdvicePaymentReq deletProcessAdvicePaymentReq);
	
	List<String> getNamesList(String name);
	
	/**
	 * @author pradeepy
	 * @date feb 3, 2022
	 * @return string
	 * @param paymentId
	 * @return string
	 * @Description : This is getProcessedByEmail method to get processedByEmail.
	 */
	
	CancelPaymentDto getProcessedByEmail(int paymentId);
	
	/**
	 * @author pradeepy
	 * @date Feb 13, 2022
	 * @param loanNumber
	 * @return List<BorrowerNameDto>
	 * @Description : this is getBorroweListByLoanNumber method to get list of
	 *              borrowers on basis of entered loan number.
	 */
	 List<BorrowerNameDto> getBorroweNameByLoanNumber(long loanNumber);

	List<GetAdviceBatchDto> getAdviceBatchInfo(GetAdviceBatchRequest getAdviceBatchRequest);

	boolean updateAdviceBatchInfo(GetAdviceBatchRequest getAdviceBatchRequest);

	boolean insertAdviceBatchInfo(GetAdviceBatchRequest getAdviceBatchRequest);
	
}
