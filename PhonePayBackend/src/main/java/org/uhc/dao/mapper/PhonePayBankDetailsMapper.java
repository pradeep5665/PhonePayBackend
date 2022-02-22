/**
 * 
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PhonePayBankDetailsDto;

/**
 * @author nehas3
 *
 */
public class PhonePayBankDetailsMapper implements RowMapper<PhonePayBankDetailsDto>{

	@Override
	public PhonePayBankDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PhonePayBankDetailsDto bankDetails = new PhonePayBankDetailsDto();
		bankDetails.setId(rs.getInt("ID"));
		bankDetails.setSchedulePaymentId(rs.getInt("SCHEDULED_PAYMENT_ID"));
		bankDetails.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
		bankDetails.setRoutingNumber(rs.getString("ROUTING_NUMBER"));
		bankDetails.setPayorName(rs.getString("PAYOR_NAME"));
		bankDetails.setPayorType(rs.getString("PAYOR_TYPE"));
		bankDetails.setPayorAddress(rs.getString("PAYOR_STREET"));
		bankDetails.setPayorPhone(rs.getString("PAYOR_PHONE"));
		bankDetails.setEmails(rs.getString("EMAIL"));
		bankDetails.setEnteredBy(rs.getString("ENTERED_BY"));
		return bankDetails;
	}

	
}
