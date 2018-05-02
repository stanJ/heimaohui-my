/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.proxy.invoke.BeforeInvokerListener;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.Result;

/**
 * @author nijie
 *
 */
public interface Filter{
	
	public void setSort(int sort);
	
	public int getSort();
	
	/**
	 * invoke a invoker based on invacation
	 * @param invoker
	 * @param invocation
	 * @return
	 * @throws BusinessException
	 */
	Result invoke(Invoker invoker, Invocation invocation) throws BusinessException;

}
