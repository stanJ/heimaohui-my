/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import java.util.Map;

/**
 * @author nijie
 *
 */
public class ServiceResult implements Result{

	private Object result;
	
	
	public void setResult(Object result){
		this.result=result;
	}
	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Result#getValue()
	 */
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Result#getException()
	 */
	@Override
	public Throwable getException() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Result#hasException()
	 */
	@Override
	public boolean hasException() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Result#recreate()
	 */
	@Override
	public Object recreate() throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Result#getResult()
	 */
	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return this.result;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Result#getAttachments()
	 */
	@Override
	public Map<String, String> getAttachments() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Result#getAttachment(java.lang.String)
	 */
	@Override
	public String getAttachment(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Result#getAttachment(java.lang.String, java.lang.String)
	 */
	@Override
	public String getAttachment(String key, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
