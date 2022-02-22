
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PaymentAdviceDetailsDto;

public class PaymentAdviceDetailsMapper  implements RowMapper<PaymentAdviceDetailsDto>{

	@Override
	public PaymentAdviceDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PaymentAdviceDetailsDto paymentAdviceDetailsDto = new PaymentAdviceDetailsDto();
		paymentAdviceDetailsDto.setPaymentId(rs.getInt("PAYMENT_ID"));
		paymentAdviceDetailsDto.setPaymentAdviceMessage(rs.getString("NOTE"));
		paymentAdviceDetailsDto.setPaymentAdviceStatus(rs.getBoolean("COMPLETED"));
		paymentAdviceDetailsDto.setProcessedBy(rs.getString("PROCESSED_BY"));
		paymentAdviceDetailsDto.setBatchCode(rs.getString("BATCH_CODE"));
		paymentAdviceDetailsDto.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		Date paymentDateTime = rs.getTimestamp("DATE_CREATED");
		paymentAdviceDetailsDto.setScheduledDate(paymentDateTime == null ? null : paymentDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		paymentAdviceDetailsDto.setConfirmationNumber(rs.getString("CONFIRMATION_NUMBER"));
		paymentAdviceDetailsDto.setPaymentAdviceType(rs.getString("DEPT_NAME"));
		paymentAdviceDetailsDto.setPayorName(rs.getString("PAYOR_NAME"));
		paymentAdviceDetailsDto.setPayorType(rs.getString("PAYOR_TYPE"));
		paymentAdviceDetailsDto.setEnteredBy(rs.getString("ENTERED_BY"));
		Date dateCanceled = rs.getTimestamp("DATE_CANCELED");
		paymentAdviceDetailsDto.setDateCalcelled(dateCanceled==null ? false : true);
		paymentAdviceDetailsDto.setRemovedFromBK(rs.getBoolean("REMOVED_FROM_BK"));
		
		return paymentAdviceDetailsDto;
	}

}
