/**
 * 
 */
package com.xa3ti.blackcat.message.channel.sys;

/**
 * @author nijie
 *
 */
public class ErrorMessage {
	
	private Long msgId;
	
	
	private String errorReason;


	public Long getMsgId() {
		return msgId;
	}


	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}


	public String getErrorReason() {
		return errorReason;
	}


	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	
	

}
