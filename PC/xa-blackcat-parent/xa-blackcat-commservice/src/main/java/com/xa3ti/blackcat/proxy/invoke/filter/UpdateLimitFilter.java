/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;
import com.xa3ti.blackcat.business.util.TokenCenter;

/**
 * @author nijie
 *
 */
public class UpdateLimitFilter extends AbstractFilter {

	/**
	 * @param sort
	 */
	UpdateLimitFilter(int sort) {
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
		System.out.println("UpdateLimitFilter invoked");
		Token t = (Token) invocation
				.getAttachment(BaseConstant.ATTACTMENT_KEY_TOKEN);

		if (TokenCenter.TOKEN_TYPE_GM.equals(t.getType())
				|| TokenCenter.TOKEN_TYPE_LOGINUSER.equals(t.getType())) {

			if (invocation instanceof ServiceInvocation) {
				String methodName = invocation.getMethodName();
				ServiceInvoker si = (ServiceInvoker) invocation.getInvoker();
				if (methodName.indexOf("update") >= 0
						|| methodName.indexOf("create") >= 0
						|| methodName.toLowerCase().indexOf("operate") >= 0) {
				
					// 普通用户只能对合作记录配合度有修改删除权限，其他的无修改删除权限
					if (TokenCenter.TOKEN_TYPE_LOGINUSER.equals(t.getType())
							&& (methodName.toLowerCase().indexOf("operate") >= 0 || methodName
									.toLowerCase().indexOf("update") >= 0)) {

						if (methodName.indexOf("Cooperate") >= 0
								|| methodName.indexOf("Work") >= 0) {
							
						} else
							throw new BusinessException("普通用户不能修改或删除该类型记录");
					}

					String modelName = null;
					boolean isFind = false;
					boolean isUpdate = false;
					boolean isCreate = false;
					boolean isDelete = false;
					if (methodName.indexOf("find") >= 0) {
						isFind = true;
					}
					else if (methodName.indexOf("update") >= 0) {
						modelName = methodName.replace("update", "");
						isUpdate = true;
					} else if (methodName.indexOf("create") >= 0) {
						modelName = methodName.replace("create", "");
						isCreate = true;
					} else if (methodName.toLowerCase().indexOf("operate") >= 0) {
						modelName = methodName.replace("operate", "");
						modelName = modelName.replace("multiOperate", "");
						isDelete = true;
					}
					if (isUpdate) {
						Object model = invocation.getArguments()[1];
						String modelId = null;
						XaResult<Object> xa = null;
						String createUser = null;
						try {
							modelId = (String) AnnotationUtil
									.invokeMethodOnObject(model, "getTid", null);

							if (modelId != null) {
								String findMethodName = "find" + modelName;

								String[] argus = new String[] { modelId };

								Method method = ReflectionUtils
										.findMethod(si.getInterface(),
												findMethodName, null);
								Object ro = ReflectionUtils.invokeMethod(
										method, si.getService(), argus);
								if (ro != null) {
									xa = (XaResult<Object>) ro;

									Object o = xa.getObject();
									createUser = (String) AnnotationUtil
											.invokeMethodOnObject(o,
													"getCreateUser", null);
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						//if (TokenCenter.TOKEN_TYPE_GM.equals(t.getType())) {
							if (!t.getUserId().equals(createUser))
								throw new BusinessException(
										"该用户不能修改其他用户创建的记录，只能修改本人创建的记录");
						//}
					}

					else if (isDelete) {
						XaResult<Object> xa = null;
						String createUser = null;

						String modelIds = (String) invocation.getArguments()[1];
						String[] modelIdArray = modelIds.split(",");
						for (String modelId : modelIdArray) {
							String findMethodName = "find" + modelName;

							String[] argus = new String[] { modelId };
							try {
								Method method = ReflectionUtils
										.findMethod(si.getInterface(),
												findMethodName, null);
								Object ro = ReflectionUtils.invokeMethod(
										method, si.getService(), argus);
								if (ro != null) {
									xa = (XaResult<Object>) ro;

									Object o = xa.getObject();
									createUser = (String) AnnotationUtil
											.invokeMethodOnObject(o,
													"getCreateUser", null);
								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							//if (TokenCenter.TOKEN_TYPE_GM.equals(t.getType())) {
								if (!t.getUserId().equals(createUser))
									throw new BusinessException(
											"该用户不能删除其他用户创建的记录，只能删除本人创建的记录");
							//}

						}
					}

				}

			}

		}
		return invoker.invoke(invocation);
	}
}
