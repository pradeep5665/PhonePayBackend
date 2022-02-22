package org.uhc.envelop.request;

import java.util.List;

import org.uhc.dao.dto.GetProcessPaymentsListDto;

public class ProcessPaymentPrintLetterReq {

	private List<Integer> successfulIds;
	private boolean updatedForNoti;
	List<GetProcessPaymentsListDto> processPaymentsList;
	private List<GetProcessPaymentsListDto>  processPaymentsListForFailedPrints;
	
	
	public List<Integer> getSuccessfulIds() {
		return successfulIds;
	}
	public void setSuccessfulIds(List<Integer> successfulIds) {
		this.successfulIds = successfulIds;
	}
	
	public boolean isUpdatedForNoti() {
		return updatedForNoti;
	}
	public void setUpdatedForNoti(boolean updatedForNoti) {
		this.updatedForNoti = updatedForNoti;
	}
	public List<GetProcessPaymentsListDto> getProcessPaymentsList() {
		return processPaymentsList;
	}
	public void setProcessPaymentsList(List<GetProcessPaymentsListDto> processPaymentsList) {
		this.processPaymentsList = processPaymentsList;
	}
	public List<GetProcessPaymentsListDto> getProcessPaymentsListForFailedPrints() {
		return processPaymentsListForFailedPrints;
	}
	public void setProcessPaymentsListForFailedPrints(List<GetProcessPaymentsListDto> processPaymentsListForFailedPrints) {
		this.processPaymentsListForFailedPrints = processPaymentsListForFailedPrints;
	}
	
	
}
