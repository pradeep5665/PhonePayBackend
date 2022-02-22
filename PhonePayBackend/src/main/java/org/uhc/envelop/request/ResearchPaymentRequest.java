/**
 * 
 */
package org.uhc.envelop.request;

import org.uhc.dao.dto.ResearchPaymentActualListDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author nehas3
 *
 */
public class ResearchPaymentRequest {

	private String fromDate;
	private String toDate;
	private String fromAmount;
	private String toAmount;
	private List<Long> loanNumbers;
	private List<String> servicingAgents;
	private String lastName;
	private String firstName;
	private List<SortingList> sortingList;
	List<ResearchPaymentActualListDto> researchPaymentsList;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getFromAmountForTotal() {
		return (new BigDecimal(fromAmount));
	}

	public String getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(String forAmount) {
		this.fromAmount = forAmount;
	}

	public BigDecimal getToAmountForTotal() {
		return (new BigDecimal(toAmount));
	}

	public String getToAmount() {
		return toAmount;
	}

	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}

	public List<Long> getLoanNumbers() {
		return loanNumbers;
	}

	public void setLoanNumbers(List<Long> loanNumbers) {
		this.loanNumbers = loanNumbers;
	}

	public List<String> getServicingAgents() {
		return servicingAgents;
	}

	public void setServicingAgents(List<String> servicingAgents) {
		this.servicingAgents = servicingAgents;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<SortingList> getSortingList() {
		return sortingList;
	}

	public void setSortingList(List<SortingList> sortingList) {
		this.sortingList = sortingList;
	}

	public List<ResearchPaymentActualListDto> getResearchPaymentsList() {
		return researchPaymentsList;
	}
	public void setResearchPaymentsList(List<ResearchPaymentActualListDto> researchPaymentsList) {
		this.researchPaymentsList = researchPaymentsList;
	}
}
