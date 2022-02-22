/* 
 * ===========================================================================
 * File Name UserDaoImpl.java
 * 
 * Created on Feb 25, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: UserDaoImpl.java,v $
 * ===========================================================================
 */
package org.uhc.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.uhc.bean.ProcessingPaymentsResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.*;
import org.uhc.dao.mapper.*;
import org.uhc.envelop.request.*;
import org.uhc.util.Constants.ScheduledPaymentType;

/**
 * @author nehas3
 * @date Feb 25, 2019
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Repository
public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

	@Autowired
	@Qualifier("db2JdbcTemplate")
	private JdbcTemplate db2JdbcTemplate;

	@Autowired
	@Qualifier("db2NamedJdbcTemplate")
	private NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate;

	@Autowired
	@Qualifier("postgresJdbcTemplate")
	private JdbcTemplate postgresJdbcTemplate;

	@Autowired
	@Qualifier("postgresNamedJdbcTemplate")
	private NamedParameterJdbcTemplate postgresNamedParameterJdbcTemplate;

	@Value("${uhc.homeowner}")
	private String homeowner;
	
	@Value("${uhc.ldataser}")
	private String ldataser;

	@Value("${uhc.combined}")
	private String combined;

	@Value("${uhc.as400cgi}")
	private String as400cgi;

	@Value("${uhc.uhfalib}")
	private String uhfalib;

	@Value("${uhc.utelib}")
	private String utelib;

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return UserDto
	 * @param username
	 * @return UserDto
	 * @Description : this is getUserByUsername method to get user-name on basis of
	 *              entered user name.
	 */
	@Override
	public UserDto1 getUserByUsername(String username) {
		UserDto1 userDto = null;
		try {
			LOGGER.info("getUserByUsername, username = ", username);
			final String SQL = "select ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, ROLE, LOGIN_FAILS, LOCKED, LOGIN_STATUS from "
					+ homeowner + ".USER where USERNAME = ?";
			userDto = db2JdbcTemplate.query(SQL, new Object[] { username }, new UserMapper1()).get(0);
		} catch (Exception exp) {
			LOGGER.error("Excpetion occured while varifying user name on the basis of entered user name. ", exp);
		}
		return userDto;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return UserDto1
	 * @param loanNumber
	 * @return UserDto1
	 * @Description : this is getUserByLoanNumber method to get user on basis of
	 *              entered loan number.
	 */
	@Override
	public List<UserDto1> getUserByLoanNumber(long loanNumber) {
		List<UserDto1> userDtoList = null;
		try {
			LOGGER.info("getUserByLoanNumber, loanNumber = ", loanNumber);
			final String SQL = "SELECT ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, ROLE, LOGIN_FAILS, LOCKED, LOGIN_STATUS from "
					+ homeowner + ".user, " + ldataser + ".srvalt1 where ssn = a1_soc_sec and a1_loan = ?";

			userDtoList = db2JdbcTemplate.query(SQL, new Object[] { loanNumber }, new UserMapper1());
			
		} catch (Exception exp) {
			LOGGER.error("Exception occurred while verifying user name on the basis of entered loan number. ", exp);
		}
		return userDtoList;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return UserDto1
	 * @param userId
	 * @return UserDto1
	 * @Description : this is getUserByUserId method to get user on basis of entered
	 *              userId.
	 */
	@Override
	public UserDto1 getUserByUserId(int userId) {
		UserDto1 userDto = null;
		try {
			LOGGER.info("getUserByUserId, userId = ", userId);
			final String SQL = "select ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, ROLE, LOGIN_FAILS, LOCKED, LOGIN_STATUS from "
					+ homeowner + ".USER where id = ?";
			userDto = db2JdbcTemplate.query(SQL, new Object[] { userId }, new UserMapper1()).get(0);
		} catch (Exception exp) {
			LOGGER.error("Excpetion occured while varifying user name on the basis of entered user id. ", exp);
		}
		return userDto;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param userId
	 * @return List<LoanInfoDto>
	 * @Description : this is getLoanAccountsByUserId method to get list of loan
	 *              accounts on basis of entered userId.
	 */
	@Override
	public List<LoanInfoDto> getLoanAccountsByUserId(int userId) {
		List<LoanInfoDto> loanDtoList = null;
		try {
			LOGGER.info("User id entered is", userId);

			final String SQL = "select coalesce(swloan#,0) as stopPayment, blloan as loan,sma240 as unpaidPrincipalBalance,sml060 as interestRate,sma070 as principalAndInterest,sme010 as escrow, sma070+sme010-sms010 as monthlyPayment, date(sma133 concat '-' concat sma132 concat '-' concat sma131) as nextDue, ml040_loan_amt as totalPrincipleAmount, sma200 as lateFees, sma220 as nsfFees,sme040 as escrowBalance, sme050 as escrowAdvance "
					+ "from " + ldataser + ".srvbal, " + ldataser + ".srvalt1, " + homeowner + ".user, " + ldataser
					+ ".srvdsr left outer join " + uhfalib + ".stopweb on smr010 = swloan# "
					+ "where smr010 * 10 = blloan and blloan=a1_loan and id = ? and ssn = a1_soc_sec";
			loanDtoList = db2JdbcTemplate.query(SQL, new Object[] { userId }, new LoanInfoMapper());

		} catch (Exception exp) {
			LOGGER.error("Exception occured while Finding the List of Loan Accounts ", exp);
		}
		return loanDtoList;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return LoanInfoDto
	 * @Description : this is getLoanAccountDetailsByLoanNum method to get loan
	 *              information on basis of entered loanNumber.
	 */
	@Override
	public LoanInfoDto getLoanAccountDetailsByLoanNum(long loanNumber) {
		LoanInfoDto loanDto = null;
		try {
			LOGGER.info("For getting Loan Account details, Loan number entered is", loanNumber);

			final String SQL = "select coalesce(swloan#,0) as stopPayment, SWDESC as stopDesc, blloan as loan,sma240 as unpaidPrincipalBalance,sml060 as interestRate,sma070 as principalAndInterest,sme010 as escrow, sma070+sme010-sms010 as monthlyPayment, date(sma133 concat '-' concat sma131 concat '-' concat sma132) as nextDue, ml040_loan_amt as totalPrincipleAmount, sma200 as lateFees, sma220 as nsfFees,sme040 as escrowBalance, sme050 as escrowAdvance "
					+ "from " + ldataser + ".srvbal, " + ldataser + ".srvalt1, " + ldataser + ".srvdsr left outer join "
					+ uhfalib + ".stopweb on smr010 = swloan# "
					+ "where smr010 * 10 = blloan and blloan=a1_loan and blloan = ?";

			loanDto = (LoanInfoDto) db2JdbcTemplate.query(SQL, new Object[] { loanNumber }, new LoanInfoMapper())
					.get(0);
			LOGGER.info("Loan account details by loan number is :", loanDto);
			LOGGER.info("Stop Code: " + loanDto.getStopDesc());
		} catch (Exception exp) {
			LOGGER.error("Exception occured Loan Account Details could not found ", exp);
		}
		return loanDto;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param userName
	 * @return List<Long>
	 * @Description : this is getLoanAccountsByUserName method to get list of loan
	 *              numbers on basis of entered userName.
	 */
	@Override
	public List<Long> getLoanAccountsByUserName(String userName) {
		List<Long> loanList = null;
		try {
			LOGGER.info("To loan number, user name enterd is :", userName);
			final String SQL = "select blloan as loan " + "from " + ldataser + ".srvbal, " + ldataser + ".srvalt1, "
					+ homeowner + ".user " + "where blloan=a1_loan and USERNAME = ? and ssn = a1_soc_sec";

			loanList = db2JdbcTemplate.queryForList(SQL, new Object[] { userName }, Long.class);
			LOGGER.info(
					"List of loan number is:" + (loanList.isEmpty() ? "Loan account does not exist " : " " + loanList));
		} catch (Exception exp) {
			LOGGER.error("Exception occured while getting loan number by using user name :: ", exp);
		}
		return loanList;
	}

	/**
	 * @author nehas3
	 * @date July 01, 2019
	 * @param userName
	 * @return List<Long>
	 * @Description : this is getLoanAccountsByLoanNumber method to get list of loan
	 *              numbers on basis of entered loanNumber.
	 */
	@Override
	public List<Long> getLoanAccountsByLoanNumber(long loanNumber) {
		List<Long> loanList = null;
		try {
			LOGGER.info("To loan number, user name enterd is :" , loanNumber);

			final String SQL = "SELECT DISTINCT A1_LOAN as loan " + "from " + ldataser + ".srvalt1"
					+ " where A1_SOC_SEC IN (SELECT A1_SOC_SEC FROM " + ldataser + ".srvalt1 WHERE A1_LOAN = ?) AND A1_SOC_SEC > 0 ORDER BY A1_LOAN";

			loanList = db2JdbcTemplate.queryForList(SQL, new Object[] { loanNumber }, Long.class);
			LOGGER.info(
					"List of loan number is:" + (loanList.isEmpty() ? "Loan account does not exist " : " " + loanList));
		} catch (Exception exp) {
			LOGGER.error("Exception occured while getting loan number by using loan number :: ", exp);
		}
		return loanList;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return PropertyDto
	 * @Description : this is getPropertyInfoByLoanNum method to get property
	 *              information of borrowers on basis of entered loanNumber.
	 */
	@Override
	public PropertyDto getPropertyInfoByLoanNum(long loanNumber) {
		PropertyDto propertyDto = null;
		try {
			LOGGER.info("For Property Details, Loan number entered is ", loanNumber);

			final String SQL = "select A1_ADDR_1, A1_CITY, A1_STATE, A1_ZIP from " + ldataser
					+ ".SRVALT1 where A1_LOAN = ?";
			propertyDto = (PropertyDto) db2JdbcTemplate
					.query(SQL, new Object[] { loanNumber }, new PropertyInfoMapper()).get(0);
			LOGGER.info("Property Details are : " , propertyDto.toString());
		} catch (Exception exp) {
			LOGGER.error("Exception occured while collecting property info ", exp);
			exp.getMessage();
		}
		return propertyDto;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return MailingAddressDto
	 * @Description : this is getMailingAddressByLoanNum method to get mailing
	 *              address of borrowers on basis of entered loanNumber.
	 */
	@Override
	public MailingAddressDto getMailingAddressByLoanNum(long loanNumber) {
		MailingAddressDto mailingAddressDto = null;
		try {
			LOGGER.info("For Getting Mailing Adress, Loan number entered is ", loanNumber);

			final String SQL = "select MAADD1, MACTY, MAST, MAZIP from " + combined + ".MPF001 where MALOAN# * 10 = ?";
			mailingAddressDto = (MailingAddressDto) db2JdbcTemplate
					.query(SQL, new Object[] { loanNumber }, new MailingAddressMapper()).get(0);
			LOGGER.info("Mailing Adress is : " , mailingAddressDto.toString());
		} catch (Exception exp) {
			LOGGER.error("Exception occured while collecting mailing address ", exp);
			exp.getMessage();
		}
		return mailingAddressDto;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param loanNumber
	 * @return List<BorrowerInfoDto>
	 * @Description : this is getBorroweListByLoanNumber method to get list of
	 *              borrowers on basis of entered loan number.
	 */
	@Override
	public List<BorrowerInfoDto> getBorroweListByLoanNumber(long loanNumber) {
		List<BorrowerInfoDto> borrowerList = null;
		try {
			LOGGER.info("The loan number, user name enterd is :", loanNumber);
			/*final String SQL = "select A1_LOAN, A1_SEQ, A1_FIRST_NAME, A1_LAST_NAME, A1_HOME_AREA concat A1_HOME_PREFIX concat A1_HOME_NUMBER as phoneNumber from "
					+ ldataser + ".srvalt1 where A1_LOAN = ?";*/
			final String SQL = "select A1_LOAN, A1_SEQ, A1_FIRST_NAME, A1_LAST_NAME, A1_HOME_AREA , A1_HOME_PREFIX , A1_HOME_NUMBER  from "
					+ ldataser + ".srvalt1 where A1_LOAN = ?";
			borrowerList = db2JdbcTemplate.query(SQL, new Object[] { loanNumber }, new BorrowerInfoMapper());
			LOGGER.info("List of Borrower is:"
					+ (borrowerList.isEmpty() ? "Borrowr info could not be found " : " " + borrowerList));
		} catch (Exception exp) {
			LOGGER.error("Exception occured while getting loan number by using user name :: ", exp);
			exp.getMessage();
		}
		return borrowerList;
	}

	@Override
	public List<BorrowerInfoDto> getBorroweList(long loanNumber, String lastName) {
		List<BorrowerInfoDto> borrowerList = null;
		try {
			LOGGER.info("The loan number, user name enterd is :", loanNumber);
			final String SQL = "select A1_LOAN, A1_SEQ, A1_FIRST_NAME, A1_LAST_NAME, A1_HOME_AREA , A1_HOME_PREFIX , A1_HOME_NUMBER  from "
					+ ldataser + ".srvalt1 where (A1_LAST_NAME = ? OR ? IS NULL ) and A1_LOAN = ?";

			borrowerList = db2JdbcTemplate.query(SQL, new Object[] { lastName, lastName, loanNumber },
					new BorrowerInfoMapper());
			LOGGER.info("List of Borrower is:"
					+ (borrowerList.isEmpty() ? "Borrowr info could not be found " : " " + borrowerList));
		} catch (Exception exp) {
			LOGGER.error("Exception occured while getting loan number by using user name :: ", exp);
			exp.getMessage();
		}
		return borrowerList;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param routingNum
	 * @return BankingInfoDto
	 * @Description : this is getBankingInfoByRoutingNum method to get banking
	 *              information of borrowers on basis of entered routingNum.
	 */
	@Override
	public BankingInfoDto getBankingInfoByRoutingNum(String routingNum) {
		BankingInfoDto bankingInfoDto = null;
		try {
			final String SQL = "select RNUMB, RBANK, RADDR1, RADDR2, RADDR3, RADDR4, RADDR5 from " + as400cgi
					+ ".routnumb where RNUMB = ?";
			bankingInfoDto = db2JdbcTemplate.query(SQL, new Object[] { routingNum }, new BankingInfoMapper()).get(0);
		} catch (Exception exp) {
			exp.getMessage();
		}
		return bankingInfoDto;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param routingNum
	 * @return boolean
	 * @Description : this is isRoutingNumberWhiteListed method to check if routing
	 *              number whitelisted
	 */
	@Override
	public boolean isRoutingNumberWhiteListed(String routingNumber) {
		RoutingNumberDto rout_numb = null;
		try {
			final String SQL = "select rnumb from " + as400cgi + ".routnumb where rnumb = ?";
			rout_numb = db2JdbcTemplate.query(SQL, new Object[] { routingNumber }, new RoutingNumberMapper()).get(0);

		} catch (Exception exp) {
			LOGGER.error("could not check routing number because of exception ", exp);
		}
		return ((rout_numb != null) ? true : false);
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param confirmationNumber, userId
	 * @return boolean
	 * @Description : this is doesConfirmationNumberExistForUser method to check if
	 *              confirmation number already exists
	 */
	@Override
	public boolean doesConfirmationNumberExistForUser(String confirmationNumber, int userId) {
		final String SQL = "select count(*) from " + homeowner
				+ ".SCHEDULED_PAYMENT where confirmation_number = ? and user_id = ?";
		int numCollisions = db2JdbcTemplate.queryForObject(SQL, Integer.class, confirmationNumber, userId);
		boolean collided = numCollisions > 0;
		if (collided) {
			LOGGER.warn("Amazing; a confirmation number collision was found for confirmationNumber: ",
					confirmationNumber + " and userId: ", userId);
		}
		return collided;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param paymentType, loanNumber, canceled
	 * @return List<Integer>
	 * @Description : this is getActivePaymentByPaymentType method to check if
	 *              payment already exists before scheduling new payment
	 */
	@Override
	public List<Integer> getActivePaymentByPaymentType(ScheduledPaymentType paymentType, long loanNumber,
			int canceled) {
		LOGGER.info("query active payments that the type is {}.", paymentType.getDesc());
		List<Integer> activatedPaymentId = null;
		try {
			final String SQL = "SELECT ID FROM PAYMENT WHERE LOAN_NUMBER = ?  and DATE_CANCELED IS NULL AND DATE_PROCESSED IS NULL";
			activatedPaymentId = postgresJdbcTemplate.queryForList(SQL, Integer.class, loanNumber);

		} catch (Exception exp) {
			LOGGER.error("could not get activated payment because of exception ", exp);
		}
		return activatedPaymentId;
	}

	@Override
	public List<GetScheduledPaymentListDto> getActivePaymentByPaymentType(ScheduledPaymentType paymentType,
			long loanNumber) {
		LOGGER.info("query active payments that the type is: {}", paymentType.getDesc());
		List<GetScheduledPaymentListDto> activatedPaymentId = null;
		try {
			final String SQL = "select * from PAYMENT where LOAN_NUMBER = ? "
					+ " and DATE_CANCELED is null and DATE_PROCESSED is null";
			activatedPaymentId = postgresJdbcTemplate.query(SQL, new GetScheduledPaymentListMapper(), loanNumber);

		} catch (Exception exp) {
			LOGGER.error("could not get activated payment because of exception ", exp);
		}
		return activatedPaymentId;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param userId, scheduledCheckPaymentRequest, paymentSource, sceduleType
	 * @return boolean
	 * @Description : this is scheduleCheckPayment method to schedule payment on
	 *              basis of requested parameters
	 */
	@Override
	public boolean scheduleCheckPayment(ScheduledCheckPaymentRequest scheduledCheckPaymentRequest) {
		int insertedRowCount = 0;
		try {

			final String SQL = "insert into PAYMENT (LOAN_NUMBER, CONFIRMATION_NUMBER, BANK_ACCOUNT_NUMBER, BANK_ROUTING_NUMBER, BANK_ACCOUNT_TYPE, PAYOR_NAME, PAYOR_TYPE, PAYOR_STREET, PAYOR_CITY, PAYOR_STATE, PAYOR_ZIP, PAYOR_PHONE, NOTIFICATION_EMAILS, ENTERED_BY, SHOULD_SEND_LETTER) "
					+ " values (:LOAN_NUMBER, :CONFIRMATION_NUMBER, :BANK_ACCOUNT_NUMBER, :BANK_ROUTING_NUMBER, :BANK_ACCOUNT_TYPE, :PAYOR_NAME, :PAYOR_TYPE, :PAYOR_STREET, :PAYOR_CITY, :PAYOR_STATE, :PAYOR_ZIP, :PAYOR_PHONE, :NOTIFICATION_EMAILS, :ENTERED_BY, :SHOULD_SEND_LETTER)";

			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("LOAN_NUMBER", scheduledCheckPaymentRequest.getLoanNumber())
					.addValue("CONFIRMATION_NUMBER", scheduledCheckPaymentRequest.getConfirmationNumber())
					.addValue("BANK_ACCOUNT_NUMBER", scheduledCheckPaymentRequest.getAccountNumber())
					.addValue("BANK_ROUTING_NUMBER", scheduledCheckPaymentRequest.getRoutingNumber())
					.addValue("BANK_ACCOUNT_TYPE", scheduledCheckPaymentRequest.getAccountType())
					.addValue("PAYOR_NAME", scheduledCheckPaymentRequest.getPayorName())
					.addValue("PAYOR_TYPE", scheduledCheckPaymentRequest.getPayorType())
					.addValue("PAYOR_STREET", scheduledCheckPaymentRequest.getPayorStreetAddress())
					.addValue("PAYOR_CITY", scheduledCheckPaymentRequest.getPayorCity())
					.addValue("PAYOR_STATE", scheduledCheckPaymentRequest.getPayorState())
					.addValue("PAYOR_ZIP", scheduledCheckPaymentRequest.getPayorZip())
					.addValue("PAYOR_PHONE", scheduledCheckPaymentRequest.getPayorPhone())
					.addValue("NOTIFICATION_EMAILS", scheduledCheckPaymentRequest.getEmails())
					.addValue("ENTERED_BY", scheduledCheckPaymentRequest.getEnteredBy())
					.addValue("SHOULD_SEND_LETTER", scheduledCheckPaymentRequest.isPrintedConfirmation());

			insertedRowCount = postgresNamedParameterJdbcTemplate.update(SQL, params);

		} catch (Exception exp) {
			LOGGER.error("Payment could not be scheduled because of Exception ", exp);
		}
		return (insertedRowCount > 0 ? true : false);
	}

	@Override
	public boolean updateScheduleCheckPayment(ScheduledCheckPaymentRequest scheduledCheckPaymentRequest) {
		int insertedRowCount = 0;
		try {
			final String SQL = "UPDATE PAYMENT SET LOAN_NUMBER = ?, CONFIRMATION_NUMBER = ?, BANK_ACCOUNT_NUMBER = ?, "
					+ "BANK_ROUTING_NUMBER = ?, BANK_ACCOUNT_TYPE = ?, PAYOR_NAME = ?, PAYOR_TYPE = ?, "
					+ "PAYOR_STREET = ?, PAYOR_STATE = ?, PAYOR_CITY = ?, PAYOR_ZIP = ?, PAYOR_PHONE = ?, "
					+ "NOTIFICATION_EMAILS = ?, ENTERED_BY = ?, SHOULD_SEND_LETTER = ?, DATE_CREATED = CURRENT_TIMESTAMP where ID = ?";
			Object[] params = new Object[] { 
					scheduledCheckPaymentRequest.getLoanNumber(),
					scheduledCheckPaymentRequest.getConfirmationNumber(),
					scheduledCheckPaymentRequest.getAccountNumber(), scheduledCheckPaymentRequest.getRoutingNumber(),
					scheduledCheckPaymentRequest.getAccountType(), scheduledCheckPaymentRequest.getPayorName(),
					scheduledCheckPaymentRequest.getPayorType(), scheduledCheckPaymentRequest.getPayorStreetAddress(),
					scheduledCheckPaymentRequest.getPayorState(), scheduledCheckPaymentRequest.getPayorCity(),
					scheduledCheckPaymentRequest.getPayorZip(), scheduledCheckPaymentRequest.getPayorPhone(),
					scheduledCheckPaymentRequest.getEmails(), scheduledCheckPaymentRequest.getEnteredBy(),
					scheduledCheckPaymentRequest.isPrintedConfirmation(), scheduledCheckPaymentRequest.getPaymentId() 
					};
			insertedRowCount = postgresJdbcTemplate.update(SQL, params);

		} catch (Exception exp) {
			LOGGER.error("Payment could not be scheduled because of Exception ", exp);
		}
		return (insertedRowCount > 0 ? true : false);
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @return List<PhonePayFeeDto>
	 * @Description : this is getPhonePayFeeList method to get list of fee and its
	 *              corresponding code on basis of entered.
	 */
	@Override
	public List<PhonePayFeeDto> getPhonePayFeeList() {
		List<PhonePayFeeDto> phonePayFeeDto = null;
		try {
			final String SQL = "SELECT ID, NAME FROM PAYMENT_ITEM_TYPE WHERE dropdown = true";
			phonePayFeeDto = postgresJdbcTemplate.query(SQL, new PhonePayFeeMapper());
		} catch (Exception exp) {
			LOGGER.info("Could not get list of fee beacuse of exception", exp);
			exp.getMessage();
		}
		return phonePayFeeDto;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param paymentSource, loanNumber
	 * @return int
	 * @Description : this is getLastUpdatedSchedulePaymentIdByLoanNumber to get
	 *              last scheduled payment on basis of payment source and loan
	 *              number
	 */
	@Override
	public int getLastUpdatedSchedulePaymentIdByLoanNumber(long loanNumber) {
		int scheduledPaymentId = 0;
		try {
			final String SQL = "select ID from payment where id = (SELECT MAX(id) FROM payment where loan_number = ?)";
			scheduledPaymentId = postgresJdbcTemplate.queryForObject(SQL, Integer.class, loanNumber);
		} catch (Exception exp) {
			LOGGER.info("Could not get schedule payment id beacuse of exception", exp);
			exp.getMessage();
		}
		return scheduledPaymentId;
	}

	/**
	 * @author nehas3
	 * @date Feb 25, 2019
	 * @param paymentSource, loanNumber
	 * @return boolean
	 * @Description : this is updateFeeTableBySchedulePaymentId to updated selected
	 *              fees in fee table while scheduling payment
	 */
	@Override
	public boolean insertFeeTableBySchedulePaymentId(OtherFeeDto otherFeeDto) {
		int insertedRowCount = 0;
		try {
			final String SQL = "insert into payment_item (PAYMENT_ID, ITEM_TYPE_ID, AMOUNT)"
					+ " values (:payment_id, :item_type_id, :amount)";
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("payment_id", otherFeeDto.getSchedulePaymentId())
					.addValue("item_type_id", otherFeeDto.getFeeCode()).addValue("amount", otherFeeDto.getFeeAmount());

			insertedRowCount = postgresNamedParameterJdbcTemplate.update(SQL, params);

		} catch (Exception exp) {
			LOGGER.info("Could not insert fee details because of exception", exp);
			exp.getMessage();
		}
		return (insertedRowCount > 0 ? true : false);
	}

	@Override
	public boolean updateFeeTableBySchedulePaymentId(OtherFeeDto otherFeeDto) {
		int rowsAffected = 0;
		boolean isUpdated = false;
		try {
			final String SQL = "UPDATE PAYMENT_ITEM SET ITEM_TYPE_ID = ?, AMOUNT = ?  WHERE PAYMENT_ID = ? and ID = ?";

			Object[] params = new Object[] { otherFeeDto.getFeeCode(), otherFeeDto.getFeeAmount(),
					otherFeeDto.getSchedulePaymentId(), otherFeeDto.getId()};

			rowsAffected = postgresJdbcTemplate.update(SQL, params);

			if (rowsAffected == 1) {
				isUpdated = true;
			} else {
				LOGGER.info("could not update payment advice for", otherFeeDto.getSchedulePaymentId());
			}

		} catch (Exception exp) {
			LOGGER.error("could not update payment advice because of exception ", exp);
		}
		return isUpdated;
	}

	/**
	 * @author nehas3
	 * @date Feb 26, 2019
	 * @return String
	 * @param schedulePaymentId
	 * @Description : this is getPaymentSourceOfScheduledPayment to get payment
	 *              source of already scheduled payment
	 */
	@Override
	public String getPaymentSourceOfScheduledPayment(int schedulePaymentId) {
		String paymentSource = null;
		try {
			final String SQL = "SELECT PAYMENT_SOURCE FROM " + homeowner + ".SCHEDULED_PAYMENT WHERE Id = ?";

			paymentSource = db2JdbcTemplate.queryForObject(SQL, String.class, schedulePaymentId);
		} catch (Exception exp) {
			LOGGER.error("could not get payment source because of exception ", exp);
		}
		return paymentSource;
	}

	/**
	 * @author nehas3
	 * @date March 8, 2019
	 * @return String
	 * @param feeId
	 * @Description : this is getFeeNameByFeeId to get fee name from fee code table
	 */
	@Override
	public String getFeeNameByFeeId(int feeId) {
		String feeName = null;
		try {
			final String SQL = "SELECT NAME FROM PAYMENT_ITEM_TYPE WHERE ID = ?";

			feeName = postgresJdbcTemplate.queryForObject(SQL, String.class, feeId);
		} catch (Exception exp) {
			LOGGER.error("could not get fee name because of exception ", exp);
		}
		return feeName;
	}

	/**
	 * @author nehas3
	 * @date March 8, 2019
	 * @return String
	 * @param feeId
	 * @Description : this is getFeeDetailsByFeeName to get fee name from fee code
	 *              table
	 */
	@Override
	public PhonePayFeeDto getFeeDetailsByFeeName() {
		PhonePayFeeDto phonePayFeeDto = null;
		try {
			final String SQL = "SELECT ID, NAME FROM PAYMENT_ITEM_TYPE WHERE NAME = 'PhonePay Fee'";
			phonePayFeeDto = postgresJdbcTemplate.queryForObject(SQL, new PhonePayFeeMapper());
		} catch (Exception exp) {
			LOGGER.info("Could not get list of fee beacuse of exception", exp);
			exp.getMessage();
		}
		return phonePayFeeDto;
	}

	/**
	 * @author nehas3
	 * @date Feb 26, 2019
	 * @return boolean
	 * @param phonePayBankDetails
	 * @Description : this is addPhonepayUserBankingDetails to insert banking
	 *              details.
	 */
	@Override
	public boolean addPhonepayUserBankingDetails(PhonePayBankDetailsDto phonePayBankDetails) {
		int insertedRowCount = 0;
		try {
			final String SQL = "insert into " + homeowner
					+ ".PHONE_PAY (SCHEDULED_PAYMENT_ID, ACCOUNT_NUMBER, ROUTING_NUMBER, PAYOR_NAME, PAYOR_TYPE, PAYOR_ADDRESS, PAYOR_PHONE, EMAIL, ENTERED_BY)"
					+ " values (:spid, :account_number, :routing_number, :payor_name, :payor_type, :payor_addresss, :payor_phone, :email, :entered_by)";
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("spid", phonePayBankDetails.getSchedulePaymentId())
					.addValue("account_number", phonePayBankDetails.getAccountNumber())
					.addValue("routing_number", phonePayBankDetails.getRoutingNumber())
					.addValue("payor_name", phonePayBankDetails.getPayorName())
					.addValue("payor_type", phonePayBankDetails.getPayorType())
					.addValue("payor_addresss", phonePayBankDetails.getPayorAddress())
					.addValue("payor_phone", phonePayBankDetails.getPayorPhone())
					.addValue("email", phonePayBankDetails.getEmails())
					.addValue("entered_by", phonePayBankDetails.getEnteredBy());

			insertedRowCount = db2NamedParameterJdbcTemplate.update(SQL, params);

		} catch (Exception exp) {
			LOGGER.info("Could not get add bank details because of exception", exp);
			exp.getMessage();
		}
		return (insertedRowCount > 0 ? true : false);
	}

	@Override
	public boolean updatePhonepayUserBankingDetails(PhonePayBankDetailsDto phonePayBankDetails) {
		int rowsAffected = 0;
		boolean isUpdated = false;
		try {
			final String SQL = "UPDATE " + homeowner + ".PHONE_PAY "
					+ "SET ACCOUNT_NUMBER = ?, ROUTING_NUMBER = ?, PAYOR_NAME = ?, PAYOR_TYPE = ?, PAYOR_ADDRESS = ?, PAYOR_PHONE = ?, EMAIL = ?, ENTERED_BY = ? WHERE SCHEDULED_PAYMENT_ID = ?";

			Object[] params = new Object[] { phonePayBankDetails.getAccountNumber(),
					phonePayBankDetails.getRoutingNumber(), phonePayBankDetails.getPayorName(),
					phonePayBankDetails.getPayorType(), phonePayBankDetails.getPayorAddress(),
					phonePayBankDetails.getPayorPhone(), phonePayBankDetails.getEmails(),
					phonePayBankDetails.getEnteredBy(), phonePayBankDetails.getSchedulePaymentId(), };

			rowsAffected = db2JdbcTemplate.update(SQL, params);

			if (rowsAffected == 1) {
				isUpdated = true;
			} else {
				LOGGER.info("could not update banking details for{}", phonePayBankDetails.getSchedulePaymentId());
			}

		} catch (Exception exp) {
			LOGGER.error("could not update banking details because of exception ", exp);
		}
		return isUpdated;
	}

	@Override
	public PhonePayBankDetailsDto getPhonepayUserBankingDetails(int scheduledPaymentId) {
		PhonePayBankDetailsDto bankingDetails = null;
		try {
			final String SQL = "Select ID, SCHEDULED_PAYMENT_ID, ACCOUNT_NUMBER, ROUTING_NUMBER, PAYOR_NAME, PAYOR_TYPE, PAYOR_ADDRESS, PAYOR_PHONE, EMAIL, ENTERED_BY from "
					+ homeowner + ".PHONE_PAY WHERE SCHEDULED_PAYMENT_ID = ?";

			bankingDetails = db2JdbcTemplate.queryForObject(SQL, new PhonePayBankDetailsMapper(), scheduledPaymentId);
		} catch (Exception exp) {
			LOGGER.error("could not get banking details because of exception ", exp);
		}
		return bankingDetails;
	}

	/**
	 * @author nehas3
	 * @date April 9, 2019
	 * @return List
	 * @Description : this is getScheduledPaymentList to get record of scheduled
	 *              payment that are not processed
	 */
	@Override
	public List<GetScheduledPaymentListDto> getScheduledPaymentList() {
		List<GetScheduledPaymentListDto> getScheduledPaymentListDto = null;
		try {
			final String SQL = "select * from payment where date_canceled IS NULL and date_processed is null and date_created >= now() - INTERVAL '24 HOURS'";
			getScheduledPaymentListDto = postgresJdbcTemplate.query(SQL, new GetScheduledPaymentListMapper());
		} catch (Exception exp) {
			LOGGER.info("Could not get list of fee beacuse of exception", exp);
			exp.getMessage();
		}
		return getScheduledPaymentListDto;
	}

	/**
	 * @author nehas3
	 * @date April 29, 2019
	 * @return boolean
	 * @Description : this is isLoanInStopFile to check if loan exist in stop file
	 *              list
	 */
	@Override
	public boolean isLoanInStopFile(String loanNumber) {
		String stopFileLoanNum = null;
		try {
			final String SQL = "select * from " + utelib + ".STOPKEY where ckey = ?";
			List<String> result = db2JdbcTemplate.queryForList(SQL, String.class, loanNumber);
			if (result != null && !result.isEmpty()) {
				stopFileLoanNum = result.get(0);
			} else {
				LOGGER.info("loan account is not available in stopfile");
			}
		} catch (Exception exp) {
			LOGGER.info("Could not get stop file status because of exception", exp);
			exp.getMessage();
		}
		return ((stopFileLoanNum != null) ? true : false);
	}

	/**
	 * @author nehas3
	 * @date May 06, 2019
	 * @return int
	 * @Description : this is getpaymentAdviceIdByName get id of paymentAdviceType
	 *              on basis of its type
	 */
	@Override
	public int getpaymentAdviceIdByName(String paymentAdviceType) {
		int paymentAdviceId = 0;
		try {
			final String SQL = "SELECT ID FROM ADVICE_CODE WHERE DEPT_NAME = ?";

			paymentAdviceId = postgresJdbcTemplate.queryForObject(SQL, Integer.class, paymentAdviceType);
		} catch (Exception exp) {
			LOGGER.error("could not get paymentAdviceId because of exception ", exp);
		}
		return paymentAdviceId;
	}

	/**
	 * @author nehas3
	 * @date May 06, 2019
	 * @return int
	 * @Description : this is getpaymentAdviceIdByName get id of paymentAdviceType
	 *              on basis of its type
	 */
	@Override
	public boolean addPaymentAdviceDetails(PaymentAdviceDto paymentAdviceDto) {
		int insertedRowCount = 0;
		try {
			final String SQL = "insert into ADVICE (PAYMENT_ID, ADVICE_CODE_ID, COMPLETED, NOTE, PROCESSED_BY, BATCH_CODE)"
					+ " values (:pid, :acid, :completed, :note, :processed_by, :batch_code)";
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("pid", paymentAdviceDto.getSchedulePaymentId())
					.addValue("acid", paymentAdviceDto.getAdviceId())
					.addValue("completed", paymentAdviceDto.isCompleted())
					.addValue("note", paymentAdviceDto.getAdviceNotes())
					.addValue("processed_by", paymentAdviceDto.getProcessedBy())
					.addValue("batch_code", paymentAdviceDto.getBatchCode());

			insertedRowCount = postgresNamedParameterJdbcTemplate.update(SQL, params);

		} catch (Exception exp) {
			LOGGER.info("Could not add advice details because of exception", exp);
			exp.getMessage();
		}
		return (insertedRowCount > 0 ? true : false);
	}

	@Override
	public boolean updatePaymentAdviceDetails(PaymentAdviceDto paymentAdviceDto) {
		int rowsAffected = 0;
		boolean isUpdated = false;
		try {
			final String SQL = "UPDATE ADVICE SET ADVICE_CODE_ID = ?, NOTE = ?  WHERE PAYMENT_ID = ?";

			Object[] params = new Object[] { paymentAdviceDto.getAdviceId(), paymentAdviceDto.getAdviceNotes(),
					paymentAdviceDto.getSchedulePaymentId(), };

			rowsAffected = postgresJdbcTemplate.update(SQL, params);

			if (rowsAffected == 1) {
				isUpdated = true;
			} else {
				LOGGER.info("could not update PaymentAdvice Details for{}", paymentAdviceDto.getSchedulePaymentId());
			}

		} catch (Exception exp) {
			LOGGER.error("could not update PaymentAdvice details because of exception ", exp);
		}
		return isUpdated;
	}

	@Override
	public PaymentAdviceDto getPaymentAdvice(int scheduledPaymentId) {
		PaymentAdviceDto paymentAdvice = null;
		try {
			final String SQL = "SELECT * FROM ADVICE WHERE PAYMENT_ID = ?";

			List<PaymentAdviceDto> result = postgresJdbcTemplate.query(SQL, new PaymentAdviceMapper(),
					scheduledPaymentId);
			if (result != null && !result.isEmpty()) {
				paymentAdvice = result.get(0);
			} else {
				LOGGER.info("No advice available for this user");
			}
		} catch (Exception exp) {
			LOGGER.info("Could not add advice details because of exception", exp);
			exp.getMessage();
		}
		return paymentAdvice;
	}

	@Override
	public String getPaymentAdviceType(int adviceID) {
		String adviceType = null;
		try {
			final String SQL = "SELECT DEPT_NAME FROM ADVICE_CODE WHERE ID = ?";

			adviceType = postgresJdbcTemplate.queryForObject(SQL, String.class, adviceID);

		} catch (Exception exp) {
			LOGGER.info("Could not add advice type, because of exception", exp);
		}
		return adviceType;
	}

	@Override
	public List<OtherFeeDto> getOtherFeeListForPayment(int schedulePaymentId) {
		List<OtherFeeDto> otherFeeDtoDtoList = null;
		try {
			final String SQL = "SELECT ID, PAYMENT_ID, ITEM_TYPE_ID, AMOUNT FROM PAYMENT_ITEM WHERE PAYMENT_ID = ?";
			otherFeeDtoDtoList = postgresJdbcTemplate.query(SQL, new OtherFeeMapper(),
					new Object[] { schedulePaymentId });

			LOGGER.info("Other Fee List : ", (otherFeeDtoDtoList.isEmpty() ? "No fee paid not found..."
					: "Got other fees details successfully "));
		} catch (Exception exp) {
			LOGGER.error("Exception occured while getting other fee list", exp);
		}
		return otherFeeDtoDtoList;
	}
	
	@Override
	public Integer getOtherFeeListForPaymentByID(int id){
		Integer feesID = null;
		List<Integer> result = null;
		final String SQL = "SELECT ID FROM PAYMENT_ITEM WHERE ID = ?";
		result = postgresJdbcTemplate.queryForList(SQL,Integer.class,  id );

		LOGGER.info("Other Fee Id List : ", (result.isEmpty() ? "No fee paid not found for this Id"
					: "Got other fees Id successfully "));
		if(!result.isEmpty()) {
			feesID = result.get(0);
		}
		return feesID;
	}
	
	@Override
	public boolean deleteOtherFeeByID(int id) {
		int rowsAffected = 0;
		boolean isUpdated = false;
			final String SQL = "DELETE FROM PAYMENT_ITEM WHERE ID = ?";
			
			rowsAffected = postgresJdbcTemplate.update(SQL, id);

			if (rowsAffected == 1) {
				isUpdated = true;
			} else {
				LOGGER.info("could not found records to delete fee Details");
			}
			return isUpdated;
		} 
	
	@Override
	public boolean updatePaymentAdvice(UpdatePaymentAdviceReq updatePaymentAdviceReq) {
		int rowsAffected = 0;
		boolean isUpdated = false;
		try {
			final String SQL = "UPDATE ADVICE SET COMPLETED = TRUE, BATCH_CODE = ?, PROCESSED_BY = ?, PROCESSED_BY_EMAIL = ? WHERE PAYMENT_ID = ?";

			rowsAffected = postgresJdbcTemplate.update(SQL, updatePaymentAdviceReq.getBatchCode(),
					updatePaymentAdviceReq.getProcessedBy(), updatePaymentAdviceReq.getProcessedByEmail(), updatePaymentAdviceReq.getSchedulePaymentId());

			if (rowsAffected == 1) {
				isUpdated = true;
			} else {
				LOGGER.info("could not update payment advice for{}", updatePaymentAdviceReq.getSchedulePaymentId());
			}

		} catch (Exception exp) {
			LOGGER.error("could not update payment advice because of exception ", exp);
		}
		return isUpdated;
	}

	@Override
	public boolean updatePaymentAdviceType(UpdatePaymentAdviceTypeReq updatePaymentAdviceTypeReq, int adviceId) {
		int rowsAffected = 0;
		boolean isUpdated = false;
		try {
			final String SQL = "UPDATE ADVICE SET ADVICE_CODE_ID = ? WHERE PAYMENT_ID = ?";

			rowsAffected = postgresJdbcTemplate.update(SQL, adviceId,
					updatePaymentAdviceTypeReq.getSchedulePaymentId());

			if (rowsAffected == 1) {
				isUpdated = true;
			} else {
				LOGGER.info(
						"could not update payment advice type for" + updatePaymentAdviceTypeReq.getSchedulePaymentId());
			}

		} catch (Exception exp) {
			LOGGER.error("could not update payment advice type because of exception ", exp);
		}
		return isUpdated;
	}

	/**
	 * @param paymentId
	 * @return ScheduledPaymentDto
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting Scheduled Payment By Payment Id.
	 */
	@Override
	public GetScheduledPaymentListDto getScheduledPaymentByPaymentId(int paymentId) {
		LOGGER.info("Retrieving scheduled payment by scheduledPaymentId. ScheduledPaymentId: {}" + paymentId);
		GetScheduledPaymentListDto scheduledPayment = null;
		try {
			final String SQL = "SELECT ID, LOAN_NUMBER, DATE_CREATED, DATE_PROCESSED, CONFIRMATION_NUMBER, BANK_ACCOUNT_NUMBER, BANK_ROUTING_NUMBER, BANK_ACCOUNT_TYPE, NOTIFICATION_EMAILS, PAYOR_STREET, PAYOR_CITY, PAYOR_STATE, PAYOR_ZIP, PAYOR_NAME, PAYOR_PHONE, PAYOR_TYPE, ENTERED_BY, should_send_letter FROM "
					+ "PAYMENT WHERE ID = ?";
			List<GetScheduledPaymentListDto> list = (List<GetScheduledPaymentListDto>) postgresJdbcTemplate.query(SQL,
					new GetScheduledPaymentListMapper(), paymentId);

			if (list != null && !list.isEmpty()) {
				scheduledPayment = list.get(0);
			} else {
				LOGGER.error("could not get Scheduled payment info");
			}

		} catch (Exception exp) {
			LOGGER.error("could not get Scheduled payment info because of exception ", exp);
		}
		return scheduledPayment;
	}

	/**
	 * @param paymentId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Checking if Payment info has been moved to payment table by
	 *              paymentId.
	 */
	@Override
	public boolean hasPaymentMoved(int paymentId) {
		LOGGER.info("Checking if payment has already been moved to the Payment table by Scheduled Payment Id: {}"
				+ paymentId);

		final String SQL = "SELECT COUNT(*) FROM " + homeowner + ".PAYMENT WHERE SCHEDULED_PAYMENT_ID = ?";

		return db2JdbcTemplate.queryForObject(SQL, Integer.class, paymentId) == 1;
	}

	/**
	 * @param paymentId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Canceling Payment In Payment Table ByPaymentId.
	 */
	@Override
	public boolean cancelPaymentInPaymentTableByPaymentId(int paymentId) {
		LOGGER.info("Attempting to cancel payment in Payment table");
		int rowsAffected = 0;
		boolean isPymentCanceled = false;
		try {
			final String SQL = "UPDATE " + homeowner + ".PAYMENT SET CANCELED = 1 WHERE SCHEDULED_PAYMENT_ID = ?";
			rowsAffected = db2JdbcTemplate.update(SQL, paymentId);
			if (rowsAffected == 1) {
				isPymentCanceled = true;
				LOGGER.info("Payment was successfully canceled in Payment table by Payment ID: {}" + paymentId);
			} else {
				LOGGER.info("Failed to cancel a payment in Payment table by Payment ID: {}", paymentId);
			}
		} catch (Exception exp) {
			LOGGER.error("Failed to cancel a payment in Payment table because of exception ", exp);
		}
		return isPymentCanceled;
	}

	/**
	 * @param paymentId, userId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Canceling Payment ByPaymentId In Scheduled Payment Table.
	 */
	@Override
	public boolean cancelPaymentByPaymentId(int paymentId, String canceledBy) {
		LOGGER.info("Attempting to cancel payment");
		int rowsAffected = 0;
		try {
			final String SQL = "UPDATE PAYMENT SET DATE_CANCELED = CURRENT_TIMESTAMP, CANCELED_BY = ? WHERE ID = ?";
			rowsAffected = postgresJdbcTemplate.update(SQL, canceledBy, paymentId);
			if (rowsAffected == 1) {
				LOGGER.info("Payment was successfully canceled. Payment ID: {}" , paymentId);
			} else {
				LOGGER.info("Failed to cancel a payment. Payment ID: {}" , paymentId);
			}
		} catch (Exception exp) {
			LOGGER.error("Failed to cancel a payment because of exception ", exp);
		}
		return true;
	}

	/**
	 * @param
	 * @return PaymentAdviceDetailsDto list
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting all payment advice list that are in active state.
	 */
	@Override
	public List<PaymentAdviceDetailsDto> getAllPaymentAdviceDetails() {
		List<PaymentAdviceDetailsDto> paymentAdviceDetailsDtoList = null;
		try {
			final String SQL = "SELECT ADV.PAYMENT_ID, ADV.NOTE, ADV.COMPLETED, ADV.PROCESSED_BY, ADV.BATCH_CODE, SCP.LOAN_NUMBER, SCP.DATE_CREATED,SCP.DATE_CANCELED, "
					+ " SCP.CONFIRMATION_NUMBER, SCP.PAYOR_NAME, SCP.PAYOR_TYPE, SCP.ENTERED_BY, ADVC.DEPT_NAME, ADV.REMOVED_FROM_BK"
					+ " from ADVICE AS ADV JOIN ADVICE_CODE AS ADVC on ADVC.ID = ADV.ADVICE_CODE_ID "
					+ "JOIN PAYMENT AS SCP on SCP.ID = ADV.PAYMENT_ID "
					+ "where (SCP.date_processed is null and SCP.date_canceled is null)"
					+ "or (SCP.date_processed is not null and ADV.completed = false)"
					+"or (SCP.date_canceled is not null and ADV.completed = true and  ADV.removed_from_bk = false)";
							
			paymentAdviceDetailsDtoList = postgresJdbcTemplate.query(SQL, new PaymentAdviceDetailsMapper());

			LOGGER.info("Loan Account List: {}", (paymentAdviceDetailsDtoList.isEmpty() ? "Loan account not found "
					: "Got payment advice details successfully "));
		} catch (Exception exp) {
			LOGGER.error("Exception occured while Finding the List of payment  advice details ", exp);
		}
		return paymentAdviceDetailsDtoList;
	}

	@Override
	public List<PaymentsInProcessDto> getPaymentsInProcessList() {
		List<PaymentsInProcessDto> paymentsInProcessDto = null;
		try {
			String SQL = "SELECT SCP.ID, SCP.LOAN_NUMBER, SCP.DATE_CREATED, SCP.PAYOR_NAME, SCP.PAYOR_TYPE, SCP.ENTERED_BY, SCP.DATE_CANCELED,"
					+ " ADV.PAYMENT_ID, ADV.COMPLETED "
					+ "FROM PAYMENT AS SCP LEFT JOIN ADVICE AS ADV ON ADV.PAYMENT_ID = SCP.ID "
					+"where (SCP.date_canceled is null and SCP.date_processed is null)"
					+"or (SCP.date_processed is not null and ADV.completed = false)"
					+"or (SCP.date_canceled is not null and ADV.completed = true and ADV.removed_from_bk = false ) ORDER BY SCP.LOAN_NUMBER";
			paymentsInProcessDto = postgresJdbcTemplate.query(SQL, new PaymentsInProcessMapper());
		} catch (Exception exp) {
			LOGGER.error("Exception occured while Finding the List of payment  advice details ", exp);
		}
		return paymentsInProcessDto;
	}

	@Override
	public List<GetProcessPaymentsListDto> getProcessPaymentsList() {
		List<GetProcessPaymentsListDto> paymentsInProcessDto = null;
		try {
			final String SQL = "SELECT SCP.ID, SCP.LOAN_NUMBER, SCP.DATE_CREATED, SCP.PAYOR_NAME, SCP.CONFIRMATION_NUMBER, SCP.ENTERED_BY, SCP.BANK_ACCOUNT_NUMBER, SCP.BANK_ROUTING_NUMBER, SCP.BANK_ACCOUNT_TYPE, SCP.NOTIFICATION_EMAILS, SCP.PAYOR_STREET, SCP.PAYOR_CITY, SCP.PAYOR_STATE, SCP.PAYOR_ZIP, SCP.SHOULD_SEND_LETTER,"
					+ " ADV.ADVICE_CODE_ID, ADV.NOTE  "
					+ "FROM PAYMENT AS SCP LEFT JOIN ADVICE AS ADV ON ADV.PAYMENT_ID = SCP.ID "
					+ "WHERE DATE_CANCELED IS NULL and DATE_PROCESSED IS NULL ORDER BY SCP.LOAN_NUMBER";

			paymentsInProcessDto = postgresJdbcTemplate.query(SQL, new GetProcessPaymentsListMapper());
		} catch (Exception exp) {
			LOGGER.error("Exception occured while Finding the List of payment  advice details ", exp);
		}
		return paymentsInProcessDto;
	}
	
	@Override
	public List<GetProcessPaymentsListDto> getProcessedPaymentsListByPaymentBatch(int paymentBatchId){
		List<GetProcessPaymentsListDto> paymentsInProcessDto = null;
		try {
			final String SQL = "SELECT SCP.ID, SCP.LOAN_NUMBER, SCP.DATE_CREATED, SCP.PAYOR_NAME, SCP.CONFIRMATION_NUMBER, SCP.BANK_ACCOUNT_NUMBER, SCP.ENTERED_BY, SCP.BANK_ROUTING_NUMBER, SCP.BANK_ACCOUNT_TYPE, SCP.NOTIFICATION_EMAILS, SCP.PAYOR_STREET, SCP.PAYOR_CITY, SCP.PAYOR_STATE, SCP.PAYOR_ZIP, SCP.SHOULD_SEND_LETTER,"
					+ " ADV.ADVICE_CODE_ID, ADV.NOTE  "
					+ "FROM PAYMENT AS SCP LEFT JOIN ADVICE AS ADV ON ADV.PAYMENT_ID = SCP.ID "
					+ "WHERE PAYMENT_BATCH_ID = ? ORDER BY DATE_PROCESSED";

			paymentsInProcessDto = postgresJdbcTemplate.query(SQL, new Object[] {paymentBatchId}, new GetProcessPaymentsListMapper());
		} catch (Exception exp) {
			LOGGER.error("Exception occured while Finding the List of payment  advice details ", exp);
		}
		return paymentsInProcessDto;
	}

	/**
	 * @param paymentAdviceType
	 * @return PaymentAdviceDetailsDto list
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting payment advice details on basis of its type.
	 */
	@Override
	public List<PaymentAdviceDetailsDto> getPaymentAdviceDetails(String paymentAdviceType) {
		List<PaymentAdviceDetailsDto> paymentAdviceDetailsDtoList = null;
		try {
			final String SQL= "SELECT ADV.PAYMENT_ID, ADV.NOTE, ADV.COMPLETED, ADV.PROCESSED_BY, ADV.BATCH_CODE, SCP.LOAN_NUMBER, SCP.DATE_CREATED, SCP.DATE_CANCELED, "
					+ "SCP.CONFIRMATION_NUMBER, SCP.PAYOR_NAME, SCP.PAYOR_TYPE, SCP.ENTERED_BY, ADVC.DEPT_NAME,ADV.REMOVED_FROM_BK "
					+ " from ADVICE AS ADV JOIN ADVICE_CODE AS ADVC on ADVC.ID = ADV.ADVICE_CODE_ID JOIN "
					+ "PAYMENT AS SCP on SCP.ID = ADV.PAYMENT_ID "
					+"where((SCP.date_processed is null and SCP.date_canceled is null) " 
					+"or (SCP.date_processed is not null and ADV.completed = false) or (SCP.date_canceled is not null" 
					+" and ADV.completed = true and  ADV.removed_from_bk = false))" 
					+" and ADVC.DEPT_NAME = ? ORDER BY SCP.LOAN_NUMBER";

			paymentAdviceDetailsDtoList = postgresJdbcTemplate.query(SQL, new PaymentAdviceDetailsMapper(),
					new Object[] { paymentAdviceType });

			LOGGER.info("Loan Account List : ", (paymentAdviceDetailsDtoList.isEmpty() ? "Loan account not found..."
					: "Got payment advice details successfully "));
		} catch (Exception exp) {
			LOGGER.error("Exception occured while Finding the List of payment  advice details ", exp);
		}
		return paymentAdviceDetailsDtoList;
	}

	@Override
	public List<GetScheduledPaymentListDto> getResearchPaymentDetails(int paymentId) {
		List<GetScheduledPaymentListDto> getResearchPaymentDetailsDtoList = null;
		try {
			final String SQL = "SELECT ID, LOAN_NUMBER, DATE_CREATED, DATE_PROCESSED, CONFIRMATION_NUMBER, "
					+ "BANK_ACCOUNT_NUMBER, BANK_ROUTING_NUMBER, "
					+ "BANK_ACCOUNT_TYPE, NOTIFICATION_EMAILS, PAYOR_STREET, PAYOR_CITY, PAYOR_STATE, PAYOR_ZIP, PAYOR_NAME, should_send_letter,"
					+ "PAYOR_PHONE, PAYOR_TYPE, ENTERED_BY, CANCELED_BY, DATE_CANCELED FROM PAYMENT WHERE ID = ?";

			getResearchPaymentDetailsDtoList = postgresJdbcTemplate.query(SQL, new GetScheduledPaymentListMapper(),
					new Object[] { paymentId });

		} catch (Exception exp) {
			LOGGER.error("Exception occured while Finding the List of payment  advice details ", exp);
		}
		return getResearchPaymentDetailsDtoList;
	}

	@Override
	public List<ResearchPaymentDto> getResearchPaymentInfo(ResearchPaymentRequest researchPaymentRequest) {
		List<ResearchPaymentDto> researchPaymentDtoList = null;
		List<Long> loans = researchPaymentRequest.getLoanNumbers();
		List<String> agentNames = researchPaymentRequest.getServicingAgents();
		
		SqlParameterSource param = new MapSqlParameterSource();
		
	    String sql = "SELECT ID, DATE_CREATED, LOAN_NUMBER, DATE_PROCESSED, DATE_CANCELED FROM PAYMENT "
		+ "WHERE DATE_CREATED::DATE BETWEEN '"+researchPaymentRequest.getFromDate()+"' AND '"+researchPaymentRequest.getToDate()+"'";
	    
	    if(researchPaymentRequest.getLoanNumbers()!=null && !researchPaymentRequest.getLoanNumbers().isEmpty()){
	    	sql += " AND LOAN_NUMBER IN(:loanNumbers)";
			((MapSqlParameterSource) param).addValue("loanNumbers", loans);
		}
	    
	    if(researchPaymentRequest.getServicingAgents()!=null && !researchPaymentRequest.getServicingAgents().isEmpty()){
	    	sql += " AND ENTERED_BY IN(:serviceAgentNames)";
			((MapSqlParameterSource) param).addValue("serviceAgentNames", agentNames);
		}
	    
	    if(researchPaymentRequest.getSortingList()!=null && !researchPaymentRequest.getSortingList().isEmpty()){
	    	sql += " ORDER BY ";
	    	int count=1;
	    	for(SortingList sortDetail: researchPaymentRequest.getSortingList()) {
	    		if(sortDetail.getSortingType().equals("Date") && sortDetail.getSortingOrder().equals("ASC")) {
	    			sql += " DATE_CREATED ASC";
	    		}else if(sortDetail.getSortingType().equals("Date") && sortDetail.getSortingOrder().equals("DESC")) {
	    			sql += " DATE_CREATED DESC";
	    		}
	    		
	    		if(sortDetail.getSortingType().equals("Amount") && sortDetail.getSortingOrder().equals("ASC")) {
	    			sql += " ID ASC";
	    		}else if(sortDetail.getSortingType().equals("Amount") && sortDetail.getSortingOrder().equals("DESC")) {
	    			sql += " ID DESC";
	    		}
	    		
	    		if(sortDetail.getSortingType().equals("Loan") && sortDetail.getSortingOrder().equals("ASC")) {
	    			sql += " LOAN_NUMBER ASC";
	    		}else if(sortDetail.getSortingType().equals("Loan") && sortDetail.getSortingOrder().equals("DESC")) {
	    			sql += " LOAN_NUMBER DESC";
	    		}
	    		
	    		if(sortDetail.getSortingType().equals("Agent") && sortDetail.getSortingOrder().equals("ASC")) {
	    			sql += " ENTERED_BY ASC";
	    		}else if(sortDetail.getSortingType().equals("Agent") && sortDetail.getSortingOrder().equals("DESC")) {
	    			sql += " ENTERED_BY DESC";
	    		}
	    		
	    		if(count < researchPaymentRequest.getSortingList().size()) {
	    			sql += " ,";
	    		}
	    		count ++;
	    		
	    	}
		}
	
		researchPaymentDtoList = (List<ResearchPaymentDto>) postgresNamedParameterJdbcTemplate.query(sql,
				param, new ResearchPaymentMapper());

		return researchPaymentDtoList;
		}

	
	@Override
	public List<String> getLastNamesList(String name) {
		List<String> names = null;
		try {
			final String SQL = "select DISTINCT LAST_NAME from " + homeowner + ".USER where LAST_NAME like '" + name
					+ "%' order by LAST_NAME";

			names = db2JdbcTemplate.queryForList(SQL, String.class);

		} catch (Exception exp) {
			LOGGER.info("Could not get last names, because of exception", exp);
			exp.getMessage();
		}
		return names;
	}

	@Override
	public List<String> getLastNamesListForFirstAndLastName(GetNamesRequest getNamesRequest) {
		List<String> names = null;
		try {
			final String SQL = "select DISTINCT LAST_NAME from " + homeowner + ".USER where LAST_NAME like '"
					+ getNamesRequest.getLastName().toUpperCase() + "%' AND FIRST_NAME like '"
					+ getNamesRequest.getFirstName().toUpperCase() + "%' order by LAST_NAME";

			names = db2JdbcTemplate.queryForList(SQL, String.class);

		} catch (Exception exp) {
			LOGGER.info("Could not get last names, because of exception", exp);
			exp.getMessage();
		}
		return names;
	}

	@Override
	public List<String> getFirstNamesList(String name) {
		List<String> names = null;
		try {
			final String SQL = "select DISTINCT FIRST_NAME from " + homeowner + ".USER where FIRST_NAME like '" + name
					+ "%' order by FIRST_NAME";

			names = db2JdbcTemplate.queryForList(SQL, String.class);

		} catch (Exception exp) {
			LOGGER.info("Could not get first names, because of exception", exp);
			exp.getMessage();
		}
		return names;
	}

	@Override
	public List<String> getFirstNamesListForFirstAndLastName(GetNamesRequest getNamesRequest) {
		List<String> names = null;
		try {
			final String SQL = "select DISTINCT FIRST_NAME from " + homeowner + ".USER where FIRST_NAME like '"
					+ getNamesRequest.getFirstName().toUpperCase() + "%' AND LAST_NAME like '"
					+ getNamesRequest.getLastName().toUpperCase() + "%' order by FIRST_NAME";

			names = db2JdbcTemplate.queryForList(SQL, String.class);

		} catch (Exception exp) {
			LOGGER.info("Could not get first names, because of exception", exp);
			exp.getMessage();
		}
		return names;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.uhc.dao.UserDao#updateProcessedPaymentsDate(int)
	 */
	@Override
	public boolean updateProcessedPaymentsDate(List<Integer> paymentIds, int batchId) {
		int rowsAffected = 0;
		try {
			String SQL = "UPDATE PAYMENT SET DATE_PROCESSED = CURRENT_TIMESTAMP, PAYMENT_BATCH_ID = (:batchId) WHERE ID IN (:paymentIds)";

			List<Integer> params = paymentIds;

			SqlParameterSource param = new MapSqlParameterSource().addValue("batchId", batchId)
					.addValue("paymentIds", params);

			rowsAffected = postgresNamedParameterJdbcTemplate.update(SQL, param);

			if (rowsAffected > 0) {
				LOGGER.info("Processed payments was successfully updated. Payment ID: {}", paymentIds);
			} else {
				LOGGER.info("No records found to update processed payments. Payment ID: {}", paymentIds);
			}
		} catch (Exception exp) {
			LOGGER.error("Failed to update processed payments because of exception ", exp);
		}
		return (rowsAffected > 0);
	}

	@Override
	public boolean insertProcessedPaymentBatch(ProcessingPaymentsResponse paymentBatch) {
		int insertedRowCount = 0;
		try {
			final String SQL = "INSERT INTO PAYMENT_BATCH (status, num_of_successful_payments, total_successful_payments, num_non_advice_payments, total_non_advice_payments, num_cashiering, total_cashiering, num_collections, total_collections, num_bankruptcy, total_bankruptcy, num_loss_mit, "
					+ "total_loss_mit, num_zions_tel, total_zions_tel, num_zions_tad, total_zions_tad, num_bk_071, total_bk_071, num_bk_073, total_bk_073)"
					+ " values (:status, :num_of_successful_payments, :total_successful_payments, :num_non_advice_payments, :total_non_advice_payments, :num_cashiering, :total_cashiering, :num_collections, :total_collections, :num_bankruptcy, :total_bankruptcy, "
					+ ":num_loss_mit, :total_loss_mit, :num_zions_tel, :total_zions_tel, :num_zions_tad, :total_zions_tad, :num_bk_071, :total_bk_071, :num_bk_073, :total_bk_073)";
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("status", paymentBatch.getStatus())
					.addValue("num_of_successful_payments", paymentBatch.getNumOfSuccessfulPayments())
					.addValue("total_successful_payments", paymentBatch.getTotalSuccessfulPayments())
					.addValue("num_non_advice_payments", paymentBatch.getNumNonAdvicePayments())
					.addValue("total_non_advice_payments", paymentBatch.getTotalNonAdvicePayments())
					.addValue("num_cashiering", paymentBatch.getNumCashiering())
					.addValue("total_cashiering", paymentBatch.getTotalCashiering())
					.addValue("num_collections", paymentBatch.getNumCollections())
					.addValue("total_collections", paymentBatch.getTotalCollections())
					.addValue("num_bankruptcy", paymentBatch.getNumBankruptcy())
					.addValue("total_bankruptcy", paymentBatch.getTotalBankruptcy())
					.addValue("num_loss_mit", paymentBatch.getNumLossMit())
					.addValue("total_loss_mit", paymentBatch.getTotalLossMit())
					.addValue("num_zions_tel", paymentBatch.getNumZionsTel())
					.addValue("total_zions_tel", paymentBatch.getTotalZionsTel())
					.addValue("num_zions_tad", paymentBatch.getNumZionsTad())
					.addValue("total_zions_tad", paymentBatch.getTotalZionsTad())
					.addValue("num_bk_071", paymentBatch.getNumBk071())
					.addValue("total_bk_071", paymentBatch.getTotalBk071())
					.addValue("num_bk_073", paymentBatch.getNumBk073())
					.addValue("total_bk_073", paymentBatch.getTotalBk073());
			

			insertedRowCount = postgresNamedParameterJdbcTemplate.update(SQL, params);

		} catch (Exception exp) {
			LOGGER.info("Could not add advice details because of exception", exp);
			exp.getMessage();
		}
		return (insertedRowCount > 0 ? true : false);

	}
	
	@Override
	public ProcessingPaymentsResponse getPaymentBatchDetails() {
		ProcessingPaymentsResponse paymentBatch = null;
		try {
			final String SQL = "SELECT * FROM PAYMENT_BATCH WHERE DATE::DATE = CURRENT_DATE";
			List<ProcessingPaymentsResponse> list = (List<ProcessingPaymentsResponse>) postgresJdbcTemplate.query(SQL,
					new PaymentBatchMapper());

			if (list != null && !list.isEmpty()) {
				paymentBatch = list.get(0);
			} else {
				LOGGER.error("could not get payment batch info");
			}

		} catch (Exception exp) {
			LOGGER.error("could not get payment batch info because of exception ", exp);
		}
		return paymentBatch;
	}
	
	@Override
	public List<ProcessingPaymentsResponse> getPaymentBatchDetailsBetweenTwoDates(GetStatisticDetailsRequest statisticRequest){
		List<ProcessingPaymentsResponse> paymentBatchList = null;
		try {
			final String SQL = "SELECT * FROM PAYMENT_BATCH WHERE DATE::DATE BETWEEN '"+statisticRequest.getStartDate()+"' AND '"+statisticRequest.getEndDate()+"' ORDER BY DATE";
			paymentBatchList = (List<ProcessingPaymentsResponse>) postgresJdbcTemplate.query(SQL,
					new Object[]{}, new PaymentBatchMapper());

		} catch (Exception exp) {
			LOGGER.error("could not get payment batch info because of exception ", exp);
		}
		return paymentBatchList;
	}
	
	@Override
	public int getLastUpdatedPaymentBatchId() {
		int payemntBatchId = 0;
		try {
			final String SQL = "SELECT ID FROM payment_batch WHERE ID = (SELECT MAX(ID) FROM payment_batch)";
			payemntBatchId = postgresJdbcTemplate.queryForObject(SQL, Integer.class);
		} catch (Exception exp) {
			LOGGER.info("Could not get payment batch id beacuse of exception", exp);
			exp.getMessage();
		}
		return payemntBatchId;
	}


	@Override
	public boolean deleteProcessAdvicePayment(DeleteProcessAdvicePaymentReq deletProcessAdvicePaymentReq) {
		int rowsAffected = 0;
		boolean isUpdated = false;
		try {
			final String SQL = "UPDATE ADVICE SET  REMOVED_FROM_BK = ?, ADVICE_DELETED_BY = ? WHERE PAYMENT_ID = ?";

			rowsAffected = postgresJdbcTemplate.update(SQL, deletProcessAdvicePaymentReq.isRemovedFrom(),
					deletProcessAdvicePaymentReq.getDeletedBy(), deletProcessAdvicePaymentReq.getSchedulePaymentId());

			if (rowsAffected == 1) {
				isUpdated = true;
			} else {
				LOGGER.info("could not delet Process  advice payment for{} ", deletProcessAdvicePaymentReq.getSchedulePaymentId());
			}

		} catch (Exception exp) {
			LOGGER.error("could not delet Process  advice paymen because of exception ", exp);
		}
		return isUpdated;
	}

	@Override
	public List<String> getNamesList(String name) {
		List<String> names = null;
		try {
			final String SQL = "select DISTINCT entered_by from payment " +" where entered_by iLIKE '" + name
					+ "%' order by entered_by";

			names = postgresJdbcTemplate.queryForList(SQL, String.class);

		} catch (Exception exp) {
			LOGGER.info("Could not get last names, because of exception", exp);
			exp.getMessage();
		}
		return names;
	}

	@Override
	public List<GetAdviceBatchDto> getAdviceBatchInfo(GetAdviceBatchRequest getAdviceBatchRequest) {
		List<GetAdviceBatchDto> getAdviceBatchDtoList = null;

		SqlParameterSource param = new MapSqlParameterSource().addValue("batchId", getAdviceBatchRequest.getBk_batch_code());

		String sql = "SELECT ID, DATE, PROCESSED_BY, BK_BATCH_CODE, CLOSED FROM ADVICE_CLOSED_BATCH WHERE DATE = CURRENT_DATE AND BK_BATCH_CODE = (:batchId)";


		getAdviceBatchDtoList = (List<GetAdviceBatchDto>) postgresJdbcTemplate.query(sql, new AdviceBatchMapper(), param);

		return getAdviceBatchDtoList;
	}

	@Override
	public boolean updateAdviceBatchInfo(GetAdviceBatchRequest getAdviceBatchRequest){
		int rowsAffected = 0;
		try {
			String SQL = "UPDATE ADVICE_CLOSED_BATCH SET CLOSED = (:closed) WHERE BK_BATCH_CODE = (:batchId) AND DATE = CURRENT_DATE";

			SqlParameterSource param = new MapSqlParameterSource()
					.addValue("batchId", getAdviceBatchRequest.getBk_batch_code())
					.addValue("closed", getAdviceBatchRequest.getClosed());

			rowsAffected = postgresNamedParameterJdbcTemplate.update(SQL, param);

			if (rowsAffected > 0) {
				LOGGER.info("Advice Batch was successfully updated. Batch ID: {}", getAdviceBatchRequest.getBk_batch_code());
			} else {
				LOGGER.info("No records found to update Advice Batch. Batch ID: {}", getAdviceBatchRequest.getBk_batch_code());
			}
		} catch (Exception exp) {
			LOGGER.error("Failed to update Advice Batch because of exception ", exp);
		}
		return (rowsAffected > 0);
	}


	@Override
	public boolean insertAdviceBatchInfo(GetAdviceBatchRequest getAdviceBatchRequest) {
		int insertedRowCount = 0;
		try {
			final String SQL = "insert into advice_closed_batch (DATE, PROCESSED_BY, BK_BATCH_CODE, CLOSED)"
					+ " values (:date, :processed_by, :bk_batch_code, :closed)";
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("date", getAdviceBatchRequest.getDate())
					.addValue("processed_by", getAdviceBatchRequest.getProcessed_by())
					.addValue("bk_batch_code", getAdviceBatchRequest.getBk_batch_code())
					.addValue("closed", getAdviceBatchRequest.getClosed());

			insertedRowCount = postgresNamedParameterJdbcTemplate.update(SQL, params);

		} catch (Exception exp) {
			LOGGER.info("Could not insert Advice Batch because of exception", exp);
			exp.getMessage();
		}
		return (insertedRowCount > 0 ? true : false);
	}
	/**
	 * @param paymentId
	 * @return string
	 * @Description : get ProcessedByEmail ID.
	 */
	@Override
	public CancelPaymentDto getProcessedByEmail(int paymentId) {
		LOGGER.info("Retrieving processed payment by paymentId. paymentId: {}" + paymentId);
		CancelPaymentDto processedPayment = null;
		try {
			final String SQL = "SELECT PROCESSED_BY_EMAIL, BATCH_CODE FROM advice WHERE PAYMENT_ID = ?";
			List<CancelPaymentDto> list = (List<CancelPaymentDto>) postgresJdbcTemplate.query(SQL,new CancelPaymentMapper(), paymentId);

			if (list != null && !list.isEmpty()) {
				processedPayment = list.get(0);
			} else {
				LOGGER.error("could not get processed payment info");
			}

		} catch (Exception exp) {
			LOGGER.error("could not get processed payment info because of exception ", exp);
		}
		return processedPayment;
	}
	
	/**
	 * @author pradeepy
	 * @date Feb 13, 2022
	 * @param loanNumber
	 * @return List<BorrowerNameDto>
	 * @Description : this is getBorroweNameListByLoanNumber method to get list of
	 *              borrowers on basis of entered loan number.
	 */
	@Override
	public List<BorrowerNameDto> getBorroweNameByLoanNumber(long loanNumber) {
		List<BorrowerNameDto> borrowerNameList = null;
		try {
			LOGGER.info("The loan number, user name enterd is :", loanNumber);
			
			final String SQL = "select A1_LOAN, A1_FIRST_NAME, A1_LAST_NAME from "
					+ ldataser + ".srvalt1 where A1_LOAN = ?";
			borrowerNameList = db2JdbcTemplate.query(SQL, new Object[] { loanNumber }, new BorrowerNameMapper());
			LOGGER.info("List of Borrower is:"
					+ (borrowerNameList.isEmpty() ? "Borrowr info could not be found " : " " + borrowerNameList));
		} catch (Exception exp) {
			LOGGER.error("Exception occured while getting loan number by using user name :: ", exp);
			exp.getMessage();
		}
		return borrowerNameList;
	}
}
