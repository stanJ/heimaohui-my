/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.event;

import java.util.Date;

/**
 * @author nijie
 *
 */
public class LoginEvent implements Event{
	
	private Long loginTime;
	
	private Boolean authSuccess;
	
	private String username;
	
	
	
	private String agent;
	
	
	private String password;
	
	
	
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public Boolean getAuthSuccess() {
		return authSuccess;
	}

	public void setAuthSuccess(Boolean authSuccess) {
		this.authSuccess = authSuccess;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.invoke.event.Event#getSource()
	 */
	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.invoke.event.Event#getDateTime()
	 */
	@Override
	public Date getDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.invoke.event.Event#getObject()
	 */
	@Override
	public Object getObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
