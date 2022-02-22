/**
 * 
 */
package org.uhc.dao.mapper;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.GetScheduledPaymentListDto;

/**
 * @author nehas3
 *
 */
public class GetScheduledPaymentListMapper implements RowMapper<GetScheduledPaymentListDto> {

	@Override
	public GetScheduledPaymentListDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		GetScheduledPaymentListDto getScheduledPayment = new GetScheduledPaymentListDto();

		getScheduledPayment.setPaymentId(rs.getInt("ID"));
		getScheduledPayment.setLoanNumber(rs.getLong("LOAN_NUMBER"));

		Date processedDate = rs.getTimestamp("DATE_PROCESSED");
		getScheduledPayment.setProcessedDate(processedDate != null ? processedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);

		Date createDateTime = rs.getTimestamp("DATE_CREATED");
		 
		getScheduledPayment.setScheduledDate(createDateTime == null ? null : createDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

		getScheduledPayment.setConfNum(rs.getString("CONFIRMATION_NUMBER"));	
		getScheduledPayment.setAccountNumber(rs.getString("BANK_ACCOUNT_NUMBER"));
		getScheduledPayment.setRoutingNumber(rs.getString("BANK_ROUTING_NUMBER"));
		getScheduledPayment.setAccountType(rs.getString("BANK_ACCOUNT_TYPE"));
		getScheduledPayment.setEmails(rs.getString("NOTIFICATION_EMAILS"));
		getScheduledPayment.setPayorName(rs.getString("PAYOR_NAME"));
		getScheduledPayment.setPayorAddress(rs.getString("PAYOR_STREET"));
		getScheduledPayment.setPayorCity(rs.getString("PAYOR_CITY"));
		getScheduledPayment.setPayorState(rs.getString("PAYOR_STATE"));
		getScheduledPayment.setPayorZip(rs.getString("PAYOR_ZIP"));
		getScheduledPayment.setPayorPhone(rs.getString("PAYOR_PHONE"));
		getScheduledPayment.setPayorType(rs.getString("PAYOR_TYPE"));
		getScheduledPayment.setEnteredBy(rs.getString("ENTERED_BY"));
		getScheduledPayment.setPrintConfirmation(rs.getBoolean("should_send_letter"));

	
		return getScheduledPayment;

	}
}
