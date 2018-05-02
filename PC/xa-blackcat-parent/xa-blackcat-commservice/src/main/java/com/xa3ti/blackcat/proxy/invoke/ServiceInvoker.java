/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.ReflectionUtils;

import com.mysql.jdbc.MysqlDataTruncation;
import com.xa3ti.blackcat.base.annotation.XALogger;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.event.DistributeCenter;
import com.xa3ti.blackcat.event.ServiceEvent;
import com.xa3ti.blackcat.proxy.invoke.filter.FilterRegister;

/**
 * @author nijie
 *
 */
public class ServiceInvoker implements Invoker {

	public Object service;
	
	public ServiceInvoker(Object aService) {
		this.service = aService;
		//observer.addInvoker(this);
	}
	
	public Object getService(){
		return service;
	}

	@Override
	public Class getInterface() {
		return service.getClass();
	}
	
	@Override
	public Result invoke(Invocation invocation) throws BusinessException {
		Class[] params = invocation.getParameterTypes();
		
		String methodName = invocation.getMethodName();

		Object ro = null;
		try {

			
			beforeInvoke(invocation);
			
			
			
			Method method = ReflectionUtils.findMethod(service.getClass()
					.getSuperclass(), methodName, null);
			
			
			
			Object[] argus = invocation.getArguments();
			
			long startTime=java.lang.System.currentTimeMillis();
			ro =AnnotationUtil.invokeMethodOnObject(service, method, argus);
			//ro = ReflectionUtils.invokeMethod(method, service, argus);
			long endTime=java.lang.System.currentTimeMillis();
			
			
			XALogger logger=method.getAnnotation(XALogger.class);
			if(logger!=null){
				Boolean needLog=logger.need();
				if(needLog)
				  invocation.getAttachments().put(BaseConstant.LOG.NEEDLOG, true);
			}
			
			afterInvoke(invocation,endTime-startTime,ro);
			
			
		} catch (IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		} catch (SecurityException e) {
			throw new BusinessException(e.getMessage());
		}
		catch (Exception e) {
			if(e instanceof InvocationTargetException){
				InvocationTargetException ee=(InvocationTargetException)e;
				
				
				if( ee.getTargetException() instanceof DataIntegrityViolationException){
					DataIntegrityViolationException e1=(DataIntegrityViolationException)ee.getTargetException();
					if (e1.getCause().getCause() instanceof MysqlDataTruncation){
						MysqlDataTruncation e2=(MysqlDataTruncation)e1.getCause().getCause();
						if("22001".equals(e2.getSQLState())){
							throw new BusinessException(22001,ee.getTargetException().getMessage());
						}
					}
				}
				
				
				
				
				throw new BusinessException(ee.getTargetException().getMessage());
			}
			
			throw new BusinessException(e.getMessage());
		}
		ServiceResult sr = new ServiceResult();
		sr.setResult(ro);
		return sr;

	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Invoker#beforeInvoke(com.xa3ti.blackcat.proxy.bean.Invocation)
	 */
	
	private void beforeInvoke(Invocation invocation) throws BusinessException {
		invocation.setInvoker(this);
		FilterRegister.buildFilterChain(new MockInvoker()).invoke(invocation);
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.Invoker#afterInvoke(com.xa3ti.blackcat.proxy.bean.Invocation)
	 */
	
	private void afterInvoke(Invocation invocation,long timeUsed,Object result) throws BusinessException {
		DistributeCenter.distributeEvent(new ServiceEvent(this,invocation,result,timeUsed));
	}

	

}
