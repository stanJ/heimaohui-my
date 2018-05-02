/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.util.WebUitl;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;

/**
 * @author nijie
 *
 */
public class ParamaterRefactor extends AbstractFilter{
   
	
	/**
	 * @param sort
	 */
	ParamaterRefactor(int sort) {
		super(sort);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Filter#invoke(com.xa3ti.blackcat.proxy.bean.Invoker, com.xa3ti.blackcat.proxy.bean.Invocation)
	 */
	@Override
	public Result invoke(Invoker invoker, Invocation invocation)
			throws BusinessException {
		System.out.println("ParamaterRefactor invoked");
		
		boolean needRefactor=false;
		ServiceInvocation si=null;
		if(invocation instanceof ServiceInvocation){
			
			si=(ServiceInvocation)invocation;
			HttpServletRequest rq=si.getRequest();
			
			Object nextPage=null;
			Object pageSize=null;
			Object sortData=null;
			Object jsonFilter=null;
			Object status=null;
			try{
			 nextPage=si.getParamater("nextPage");
			 pageSize=si.getParamater("pageSize");
			 sortData=si.getParamater("sortData");
			 jsonFilter=si.getParamater("jsonFilter");
			 status=si.getParamater("status");
			
			}catch(Exception e){
				return invoker.invoke(invocation);
			}
			
			
			Pageable pageable = null;
			Map<String, Object> filterParams=new HashMap<String, Object>();
			
			if(nextPage!=null&&pageSize!=null&&sortData!=null)
			{  
				
				 pageable = WebUitl.buildPageRequest((Integer)(nextPage), (Integer)pageSize,
					String.valueOf(sortData));
				
				needRefactor=true;
			}
			
			if (jsonFilter != null
					&& (((String)jsonFilter).contains("search_") || "{}".equals(jsonFilter))) {
				
				filterParams = WebUitl.getParametersStartingWith((String)jsonFilter,
						"search_");
				
				needRefactor=true;
			} else if(jsonFilter != null){
				
				filterParams.put(BaseConstant.SQLFILTERKEY, (String)jsonFilter);
				
				needRefactor=true;
			}
		
			
			if(needRefactor){
				si.clearAllParamaters();
				si.appendParameter("status",status);
				si.appendParameter("filterParams",filterParams);
				si.appendParameter("pageable",pageable);
			
				return invoker.invoke(si);
			}
		}
		
		return invoker.invoke(invocation);
		
	}


	

}
