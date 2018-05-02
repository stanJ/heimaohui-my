/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.util;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nijie
 *
 */
public class Token implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1987964354982323L;
	
	public final static Integer TOKENTYPE_ONCE=0;
	public final static Integer TOKENTYPE_AUTORENEW=1;
	
	
	
	
	private Long generateTime;
	
	private Integer duration;
	
	private Integer type;
	
	private String userId;
	
	private String agent;
	
	
	private String token;
	
	
	
	private Long lastActionTime=30*60*1000l; //30分钟令牌超时
	
	
	private Integer status;
	private Long removeTime;
	
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(Long removeTime) {
		this.removeTime = removeTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getLastActionTime() {
		return lastActionTime;
	}

	public void setLastActionTime(Long lastActionTime) {
		this.lastActionTime = lastActionTime;
	}

	public Long getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Long generateTime) {
		this.generateTime = generateTime;
	}

	

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	
	
	

}
