package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.GetAdviceBatchDto;
import org.uhc.dao.dto.ResearchPaymentActualListDto;
import org.uhc.dao.dto.ResearchPaymentDto;
import org.uhc.envelop.request.GetAdviceBatchRequest;
import org.uhc.envelop.response.GetAdviceBatchResponse;
import org.uhc.envelop.response.GetPaymentBatchResponse;
import org.uhc.envelop.response.ResearchPaymentResponse;
import org.uhc.service.GetAdviceBatchService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAdviceBatchServiceImpl implements GetAdviceBatchService {
    private static final Logger LOGGER = LogManager.getLogger(GetAdviceBatchServiceImpl.class.getName());

    @Autowired
    UserDao userDao;

    @Override
    public GetAdviceBatchResponse getAdviceBatchDetails(GetAdviceBatchRequest getAdviceBatchRequest) {
        LOGGER.info("Entering into getAdviceBatchDetails method");

        GetAdviceBatchResponse adviceBatchResponse = new GetAdviceBatchResponse();
        List<GetAdviceBatchDto> adviceBatchList = new ArrayList<>();
        GetAdviceBatchDto getAdviceBatchDto = null;
        try {
            boolean getAdviceBatchDtoList = userDao.updateAdviceBatchInfo(getAdviceBatchRequest);
            if(getAdviceBatchDtoList){
                LOGGER.info("RESULTS");
                LOGGER.info("Update Results: ", getAdviceBatchDtoList);

                adviceBatchResponse.setIsSuccessful(true);
                adviceBatchResponse.setMessage("Advice Batch Successfully Updated");
            }else{
                Boolean insertAdviceBatchInfo = userDao.insertAdviceBatchInfo(getAdviceBatchRequest);
                LOGGER.info("Insert Results: ", insertAdviceBatchInfo);
                adviceBatchResponse.setIsSuccessful(true);
                adviceBatchResponse.setMessage("Advice Batch Successfully Added");

            }

            adviceBatchResponse.setIsSuccessful(false);
            adviceBatchResponse.setMessage("Error with Advice Batch");

        }catch(Exception exp) {
            LOGGER.error("Exception occured while getting advice batch", exp);

        }

        LOGGER.info("Exit from getAdviceBatchDetails method");
        return adviceBatchResponse;
    }

}
