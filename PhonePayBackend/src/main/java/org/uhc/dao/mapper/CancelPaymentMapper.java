package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.CancelPaymentDto;

public class CancelPaymentMapper implements RowMapper<CancelPaymentDto> {

	@Override
	public CancelPaymentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CancelPaymentDto cancelPaymentDto = new CancelPaymentDto();

		cancelPaymentDto.setProcessedByEmail(rs.getString("PROCESSED_BY_EMAIL"));
		cancelPaymentDto.setBatchCode(rs.getString("BATCH_CODE"));
		return cancelPaymentDto;
	}

}
