/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.Result;

/**
 * @author nijie
 *
 */
public abstract class AbstractFilter implements Filter {

	private int sort;
	
	AbstractFilter(int sort){
		this.sort=sort;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Filter#setSort(int)
	 */
	@Override
	public void setSort(int sort) {
		this.sort=sort;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Filter#getSort()
	 */
	@Override
	public int getSort() {
		return this.sort;
	}

}
