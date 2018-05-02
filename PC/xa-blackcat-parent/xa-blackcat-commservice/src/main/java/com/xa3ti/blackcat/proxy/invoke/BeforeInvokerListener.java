/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import com.xa3ti.blackcat.base.exception.BusinessException;

/**
 * @author nijie
 *
 */
public interface BeforeInvokerListener {
	
	
	/**
	 * invoke a invoker based on invacation
	 * @param invoker
	 * @param invocation
	 * @return
	 * @throws BusinessException
	 */
	Result invoke(Invoker invoker, Invocation invocation) throws BusinessException;


}
