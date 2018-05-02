package com.xa3ti.blackcat.business.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import java.sql.Time;
import java.util.Date;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.util.XaUtil;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.Dict;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.annotation.TableJoin;

/**
 * 
 * @ClassName: Log
 * @Description: 日志定义表
 * @author 曹文波
 * @date 2014年10月11日 上午10:47:49
 *
 */
@Entity
@Table(name = "tb_ms_cms_log")
@ApiModel(value = "日志定义表")
@TableJoin
@QueryDao(method = BaseConstant.DAO.DYNASQL)
public class Log extends BaseEntity {

	@ApiModelProperty(value = "客户端,客户端")
	private String client;
	@ApiModelProperty(value = "操作员,操作员")
	@Depend(name = "XaCmsUser.userId", showname = "XaCmsUser.nickName")
	private String userId;
	@ApiModelProperty(value = "操作日期,操作日期")
	private Date ldate;
	@ApiModelProperty(value = "调用服务,调用服务")
	private String service;
	@ApiModelProperty(value = "调用方法,调用方法")
	private String method;
	@ApiModelProperty(value = "调用时间,调用方法")
	private Long usedTimes;
	@ApiModelProperty(value = "调用结果,调用结果")
	private String result;

	@Column(nullable = true, length = 50)
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Column(nullable = true, length = 10)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(nullable = false, length = 10)
	public Date getLdate() {
		return ldate;
	}

	public void setLdate(Date ldate) {
		this.ldate = ldate;
	}

	@Column(nullable = false, length = 50)
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Column(nullable = false, length = 50)
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(nullable = false, length = 10)
	public Long getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(Long usedTimes) {
		this.usedTimes = usedTimes;
	}

	@Column(nullable = true, length = 1000)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
