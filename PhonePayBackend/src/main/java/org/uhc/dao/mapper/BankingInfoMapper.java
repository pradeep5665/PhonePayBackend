package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.BankingInfoDto;

public class BankingInfoMapper implements RowMapper<BankingInfoDto> {

	@Override
	public BankingInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		BankingInfoDto bankingInfo = new BankingInfoDto();

		bankingInfo.setRoutingNumber(rs.getString("RNUMB"));
		bankingInfo.setBankName(rs.getString("RBANK"));
		bankingInfo.setBankAddress(rs.getString("RADDR1"));
		bankingInfo.setBankCity(rs.getString("RADDR2"));
		bankingInfo.setBankCountry(rs.getString("RADDR3"));
		bankingInfo.setBankZip(rs.getString("RADDR4"));
		bankingInfo.setBankPhoneNumber(rs.getString("RADDR5"));

		return bankingInfo;
	}

}
