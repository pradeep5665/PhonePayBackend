package org.uhc.dao.mapper;

import org.uhc.dao.dto.GetAdviceBatchDto;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

public class AdviceBatchMapper implements RowMapper<GetAdviceBatchDto> {

    @Override
    public GetAdviceBatchDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        GetAdviceBatchDto getAdviceBatchDto = new GetAdviceBatchDto();
        //Date date = rs.getTimestamp("DATE");
        getAdviceBatchDto.setDate(rs.getDate("DATE"));

        getAdviceBatchDto.setBk_batch_code(rs.getString("BK_BATCH_CODE"));
        getAdviceBatchDto.setClosed(rs.getBoolean("CLOSED"));
        getAdviceBatchDto.setProcessed_by(rs.getString("PROCESSED_BY"));

        return getAdviceBatchDto;
    }
}
