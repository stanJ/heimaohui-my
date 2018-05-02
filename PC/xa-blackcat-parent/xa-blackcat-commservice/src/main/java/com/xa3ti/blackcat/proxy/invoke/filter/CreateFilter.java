/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import java.util.Date;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;

/**
 * @author nijie
 *
 */
public class CreateFilter extends AbstractFilter {

	/**
	 * @param sort
	 */
	CreateFilter(int sort) {
		super(sort);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.invoke.filter.Filter#invoke(com.xa3ti.blackcat.proxy.invoke.Invoker, com.xa3ti.blackcat.proxy.invoke.Invocation)
	 */
	@Override
	public Result invoke(Invoker invoker, Invocation invocation)
			throws BusinessException {
		System.out.println("CreateFilter invoked");
		Token t = (Token) invocation
				.getAttachment(BaseConstant.ATTACTMENT_KEY_TOKEN);

		
		if (invocation instanceof ServiceInvocation) {
			String methodName = invocation.getMethodName();
			ServiceInvoker si = (ServiceInvoker) invocation.getInvoker();
			if (methodName.indexOf("create") >= 0){
				
				Object model = invocation.getArguments()[1];
				
				
				try {
					AnnotationUtil.invokeMethodOnObject(model, "setModifyTime", new Object[]{com.xa3ti.blackcat.base.util.DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss")});
					AnnotationUtil.invokeMethodOnObject(model, "setModifyUser", new Object[]{t.getUserId()});
					AnnotationUtil.invokeMethodOnObject(model, "setModifyDescription", new Object[]{"初始创建"});
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
		return invoker.invoke(invocation);
	}

}
