/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.message;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author nijie
 *
 */
public interface MsgPusher {

	/**
	 * 推送一个消息给客户
	 * @param msg
	 * @return
	 */
	public boolean pushMessageToClient(Msg msg);
	
	/**
	 * 加一个消息到队列
	 * @param msg
	 * @return
	 */
	public boolean addMessageToQueue(Msg msg);
	
	
	/**
	 * 从一个消息到队列取消息
	 * @param msg
	 * @return
	 */
	public LinkedBlockingQueue<Msg> getMessageFromQueue(Integer priority);
	
}
