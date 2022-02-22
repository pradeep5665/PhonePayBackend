/* 
 * ===========================================================================
 * File Name CancelPaymentServiceImpl.java
 * 
 * Created on Jul 11, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: CancelPaymentServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BorrowerNameDto;
import org.uhc.dao.dto.CancelPaymentDto;
import org.uhc.dao.dto.GetScheduledPaymentListDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.envelop.request.CancelPaymentRequest;
import org.uhc.envelop.response.CancelPaymentResponse;
import org.uhc.service.CancelPaymentService;
import org.uhc.util.EmailBean;
import org.uhc.util.EmailService;

/**
 * @author nehas3
 * @date June 06, 2019
 * @description Implementing CancelPaymentService to get the
 *              CancelPaymentResponse
 */
@Service
public class CancelPaymentServiceImpl implements CancelPaymentService {

	private static final Logger LOGGER = LogManager.getLogger(CancelPaymentServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EmailService emailService;

	String[] spCheckbox;

	/**
	 * @author nehas3
	 * @date June 06, 2019
	 * @return CancelPaymentResponse
	 * @param cancelPaymentRequest
	 * @return true or false
	 * @Description : this is cancelPayment method that is used to cancel the scheduled payment on basis of requested parameters.
	 */
	@Override
	public CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest) {
		LOGGER.info("Entering into cancelPayment method");
		CancelPaymentResponse cancelPaymentResponse = new CancelPaymentResponse();
		try{
			spCheckbox = cancelPaymentRequest.getPaymentIdOfCheckBox();
			boolean isCancelled = false;
			String processedByEmail="";
			
			//Add payment cancel payment attribute for template 
			
			String primaryLoanNumber="";
			String secondaryLoanNumber="";
			BorrowerNameDto borrowerName;
			BorrowerNameDto coborrowerName;
			String borrowerFullName = "";
			String coborrowerFullName="";
			String primaryTotalPaymentAmt="";
			String secondaryTotalPaymentAmt="";
			String primaryMonthlyPayment="";
			String secondaryMonthlyPayment="";
			String primaryLateFee="";
			String secondaryLateFee="";
			String primaryNsfFee="";
			String secondaryNsfFee="";
			String primaryAdditionalEscrow="";
			String secondaryAdditionalEscrow="";
			String primaryAdditionalPrincipal="";
			String secondaryAdditionalPrincipal="";
			String primaryCorporateAdvance="";
			String secondaryCorporateAdvance="";
			String primaryOther="";
			String secondaryOther="";
			String primaryPhonePayFee="";
			String secondaryPhonePayFee="";
			String primaryPayor="";
			String secondaryPayor="";
			String primaryBKBatchCode="";
			String secondaryBKBatchCode="";
			String transactionTotal="";
			BigDecimal totalTransationAmt = new BigDecimal(0);
			for(String scheduledPaymentId : spCheckbox){
				GetScheduledPaymentListDto scheduledPayment = userDao.getScheduledPaymentByPaymentId(Integer.parseInt(scheduledPaymentId));
				if(scheduledPayment!= null) {
					/**This checks if the payment has already moved from scheduled payment table to payment table. If so,
	                 * the payment in the payment table is also canceled.
	                 */
					
					if(userDao.cancelPaymentByPaymentId(Integer.parseInt(scheduledPaymentId), cancelPaymentRequest.getCanceledBy())){ 
						isCancelled = true; 
						CancelPaymentDto cancelPaymentDto;
						cancelPaymentDto = userDao.getProcessedByEmail(Integer.parseInt(scheduledPaymentId));
						if(cancelPaymentDto!=null) {
						processedByEmail = cancelPaymentDto.getProcessedByEmail();
						}
						List<OtherFeeDto> feeList = userDao.getOtherFeeListForPayment(Integer.parseInt(scheduledPaymentId));
						String loanNumber=String.valueOf(scheduledPayment.getLoanNumber()); 
						if(!loanNumber.startsWith("9")) {
							BigDecimal totalOtherFeeAmount = new BigDecimal(0);
							BigDecimal totalCorporateFeeAmount = new BigDecimal(0);
							BigDecimal primaryMonthlyPayments = new BigDecimal(0);
							BigDecimal primaryAdditionalPrincipals = new BigDecimal(0);
							BigDecimal primaryAdditionalEscrows = new BigDecimal(0);
							BigDecimal primaryLateFees = new BigDecimal(0);
							BigDecimal primaryNsfFees = new BigDecimal(0);
							BigDecimal primaryPhonePayFees = new BigDecimal(0);
							List<BorrowerNameDto> borrowerNameList = userDao.getBorroweNameByLoanNumber(Long.valueOf(loanNumber));
							if(borrowerNameList!=null) {
							borrowerName = borrowerNameList.get(0);
							borrowerFullName = borrowerName.getFirstName()+" "+borrowerName.getLastName();
							}
							primaryLoanNumber = loanNumber;
							primaryPayor = scheduledPayment.getPayorName();
							if(cancelPaymentDto!=null) {
							primaryBKBatchCode = cancelPaymentDto.getBatchCode();
							}
						if (feeList != null && !feeList.isEmpty()) {
							for (OtherFeeDto fee : feeList) {
								String feeName = userDao.getFeeNameByFeeId(fee.getFeeCode());
								if (feeName.equals("Monthly Payment")) {
									primaryMonthlyPayments = fee.getFeeAmount();
								} else if (feeName.equals("Additional Principal")) {
									primaryAdditionalPrincipals = fee.getFeeAmount();
								} else if (feeName.equals("Additional Escrow")) {
									primaryAdditionalEscrows = fee.getFeeAmount();
								} else if (feeName.equals("Late Fee")) {
									primaryLateFees = fee.getFeeAmount();
								} else if (feeName.equals("NSF Fee")) {
									primaryNsfFees = fee.getFeeAmount();
								} else if (feeName.equals("Other") || feeName.equals("Other Fee")) {
									totalOtherFeeAmount = totalOtherFeeAmount.add(fee.getFeeAmount());
								} else if (feeName.equals("PhonePay Fee")) {
									primaryPhonePayFees = fee.getFeeAmount();
								} else if (feeName.equals("Corporate Advance")) {
									totalCorporateFeeAmount = totalCorporateFeeAmount.add(fee.getFeeAmount());
								}
							}
							
						}
						primaryOther = totalOtherFeeAmount.toString();
						primaryCorporateAdvance = totalCorporateFeeAmount.toString();
						
						primaryMonthlyPayment = primaryMonthlyPayments.toString();
						primaryAdditionalPrincipal = primaryAdditionalPrincipals.toString();
						primaryAdditionalEscrow = primaryAdditionalEscrows.toString();
						primaryLateFee = primaryLateFees.toString();
						primaryNsfFee = primaryNsfFees.toString();
						primaryPhonePayFee = primaryPhonePayFees.toString();
						BigDecimal primaryTotalPaymentAmtSum = primaryMonthlyPayments.add(primaryAdditionalPrincipals).add(primaryAdditionalEscrows).add(primaryLateFees).add(primaryNsfFees).add(totalOtherFeeAmount).add(totalCorporateFeeAmount).add(primaryPhonePayFees);
						totalTransationAmt = primaryTotalPaymentAmtSum;
						primaryTotalPaymentAmt = primaryTotalPaymentAmtSum.toString();
						}else {
							BigDecimal totalOtherFeeAmount = new BigDecimal(0);
							BigDecimal totalCorporateFeeAmount = new BigDecimal(0);
							BigDecimal secondaryMonthlyPayments = new BigDecimal(0);
							BigDecimal secondaryAdditionalPrincipals = new BigDecimal(0);
							BigDecimal secondaryAdditionalEscrows = new BigDecimal(0);
							BigDecimal secondaryLateFees = new BigDecimal(0);
							BigDecimal secondaryNsfFees = new BigDecimal(0);
							BigDecimal secondaryPhonePayFees = new BigDecimal(0);
							secondaryLoanNumber = loanNumber;
							List<BorrowerNameDto> coborrowerNameList  = userDao.getBorroweNameByLoanNumber(Long.valueOf(secondaryLoanNumber));
							coborrowerName = coborrowerNameList.get(1);
							coborrowerFullName = coborrowerName.getFirstName()+" "+coborrowerName.getLastName();
							secondaryPayor = scheduledPayment.getPayorName();
							if(cancelPaymentDto!=null) {
							secondaryBKBatchCode = cancelPaymentDto.getBatchCode();
							}
						if (feeList != null && !feeList.isEmpty()) {
							for (OtherFeeDto fee : feeList) {
								String feeName = userDao.getFeeNameByFeeId(fee.getFeeCode());
								if (feeName.equals("Monthly Payment")) {
									secondaryMonthlyPayments = fee.getFeeAmount();
								} else if (feeName.equals("Additional Principal")) {
									secondaryAdditionalPrincipals = fee.getFeeAmount();
								} else if (feeName.equals("Additional Escrow")) {
									secondaryAdditionalEscrows = fee.getFeeAmount();
								} else if (feeName.equals("Late Fee")) {
									secondaryLateFees = fee.getFeeAmount();
								} else if (feeName.equals("NSF Fee")) {
									secondaryNsfFees = fee.getFeeAmount();
								} else if (feeName.equals("Other") || feeName.equals("Other Fee")) {
									totalOtherFeeAmount = totalOtherFeeAmount.add(fee.getFeeAmount());
								} else if (feeName.equals("PhonePay Fee")) {
									secondaryPhonePayFees = fee.getFeeAmount();
								} else if (feeName.equals("Corporate Advance")) {
									totalCorporateFeeAmount = totalCorporateFeeAmount.add(fee.getFeeAmount());
								}
							}
						}
						secondaryOther = totalOtherFeeAmount.toString();
						secondaryCorporateAdvance = totalCorporateFeeAmount.toString();
						secondaryMonthlyPayment = secondaryMonthlyPayments.toString();
						secondaryAdditionalPrincipal = secondaryAdditionalPrincipals.toString();
						secondaryAdditionalEscrow = secondaryAdditionalEscrows.toString();
						secondaryLateFee = secondaryLateFees.toString();
						secondaryNsfFee = secondaryNsfFees.toString();
						secondaryPhonePayFee = secondaryPhonePayFees.toString();
						BigDecimal secondaryTotalPaymentAmtSum = secondaryMonthlyPayments.add(secondaryAdditionalPrincipals).add(secondaryAdditionalEscrows).add(secondaryLateFees).add(secondaryNsfFees).add(totalOtherFeeAmount).add(totalCorporateFeeAmount).add(secondaryPhonePayFees);
						totalTransationAmt = totalTransationAmt.add(secondaryTotalPaymentAmtSum);
						secondaryTotalPaymentAmt = 	secondaryTotalPaymentAmtSum.toString();
						}
						if(primaryLoanNumber.equals("")) {
							primaryLoanNumber = "_ _";
						}
						if(primaryMonthlyPayment.equals("")) {
							primaryMonthlyPayment = "_ _";
						}
						if(primaryLateFee.equals("") || primaryLateFee.equals("0.00")) {
							primaryLateFee = "_ _";
						}
						if(primaryNsfFee.equals("") || primaryNsfFee.equals("0.00")){
							primaryNsfFee = "_ _";
						}
						if(primaryAdditionalEscrow.equals("") || primaryAdditionalEscrow.equals("0.00")) {
							primaryAdditionalEscrow = "_ _";
						}
						if(primaryAdditionalPrincipal.equals("") || primaryAdditionalPrincipal.equals("0.00")) {
							primaryAdditionalPrincipal="_ _";
						}
						if(primaryCorporateAdvance.equals("") || primaryCorporateAdvance.equals("0")) {
							primaryCorporateAdvance = "_ _";
						}
						if(primaryOther.equals("") || primaryOther.equals("0")) {
							primaryOther="_ _";
						}
						if(primaryPhonePayFee.equals("")) {
							primaryPhonePayFee = "_ _";
						}
						if(primaryPayor.equals("")) {
							primaryPayor = "_ _";
						}
						if(primaryBKBatchCode !=null) {
						if(primaryBKBatchCode.equals("")) {
							primaryBKBatchCode="_ _";
						}
						}else {
							if(primaryBKBatchCode==null) {
							primaryBKBatchCode="_ _";
							}
						}
						
						if(primaryTotalPaymentAmt.equals("") || primaryTotalPaymentAmt.equals("0.00")) {
							primaryTotalPaymentAmt="_ _";
						}
						if(borrowerFullName.equals("")) {
							borrowerFullName="_ _";
						}
						// set data for secondary loan
						
						if(secondaryLoanNumber.equals("")) {
							secondaryLoanNumber = "_ _";
						}
						if(secondaryMonthlyPayment.equals("")) {
							secondaryMonthlyPayment = "_ _";
						}
						if(secondaryLateFee.equals("") || secondaryLateFee.equals("0.00")) {
							secondaryLateFee = "_ _";
						}
						if(secondaryNsfFee.equals("") || secondaryNsfFee.equals("0.00")){
							secondaryNsfFee = "_ _";
						}
						if(secondaryAdditionalEscrow.equals("") || secondaryAdditionalEscrow.equals("0")) {
							secondaryAdditionalEscrow = "_ _";
						}
						if(secondaryAdditionalPrincipal.equals("") || secondaryAdditionalPrincipal.equals("0.00")) {
							secondaryAdditionalPrincipal="_ _";
						}
						if(secondaryCorporateAdvance.equals("") || secondaryCorporateAdvance.equals("0")) {
							secondaryCorporateAdvance = "_ _";
						}
						if(secondaryOther.equals("") || secondaryOther.equals("0")) {
							secondaryOther="_ _";
						}
						if(secondaryPhonePayFee.equals("") || secondaryPhonePayFee.equals("0")) {
							secondaryPhonePayFee = "_ _";
						}
						if(secondaryPayor.equals("")) {
							secondaryPayor = "_ _";
						}
						if(secondaryBKBatchCode!=null) {
						if(secondaryBKBatchCode.equals("")) {
							secondaryBKBatchCode="_ _";
						}
						}else {
							if(secondaryBKBatchCode==null) {
							secondaryBKBatchCode="_ _";
						}
						}
						if(secondaryTotalPaymentAmt.equals("") || secondaryTotalPaymentAmt.equals("0.00")) {
							secondaryTotalPaymentAmt="_ _";
						}
						if(coborrowerFullName.equals("")) {
							coborrowerFullName="_ _";
						}
						
						transactionTotal = totalTransationAmt.toString();
						
					}else{
						LOGGER.info("Error canceling payment by paymentId: {}", scheduledPaymentId);
						cancelPaymentResponse.setIsSuccessful(false);
						cancelPaymentResponse.setMessage("Payment could not be Cancelled");
					}
					
				}else {
					cancelPaymentResponse.setIsSuccessful(false);
					cancelPaymentResponse.setMessage("Payment is not available to cancel");
				}
			}if(isCancelled){
				 if(processedByEmail!=null){
					 LOGGER.info("processedByEmail: {}", processedByEmail);
					 if(!processedByEmail.equals("")) {
						String email = processedByEmail;
						EmailBean emailBean = new EmailBean();
						emailBean.setSubject("PhonePay payments canceled");
						emailBean.setMessageBody("<html> <head> <style>table{font-family: arial, sans-serif; border-collapse: collapse; width: 90%; margin: 0 auto;}td, th{border: 0px; text-align: right; padding: 8px; width: 100px;}.head{padding-left: 18rem; margin: 2rem 0rem 3.5rem 0rem; font-size: 20px;}@media (max-width:1400px){.head{padding-left: 10rem;}}@media (max-width:992px){.head{padding-left: 8rem;}}</style> </head> <body> <p class=\"head\">The following PhonePay payments are canceled. Please remove them from your Black Knight batch.</p><table> <tr> <td>Loan Number:</td><td>"+primaryLoanNumber+"</td><td>"+secondaryLoanNumber+"</td></tr><tr> <td>Borrower Name:</td><td>"+borrowerFullName+"</td><td>"+coborrowerFullName+"</td></tr><tr> <td>Total Payment Amount:</td><td>"+primaryTotalPaymentAmt+"</td><td>"+secondaryTotalPaymentAmt+"</td></tr><tr> <td>Monthly Payment:</td><td>"+primaryMonthlyPayment+"</td><td>"+secondaryMonthlyPayment+"</td></tr><tr> <td>Late Fee:</td><td>"+primaryLateFee+"</td><td>"+secondaryLateFee+"</td></tr><tr> <td>NSF FEE:</td><td>"+primaryNsfFee+"</td><td>"+secondaryNsfFee+"</td></tr><tr> <td>Additional Escrow:</td><td>"+primaryAdditionalEscrow+"</td><td>"+secondaryAdditionalEscrow+"</td></tr><tr> <td>Additional Principal:</td><td>"+primaryAdditionalPrincipal+"</td><td>"+secondaryAdditionalPrincipal+"</td></tr><tr> <td>Corporate Advance:</td><td>"+primaryCorporateAdvance+"</td><td>"+secondaryCorporateAdvance+"</td></tr><tr> <td>Other:</td><td>"+primaryOther+"</td><td>"+secondaryOther+"</td></tr><tr> <td>PhonePay Fee:</td><td>"+primaryPhonePayFee+"</td><td>"+secondaryPhonePayFee+"</td></tr><tr> <td>Payor:</td><td>"+primaryPayor+"</td><td>"+secondaryPayor+"</td></tr><tr> <td>Black Knight Batch Code:</td><td>"+primaryBKBatchCode+"</td><td>"+secondaryBKBatchCode+"</td></tr><tr style=\"background-color:#942911; color: #fff; height: 50px; font-size: 25px;\"> <td>Transaction Total</td><td></td><td>$"+transactionTotal+"</td></tr></table> </body></html>");
						emailBean.addRecipient(email);
						emailService.sendEmail(emailBean);
					 }
				 }
				cancelPaymentResponse.setIsSuccessful(true);
				cancelPaymentResponse.setMessage("Payment cancelled successfully");
			}
		}catch(Exception exp) {
			LOGGER.error("Payment Could Not be Cancelled Bacause Of Exception " , exp);
			cancelPaymentResponse.setIsSuccessful(false);
			cancelPaymentResponse.setMessage("Something Went Wrong While Canceling Payment");
		}
		return cancelPaymentResponse;
	}

}
