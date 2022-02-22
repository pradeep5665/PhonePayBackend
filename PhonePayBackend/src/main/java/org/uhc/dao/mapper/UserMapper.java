package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.UserDto;

public class UserMapper implements RowMapper<UserDto>{

	@Override
	public UserDto mapRow(ResultSet rs, int i) throws SQLException {
		UserDto user = new UserDto();
		user.setId(rs.getInt("id"));
		user.setEmail(rs.getString("email"));
		user.setFirstName(rs.getString("first_name"));
		user.setPassword(rs.getString("password"));
		user.setLastName(rs.getString("last_name"));
		
		return user;
	}
}
