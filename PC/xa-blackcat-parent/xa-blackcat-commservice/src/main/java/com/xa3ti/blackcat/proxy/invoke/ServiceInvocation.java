/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



/**
 * @author nijie
 *
 */
public class ServiceInvocation implements Invocation {

	 private static final long serialVersionUID = -4355285085441097045L;

	    private String               methodName;

	    private ParamaterMap 	     paramaterMap;

	    private Map<String, Object>  attachments=new HashMap<String,Object>();

	    private transient Invoker invoker;
	    
	    private HttpServletRequest request;

	    public ServiceInvocation() {
	    }

	  
	 
	 
	    
	    public ServiceInvocation(HttpServletRequest request,String methodName, ParamaterMap paramaterMap, Map<String, Object> attachments) {
	    	
	    	  this.methodName = methodName;
		       this.paramaterMap=paramaterMap;
		        this.attachments = attachments == null ? new HashMap<String, Object>() : attachments;
		        this.invoker = invoker;
		        this.request=request;
	    }

	 
	   
	    
	    
	    public HttpServletRequest getRequest(){
	    	return this.request;
	    }
	    
	    public Invoker getInvoker() {
	        return invoker;
	    }

	    public void setInvoker(Invoker invoker) {
	        this.invoker = invoker;
	    }

	    public String getMethodName() {
	        return methodName;
	    }

	    public Class<?>[] getParameterTypes() {
	        return this.paramaterMap.getParamaterTypes();
	    }

	    public Object[] getArguments() {
	        return this.paramaterMap.getParamaterValues();
	    }

	    public Map<String, Object> getAttachments() {
	        return attachments;
	    }

	    public void setMethodName(String methodName) {
	        this.methodName = methodName;
	    }

	  

	    public void setAttachments(Map<String, Object> attachments) {
	        this.attachments = attachments == null ? new HashMap<String, Object>() : attachments;
	    }
	    
	    public void setAttachment(String key, Object value) {
	        if (attachments == null) {
	            attachments = new HashMap<String, Object>();
	        }
	        attachments.put(key, value);
	    }

	    public void setAttachmentIfAbsent(String key, Object value) {
	        if (attachments == null) {
	            attachments = new HashMap<String, Object>();
	        }
	        if (! attachments.containsKey(key)) {
	        	attachments.put(key, value);
	        }
	    }

	    public void addAttachments(Map<String, Object> attachments) {
	    	if (attachments == null) {
	    		return;
	    	}
	    	if (this.attachments == null) {
	    		this.attachments = new HashMap<String, Object>();
	        }
	    	this.attachments.putAll(attachments);
	    }

	    public void addAttachmentsIfAbsent(Map<String, String> attachments) {
	    	if (attachments == null) {
	    		return;
	    	}
	    	for (Map.Entry<String, String> entry : attachments.entrySet()) {
	    		setAttachmentIfAbsent(entry.getKey(), entry.getValue());
	    	}
	    }

	    public Object getAttachment(String key) {
	        if (attachments == null) {
	            return null;
	        }
	        return attachments.get(key);
	    }
	    
	    public Object getAttachment(String key, String defaultValue) {
	        if (attachments == null) {
	            return defaultValue;
	        }
	        Object value = attachments.get(key);
//	        if (value == null || value.length() == 0) {
//	            return defaultValue;
//	        }
	        return value;
	    }

	    
	    
	    @Override
	    public String toString() {
	        return "RpcInvocation [methodName=" + methodName + ", parameterTypes="
	                + Arrays.toString(this.paramaterMap.getParamaterTypes()) + ", arguments=" + Arrays.toString(this.paramaterMap.getParamaterValues())
	                + ", attachments=" + attachments + "]";
	    }

		/* (non-Javadoc)
		 * @see com.xa3ti.blackcat.proxy.invoke.Invocation#addParameter(java.lang.String, java.lang.Object)
		 */
		
		public boolean appendParameter(String name, Object value) {
			return this.paramaterMap.appendParamater(name, value);
		}

		/* (non-Javadoc)
		 * @see com.xa3ti.blackcat.proxy.invoke.Invocation#getParameterNames()
		 */
	
		public boolean modifyParameter(String name, Object value) {
			return this.paramaterMap.modifyParamter(name, value);
		}

		
		
		public void clearAllParamaters(){
			this.paramaterMap.clearAllParamaters();
		}
		
		
		public Object getParamater(String name){
			return this.paramaterMap.getParamaterValue(name);
		}

		
}
