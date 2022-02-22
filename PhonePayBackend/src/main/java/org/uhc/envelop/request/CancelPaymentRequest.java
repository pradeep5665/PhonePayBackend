/* 
 * ===========================================================================
 * File Name CancelPaymentRequest.java
 * 
 * Created on Jul 11, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (UHC) Utah Housing Corporation. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: CancelPaymentRequest.java,v $
 * ===========================================================================
 */
package org.uhc.envelop.request;

/**
 * @author nehas3
 * @date June 06, 2019
 * @Description : This is CancelPaymentRequest request bean to cancel the
 *              scheduled payment on the basis of userId.
 */
public class CancelPaymentRequest {

	private String[] paymentIdOfCheckBox;
	private String canceledBy;

	public String[] getPaymentIdOfCheckBox() {
		return paymentIdOfCheckBox;
	}

	public void setPaymentIdOfCheckBox(String[] paymentIdOfCheckBox) {
		this.paymentIdOfCheckBox = paymentIdOfCheckBox;
	}

	public String getCanceledBy() {
		return canceledBy;
	}

	public void setCanceledBy(String canceledBy) {
		this.canceledBy = canceledBy;
	}

}
