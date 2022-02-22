/* 
 * ===========================================================================
 * File Name LoanMapper.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * (UHC) Utah Housing Corporation. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: LoanMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.LoanInfoDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is The LoanMapper class that is mapping the concerned table values to LoanDto class.
 */
public class LoanInfoMapper implements RowMapper<LoanInfoDto> {

    @Override
    public LoanInfoDto mapRow(ResultSet rs, int i) throws SQLException {
        LoanInfoDto loan = new LoanInfoDto();
        loan.setLoanNumber(rs.getLong("loan"));
        loan.setUnpaidPrincipalBalance(rs.getBigDecimal("unpaidPrincipalBalance"));
        loan.setInterestRate(rs.getBigDecimal("interestRate"));
        loan.setMonthlyPayment(rs.getBigDecimal("monthlyPayment"));
        loan.setEscrow(rs.getBigDecimal("escrow"));
        loan.setPrincipalAndInterest(rs.getBigDecimal("principalAndInterest"));
        loan.setNextDue(rs.getDate("nextDue"));
        loan.setTotalPrincipalAmount(rs.getLong("TOTALPRINCIPLEAMOUNT"));
        loan.setLateFees(rs.getBigDecimal("lateFees"));
        loan.setNsfFees(rs.getBigDecimal("nsfFees"));
        loan.setEscrowBalance(rs.getBigDecimal("escrowBalance"));
        loan.setEscrowAdvance(rs.getBigDecimal("escrowAdvance"));
        loan.setStopPayment((rs.getInt("stopPayment") != 0));
        loan.setStopDesc(rs.getString("stopDesc"));
        return loan;
    }
}
