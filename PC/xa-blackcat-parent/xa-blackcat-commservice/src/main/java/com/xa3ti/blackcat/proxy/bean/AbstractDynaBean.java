/**
 * 
 */
package com.xa3ti.blackcat.proxy.bean;

import java.util.HashMap;

import org.apache.commons.beanutils.DynaBean;

/**
 * @author nijie
 *
 */
public interface AbstractDynaBean{
	
//	//转化为HashMap
//	public HashMap toHashMap();
//	
//	//级联转化为HashMap
//	public HashMap toHashMapCascade();
//	
	
	public Object get(String propName);
	
	
	public void set(String propName,Object value);

}
