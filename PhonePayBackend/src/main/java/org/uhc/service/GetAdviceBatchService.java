package org.uhc.service;
import org.uhc.envelop.request.GetAdviceBatchRequest;
import org.uhc.envelop.response.GetAdviceBatchResponse;

public interface GetAdviceBatchService {
    GetAdviceBatchResponse getAdviceBatchDetails(GetAdviceBatchRequest getAdviceBatchRequest);
}
