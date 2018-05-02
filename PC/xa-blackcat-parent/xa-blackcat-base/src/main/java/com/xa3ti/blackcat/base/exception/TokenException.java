/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.exception;

import org.apache.poi.ss.formula.functions.T;

import com.xa3ti.blackcat.base.constant.BusinessErrorEnum;
import com.xa3ti.blackcat.base.entity.XaResult;



/**
 * @author nijie
 *
 */
public class TokenException extends BaseException {

	
	 private static final long serialVersionUID = 1L;
	    
	    public TokenException() {
	        super();
	    }

	    public TokenException(String errorMsg) {
	        super(errorMsg);
	    }

	    public TokenException(int errorCode, String errorMsg) {
	        super(errorMsg);
	        this.errorCode = errorCode;
	    }

	    public TokenException(int errorCode, String errorMsg, XaResult<T> dataResult) {
	        super(errorMsg);
	        this.errorCode = errorCode;
	        this.dataResult = dataResult;
	    }

	    public TokenException(String errorMsg, Throwable cause) {
	        super(errorMsg, cause);
	    }

	    public TokenException(int errorCode, String errorMsg, Throwable cause) {
	        super(errorMsg, cause);
	        this.errorCode = errorCode;
	    }

	    public TokenException(int errorCode, String errorMsg, XaResult<T> dataResult, Throwable cause) {
	        super(errorMsg, cause);
	        this.errorCode = errorCode;
	        this.dataResult = dataResult;
	    }

	    public  static BusinessException getBusinessException(BusinessErrorEnum businessErrorEnum)
	    {
	       return new BusinessException(businessErrorEnum.getErrorCode(),businessErrorEnum.getErrorMsg());
	    }

}
