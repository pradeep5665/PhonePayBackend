package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.UserDto1;

public class UserMapper1 implements RowMapper<UserDto1>{

	@Override
	public UserDto1 mapRow(ResultSet rs, int i) throws SQLException {
		UserDto1 user = new UserDto1();
		user.setUserId(rs.getInt("ID"));
		user.setUsername(rs.getString("USERNAME"));
		user.setFirstName(rs.getString("FIRST_NAME"));
		user.setLastName(rs.getString("LAST_NAME"));
		user.setEmail(rs.getString("EMAIL"));
		user.setRole(rs.getString("ROLE"));
		user.setLoginFails(rs.getInt("LOGIN_FAILS"));
		user.setLocked("Y".equals(rs.getString("LOCKED")));
		user.setLoginStatus(rs.getInt("LOGIN_STATUS")==1);
		return user;
	}
}
