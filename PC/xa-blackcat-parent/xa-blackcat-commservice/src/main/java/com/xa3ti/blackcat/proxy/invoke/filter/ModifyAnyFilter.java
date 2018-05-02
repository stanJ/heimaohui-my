/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.util.ContextUtil;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.business.entity.Customer;
import com.xa3ti.blackcat.business.entity.Expert;
import com.xa3ti.blackcat.business.service.CustomerService;
import com.xa3ti.blackcat.business.service.ExpertService;
import com.xa3ti.blackcat.proxy.invoke.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author nijie
 *
 */
public class ModifyAnyFilter extends AbstractFilter {

	/**
	 * @param sort
	 */
	ModifyAnyFilter(int sort) {
		super(sort);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.proxy.invoke.filter.Filter#invoke(com.xa3ti.blackcat
	 * .proxy.invoke.Invoker, com.xa3ti.blackcat.proxy.invoke.Invocation)
	 */
	@Override
	public Result invoke(Invoker invoker, Invocation invocation)
			throws BusinessException {
		System.out.println("ModifyAnyFilter invoked");
		Token t = (Token) invocation
				.getAttachment(BaseConstant.ATTACTMENT_KEY_TOKEN);

		String expertId = null;
		String customerId = null;
		String modifyDes = "";
		if (invocation instanceof ServiceInvocation) {
			String methodName = invocation.getMethodName();
			ServiceInvoker si = (ServiceInvoker) invocation.getInvoker();
			
			//如果是涉及客户专家本身修改或其他修改 直接跳出
			if(methodName.indexOf("Announce")>=0||methodName.indexOf("Browse")>=0||methodName.indexOf("Intro")>=0
					||methodName.indexOf("News")>=0||methodName.indexOf("UserExtend")>=0
					||methodName.indexOf("Customer")>=0||methodName.indexOf("Expert")>=0)
				return invoker.invoke(invocation);
			
			if (methodName.indexOf("create") >= 0
					|| methodName.indexOf("update") >= 0) {
				Object model = invocation.getArguments()[1];
				try {
					expertId = (String) AnnotationUtil.invokeMethodOnObject(
							model, "getExpertId", null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					customerId = (String) AnnotationUtil.invokeMethodOnObject(
							model, "getCustomerId", null);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (methodName.indexOf("create") >= 0) {
					if (expertId != null)
						modifyDes = "创建专家"+model.getClass().getSimpleName()+"信息涉及专家id:"+expertId;
					else if (customerId != null)
						modifyDes = "创建客户"+model.getClass().getSimpleName()+"信息涉及客户id:"+customerId;

				} else if (methodName.indexOf("update") >= 0) {
					if (expertId != null)
						modifyDes = "修改专家"+model.getClass().getSimpleName()+"信息涉及专家id:"+expertId;
					else if (customerId != null)
						modifyDes = "修改客户"+model.getClass().getSimpleName()+"信息涉及客户id:"+customerId;
				}

			} else if ((methodName.toLowerCase().indexOf("operate") >= 0 && methodName
					.indexOf("Cooperate") < 0)
					|| methodName.toLowerCase().indexOf("operatecooperate") >= 0) {
				String modelIds = (String) invocation.getArguments()[1];
				String modelName = methodName.replace("operate", "");
				modelName = modelName.replace("multiOperate", "");
				String[] modelIdArray = modelIds.split(",");
				Object o =null;
				for (String modelId : modelIdArray) {
					String findMethodName = "find" + modelName;

					String[] argus = new String[] { modelId };
					try {
						Method method = ReflectionUtils.findMethod(
								si.getInterface(), findMethodName, null);
						Object ro = ReflectionUtils.invokeMethod(method,
								si.getService(), argus);
						if (ro != null) {
							XaResult<Object> xa = (XaResult<Object>) ro;
							if(xa!=null){
							    o = xa.getObject();
							    if(o!=null){
									try {
										expertId = (String) AnnotationUtil
												.invokeMethodOnObject(o, "getExpertId",
														null);
									} catch (Exception e) {
										e.printStackTrace();
									}
									try {
										customerId = (String) AnnotationUtil
												.invokeMethodOnObject(o,
														"getCustomerId", null);
									} catch (Exception e) {
										e.printStackTrace();
									}
							    }
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if(o!=null){
					if (expertId != null)
						modifyDes = "删除专家"+o.getClass().getSimpleName()+"信息涉及客户id:"+expertId;
					else if (customerId != null)
						modifyDes = "删除客户"+o.getClass().getSimpleName()+"信息涉及客户id:"+customerId;
				}
			}

			// 如果修改的是涉及专家记录
			if (expertId != null) {
				ExpertService expertService=(ExpertService)ContextUtil.getContext().getBean("ExpertService");
				
				XaResult<Expert> xa=expertService.findExpert(expertId);
				if(xa!=null){
					Expert expert=xa.getObject();
					if(expert!=null){
						//expert.setModifyDescription(modifyDes);
						//expert.setModifyTime(com.xa3ti.blackcat.base.util.DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
						//expert.setModifyUser(t.getUserId());
					    expertService.updateExpertModifyTime(expert.getTid(),new Date(),modifyDes,t.getUserId());
					}
				}
			}
			// 如果修改的是涉及客户记录
			if (customerId != null) {
				CustomerService customerService=(CustomerService)ContextUtil.getContext().getBean("CustomerService");
				
				XaResult<Customer> xa=customerService.findCustomer(customerId);
				if(xa!=null){
				Customer customer=xa.getObject();
				if(customer!=null){
//					customer.setModifyDescription(modifyDes);
//					customer.setModifyTime(com.xa3ti.blackcat.base.util.DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
//					customer.setModifyUser(t.getUserId());
					customerService.updateCustomerModifyTime(customer.getTid(),new Date(),modifyDes,t.getUserId());
				}
				}
			}

		}
		return invoker.invoke(invocation);
	}

}
