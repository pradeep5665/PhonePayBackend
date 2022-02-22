package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.BorrowerNameDto;

public class BorrowerNameMapper implements RowMapper<BorrowerNameDto> {

	@Override
	public BorrowerNameDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		BorrowerNameDto borrowerName = new BorrowerNameDto();
		borrowerName.setLoanNumber(rs.getLong("A1_LOAN"));
		borrowerName.setFirstName(rs.getString("A1_FIRST_NAME"));
		borrowerName.setLastName(rs.getString("A1_LAST_NAME"));
		return borrowerName;
	}

}
