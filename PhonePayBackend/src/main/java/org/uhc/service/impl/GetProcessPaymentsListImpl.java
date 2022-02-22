/**
 * 
 */
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.uhc.bean.ProcessingPaymentsNotificationResponse;
import org.uhc.bean.ProcessingPaymentsResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.Address;
import org.uhc.dao.dto.ConfirmationEmailInfoDTO;
import org.uhc.dao.dto.ConfirmationLetterInfoDTO;
import org.uhc.dao.dto.FailedEmailsDto;
import org.uhc.dao.dto.FailedPrintsDto;
import org.uhc.dao.dto.GetProcessPaymentsListDto;
import org.uhc.dao.dto.LoanPaymentDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.envelop.request.ProcessPaymentPrintLetterReq;
import org.uhc.envelop.request.ProcessPaymentSendEmailReq;
import org.uhc.envelop.response.ProcessPaymentsResponse;
import org.uhc.service.GetProcessPaymentsList;
import org.uhc.util.Constants;

/**
 * @author nehas3
 *
 */
@Service
public class GetProcessPaymentsListImpl implements GetProcessPaymentsList {

	private static final Logger LOGGER = LogManager.getLogger(GetProcessPaymentsListImpl.class.getName());

	@Autowired
	UserDao userDao;

	@Value("${processingPaymentsURL}")
	String ppsURI;

	@Value("${sendingEmailConfirmationURL}")
	String emailConfURI;
	
	@Value("${letterNotificationURL}")
	String letterConfURI;

	private List<Integer> successfulIds = null;
	private boolean isProcessedPaymentsUpdatedForNoti = false;
	List<GetProcessPaymentsListDto> processPaymentsList = null;

