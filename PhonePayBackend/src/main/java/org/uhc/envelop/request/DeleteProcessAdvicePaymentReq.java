/**
 * 
 */
package org.uhc.envelop.request;

/**
 * @author pradeepy
 *
 */
public class DeleteProcessAdvicePaymentReq {

	private int schedulePaymentId;
	private String deletedBy;
	private boolean removedFrom;
	public int getSchedulePaymentId() {
		return schedulePaymentId;
	}
	public void setSchedulePaymentId(int schedulePaymentId) {
		this.schedulePaymentId = schedulePaymentId;
	}
	public String getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}
	public boolean isRemovedFrom() {
		return removedFrom;
	}
	public void setRemovedFrom(boolean removedFrom) {
		this.removedFrom = removedFrom;
	}
	
	
}
