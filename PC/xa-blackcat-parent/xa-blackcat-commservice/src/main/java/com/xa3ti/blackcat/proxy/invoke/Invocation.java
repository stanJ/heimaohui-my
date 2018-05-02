/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import java.util.Map;

/**
 * @author nijie
 *
 */
public interface Invocation {
	
	
	
	/**
	 * get method name.
	 * 
	 * @serial
	 * @return method name.
	 */
	String getMethodName();

	/**
	 * get parameter types.
	 * 
	 * @serial
	 * @return parameter types.
	 */
	Class<?>[] getParameterTypes();
	
	
	
	


	/**
	 * get arguments.
	 * 
	 * @serial
	 * @return arguments.
	 */
	Object[] getArguments();

	/**
	 * get attachments.
	 * 
	 * @serial
	 * @return attachments.
	 */
	Map<String, Object> getAttachments();
	
	/**
     * get attachment by key.
     * 
     * @serial
     * @return attachment value.
     */
	Object getAttachment(String key);
	
	/**
     * get attachment by key with default value.
     * 
     * @serial
     * @return attachment value.
     */
	Object getAttachment(String key, String defaultValue);

    /**
     * get the invoker in current context.
     * 
     * @transient
     * @return invoker.
     */
    Invoker getInvoker();
    
    
   
    void setInvoker(Invoker invoker);


}
