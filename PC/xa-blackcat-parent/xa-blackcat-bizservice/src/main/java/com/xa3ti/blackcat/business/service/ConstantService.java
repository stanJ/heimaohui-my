/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.business.service;

import java.util.HashMap;

import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;





/**
 * @author nijie
 *
 */
public interface ConstantService {
	
	public XaResult<HashMap> getConstant() throws BusinessException;

}
