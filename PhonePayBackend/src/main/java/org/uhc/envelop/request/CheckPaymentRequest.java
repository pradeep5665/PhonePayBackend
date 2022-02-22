package org.uhc.envelop.request;

import java.util.ArrayList;
import java.util.List;

public class CheckPaymentRequest {

	private long loanNumber;
	/*
	 * private String paymentSource; private String scheduledType;
	 */
	private boolean pay;
	private boolean printedConfirmation;
	private List<ScheduledCheckPaymentRequest> scheduledPayments;
	private ScheduledCheckPaymentRequest schedulingPayment;
	private List<String> emailAddressList;

	CheckPaymentRequest() {
		scheduledPayments = new ArrayList<>();
		schedulingPayment = new ScheduledCheckPaymentRequest();
	}

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public boolean isPay() {
		return pay;
	}

	public void setPay(boolean pay) {
		this.pay = pay;
	}

	public boolean isPrintedConfirmation() {
		return printedConfirmation;
	}

	public void setPrintedConfirmation(boolean printedConfirmation) {
		this.printedConfirmation = printedConfirmation;
	}

	public List<ScheduledCheckPaymentRequest> getScheduledPayments() {
		return scheduledPayments;
	}

	public void setScheduledPayments(List<ScheduledCheckPaymentRequest> scheduledPayments) {
		this.scheduledPayments = scheduledPayments;
	}

	public ScheduledCheckPaymentRequest getSchedulingPayment() {
		return schedulingPayment;
	}

	public void setSchedulingPayment(ScheduledCheckPaymentRequest schedulingPayment) {
		this.schedulingPayment = schedulingPayment;
	}

	public List<String> getEmailAddressList() {
		return emailAddressList;
	}

	public void setEmailAddressList(List<String> emailAddressList) {
		this.emailAddressList = emailAddressList;
	}

	@Override
	public String toString() {
		return "CheckPaymentRequest [loanNumber=" + loanNumber + ", pay=" + pay + ", printedConfirmation="
				+ printedConfirmation + ", scheduledPayments=" + scheduledPayments + ", schedulingPayment="
				+ schedulingPayment + ", emailAddressList=" + emailAddressList + "]";
	}

}
