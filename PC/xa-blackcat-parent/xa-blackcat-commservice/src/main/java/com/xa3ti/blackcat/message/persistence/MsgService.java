/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.message.persistence;

/**
 * @author nijie
 *
 */
public interface MsgService {

	
	public void updateMessageRetryTimes(Long msgId, Integer times) ;
	
	public void updateMessageSuccess(Long msgId);
	
	public void updateMessageFailure(Long msgId,String failureReason);
	
	public void updateMessageInfo(Long msgId,String info);
	
	
	public void getMessage(Integer priority);
}
