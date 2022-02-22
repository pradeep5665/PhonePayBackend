package org.uhc.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.uhc.envelop.request.GetAdviceBatchRequest;
import org.uhc.envelop.request.UpdatePaymentAdviceReq;
import org.uhc.service.GetAdviceBatchService;

/**
 * @author GSnell
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class GetAdviceBatchController {
    private static final Logger LOGGER = LogManager.getLogger(GetAdviceBatchController.class);

    @Autowired
    private GetAdviceBatchService adviceBatchService;

    /**
     * @author GSnell
     * @date Feb 11, 2022
     * @return Object
     * @param
     * @return Daily advice batches from service
     */

    @PostMapping(value = "/adviceBatch")
    public Object getAdviceBatchAPI(@RequestBody GetAdviceBatchRequest getAdviceBatchRequest ) {
        LOGGER.info("get advice batch API called");
        return adviceBatchService.getAdviceBatchDetails(getAdviceBatchRequest);
    }

    /**
     * @author nehas3
     * @date May 14, 2019
     * @return Object
     * @param
     * @return paymentAdvice details in response
     */
    @PostMapping(value = "/updateAdviceBatch")
    public Object updatePaymentAdviceAPI(@RequestBody GetAdviceBatchRequest getAdviceBatchRequest) {

        LOGGER.info("update advice batch API called");
        LOGGER.info("Date :" + getAdviceBatchRequest.getDate() );
        LOGGER.info("Batch Code :" + getAdviceBatchRequest.getBk_batch_code() );
        LOGGER.info("Agent :" + getAdviceBatchRequest.getProcessed_by() );
        LOGGER.info("Status :" + getAdviceBatchRequest.getClosed() );

        return adviceBatchService.getAdviceBatchDetails(getAdviceBatchRequest);
    }

}
