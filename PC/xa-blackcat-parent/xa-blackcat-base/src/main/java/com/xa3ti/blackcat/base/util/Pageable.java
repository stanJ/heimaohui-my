/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.util;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.xa3ti.blackcat.base.model.BaseModel;

/**
 * @author nijie
 *
 */
public class Pageable extends PageRequest {

	private List<Object> modelList=new ArrayList<Object>();
	
	private List<Object> entityList=new ArrayList<Object>();
	
	private Integer totalSize;
	
	
	
	
	public List<Object> getModelList() {
		return modelList;
	}

	public void setModelList(List<Object> modelList) {
		this.modelList = modelList;
	}

	public List<Object> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<Object> entityList) {
		this.entityList = entityList;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}


	public void convertToEntityList(){		
		String fcn="";
		if(modelList!=null&&modelList.size()>0){
			fcn=modelList.get(0).getClass().getName().replace("model", "entity");
		}
		
		
		for(Object o:modelList){
			if(o instanceof BaseModel){
				BaseModel bo=(BaseModel)o;
				Object t=bo.convertToEntity(fcn);
				entityList.add(t);	
			}
		}		
	}

	/**
	 * @param page
	 * @param size
	 */
	public Pageable(int page, int size) {
		super(page, size);
	}
	
	public Pageable(int page, int size,Sort sort) {
		super(page, size,sort);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

}
