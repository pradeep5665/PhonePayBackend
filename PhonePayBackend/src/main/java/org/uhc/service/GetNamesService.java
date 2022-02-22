/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.request.GetNamesRequest;
import org.uhc.envelop.response.GetNamesResponse;

/**
 * @author nehas3
 *
 */
public interface GetNamesService {

	GetNamesResponse getLastNamesList(String name);
	
	GetNamesResponse getFirstNamesList(String name);
	
	GetNamesResponse getFirstNamesListByFirstAndLastName(GetNamesRequest getNamesRequest);
	
	GetNamesResponse getLastNamesListByFirstAndLastName(GetNamesRequest getNamesRequest);
	
	GetNamesResponse getFirstAndLastNamesListForAutocomplete(String name);
	
}
