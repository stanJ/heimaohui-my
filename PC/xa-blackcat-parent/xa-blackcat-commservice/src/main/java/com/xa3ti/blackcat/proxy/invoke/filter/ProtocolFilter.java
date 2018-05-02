/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.WebUitl;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;

/**
 * @author nijie
 *
 */
public class ProtocolFilter extends AbstractFilter {

	
	/**
	 * @param sort
	 */
	ProtocolFilter(int sort) {
		super(sort);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Result invoke(Invoker invoker, Invocation invocation)
			throws BusinessException {
		System.out.println("ProtocolFilter invoked");
		
		if(invocation instanceof ServiceInvocation){
			ServiceInvocation si=(ServiceInvocation)invocation;
			HttpServletRequest rq=si.getRequest();
			
			String agent=rq.getHeader("User-Agent");
			
			Map<String,Object> attachments=si.getAttachments();
			
			attachments.put("agent", agent);
			
			si.addAttachments(attachments);
			
			return invoker.invoke(si);
		}
		
		return invoker.invoke(invocation);
	}

	

}
