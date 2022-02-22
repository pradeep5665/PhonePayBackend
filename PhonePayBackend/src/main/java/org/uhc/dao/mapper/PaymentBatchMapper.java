/**
 * 
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.bean.ProcessingPaymentsResponse;

/**
 * @author nehas3
 *
 */
public class PaymentBatchMapper implements RowMapper<ProcessingPaymentsResponse> {

	
	@Override
	public ProcessingPaymentsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProcessingPaymentsResponse processingPaymentsResponse = new ProcessingPaymentsResponse();
		processingPaymentsResponse.setPaymentBatchId(rs.getInt("ID"));
		processingPaymentsResponse.setStatus(rs.getString("status"));
		Date date = rs.getTimestamp("DATE");
		processingPaymentsResponse.setDate(date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());	
		processingPaymentsResponse.setNumOfSuccessfulPayments(rs.getInt("num_of_successful_payments"));
		processingPaymentsResponse.setTotalSuccessfulPayments(rs.getBigDecimal("total_successful_payments"));
		processingPaymentsResponse.setNumNonAdvicePayments(rs.getInt("num_non_advice_payments"));
		processingPaymentsResponse.setTotalNonAdvicePayments(rs.getBigDecimal("total_non_advice_payments"));
		processingPaymentsResponse.setNumCashiering(rs.getInt("num_cashiering"));
		processingPaymentsResponse.setTotalCashiering(rs.getBigDecimal("total_cashiering"));
		processingPaymentsResponse.setNumBankruptcy(rs.getInt("num_bankruptcy"));
		processingPaymentsResponse.setTotalBankruptcy(rs.getBigDecimal("total_bankruptcy"));
		processingPaymentsResponse.setNumCollections(rs.getInt("num_collections"));
		processingPaymentsResponse.setTotalCollections(rs.getBigDecimal("total_collections"));
		processingPaymentsResponse.setNumLossMit(rs.getInt("num_loss_mit"));
		processingPaymentsResponse.setTotalLossMit(rs.getBigDecimal("total_loss_mit"));
		processingPaymentsResponse.setNumZionsTel(rs.getInt("num_zions_tel"));
		processingPaymentsResponse.setTotalZionsTel(rs.getBigDecimal("total_zions_tel"));
		processingPaymentsResponse.setNumZionsTad(rs.getInt("num_zions_tad"));
		processingPaymentsResponse.setTotalZionsTad(rs.getBigDecimal("total_zions_tad"));
		processingPaymentsResponse.setNumBk071(rs.getInt("num_bk_071"));
		processingPaymentsResponse.setTotalBk071(rs.getBigDecimal("total_bk_071"));
		processingPaymentsResponse.setNumBk073(rs.getInt("num_bk_073"));
		processingPaymentsResponse.setTotalBk073(rs.getBigDecimal("total_bk_073"));
		return processingPaymentsResponse;
	}

}
