/**
 * 
 */
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BorrowerInfoDto;
import org.uhc.dao.dto.LoanInfoDto;
import org.uhc.dao.dto.OtherFeeDto;
import org.uhc.dao.dto.ResearchPaymentActualListDto;
import org.uhc.dao.dto.ResearchPaymentDto;
import org.uhc.envelop.request.ResearchPaymentRequest;
import org.uhc.envelop.request.SortingList;
import org.uhc.envelop.response.ResearchPaymentResponse;
import org.uhc.service.ResearchPaymentService;

/**
 * @author nehas3
 *
 */
@Service
public class ResearchPaymentServiceImpl implements ResearchPaymentService {
	private static final Logger LOGGER = LogManager.getLogger(ResearchPaymentServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	/**
	 * @author nehas3
	 * @date Oct 31, 2019
	 * @return ResearchPaymentResponse
	 * @Description : this is the getResearchPaymentInfo method to get list of
	 *              research payments
	 */
	@Override
	public ResearchPaymentResponse getResearchPaymentInfo(ResearchPaymentRequest researchPaymentRequest) {
		LOGGER.info("Entering into getResearchPaymentInfo method");
		ResearchPaymentResponse researchPaymentResponse = new ResearchPaymentResponse();
		List<ResearchPaymentActualListDto> researchPaymentList = new ArrayList<>();
		ResearchPaymentActualListDto researchPaymentActualListDto = null;
		try {
			List<ResearchPaymentDto> researchPaymentDtoList = userDao.getResearchPaymentInfo(researchPaymentRequest);
			LOGGER.info("RESULTS");
			LOGGER.info("Research Results: ", researchPaymentDtoList);
			if (researchPaymentDtoList != null && !researchPaymentDtoList.isEmpty()) {
				for (ResearchPaymentDto researchPaymentDto : researchPaymentDtoList) {
					LoanInfoDto loanInfo = userDao.getLoanAccountDetailsByLoanNum(researchPaymentDto.getLoanNumber());
					researchPaymentActualListDto = new ResearchPaymentActualListDto();
					List<BorrowerInfoDto> borrowerInfoList = userDao.getBorroweList(researchPaymentDto.getLoanNumber(),
							researchPaymentRequest.getLastName());
					for (BorrowerInfoDto borrowerInfo : borrowerInfoList) {
						if (borrowerInfo.getSequence() == 1) {
							researchPaymentActualListDto
									.setBorrowerName(borrowerInfo.getFirstName() + " " + borrowerInfo.getLastName());
						} else if (borrowerInfo.getSequence() == 2) {
							researchPaymentActualListDto
									.setCoBorrowerName(borrowerInfo.getFirstName() + " " + borrowerInfo.getLastName());
						}
					}
					if (researchPaymentDto.getProcessedDate() != null) {
						researchPaymentActualListDto.setPaymentDate(researchPaymentDto.getProcessedDate());
					} else {
						researchPaymentActualListDto.setPaymentDate(researchPaymentDto.getCreateDate());
					}
					if (researchPaymentDto.getProcessedDate() != null && researchPaymentDto.getCreateDate()!=null) {
						String createDate = researchPaymentDto.getCreateDate();
						String processDate = researchPaymentDto.getProcessedDate();
						if(createDate.equals(processDate)){
							researchPaymentActualListDto.setLateProcess(false);
						}else {
							researchPaymentActualListDto.setLateProcess(true);
						}
					}
					researchPaymentActualListDto.setLoanNumber(researchPaymentDto.getLoanNumber());
					researchPaymentActualListDto.setPaymentId(researchPaymentDto.getPaymentId());
					researchPaymentActualListDto.setUserId(researchPaymentDto.getUserId());
					researchPaymentActualListDto.setPaymentStatus(researchPaymentDto.getPaymentStatus().toString());
					researchPaymentActualListDto.setMonthlyPayment(loanInfo.getMonthlyPayment());
					researchPaymentActualListDto.setCanceledBy(researchPaymentDto.getCanceledBy());
					researchPaymentActualListDto.setCanceledDate(researchPaymentDto.getCanceledDate());
					List<OtherFeeDto> feeList = userDao.getOtherFeeListForPayment(researchPaymentDto.getPaymentId());
					BigDecimal totalFeeAmount = new BigDecimal(0);
					if (!feeList.isEmpty()) {
						for (OtherFeeDto feeDetail : feeList) {
							String feeName = userDao.getFeeNameByFeeId(feeDetail.getFeeCode());
							if (feeName != null) {
								feeDetail.setFeeName(feeName);
								totalFeeAmount = totalFeeAmount.add(feeDetail.getFeeAmount());
								if(feeName.equals("PhonePay Fee")) {
									LOGGER.info("PaymentID: " + researchPaymentDto.getPaymentId());
									LOGGER.info("PhonePay Fee: " + feeDetail.getFeeAmount().intValue());
									researchPaymentActualListDto.setPhonePayFee(feeDetail.getFeeAmount().intValue());
								}
							}
							
						}
					} else {
						LOGGER.info("No other fee selected while making payment");
					}
					researchPaymentActualListDto.setTotalAmount(totalFeeAmount);
					if (researchPaymentRequest.getFromAmount() != null && researchPaymentRequest.getToAmount() != null) {
						BigDecimal fromAmount = researchPaymentRequest.getFromAmountForTotal();
						BigDecimal toAmount = researchPaymentRequest.getToAmountForTotal();
						if (researchPaymentActualListDto.getTotalAmountForTotal().compareTo(fromAmount) >= 0
								&& researchPaymentActualListDto.getTotalAmountForTotal().compareTo(toAmount) <= 0) {
							researchPaymentList.add(researchPaymentActualListDto);
						}
					} else {
						researchPaymentList.add(researchPaymentActualListDto);
					}
				}
				if (!researchPaymentList.isEmpty() && researchPaymentList != null) {
					String sortingTypeDate = ""; 
					String sortingTypeAmount = "";
					String sortingTypeLoanNumber = "";
					String DateOrder = "";
					String AmountOrder = "";
					String LoanNumberOrder = "";
					if(researchPaymentRequest.getSortingList()!=null) {
						for(SortingList getSortType: researchPaymentRequest.getSortingList()) {
							if(getSortType.getSortingType().equals("Date")){
								sortingTypeDate = getSortType.getSortingType();
								DateOrder = getSortType.getSortingOrder();
							}
							if(getSortType.getSortingType().equals("Amount")){
								sortingTypeAmount = getSortType.getSortingType();
								AmountOrder = getSortType.getSortingOrder();
							}
							if(getSortType.getSortingType().equals("Loan")){
								sortingTypeLoanNumber = getSortType.getSortingType();
								LoanNumberOrder = getSortType.getSortingOrder();
							}
						}
							if(((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC")||DateOrder.equals("DESC"))) &&  (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC")||AmountOrder.equals("DESC"))) && !sortingTypeLoanNumber.equals("Loan")) ||
									((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC")||DateOrder.equals("DESC"))) &&  (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")||LoanNumberOrder.equals("DESC"))) && !sortingTypeAmount.equals("Amount"))||	
									((sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC")||AmountOrder.equals("DESC"))) &&  (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")||LoanNumberOrder.equals("DESC"))) && !sortingTypeDate.equals("Date"))
									
									) {
								
									if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate)
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal)));
									}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate)
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed()));
									}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("DESC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate).reversed()
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal)));
									}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("DESC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate).reversed()
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed()));
									}
									
									if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate)
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber)));
									}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate)
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber).reversed()));
									}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate).reversed()
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber)));
									}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate).reversed()
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber).reversed()));
									}
									
									
									if((sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal)
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber)));
									}
								   else if((sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal)
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber).reversed()));
									}
								   else if((sortingTypeAmount.equals("Amount") && (AmountOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed()
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber)));
									}
								   else if((sortingTypeAmount.equals("Amount") && (AmountOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed()
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber).reversed()));
									}
							
						}
							
							
							if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC")||DateOrder.equals("DESC"))) &&  (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC")||AmountOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")||LoanNumberOrder.equals("DESC"))))
								{
								
									if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate)
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal)
									        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber))));
									}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate)
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal))
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber).reversed()));
								}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate).reversed()
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed())
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber).reversed()));
								}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("DESC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate)
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed())
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber).reversed()));
								}
									
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("ASC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate)
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed())
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber)));
								}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("DESC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("DESC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate).reversed()
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal))
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber).reversed()));
										
								}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("DESC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("ASC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate).reversed()
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal))
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber)));
										
								}
								   else if((sortingTypeDate.equals("Date") && (DateOrder.equals("DESC"))) && (sortingTypeAmount.equals("Amount") && (AmountOrder.equals("DESC"))) && (sortingTypeLoanNumber.equals("Loan") && (LoanNumberOrder.equals("ASC")))) {
										researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getPaymentDate).reversed()
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed())
								        	    .thenComparing(Comparator.comparing(ResearchPaymentActualListDto::getLoanNumber)));
										
								}
							}
							
							if(sortingTypeAmount.equals("Amount") && !sortingTypeDate.equals("Date") && !sortingTypeLoanNumber.equals("Loan")) {
								if(AmountOrder.equals("ASC")) {
								researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal));
								}else {
									researchPaymentList.sort(Comparator.comparing(ResearchPaymentActualListDto::getTotalAmountForTotal).reversed());
								}
							}
							
							
						
					}
					
					researchPaymentResponse.setIsSuccessful(true);
					researchPaymentResponse.setMessage("Research data found successfully");
					researchPaymentResponse.setResearchPaymentDto(researchPaymentList);
				}
			} else {
				researchPaymentResponse.setIsSuccessful(false);
				researchPaymentResponse.setMessage("Could not get research data list");
			}

		} catch (Exception exp) {
			researchPaymentResponse.setIsSuccessful(false);
			researchPaymentResponse.setMessage("Could not get research data");
			LOGGER.error("Could not get research data because of exception", exp);
		}

		return researchPaymentResponse;
	}

}
