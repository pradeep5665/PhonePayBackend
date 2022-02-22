/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.response.CheckStopFileExistanceResponse;

/**
 * @author nehas3
 *
 */
public interface CheckStopFileExistanceService {

	CheckStopFileExistanceResponse isLoanAccountInStopFile(long loanNumber);
}
