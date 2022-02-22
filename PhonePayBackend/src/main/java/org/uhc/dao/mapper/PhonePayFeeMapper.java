package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PhonePayFeeDto;

public class PhonePayFeeMapper implements RowMapper<PhonePayFeeDto>{

	@Override
	public PhonePayFeeDto mapRow(ResultSet rs, int i) throws SQLException {
		
		PhonePayFeeDto feeDto = new PhonePayFeeDto();
		feeDto.setFeeId(rs.getInt("ID"));
		feeDto.setFeeName(rs.getString("NAME"));
		return feeDto;
	}
}