	@Override
	public ProcessPaymentsResponse getProcessPaymentsList() {
		LOGGER.info("Entering into getProcessPaymentsList method");
		Format f = new SimpleDateFormat(Constants.DateFormat.EMAIL_DATE_FORMAT.getValue());
		ProcessPaymentsResponse processPaymentsResponse = new ProcessPaymentsResponse();
		processPaymentsList = new ArrayList<>();
		boolean isProcessedPaymentsUpdated = false;
		boolean isPamymentBatchUpdated = false;
		try {
			processPaymentsList = userDao.getProcessPaymentsList();
			if (processPaymentsList != null && !processPaymentsList.isEmpty()) {
				for (GetProcessPaymentsListDto getProcessPayment : processPaymentsList) {
					BigDecimal totalOtherFeeAmount = new BigDecimal(0);
					BigDecimal totalCorporateFeeAmount = new BigDecimal(0);
					List<OtherFeeDto> feeList = userDao.getOtherFeeListForPayment(getProcessPayment.getPaymentId());
					if (getProcessPayment.getAdviceType() > 0) {
						getProcessPayment.setAdvice(true);
					}
					if (feeList != null && !feeList.isEmpty()) {
						for (OtherFeeDto fee : feeList) {
							String feeName = userDao.getFeeNameByFeeId(fee.getFeeCode());
							if (feeName.equals("Monthly Payment")) {
								getProcessPayment.setMonthlyAmount(fee.getFeeAmount());
							} else if (feeName.equals("Additional Principal")) {
								getProcessPayment.setAdditionalPrincipal(fee.getFeeAmount());
							} else if (feeName.equals("Additional Escrow")) {
								getProcessPayment.setAdditionalEscrow(fee.getFeeAmount());
							} else if (feeName.equals("Late Fee")) {
								getProcessPayment.setLateFee(fee.getFeeAmount());
							} else if (feeName.equals("NSF Fee")) {
								getProcessPayment.setNsfFee(fee.getFeeAmount());
							} else if (feeName.equals("Other") || feeName.equals("Other Fee")) {
								totalOtherFeeAmount = totalOtherFeeAmount.add(fee.getFeeAmount());
							} else if (feeName.equals("PhonePay Fee")) {
								getProcessPayment.setPhonePayFee(fee.getFeeAmount());
							} else if (feeName.equals("Corporate Advance")) {
								totalCorporateFeeAmount = totalCorporateFeeAmount.add(fee.getFeeAmount());
							}
						}
					}
					getProcessPayment.setOtherTypePayments(totalOtherFeeAmount);
					getProcessPayment.setCorporateAdvance(totalCorporateFeeAmount);
				}
				RestTemplate restTemplate = new RestTemplate();
				ProcessingPaymentsResponse resultObj = new ProcessingPaymentsResponse();

				LOGGER.info("DATA SENT TO PPS:\n" + processPaymentsList);
				ResponseEntity<ProcessingPaymentsResponse> responseEntity = restTemplate.postForEntity(ppsURI,
						processPaymentsList, ProcessingPaymentsResponse.class);
				if (responseEntity.getStatusCodeValue() == 200 || responseEntity.getStatusCodeValue() == 207) {
					resultObj = responseEntity.getBody();
					resultObj.setStatus(String.valueOf(responseEntity.getStatusCodeValue()));
					if (!resultObj.getSuccessfulPayments().isEmpty()) {
						synchronized (this) {
							isPamymentBatchUpdated = userDao.insertProcessedPaymentBatch(resultObj);
							if (isPamymentBatchUpdated) {
								int paymentBatchId = userDao.getLastUpdatedPaymentBatchId();
								if (paymentBatchId > 0) {
									resultObj.setPaymentBatchId(paymentBatchId);
									isProcessedPaymentsUpdated = userDao.updateProcessedPaymentsDate(
											resultObj.getSuccessfulPayments(), paymentBatchId);
								}
							}
						}
						successfulIds = resultObj.getSuccessfulPayments();
						LOGGER.info("count" + successfulIds.size());
					}
				
				} else {
					processPaymentsResponse.setIsSuccessful(false);
					processPaymentsResponse.setMessage("Payment processing inturrupted");
				}
				if (isProcessedPaymentsUpdated) {
					processPaymentsResponse.setIsSuccessful(true);
					processPaymentsResponse.setMessage("Processed payments successfully");
					processPaymentsResponse.setPpsResponse(resultObj);
					processPaymentsResponse.setProcessPaymentsList(processPaymentsList);
					processPaymentsResponse.setUpdatedForNoti(isProcessedPaymentsUpdated);
					processPaymentsResponse.setSuccessfulIds(successfulIds);

				} else {
					processPaymentsResponse.setIsSuccessful(false);
					processPaymentsResponse.setMessage("payment processing interrupted");
				}

			} else {
				processPaymentsResponse.setIsSuccessful(false);
				processPaymentsResponse.setMessage("No records found to process payments");
			}

		} catch (Exception exp) {
			LOGGER.error("Exception occured during process", exp);
			processPaymentsResponse.setIsSuccessful(false);
			processPaymentsResponse.setMessage("Could not get records to process payments "+exp.toString());
		}
		LOGGER.info("Exit from getProcessPaymentsList method");
		return processPaymentsResponse;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public ProcessingPaymentsNotificationResponse getProcessPaymentsNotificationListAPI(ProcessPaymentSendEmailReq processPaymentSendEmailReq) {
		ProcessingPaymentsNotificationResponse processPaymentsNotiResponse = new ProcessingPaymentsNotificationResponse();
		List<GetProcessPaymentsListDto> paymentListForEmailConfirmation = new ArrayList<>();
		Format f = new SimpleDateFormat(Constants.DateFormat.EMAIL_DATE_FORMAT.getValue());
		int emailSentCount = 0;
		int emailFailedToSentCount = 0;
		if (processPaymentSendEmailReq.isUpdatedForNoti()) {
			processPaymentSendEmailReq.getProcessPaymentsList().forEach(f1 -> {
				int pid = f1.getPaymentId();
				if (processPaymentSendEmailReq.getSuccessfulIds().contains(pid)) {
					paymentListForEmailConfirmation.add(f1);
				}
			});

			if (paymentListForEmailConfirmation != null && !paymentListForEmailConfirmation.isEmpty()) {
				List<ConfirmationEmailInfoDTO> emailConfirmationListDto = new ArrayList<>();

				paymentListForEmailConfirmation.stream().filter(
						paymentConfirmation -> !String.valueOf(paymentConfirmation.getLoanNumber()).startsWith("9"))
				.forEach(primaryLoanPayment -> {
					ConfirmationEmailInfoDTO emailConfirmationDto = new ConfirmationEmailInfoDTO();
					List<LoanPaymentDto> paymentListDto = new ArrayList<>();
					long secondaryloanNumber = Long.valueOf("9" + primaryLoanPayment.getLoanNumber());
					LoanPaymentDto paymentDto = new LoanPaymentDto();
					paymentDto.setLoanNumber(primaryLoanPayment.getLoanNumber());
					paymentDto.setPayerName(primaryLoanPayment.getNameOnAccount());
					paymentDto.setAdditionalEscrow(primaryLoanPayment.getAdditionalEscrow());
					paymentDto.setMaskedLoanNumber(primaryLoanPayment.getEncryptedLoanNumber());
					paymentDto.setAdditionalPrincipal(primaryLoanPayment.getAdditionalPrincipal());
					paymentDto.setLateCharge(primaryLoanPayment.getLateFee());
					paymentDto.setNsfFee(primaryLoanPayment.getNsfFee());
					paymentDto.setMonthlyPayment(primaryLoanPayment.getMonthlyAmount());
					paymentDto.setTransactionFee(primaryLoanPayment.getPhonePayFee());
					paymentDto.setOther(primaryLoanPayment.getOtherTypePayments());
					paymentDto.setCorporateAdvanceFee(primaryLoanPayment.getCorporateAdvance());
					paymentDto.setConfirmationNumber(primaryLoanPayment.getConfirmationNumber());
					paymentListDto.add(paymentDto);
					List<GetProcessPaymentsListDto> secondaryLoanPayment = paymentListForEmailConfirmation
							.stream().filter(a -> a.getLoanNumber() == (secondaryloanNumber))
							.collect(Collectors.toList());

					if (!CollectionUtils.isEmpty(secondaryLoanPayment)) {
						LoanPaymentDto secoundaryPaymentDto = new LoanPaymentDto();
						GetProcessPaymentsListDto secondary = secondaryLoanPayment.get(0);
						secoundaryPaymentDto.setLoanNumber(secondary.getLoanNumber());
						if(primaryLoanPayment.getNameOnAccount()!=null && !primaryLoanPayment.getNameOnAccount().isEmpty()) {
						secoundaryPaymentDto.setPayerName(primaryLoanPayment.getNameOnAccount());
						}else {
						secoundaryPaymentDto.setPayerName(secondary.getNameOnAccount());	
						}
						secoundaryPaymentDto.setAdditionalEscrow(secondary.getAdditionalEscrow());
						secoundaryPaymentDto.setMaskedLoanNumber(secondary.getEncryptedLoanNumber());
						secoundaryPaymentDto.setAdditionalPrincipal(secondary.getAdditionalPrincipal());
						secoundaryPaymentDto.setLateCharge(secondary.getLateFee());
						secoundaryPaymentDto.setNsfFee(secondary.getNsfFee());
						secoundaryPaymentDto.setMonthlyPayment(secondary.getMonthlyAmount());
						secoundaryPaymentDto.setTransactionFee(secondary.getPhonePayFee());
						secoundaryPaymentDto.setOther(secondary.getOtherTypePayments());
						secoundaryPaymentDto.setCorporateAdvanceFee(secondary.getCorporateAdvance());
						secoundaryPaymentDto.setConfirmationNumber(secondary.getConfirmationNumber());
						paymentListDto.add(secoundaryPaymentDto);
					}

					emailConfirmationDto.setLoanPaymentList(paymentListDto);

					if (primaryLoanPayment.getAccountNumber().length() > 4) {
						emailConfirmationDto.setBankAccountEnding(primaryLoanPayment.getAccountNumber()
								.substring(primaryLoanPayment.getAccountNumber().length() - 4));
					} else {
						emailConfirmationDto.setBankAccountEnding(primaryLoanPayment.getAccountNumber());
					}
					if(primaryLoanPayment.getEmails()!=null && !primaryLoanPayment.getEmails().isEmpty()) {
					emailConfirmationDto.setEmailList(Stream.of(primaryLoanPayment.getEmails().split(";"))
							.collect(Collectors.toCollection(ArrayList::new)));
					}else {
						emailConfirmationDto.setEmailList(Stream.of(primaryLoanPayment.getEmails().split(";"))
								.collect(Collectors.toCollection(ArrayList::new)));
					}
					emailConfirmationDto.setTransactionDate(f.format(new Date()));
					emailConfirmationDto.setNameOfException("");
					emailConfirmationDto.setStatus("");
					
					if(primaryLoanPayment.getEmails()!="") {
					emailConfirmationListDto.add(emailConfirmationDto);
					}
				});
				List<GetProcessPaymentsListDto> paymentListForEmailConfirmationForSecondaryOnly = new ArrayList<>();
				List<Long> primaryLoan1 = new ArrayList<Long>();
				for(GetProcessPaymentsListDto ltd: paymentListForEmailConfirmation) {
					if(String.valueOf(ltd.getLoanNumber()).startsWith("9")) {
						paymentListForEmailConfirmationForSecondaryOnly.add(ltd);
					}else {
						primaryLoan1.add(ltd.getLoanNumber());
					}
					
				}
				List<GetProcessPaymentsListDto> paymentListForEmailConfirmationSecondaryLoan = new ArrayList<>();
				
				for(GetProcessPaymentsListDto ltd1:paymentListForEmailConfirmationForSecondaryOnly) {
					String loanNumber = String.valueOf(ltd1.getLoanNumber()).substring(1);
					
					if(primaryLoan1!=null && !(primaryLoan1.contains(Long.parseLong(loanNumber)))) {
						paymentListForEmailConfirmationSecondaryLoan.add(ltd1);
						
						ConfirmationEmailInfoDTO emailConfirmationDto = new ConfirmationEmailInfoDTO();
						List<LoanPaymentDto> paymentListDto = new ArrayList<>();
						LoanPaymentDto paymentDto = new LoanPaymentDto();
						paymentDto.setLoanNumber(ltd1.getLoanNumber());
						paymentDto.setPayerName(ltd1.getNameOnAccount());
						paymentDto.setAdditionalEscrow(ltd1.getAdditionalEscrow());
						paymentDto.setMaskedLoanNumber(ltd1.getEncryptedLoanNumber());
						paymentDto.setAdditionalPrincipal(ltd1.getAdditionalPrincipal());
						paymentDto.setLateCharge(ltd1.getLateFee());
						paymentDto.setNsfFee(ltd1.getNsfFee());
						paymentDto.setMonthlyPayment(ltd1.getMonthlyAmount());
						paymentDto.setTransactionFee(ltd1.getPhonePayFee());
						paymentDto.setOther(ltd1.getOtherTypePayments());
						paymentDto.setCorporateAdvanceFee(ltd1.getCorporateAdvance());
						paymentDto.setConfirmationNumber(ltd1.getConfirmationNumber());
						paymentListDto.add(paymentDto);
						
						emailConfirmationDto.setLoanPaymentList(paymentListDto);

						if (ltd1.getAccountNumber().length() > 4) {
							emailConfirmationDto.setBankAccountEnding(ltd1.getAccountNumber()
									.substring(ltd1.getAccountNumber().length() - 4));
						} else {
							emailConfirmationDto.setBankAccountEnding(ltd1.getAccountNumber());
						}
						if(ltd1.getEmails()!=null && !ltd1.getEmails().isEmpty()) {
						emailConfirmationDto.setEmailList(Stream.of(ltd1.getEmails().split(";"))
								.collect(Collectors.toCollection(ArrayList::new)));
						}else {
							emailConfirmationDto.setEmailList(Stream.of(ltd1.getEmails().split(";"))
									.collect(Collectors.toCollection(ArrayList::new)));
						}
						emailConfirmationDto.setTransactionDate(f.format(new Date()));
						emailConfirmationDto.setNameOfException("");
						emailConfirmationDto.setStatus("");
						
						if(ltd1.getEmails()!="") {
						emailConfirmationListDto.add(emailConfirmationDto);
						}
					}
				}
				LOGGER.info("paymentListForEmailConfirmationSecondaryLoan{}:::::",paymentListForEmailConfirmationSecondaryLoan);
				try {
					RestTemplate restTemplateforSendingEmail = new RestTemplate();
					if(emailConfirmationListDto.size()!=0) {
					ResponseEntity<String> emailConfResponse = restTemplateforSendingEmail.postForEntity(emailConfURI,
							emailConfirmationListDto, String.class);
					LOGGER.info("Email Confirmation Response: {}", emailConfResponse);
					LOGGER.info("emailConfirmationListDto: {}", emailConfirmationListDto);
					String sr = emailConfResponse.toString().substring(1, 4);
					if(sr.equals("200") || sr.equals("207")) { 
						List<FailedEmailsDto> failedEmailsListDtos = null;
						List<List<FailedEmailsDto>> failedEmailsList = new ArrayList<List<FailedEmailsDto>>();
						String s1 = emailConfResponse.getBody();
						LOGGER.info("s1: {}", s1);
						String s = "{\"data\":";
						String s2 = "}";
						String s3 = s + s1 + s2;
						JSONObject obj = new JSONObject(s3);
						JSONArray notidata = obj.getJSONArray("data");
						int n = notidata.length();
						for (int i = 0; i < n; ++i) {
							
							JSONObject notification = notidata.getJSONObject(i);
							String status = notification.getString("status");
							if (status.equalsIgnoreCase("Success")) {
								emailSentCount = emailSentCount + 1;
							} else {
								JSONArray arr2 = notification.getJSONArray("loanPaymentList");
								int n1 = arr2.length();
								failedEmailsListDtos = new ArrayList<FailedEmailsDto>();
								for (int j = 0; j < n1; j++) {
									JSONObject notification1 = arr2.getJSONObject(j);
									
									Long loanNum = notification1.getLong("loanNumber");
									if(!String.valueOf(loanNum).startsWith("9")) {
									
							        FailedEmailsDto	failedPrimaryEmailsDto = new FailedEmailsDto();
									loanNum = notification1.getLong("loanNumber");
									BigDecimal subtotal = BigDecimal.ZERO;
							        subtotal = subtotal.add(notification1.getBigDecimal("monthlyPayment"));
							        subtotal = subtotal.add(notification1.getBigDecimal("additionalPrincipal"));
							        subtotal = subtotal.add(notification1.getBigDecimal("additionalEscrow"));
							        subtotal = subtotal.add(notification1.getBigDecimal("lateCharge"));
							        subtotal = subtotal.add(notification1.getBigDecimal("nsfFee"));
							        subtotal = subtotal.add(notification1.getBigDecimal("other"));
							        subtotal = subtotal.add(notification1.getBigDecimal("transactionFee"));
							        subtotal = subtotal.add(notification1.getBigDecimal("corporateAdvanceFee"));
							        failedPrimaryEmailsDto.setLoanNumber(loanNum);
							        failedPrimaryEmailsDto.setTotalFee(subtotal);
							        failedPrimaryEmailsDto.setPayerName(notification1.getString("payerName"));
							        failedEmailsListDtos.add(failedPrimaryEmailsDto);
									LOGGER.info("failedEmailsListDtos: "+failedEmailsListDtos);
									}else {
									 FailedEmailsDto	failedSecondaryEmailsDto = new FailedEmailsDto();
									loanNum = notification1.getLong("loanNumber");
									BigDecimal subtotal = BigDecimal.ZERO;
							        subtotal = subtotal.add(notification1.getBigDecimal("monthlyPayment"));
							        subtotal = subtotal.add(notification1.getBigDecimal("additionalPrincipal"));
							        subtotal = subtotal.add(notification1.getBigDecimal("additionalEscrow"));
							        subtotal = subtotal.add(notification1.getBigDecimal("lateCharge"));
							        subtotal = subtotal.add(notification1.getBigDecimal("nsfFee"));
							        subtotal = subtotal.add(notification1.getBigDecimal("other"));
							        subtotal = subtotal.add(notification1.getBigDecimal("transactionFee"));
							        subtotal = subtotal.add(notification1.getBigDecimal("corporateAdvanceFee"));
							        failedSecondaryEmailsDto.setLoanNumber(loanNum);
							        failedSecondaryEmailsDto.setTotalFee(subtotal);
							        failedSecondaryEmailsDto.setPayerName(notification1.getString("payerName"));
							        failedEmailsListDtos.add(failedSecondaryEmailsDto);
									LOGGER.info("failedSecondaryEmailsListDtos: "+failedEmailsListDtos);
									}
								}
								 failedEmailsList.add(failedEmailsListDtos);
								emailFailedToSentCount = emailFailedToSentCount + 1;
							}
						}
						processPaymentsNotiResponse.setIsSuccessful(true);
						processPaymentsNotiResponse.setEmailSentSuccessfully(emailSentCount);
						processPaymentsNotiResponse.setEmailFailedToSend(emailFailedToSentCount);
						
						processPaymentsNotiResponse.setProcessPaymentsList(processPaymentSendEmailReq.getProcessPaymentsList());
						processPaymentsNotiResponse.setUpdatedForNoti(processPaymentSendEmailReq.isUpdatedForNoti());
						processPaymentsNotiResponse.setSuccessfulIds(processPaymentSendEmailReq.getSuccessfulIds());
						processPaymentsNotiResponse.setMessage("Email sent notication process completed");
						processPaymentsNotiResponse.setEmailResStatus(sr);
						processPaymentsNotiResponse.setFailedEmailsListDtos(failedEmailsListDtos);
						processPaymentsNotiResponse.setFailedEmailsList(failedEmailsList);
						return processPaymentsNotiResponse;
					} else {
						LOGGER.error("There is no content.", emailConfResponse);
						processPaymentsNotiResponse.setIsSuccessful(false);
						processPaymentsNotiResponse.setMessage(emailConfResponse.toString());
						processPaymentsNotiResponse.setProcessPaymentsList(processPaymentSendEmailReq.getProcessPaymentsList());
						processPaymentsNotiResponse.setUpdatedForNoti(processPaymentSendEmailReq.isUpdatedForNoti());
						processPaymentsNotiResponse.setSuccessfulIds(processPaymentSendEmailReq.getSuccessfulIds());
						processPaymentsNotiResponse.setEmailResStatus(sr);
						return processPaymentsNotiResponse;
					}
				}else {
					processPaymentsNotiResponse.setIsSuccessful(true);
					processPaymentsNotiResponse.setEmailSentSuccessfully(emailSentCount);
					processPaymentsNotiResponse.setEmailFailedToSend(emailFailedToSentCount);
					
					processPaymentsNotiResponse.setProcessPaymentsList(processPaymentSendEmailReq.getProcessPaymentsList());
					processPaymentsNotiResponse.setUpdatedForNoti(processPaymentSendEmailReq.isUpdatedForNoti());
					processPaymentsNotiResponse.setSuccessfulIds(processPaymentSendEmailReq.getSuccessfulIds());
					processPaymentsNotiResponse.setMessage("No Email for sent notication");
					return processPaymentsNotiResponse;
				}
				} catch (HttpStatusCodeException hpe) {
					LOGGER.error("Exception Occured duing sending email", hpe);
					processPaymentsNotiResponse.setIsSuccessful(false);
					processPaymentsNotiResponse.setMessage(hpe.toString());
					processPaymentsNotiResponse.setProcessPaymentsList(processPaymentSendEmailReq.getProcessPaymentsList());
					processPaymentsNotiResponse.setUpdatedForNoti(processPaymentSendEmailReq.isUpdatedForNoti());
					processPaymentsNotiResponse.setSuccessfulIds(processPaymentSendEmailReq.getSuccessfulIds());
					return processPaymentsNotiResponse;
				}
				
			} else {
				processPaymentsNotiResponse.setIsSuccessful(false);
				processPaymentsNotiResponse.setMessage("There is no emails for sending notification");
				return processPaymentsNotiResponse;
			}
		} else {
			processPaymentsNotiResponse.setIsSuccessful(false);
			processPaymentsNotiResponse.setMessage("Process payment not updated.");
			return processPaymentsNotiResponse;
		}
	}

	@Override
	public ProcessingPaymentsNotificationResponse getProcessPaymentsLetterNotificationList(ProcessPaymentPrintLetterReq paymentPrintLetterReq) {
		ProcessingPaymentsNotificationResponse processPaymentsNotiResponse = new ProcessingPaymentsNotificationResponse();
		List<GetProcessPaymentsListDto> paymentListForLetterConfirmation = new ArrayList<>();
		Format f = new SimpleDateFormat(Constants.DateFormat.EMAIL_DATE_FORMAT.getValue());
		int letterPrintCount = 0;
		int letterFailedToPrintCount = 0;
		List<Long> failedEmailList = new ArrayList<Long>();
		
		if (paymentPrintLetterReq.isUpdatedForNoti()) {
			paymentPrintLetterReq.getProcessPaymentsList().forEach(f1 -> {
				int pid = f1.getPaymentId();
				if (paymentPrintLetterReq.getSuccessfulIds().contains(pid)) {
					paymentListForLetterConfirmation.add(f1);
				}
			});
			
			for(GetProcessPaymentsListDto getProcPmtListDto: paymentPrintLetterReq.getProcessPaymentsListForFailedPrints()) {
				
				failedEmailList.add(getProcPmtListDto.getLoanNumber());
			}
			if (paymentListForLetterConfirmation != null && !paymentListForLetterConfirmation.isEmpty()) {
				List<ConfirmationLetterInfoDTO> letterConfirmationListDto = new ArrayList<>();

				paymentListForLetterConfirmation.stream().filter(
						paymentConfirmation -> !String.valueOf(paymentConfirmation.getLoanNumber()).startsWith("9"))
				.forEach(primaryLoanPayment -> {
					ConfirmationLetterInfoDTO letterConfirmationDto = new ConfirmationLetterInfoDTO();
					List<LoanPaymentDto> paymentListDto = new ArrayList<>();
					Address addressDto  = new Address();
					long secondaryloanNumber = Long.valueOf("9" + primaryLoanPayment.getLoanNumber());
					LoanPaymentDto paymentDto = new LoanPaymentDto();
					paymentDto.setLoanNumber(primaryLoanPayment.getLoanNumber());
					paymentDto.setPayerName(primaryLoanPayment.getNameOnAccount());
					paymentDto.setAdditionalEscrow(primaryLoanPayment.getAdditionalEscrow());
					paymentDto.setMaskedLoanNumber(primaryLoanPayment.getEncryptedLoanNumber());
					paymentDto.setAdditionalPrincipal(primaryLoanPayment.getAdditionalPrincipal());
					paymentDto.setLateCharge(primaryLoanPayment.getLateFee());
					paymentDto.setNsfFee(primaryLoanPayment.getNsfFee());
					paymentDto.setMonthlyPayment(primaryLoanPayment.getMonthlyAmount());
					paymentDto.setTransactionFee(primaryLoanPayment.getPhonePayFee());
					paymentDto.setOther(primaryLoanPayment.getOtherTypePayments());
					paymentDto.setCorporateAdvanceFee(primaryLoanPayment.getCorporateAdvance());
					paymentDto.setConfirmationNumber(primaryLoanPayment.getConfirmationNumber());
					addressDto.setLine1(primaryLoanPayment.getLine1());
					addressDto.setCity(primaryLoanPayment.getCity());
					addressDto.setState(primaryLoanPayment.getState());
					addressDto.setZip(primaryLoanPayment.getZip());
					paymentListDto.add(paymentDto);
					
					List<GetProcessPaymentsListDto> secondaryLoanPayment = paymentListForLetterConfirmation
							.stream().filter(a -> a.getLoanNumber() == (secondaryloanNumber))
							.collect(Collectors.toList());

					if (!CollectionUtils.isEmpty(secondaryLoanPayment)) {
						LoanPaymentDto secoundaryPaymentDto = new LoanPaymentDto();
						GetProcessPaymentsListDto secondary = secondaryLoanPayment.get(0);
						secoundaryPaymentDto.setLoanNumber(secondary.getLoanNumber());
						if(primaryLoanPayment.getNameOnAccount()!=null && !primaryLoanPayment.getNameOnAccount().isEmpty()) {
							secoundaryPaymentDto.setPayerName(primaryLoanPayment.getNameOnAccount());
							}else {
							secoundaryPaymentDto.setPayerName(secondary.getNameOnAccount());	
							}
						secoundaryPaymentDto.setAdditionalEscrow(secondary.getAdditionalEscrow());
						secoundaryPaymentDto.setMaskedLoanNumber(secondary.getEncryptedLoanNumber());
						secoundaryPaymentDto.setAdditionalPrincipal(secondary.getAdditionalPrincipal());
						secoundaryPaymentDto.setLateCharge(secondary.getLateFee());
						secoundaryPaymentDto.setNsfFee(secondary.getNsfFee());
						secoundaryPaymentDto.setMonthlyPayment(secondary.getMonthlyAmount());
						secoundaryPaymentDto.setTransactionFee(secondary.getPhonePayFee());
						secoundaryPaymentDto.setOther(secondary.getOtherTypePayments());
						secoundaryPaymentDto.setCorporateAdvanceFee(secondary.getCorporateAdvance());
						secoundaryPaymentDto.setConfirmationNumber(secondary.getConfirmationNumber());
						if(primaryLoanPayment.getLoanNumber()== 0 ) {
						addressDto.setLine1(secondary.getLine1());
						addressDto.setCity(secondary.getCity());
						addressDto.setState(secondary.getState());
						addressDto.setZip(secondary.getZip());
						}
						paymentListDto.add(secoundaryPaymentDto);
					}
					if(failedEmailList.contains(primaryLoanPayment.getLoanNumber())) {
						letterConfirmationDto.setFailedEmailLoanNum(primaryLoanPayment.getLoanNumber());
					}else {
						letterConfirmationDto.setFailedEmailLoanNum(0);
					}
					
					letterConfirmationDto.setProcessorName(primaryLoanPayment.getAgentName());
					letterConfirmationDto.setPayerName(primaryLoanPayment.getNameOnAccount());
					letterConfirmationDto.setAddress(addressDto);
					letterConfirmationDto.setTransactionDate(f.format(new Date()));
					letterConfirmationDto.setBankName("");
					if (primaryLoanPayment.getAccountNumber().length() > 4) {
						letterConfirmationDto.setBankAccountEnding(primaryLoanPayment.getAccountNumber()
								.substring(primaryLoanPayment.getAccountNumber().length() - 4));
					} else {
						letterConfirmationDto.setBankAccountEnding(primaryLoanPayment.getAccountNumber());
					}
					letterConfirmationDto.setNameOfException("");
					letterConfirmationDto.setStatus("");
					letterConfirmationDto.setLoanPaymentList(paymentListDto);
					letterConfirmationDto.setAccountType(primaryLoanPayment.getAccountType());
					if(primaryLoanPayment.isSendLetter()) {
					letterConfirmationListDto.add(letterConfirmationDto);
					}
				});
				
				//Start print for second loan
				List<GetProcessPaymentsListDto> paymentListForPrintConfirmationForSecondaryOnly = new ArrayList<>();
				List<Long> primaryLoanNum = new ArrayList<Long>();
				for(GetProcessPaymentsListDto paymentsListDto: paymentListForLetterConfirmation) {
					if(String.valueOf(paymentsListDto.getLoanNumber()).startsWith("9")) {
						paymentListForPrintConfirmationForSecondaryOnly.add(paymentsListDto);
					}else {
						primaryLoanNum.add(paymentsListDto.getLoanNumber());
					}
					
				}
				List<GetProcessPaymentsListDto> paymentListForPrintConfirmationSecondaryLoan = new ArrayList<>();
				
				for(GetProcessPaymentsListDto paymentsListDto:paymentListForPrintConfirmationForSecondaryOnly) {
					String loanNumber = String.valueOf(paymentsListDto.getLoanNumber()).substring(1);
					LoanPaymentDto secoundaryPaymentDto = new LoanPaymentDto();
					Address addressDto  = new Address();
					List<LoanPaymentDto> paymentListDto = new ArrayList<>();
					ConfirmationLetterInfoDTO letterConfirmationDto = new ConfirmationLetterInfoDTO();
					if(primaryLoanNum !=null && !(primaryLoanNum.contains(Long.parseLong(loanNumber)))) {
						paymentListForPrintConfirmationSecondaryLoan.add(paymentsListDto);
						secoundaryPaymentDto.setLoanNumber(paymentsListDto.getLoanNumber());
						secoundaryPaymentDto.setPayerName(paymentsListDto.getNameOnAccount());	
						secoundaryPaymentDto.setAdditionalEscrow(paymentsListDto.getAdditionalEscrow());
						secoundaryPaymentDto.setMaskedLoanNumber(paymentsListDto.getEncryptedLoanNumber());
						secoundaryPaymentDto.setAdditionalPrincipal(paymentsListDto.getAdditionalPrincipal());
						secoundaryPaymentDto.setLateCharge(paymentsListDto.getLateFee());
						secoundaryPaymentDto.setNsfFee(paymentsListDto.getNsfFee());
						secoundaryPaymentDto.setMonthlyPayment(paymentsListDto.getMonthlyAmount());
						secoundaryPaymentDto.setTransactionFee(paymentsListDto.getPhonePayFee());
						secoundaryPaymentDto.setOther(paymentsListDto.getOtherTypePayments());
						secoundaryPaymentDto.setCorporateAdvanceFee(paymentsListDto.getCorporateAdvance());
						secoundaryPaymentDto.setConfirmationNumber(paymentsListDto.getConfirmationNumber());
						addressDto.setLine1(paymentsListDto.getLine1());
						addressDto.setCity(paymentsListDto.getCity());
						addressDto.setState(paymentsListDto.getState());
						addressDto.setZip(paymentsListDto.getZip());
						
						paymentListDto.add(secoundaryPaymentDto);
						
						if(failedEmailList.contains(paymentsListDto.getLoanNumber())) {
							letterConfirmationDto.setFailedEmailLoanNum(paymentsListDto.getLoanNumber());
						}else {
							letterConfirmationDto.setFailedEmailLoanNum(0);
						}
						
						letterConfirmationDto.setProcessorName(paymentsListDto.getAgentName());
						letterConfirmationDto.setPayerName(paymentsListDto.getNameOnAccount());
						letterConfirmationDto.setAddress(addressDto);
						letterConfirmationDto.setTransactionDate(f.format(new Date()));
						letterConfirmationDto.setBankName("");
						if (paymentsListDto.getAccountNumber().length() > 4) {
							letterConfirmationDto.setBankAccountEnding(paymentsListDto.getAccountNumber()
									.substring(paymentsListDto.getAccountNumber().length() - 4));
						} else {
							letterConfirmationDto.setBankAccountEnding(paymentsListDto.getAccountNumber());
						}
						letterConfirmationDto.setNameOfException("");
						letterConfirmationDto.setStatus("");
						letterConfirmationDto.setLoanPaymentList(paymentListDto);
						letterConfirmationDto.setAccountType(paymentsListDto.getAccountType());
						if(paymentsListDto.isSendLetter()) {
						letterConfirmationListDto.add(letterConfirmationDto);
						}
					}
					
				}
				LOGGER.info("paymentListForPrintConfirmationSecondaryLoan{}:::::",paymentListForPrintConfirmationSecondaryLoan);
				//end print for second loan
				
				try {
					RestTemplate restTemplateforPrintLetter = new RestTemplate();
					if(!letterConfirmationListDto.isEmpty()) {
					ResponseEntity<String> letterConfResponse = restTemplateforPrintLetter.postForEntity(letterConfURI,
							letterConfirmationListDto, String.class);
					LOGGER.info("Letter Confirmation Response: {}", letterConfResponse);
					String sr = letterConfResponse.toString().substring(1, 4);
					if (sr.equals("200") || sr.equals("207")) {
						List<FailedPrintsDto> failedPrintsListDtos = null;
						List<List<FailedPrintsDto>> failedPrintsList = new ArrayList<List<FailedPrintsDto>>();
						String s1 = letterConfResponse.getBody();
						String s = "{\"data\":";
						String s2 = "}";
						String s3 = s + s1 + s2;
						JSONObject obj = new JSONObject(s3);
						JSONArray notidata = obj.getJSONArray("data");
						int n = notidata.length();
						for (int i = 0; i < n; ++i) {
							JSONObject notification = notidata.getJSONObject(i);
							String status = notification.getString("status");
							if (status.equalsIgnoreCase("Success")) {
								letterPrintCount = letterPrintCount + 1;
							} else {
								JSONArray arr2 = notification.getJSONArray("loanPaymentList");
								int n1 = arr2.length();
								FailedPrintsDto failedPrintsDto = new FailedPrintsDto();
								for (int j = 0; j < n1; j++) {
									JSONObject notification1 = arr2.getJSONObject(j);
									
									Long loanNum = notification1.getLong("loanNumber");
									if(!String.valueOf(loanNum).startsWith("9")) {
									failedPrintsListDtos = new ArrayList<FailedPrintsDto>();
									//FailedPrintsDto failedPrintsDto = new FailedPrintsDto();
							       
									loanNum = notification1.getLong("loanNumber");
									BigDecimal subtotal = BigDecimal.ZERO;
							        subtotal = subtotal.add(notification1.getBigDecimal("monthlyPayment"));
							        subtotal = subtotal.add(notification1.getBigDecimal("additionalPrincipal"));
							        subtotal = subtotal.add(notification1.getBigDecimal("additionalEscrow"));
							        subtotal = subtotal.add(notification1.getBigDecimal("lateCharge"));
							        subtotal = subtotal.add(notification1.getBigDecimal("nsfFee"));
							        subtotal = subtotal.add(notification1.getBigDecimal("other"));
							        subtotal = subtotal.add(notification1.getBigDecimal("transactionFee"));
							        subtotal = subtotal.add(notification1.getBigDecimal("corporateAdvanceFee"));
							        failedPrintsDto.setLoanNumber(loanNum);
							        failedPrintsDto.setTotalFee(subtotal);
							        failedPrintsDto.setPayerName(notification1.getString("payerName"));
							        failedPrintsListDtos.add(failedPrintsDto);
									LOGGER.info("failedEmailsListDtos: "+failedPrintsListDtos);
									}else {
									 FailedPrintsDto failedSecondaryPrintsDto = new FailedPrintsDto();
									loanNum = notification1.getLong("loanNumber");
									BigDecimal subtotal = BigDecimal.ZERO;
							        subtotal = subtotal.add(notification1.getBigDecimal("monthlyPayment"));
							        subtotal = subtotal.add(notification1.getBigDecimal("additionalPrincipal"));
							        subtotal = subtotal.add(notification1.getBigDecimal("additionalEscrow"));
							        subtotal = subtotal.add(notification1.getBigDecimal("lateCharge"));
							        subtotal = subtotal.add(notification1.getBigDecimal("nsfFee"));
							        subtotal = subtotal.add(notification1.getBigDecimal("other"));
							        subtotal = subtotal.add(notification1.getBigDecimal("transactionFee"));
							        subtotal = subtotal.add(notification1.getBigDecimal("corporateAdvanceFee"));
							        failedSecondaryPrintsDto.setLoanNumber(loanNum);
							        failedSecondaryPrintsDto.setPayerName(notification1.getString("payerName"));
							        failedSecondaryPrintsDto.setTotalFee(subtotal);
							        failedPrintsListDtos.add(failedSecondaryPrintsDto);
							        
									LOGGER.info("failedSecondaryEmailsListDtos: {}",failedPrintsListDtos);
									}
								}
								failedPrintsList.add(failedPrintsListDtos);
								LOGGER.info("failedPrintsList : {}",failedPrintsList);
								letterFailedToPrintCount = letterFailedToPrintCount + 1;
							}
						}
						isProcessedPaymentsUpdatedForNoti = false;
						successfulIds = null;
						processPaymentsNotiResponse.setIsSuccessful(true);
						processPaymentsNotiResponse.setLetterPrintedSuccessfully(letterPrintCount);
						processPaymentsNotiResponse.setLetterFailedToPrint(letterFailedToPrintCount);
						
						processPaymentsNotiResponse.setMessage("Letter print  process completed");
						processPaymentsNotiResponse.setPrintResStatus(sr);
						processPaymentsNotiResponse.setFailedPrintsListDtos(failedPrintsListDtos);
						processPaymentsNotiResponse.setFailedPrintsList(failedPrintsList);
						
						return processPaymentsNotiResponse;
					} else {
						LOGGER.error("There is no content.", letterConfResponse);
						processPaymentsNotiResponse.setIsSuccessful(false);
						processPaymentsNotiResponse.setMessage(letterConfResponse.toString());
						processPaymentsNotiResponse.setPrintResStatus(sr);
						return processPaymentsNotiResponse;
					}
					}else {
						processPaymentsNotiResponse.setIsSuccessful(true);
						processPaymentsNotiResponse.setLetterPrintedSuccessfully(letterPrintCount);
						processPaymentsNotiResponse.setLetterFailedToPrint(letterFailedToPrintCount);
						
						processPaymentsNotiResponse.setMessage("No Letter for print.");
						return processPaymentsNotiResponse;
					}
				} catch (HttpStatusCodeException hpe) {
					LOGGER.error("Exception Occured duing sending email", hpe);
					processPaymentsNotiResponse.setIsSuccessful(false);
					processPaymentsNotiResponse.setMessage("HttpStatusCodeException : "+hpe);
					return processPaymentsNotiResponse;
				}
			} else {
				isProcessedPaymentsUpdatedForNoti = false;
				successfulIds = null;
				processPaymentsNotiResponse.setIsSuccessful(false);
				processPaymentsNotiResponse.setMessage("There is no record for printing.");
				return processPaymentsNotiResponse;
			}
		} else {
			processPaymentsNotiResponse.setIsSuccessful(false);
			processPaymentsNotiResponse.setMessage("Process payment not updated.");
			return processPaymentsNotiResponse;
		}
	}

}
