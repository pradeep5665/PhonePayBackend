package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.BorrowerInfoDto;

public class BorrowerInfoMapper implements RowMapper<BorrowerInfoDto> {

	@Override
	public BorrowerInfoDto mapRow(ResultSet rs, int i) throws SQLException {
		BorrowerInfoDto borrowerInfo = new BorrowerInfoDto();
		borrowerInfo.setLoanNumber(rs.getLong("A1_LOAN"));
		borrowerInfo.setSequence(rs.getInt("A1_SEQ"));
		borrowerInfo.setFirstName(rs.getString("A1_FIRST_NAME"));
		borrowerInfo.setLastName(rs.getString("A1_LAST_NAME"));
		borrowerInfo.setHomeArea(rs.getInt("A1_HOME_AREA"));
		borrowerInfo.setHomePrefix(rs.getInt("A1_HOME_PREFIX"));
		borrowerInfo.setHomeNumber(rs.getInt("A1_HOME_NUMBER"));
		return borrowerInfo;
	}	
}
