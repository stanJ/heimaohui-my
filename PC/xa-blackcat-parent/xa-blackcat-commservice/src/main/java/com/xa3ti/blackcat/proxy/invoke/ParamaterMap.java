/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nijie
 *
 */
public class ParamaterMap {
	
	private List<String> paramaterNames=new ArrayList<String>();
	
	
	private List<Class> paramaterTypes=new ArrayList<Class>();
	
	
	private List<Object> paramaterValues=new ArrayList<Object>();
	
	
	public void clearAllParamaters(){
		paramaterNames.clear();
		paramaterTypes.clear();
		paramaterValues.clear();
	}
	
	public ParamaterMap(String[] paramaterNames,Object[] paramaterValues){
		for(int i=0;i<paramaterNames.length;i++){
			this.paramaterNames.add(paramaterNames[i]);
			this.paramaterValues.add(paramaterValues[i]);
			this.paramaterTypes.add(paramaterValues[i].getClass());
		}
	}



	public String[] getParamaterNames() {
		String[] pNames=new String[paramaterNames.size()];
		paramaterNames.toArray(pNames);
		return pNames;
	}



	public void setParamaterNames(String[] paramaterNames) {
		this.paramaterNames.clear();
		for(int i=0;i<paramaterNames.length;i++){
			this.paramaterNames.add(paramaterNames[i]);
		}
	}



	public Class[] getParamaterTypes() {
		Class[] pTypes=new Class[paramaterTypes.size()];
		paramaterTypes.toArray(pTypes);
		return pTypes;
	}



	public void setParamaterTypes(Class[] paramaterTypes) {
		this.paramaterTypes.clear();
		for(int i=0;i<paramaterTypes.length;i++){
			this.paramaterTypes.add(paramaterTypes[i]);
		}
	}



	public Object[] getParamaterValues() {
		
		Object[] pValues=new Object[paramaterValues.size()];
		paramaterValues.toArray(pValues);
		return pValues;
	}



	public void setParamaterValues(Object[] paramaterValues) {
		this.paramaterValues.clear();
		for(int i=0;i<paramaterValues.length;i++){
			this.paramaterValues.add(paramaterValues[i]);
		}
	}
	
	public Object getParamaterValue(String name){
		if(this.paramaterNames!=null&&this.paramaterNames.size()>0){
			for(int i=0;i<this.paramaterNames.size();i++){
				if (name.equals(this.paramaterNames.get(i))){
					return this.paramaterValues.get(i);
				}
			}
		}
		return null;
	}
	
	public boolean appendParamater(String name,Object value){
		
			this.paramaterNames.add(name);
		    this.paramaterTypes.add(value.getClass());
		    this.paramaterValues.add(value);
		    
		   
	    
	    return true;
	   
	}
	
	
	public boolean modifyParamter(String name,Object value){
		for(int i=0;i<this.paramaterNames.size();i++){
			if (name.equals(this.paramaterNames.get(i))){
				this.paramaterValues.set(i, value);
				this.paramaterTypes.set(i,value.getClass());
				return true;
			}
		}
		return false;
	   
	}
	
	
	

}
