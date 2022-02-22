package org.uhc.bean;

import java.util.List;

import org.uhc.dao.dto.FailedEmailsDto;
import org.uhc.dao.dto.FailedPrintsDto;
import org.uhc.dao.dto.GetProcessPaymentsListDto;

public class ProcessingPaymentsNotificationResponse {

	private Boolean isSuccessful;
	private String message;
	private int emailSentSuccessfully;
	private int emailFailedToSend;
	private int letterPrintedSuccessfully;
	private int letterFailedToPrint;
	private String emailResStatus;
	private String printResStatus;
	private List<Integer> successfulIds;
	private boolean updatedForNoti;
	List<GetProcessPaymentsListDto> processPaymentsList;
	private List<FailedEmailsDto> failedEmailsListDtos;
	private List<FailedPrintsDto> failedPrintsListDtos;
	private List<List<FailedEmailsDto>> failedEmailsList;
	private List<List<FailedPrintsDto>> failedPrintsList;
	
	public Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getEmailSentSuccessfully() {
		return emailSentSuccessfully;
	}
	public void setEmailSentSuccessfully(int emailSentSuccessfully) {
		this.emailSentSuccessfully = emailSentSuccessfully;
	}
	public int getEmailFailedToSend() {
		return emailFailedToSend;
	}
	public void setEmailFailedToSend(int emailFailedToSend) {
		this.emailFailedToSend = emailFailedToSend;
	}
	public int getLetterPrintedSuccessfully() {
		return letterPrintedSuccessfully;
	}
	public void setLetterPrintedSuccessfully(int letterPrintedSuccessfully) {
		this.letterPrintedSuccessfully = letterPrintedSuccessfully;
	}
	public int getLetterFailedToPrint() {
		return letterFailedToPrint;
	}
	public void setLetterFailedToPrint(int letterFailedToPrint) {
		this.letterFailedToPrint = letterFailedToPrint;
	}
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
	public String getEmailResStatus() {
		return emailResStatus;
	}
	public void setEmailResStatus(String emailResStatus) {
		this.emailResStatus = emailResStatus;
	}
	public String getPrintResStatus() {
		return printResStatus;
	}
	public void setPrintResStatus(String printResStatus) {
		this.printResStatus = printResStatus;
	}
	public List<FailedEmailsDto> getFailedEmailsListDtos() {
		return failedEmailsListDtos;
	}
	public void setFailedEmailsListDtos(List<FailedEmailsDto> failedEmailsListDtos) {
		this.failedEmailsListDtos = failedEmailsListDtos;
	}
	
	public List<FailedPrintsDto> getFailedPrintsListDtos() {
		return failedPrintsListDtos;
	}
	public void setFailedPrintsListDtos(List<FailedPrintsDto> failedPrintsListDtos) {
		this.failedPrintsListDtos = failedPrintsListDtos;
	}
	public List<List<FailedEmailsDto>> getFailedEmailsList() {
		return failedEmailsList;
	}
	public void setFailedEmailsList(List<List<FailedEmailsDto>> failedEmailsList) {
		this.failedEmailsList = failedEmailsList;
	}
	public List<List<FailedPrintsDto>> getFailedPrintsList() {
		return failedPrintsList;
	}
	public void setFailedPrintsList(List<List<FailedPrintsDto>> failedPrintsList) {
		this.failedPrintsList = failedPrintsList;
	}
	
	
}
