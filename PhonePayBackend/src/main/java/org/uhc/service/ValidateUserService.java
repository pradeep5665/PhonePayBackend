/**
 * 
 */
package org.uhc.service;

import org.uhc.envelop.request.ValidateUserRequest;
import org.uhc.envelop.response.ValidateUserResponse;

/**
 * @author nehas3
 *
 */
public interface ValidateUserService {

	 ValidateUserResponse validateUser(ValidateUserRequest validateUserRequest);
}
