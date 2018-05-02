/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.business.entity;





import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.entity.BaseEntity;


/**
 * @author nijie
 *
 */
@Entity
@Table(name = "tb_xa_constant")
@ApiModel(value = "常量表")
public class Constant extends BaseEntity {
	
	@ApiModelProperty(value = "常量键,常量键")
	private String constantKey;
	
	@ApiModelProperty(value = "常量值,常量值")
	private String constantValue;

	@Column(nullable=false,length=200)
	public String getConstantKey() {
		return constantKey;
	}

	public void setConstantKey(String constantKey) {
		this.constantKey = constantKey;
	}

	@Column(nullable=false,length=20)
	public String getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(String constantValue) {
		this.constantValue = constantValue;
	}

	
	
	
	

}
