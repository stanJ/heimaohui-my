/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.event;

import java.util.Date;
import java.util.HashMap;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.base.util.TokenUtil;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;

/**
 * @author nijie
 *
 */
public class ServiceEvent implements Event {
	public static final String INVOKER_KEY="INVOKER_KEY";
	public static final String INVOCATION_KEY="INVOCATION_KEY";
	
	private Invoker invoker;
	private Invocation invocation;
	private Object result;
	
	private Long usedTime;
	
	private Long occurTime;
	public ServiceEvent(Invoker invoker,Invocation invocation,Object result,Long usedTime){
		this.invoker=invoker;
		this.invocation=invocation;
		this.result=result;
		this.usedTime=usedTime;
		this.occurTime=java.lang.System.currentTimeMillis();
	}
	
	
	@Override
	public String getSource() {
		  String agent=(String)invocation.getAttachment(BaseConstant.ATTACTMENT_KEY_AGENT);
		  return agent;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Event#getDateTime()
	 */
	@Override
	public Date getDateTime() {
		return new Date(this.occurTime);
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Event#getObject()
	 */
	@Override
	public Object getObject() {
		HashMap map=new HashMap();
		map.put(INVOKER_KEY, this.invoker);
		map.put(INVOCATION_KEY, this.invocation);
		return map;
	}


	public Invoker getInvoker() {
		return invoker;
	}


	public void setInvoker(Invoker invoker) {
		this.invoker = invoker;
	}


	public Invocation getInvocation() {
		return invocation;
	}


	public void setInvocation(Invocation invocation) {
		this.invocation = invocation;
	}


	public Object getResult() {
		return result;
	}


	public void setResult(Object result) {
		this.result = result;
	}


	public Long getUsedTime() {
		return usedTime;
	}


	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}


	public Long getOccurTime() {
		return occurTime;
	}


	public void setOccurTime(Long occurTime) {
		this.occurTime = occurTime;
	}
	
	
	
	

}
