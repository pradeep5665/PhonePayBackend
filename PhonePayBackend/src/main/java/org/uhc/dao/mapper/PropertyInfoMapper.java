/* 
 * ===========================================================================
 * File Name PropertyInfoMapper.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: PropertyInfoMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PropertyDto;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is The PropertyInfoMapper class that is mapping the concerned table values to PropertyDto class.
 */
public class PropertyInfoMapper implements RowMapper<PropertyDto> {
	@Override
	public PropertyDto mapRow(ResultSet rs, int i) throws SQLException {
		PropertyDto propertyDto = new PropertyDto();
		propertyDto.setAddress(rs.getString("A1_ADDR_1"));
		propertyDto.setCity(rs.getString("A1_CITY"));
		propertyDto.setState(rs.getString("A1_STATE"));
		propertyDto.setZip(rs.getString("A1_ZIP"));
		return propertyDto;
	}
}
