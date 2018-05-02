/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.message;

import java.util.concurrent.LinkedBlockingQueue;

import com.xa3ti.blackcat.message.channel.sys.ErrorMessage;

/**
 * @author nijie
 *
 */
public class MsgQueue {
	
	private static LinkedBlockingQueue<Msg> messageHighestQueue = new LinkedBlockingQueue<Msg>();
	private static LinkedBlockingQueue<Msg> messageHighQueue = new LinkedBlockingQueue<Msg>();
	private static LinkedBlockingQueue<Msg> messageNormalQueue = new LinkedBlockingQueue<Msg>();
	private static LinkedBlockingQueue<Msg> messageLowQueue = new LinkedBlockingQueue<Msg>();
	private static LinkedBlockingQueue<Msg> messageLowestQueue = new LinkedBlockingQueue<Msg>();

	private static LinkedBlockingQueue<Msg> sendedMessageQueue = new LinkedBlockingQueue<Msg>();

	private static LinkedBlockingQueue<Msg> errorMessageQueue = new LinkedBlockingQueue<Msg>();

	
	public static LinkedBlockingQueue<Msg> getMessageHighestQueue() {
		return messageHighestQueue;
	}

	public static LinkedBlockingQueue<Msg> getMessageHighQueue() {
		return messageHighQueue;
	}

	public static LinkedBlockingQueue<Msg> getMessageNormalQueue() {
		return messageNormalQueue;
	}

	public static LinkedBlockingQueue<Msg> getMessageLowQueue() {
		return messageLowQueue;
	}

	public static  LinkedBlockingQueue<Msg> getMessageLowestQueue() {
		return messageLowestQueue;
	}

	public static LinkedBlockingQueue<Msg> getSendedMessageQueue() {
		return sendedMessageQueue;
	}

	public static LinkedBlockingQueue<Msg> getErrorMessageQueue() {
		return errorMessageQueue;
	}


}
