/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.util;

/**
 * @author nijie
 *
 */
public class QueryParamater {
	
	private Class type;
	
	private Object value;

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	
	QueryParamater(Class type,Object value){
		this.type=type;
		this.value=value;
	}
	
	
	QueryParamater(Object value){
		this.type=value.getClass();
		this.value=value;
	}

}
