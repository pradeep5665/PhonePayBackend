/**
 * 
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.ResearchPaymentDto;
import org.uhc.util.Constants.PaymentStatus;

public class ResearchPaymentMapper implements RowMapper<ResearchPaymentDto> {

	@Override
	public ResearchPaymentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResearchPaymentDto researchPaymentDto = new ResearchPaymentDto();
		researchPaymentDto.setPaymentId(rs.getInt("ID"));
		researchPaymentDto.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		Date canceledDate = rs.getTimestamp("DATE_CANCELED");
		researchPaymentDto.setDateTime(canceledDate == null ? null : canceledDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());	
				
		Date createDate = rs.getTimestamp("DATE_CREATED");
		researchPaymentDto.setCreateDate(createDate == null ? null : createDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		researchPaymentDto.setProcessedDate(rs.getDate("DATE_PROCESSED"));


		if(researchPaymentDto.getCanceledDate()!=null) {
			researchPaymentDto.setCanceled(true);
		}else {
			researchPaymentDto.setCanceled(false);
		}
		
		if (researchPaymentDto.isCanceled() && researchPaymentDto.getProcessedDate()==null) {
			researchPaymentDto.setPaymentStatus(PaymentStatus.CANCELED);
		}else if(!researchPaymentDto.isCanceled() && researchPaymentDto.getProcessedDate()==null) {
			researchPaymentDto.setPaymentStatus(PaymentStatus.PENDING);
		}
		else {
			researchPaymentDto.setPaymentStatus(PaymentStatus.PROCESSED);
		}

		return researchPaymentDto;
	}

}
