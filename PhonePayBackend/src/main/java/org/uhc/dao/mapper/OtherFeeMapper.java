package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.OtherFeeDto;

public class OtherFeeMapper implements RowMapper<OtherFeeDto> {

	@Override
	public OtherFeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		OtherFeeDto feeDto = new OtherFeeDto();
		feeDto.setId(rs.getInt("ID"));
		feeDto.setSchedulePaymentId(rs.getInt("PAYMENT_ID"));
		feeDto.setFeeCode(rs.getInt("ITEM_TYPE_ID"));
		feeDto.setFeeAmount(rs.getBigDecimal("AMOUNT"));
		return feeDto;
	}

}
