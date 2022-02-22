/**
 * 
 */
package org.uhc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.GetScheduledPaymentListDto;
import org.uhc.envelop.response.GetScheduledPaymentListResponse;
import org.uhc.service.GetScheduledPaymentListService;

/**
 * @author nehas3
 *
 */
@Service
public class GetScheduledPaymentListServiceImpl implements GetScheduledPaymentListService{

	private static final Logger LOGGER = LogManager.getLogger(GetScheduledPaymentListServiceImpl.class.getName());
	
	@Autowired
	UserDao userDao;
	
	/**
	 * @author nehas3
	 * @date April 9, 2019
	 * @return GetScheduledPaymentListResponse
	 * @Description : this is the getScheduledPaymentList service method created to get the list of
	 *              scheduled payment
	 */
	@Override
	public GetScheduledPaymentListResponse getScheduledPaymentList() {
		GetScheduledPaymentListResponse getSchedulePaymentRes = new GetScheduledPaymentListResponse();
		List<GetScheduledPaymentListDto> getScheduledPaymentListDto = new ArrayList<>();
		try {
			getScheduledPaymentListDto = userDao.getScheduledPaymentList();
			if(!getScheduledPaymentListDto.isEmpty()) {
				getSchedulePaymentRes.setIsSuccessFull(true);
				getSchedulePaymentRes.setMessage("successfully got Scheduled payment records");
				getSchedulePaymentRes.setScheduledPaymentList(getScheduledPaymentListDto);
			}else {
				getSchedulePaymentRes.setIsSuccessFull(false);
				getSchedulePaymentRes.setMessage("Could not get Scheduled payment records");
			}
		}catch(Exception exp) {
			LOGGER.error("Exception occured because of", exp);
		}
		return getSchedulePaymentRes;
	}

}
