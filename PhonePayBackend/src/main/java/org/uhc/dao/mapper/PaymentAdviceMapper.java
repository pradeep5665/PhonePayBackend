/**
 * 
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PaymentAdviceDto;

/**
 * @author nehas3
 *
 */
public class PaymentAdviceMapper implements RowMapper<PaymentAdviceDto>{

	@Override
	public PaymentAdviceDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		PaymentAdviceDto paymentAdvice = new PaymentAdviceDto();

		paymentAdvice.setId(rs.getInt("ID"));
		paymentAdvice.setAdviceId(rs.getInt("ADVICE_CODE_ID"));
		paymentAdvice.setSchedulePaymentId(rs.getInt("PAYMENT_ID"));
		paymentAdvice.setAdviceNotes(rs.getString("NOTE"));
		paymentAdvice.setBatchCode(rs.getString("BATCH_CODE"));
		paymentAdvice.setProcessedBy(rs.getString("PROCESSED_BY"));
		paymentAdvice.setCompleted(rs.getBoolean("COMPLETED"));
		return paymentAdvice;
	}
}
