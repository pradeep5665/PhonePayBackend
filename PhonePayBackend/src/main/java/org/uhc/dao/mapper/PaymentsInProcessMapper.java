/**
 * 
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PaymentsInProcessDto;

/**
 * @author nehas3
 *
 */
public class PaymentsInProcessMapper implements RowMapper<PaymentsInProcessDto> {
	@Override
	public PaymentsInProcessDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PaymentsInProcessDto paymentsInProcessDto = new PaymentsInProcessDto();
		paymentsInProcessDto.setPaymentId(rs.getInt("ID"));
		paymentsInProcessDto.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		Date paymentDateTime = rs.getTimestamp("DATE_CREATED");
		Date datCanceled = rs.getTimestamp("DATE_CANCELED");
		paymentsInProcessDto.setCancelled(datCanceled==null ? false : true);

		paymentsInProcessDto.setDateTime(paymentDateTime == null ? null
				: paymentDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		paymentsInProcessDto.setAdvicePaymentId(rs.getInt("PAYMENT_ID"));
		paymentsInProcessDto.setPayorName(rs.getString("PAYOR_NAME"));
		paymentsInProcessDto.setPayorType(rs.getString("PAYOR_TYPE"));
		paymentsInProcessDto.setAdviceCompleted(rs.getBoolean("COMPLETED"));
		paymentsInProcessDto.setEnteredBy(rs.getString("ENTERED_BY"));
		return paymentsInProcessDto;
	}
}
