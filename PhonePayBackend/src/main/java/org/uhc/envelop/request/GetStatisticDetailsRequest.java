/**
 * 
 */
package org.uhc.envelop.request;

/**
 * @author nehas3
 *
 */
public class GetStatisticDetailsRequest {

	private String startDate;
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
