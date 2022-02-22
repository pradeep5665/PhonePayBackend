package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.IncorrectBankingInfoDto;

public class IncorrectBankingInfoMapper implements RowMapper<IncorrectBankingInfoDto> {

	@Override
	public IncorrectBankingInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		IncorrectBankingInfoDto incorrrectBankingInfoDto = new IncorrectBankingInfoDto();
		incorrrectBankingInfoDto.setUserId(rs.getInt("USER_ID"));
		incorrrectBankingInfoDto.setRoutingNum(rs.getString("ROUTING_NUMBER"));
		incorrrectBankingInfoDto.setAccountNum(rs.getString("ACCOUNT_NUMBER"));
		return incorrrectBankingInfoDto;
	}
}
