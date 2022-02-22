/**
 * 
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.GetProcessPaymentsListDto;

/**
 * @author nehas3
 *
 */
public class GetProcessPaymentsListMapper implements RowMapper<GetProcessPaymentsListDto> {

	@Override
	public GetProcessPaymentsListDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		GetProcessPaymentsListDto getProcessPaymentsListDto = new GetProcessPaymentsListDto();
		getProcessPaymentsListDto.setPaymentId(rs.getInt("ID"));
		getProcessPaymentsListDto.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		Date paymentDateTime = rs.getTimestamp("DATE_CREATED");
		getProcessPaymentsListDto.setCreateDate(paymentDateTime == null ? null : paymentDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		getProcessPaymentsListDto.setConfirmationNumber(rs.getString("CONFIRMATION_NUMBER"));
		getProcessPaymentsListDto.setNameOnAccount(rs.getString("PAYOR_NAME"));
		getProcessPaymentsListDto.setAgentName(rs.getString("ENTERED_BY"));
		getProcessPaymentsListDto.setAccountNumber(rs.getString("BANK_ACCOUNT_NUMBER"));
		getProcessPaymentsListDto.setRoutingNumber(rs.getString("BANK_ROUTING_NUMBER"));
		getProcessPaymentsListDto.setAccountType(rs.getString("BANK_ACCOUNT_TYPE"));
		getProcessPaymentsListDto.setEmails(rs.getString("NOTIFICATION_EMAILS"));
		getProcessPaymentsListDto.setAdviceType(rs.getInt("ADVICE_CODE_ID"));
		getProcessPaymentsListDto.setAdviceNote(rs.getString("NOTE"));
		
		getProcessPaymentsListDto.setLine1(rs.getString("PAYOR_STREET"));
		getProcessPaymentsListDto.setCity(rs.getString("PAYOR_CITY"));
		getProcessPaymentsListDto.setState(rs.getString("PAYOR_STATE"));
		getProcessPaymentsListDto.setZip(rs.getString("PAYOR_ZIP"));
		getProcessPaymentsListDto.setSendLetter(rs.getBoolean("SHOULD_SEND_LETTER"));
		return getProcessPaymentsListDto;
	}
}
