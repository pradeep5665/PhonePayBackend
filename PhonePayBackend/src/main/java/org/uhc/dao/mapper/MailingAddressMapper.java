package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.MailingAddressDto;

public class MailingAddressMapper implements RowMapper<MailingAddressDto> {

	@Override
	public MailingAddressDto mapRow(ResultSet rs, int i) throws SQLException {
		MailingAddressDto mailingAddressDto = new MailingAddressDto();
		mailingAddressDto.setMailingaddress(rs.getString("MAADD1"));
		mailingAddressDto.setMailingCity(rs.getString("MACTY"));
		mailingAddressDto.setMailingState(rs.getString("MAST"));
		mailingAddressDto.setMailingZip(rs.getString("MAZIP"));
		return mailingAddressDto;
	}
}
