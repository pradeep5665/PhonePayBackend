package org.uhc.envelop.response;

import java.util.List;

import org.uhc.dao.dto.UserDto;

public class UserResponse {

	private List<UserDto> userList;

	public List<UserDto> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDto> userList) {
		this.userList = userList;
	}
	
}
