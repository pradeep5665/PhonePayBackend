package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.RoutingNumberDto;

public class RoutingNumberMapper implements RowMapper<RoutingNumberDto>{

	@Override
	public RoutingNumberDto mapRow(ResultSet rs, int i) throws SQLException {
		
		RoutingNumberDto routNumb = new RoutingNumberDto();
		routNumb.setRoutingNum(rs.getString("RNUMB"));
		return routNumb;
	}

}
