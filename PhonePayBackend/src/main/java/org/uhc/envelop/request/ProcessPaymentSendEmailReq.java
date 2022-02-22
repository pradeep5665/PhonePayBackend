package org.uhc.envelop.request;

import java.util.List;

import org.uhc.dao.dto.GetProcessPaymentsListDto;

public class ProcessPaymentSendEmailReq {

	private List<Integer> successfulIds;
	private boolean updatedForNoti;
	List<GetProcessPaymentsListDto> processPaymentsList;
	
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
	
	
}
