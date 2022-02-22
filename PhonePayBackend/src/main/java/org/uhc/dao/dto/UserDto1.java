package org.uhc.dao.dto;

import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

public class UserDto1 {

	private static final UserRole SERVICING_USER = null;

	private static final UserRole SERVICING_ADMIN = null;
	
	public enum UserRole {
		SERVICING_USER("ServicingUser"), SERVICING_ADMIN("ServicingAdmin");

		private String userRole;

		UserRole(String role) {
			this.userRole = role;
		}

		public String getUserRole() {
			return userRole;
		}
	}

	private int userId;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	private int loginFails;
	private boolean locked;
	private boolean loginStatus;

	public UserDto1() {
	}

	public UserDto1(SearchResult result) throws NamingException {
		this.role = (String) result.getAttributes().get("employeeType").get(0);
		this.firstName = (String) result.getAttributes().get("GivenName").get(0);
		this.lastName = (String) result.getAttributes().get("sn").get(0);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isServicing() {
		if (SERVICING_ADMIN != null) {
			return SERVICING_USER.getUserRole().equals(role) || SERVICING_ADMIN.getUserRole().equals(role);
		} else {
			return false;
		}
	}

	public boolean isServicingPlus() {
		if (SERVICING_ADMIN != null) {
			return SERVICING_ADMIN.getUserRole().equals(role);
		} else {
			return false;
		}

	}

	public int getLoginFails() {
		return loginFails;
	}

	public void setLoginFails(int loginFails) {
		this.loginFails = loginFails;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
}
