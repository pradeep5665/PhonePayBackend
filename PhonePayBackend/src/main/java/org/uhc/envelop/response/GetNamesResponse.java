/**
 * 
 */
package org.uhc.envelop.response;

import java.util.List;

/**
 * @author nehas3
 *
 */
public class GetNamesResponse {

	private Boolean isSuccessful;
	private String message;
	private List<String> names;

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

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}
