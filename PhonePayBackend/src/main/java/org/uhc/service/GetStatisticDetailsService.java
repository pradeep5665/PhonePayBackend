/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.request.GetStatisticDetailsRequest;
import org.uhc.envelop.response.GetStatisticDetailsResponse;

/**
 * @author nehas3
 *
 */
public interface GetStatisticDetailsService {

	GetStatisticDetailsResponse getStatisticDetails(GetStatisticDetailsRequest statisticDetailsRequest);
}
