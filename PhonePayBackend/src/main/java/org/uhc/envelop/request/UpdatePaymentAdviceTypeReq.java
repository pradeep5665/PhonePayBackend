/**
 * 
 */
package org.uhc.envelop.request;

/**
 * @author nehas3
 *
 */
public class UpdatePaymentAdviceTypeReq {

	private int schedulePaymentId;
	private String adviceType;

	public int getSchedulePaymentId() {
		return schedulePaymentId;
	}

	public void setSchedulePaymentId(int schedulePaymentId) {
		this.schedulePaymentId = schedulePaymentId;
	}

	public String getAdviceType() {
		return adviceType;
	}

	public void setAdviceType(String adviceType) {
		this.adviceType = adviceType;
	}

}
