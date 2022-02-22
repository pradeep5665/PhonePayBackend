/**
 * 
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.GetResearchPaymentDto;

/**
 * @author nehas3
 *
 */
public class GetResearchPaymentMapper implements RowMapper<GetResearchPaymentDto>{

	
	@Override
	public GetResearchPaymentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetResearchPaymentDto GetResearchpaymentDetailsDto = new GetResearchPaymentDto();
		
		return GetResearchpaymentDetailsDto;
	}

}
