/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nijie
 *
 */
public class MockInvocation implements Invocation{
	
	
	 public String getMethodName() {
	        return "echo";
	    }

	    public Class<?>[] getParameterTypes() {
	        return new Class[] { String.class };
	    }

	    public Object[] getArguments() {
	        return new Object[] { "aa" };
	    }

	    public Map<String, Object> getAttachments() {
	        Map<String, Object> attachments = new HashMap<String, Object>();
	        attachments.put("name", "mainframe");
	        return attachments;
	    }

	    public Invoker getInvoker() {
	        return null;
	    }

	    public Object getAttachment(String key) {
	        return getAttachments().get(key);
	    }

	    public Object getAttachment(String key, String defaultValue) {
	        return getAttachments().get(key);
	    }

		/* (non-Javadoc)
		 * @see com.xa3ti.blackcat.proxy.invoke.Invocation#setInvoker(com.xa3ti.blackcat.proxy.invoke.Invoker)
		 */
		@Override
		public void setInvoker(Invoker invoker) {
			// TODO Auto-generated method stub
			
		}

		

		/* (non-Javadoc)
		 * @see com.xa3ti.blackcat.proxy.bean.Invocation#getToken()
		 */
		


}
