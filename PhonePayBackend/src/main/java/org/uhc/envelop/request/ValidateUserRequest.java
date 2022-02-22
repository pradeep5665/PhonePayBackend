
package org.uhc.envelop.request;

/**
 * @author nehas3
 *
 */
public class ValidateUserRequest {

	private int userId;
	private String phonepayToken;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPhonepayToken() {
		return phonepayToken;
	}

	public void setPhonepayToken(String phonepayToken) {
		this.phonepayToken = phonepayToken;
	}

}
