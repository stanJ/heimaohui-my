/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.xa3ti.blackcat.message.channel.sms.SMSServiceImpl;
import com.xa3ti.blackcat.message.channel.sys.Client;
import com.xa3ti.blackcat.message.channel.sys.ErrorMessage;
import com.xa3ti.blackcat.message.persistence.MsgService;

/**
 * @author nijie
 *
 */
@Component("MsgPusher")
public class MsgPusherImpl implements MsgPusher {
	@Autowired
	MsgService msgService;

	
	private Map<Long, Client> webClients = new HashMap<Long, Client>();
	private Map<Long, Client> misClients = new HashMap<Long, Client>();

	private static final String COMET_CHANNEL_CHANNEL1 = "CHANNEL1";
	// private static final String CHANNEL_WEB2MIS = "WEB2MIS";

	private static final String FRONTEND_WEB = "web";
	private static final String FRONTEND_MIS = "mis";
	private static final String FRONTEND_CRM = "crm";

	public static final String[] COMET_CHANNELS = { COMET_CHANNEL_CHANNEL1 };

	private static final String KEY = "msg";

	private Map<Long, Client> getAllWebClients() {
		return webClients;
	}

	private Map<Long, Client> getAllMisClients() {
		return misClients;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.message.MessagePusher#pushMessageToClient(com.xa3ti
	 * .mainframe.message.Message)
	 */
	@Override
	public boolean pushMessageToClient(Msg msg) {
		Long userId = msg.getReceiverId();

		if (MsgConstant.ReceiverType.TYPE_WEBCLIENT.equals(msg
				.getReceiverType())) {
			Client c = webClients.get(userId);
			// if(c!=null||msg.getMobile()!=null){
			sendMsg(c,msg);
			// }
		}
		// }else
		// if(MsgConstant.MessageReceiverType.doctor.equals(msg.getReceiverType())){
		// Client c=misClients.get(userId);
		// //if(c!=null||msg.getMobile()!=null){
		// sendMsg(msg.getChannel(),msg.getId(),c,msg.getSender(),msg.getMobile(),msg.getTitle(),msg.getContent(),msg.getRetryTimes());
		// //}
		// }else
		// if(MsgConstant.MessageReceiverType.clinic_contactor.equals(msg.getReceiverType())){
		// Client c=misClients.get(userId);
		// //if(c!=null||msg.getMobile()!=null){
		// sendMsg(msg.getChannel(),msg.getId(),c,msg.getSender(),msg.getMobile(),msg.getTitle(),msg.getContent(),msg.getRetryTimes());
		// //}
		// }
 		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.message.MessagePusher#addMessageToQueue(com.xa3ti
	 * .mainframe.message.Message)
	 */
	@Override
	public boolean addMessageToQueue(Msg msg) {
		if(MsgConstant.Priority.HIGHEST==msg.getPriority()){
			MsgQueue.getMessageHighestQueue().add(msg);
		}else if(MsgConstant.Priority.HIGH==msg.getPriority()){
			MsgQueue.getMessageHighQueue().add(msg);
		}else if(MsgConstant.Priority.NORMAL==msg.getPriority()){
			MsgQueue.getMessageNormalQueue().add(msg);
		}else if(MsgConstant.Priority.LOW==msg.getPriority()){
			MsgQueue.getMessageLowQueue().add(msg);
		}else if(MsgConstant.Priority.LOWEST==msg.getPriority()){
			MsgQueue.getMessageLowestQueue().add(msg);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.message.MessagePusher#getMessageFromQueue(com.xa3ti
	 * .mainframe.message.Message)
	 */
	@Override
	public LinkedBlockingQueue<Msg> getMessageFromQueue(Integer priority) {
		if(MsgConstant.Priority.HIGHEST==priority){
			return MsgQueue.getMessageHighestQueue();
		}else if(MsgConstant.Priority.HIGH==priority){
			return MsgQueue.getMessageHighQueue();
		}else if(MsgConstant.Priority.NORMAL==priority){
			return MsgQueue.getMessageNormalQueue();
		}else if(MsgConstant.Priority.LOW==priority){
			return MsgQueue.getMessageLowQueue();
		}else if(MsgConstant.Priority.LOWEST==priority){
			return  MsgQueue.getMessageLowestQueue();
		}else
			return null;
	}

	private void sendMsg(Client to,Msg msg) {
		
		String channel=msg.getChannel();
		Long msgId=msg.getId();
		
		String from=msg.getSender();
		
		String mobile=msg.getMobile();
		String title=msg.getTitle();
		String content= msg.getContent();
		int retriedTimes=msg.getRetryTimes();

		if (retriedTimes <= 3) {
			if (MsgConstant.CHANNEL.SYS.equals(channel)) {
				if (to != null) {
					JSONObject json = new JSONObject();
					json.put("msgId", msgId);
					json.put("from", from);
					json.put("title", title);
					json.put("content", content);

					CometEngine engine = CometContext.getInstance().getEngine();
					engine.sendTo(this.COMET_CHANNEL_CHANNEL1, to.getConn(),
							json);
				} else {
					msgService
							.updateMessageFailure(msgId, "接收方WEB不在线,归入失败消息队列");// 立即更新为失败
				}
			} else if (MsgConstant.CHANNEL.SMS.equals(channel)) {
				if (mobile != null && !"".equals(mobile.trim())) {
					String ret = SMSServiceImpl.getInstance()
							.sendSMSSingeMobile(mobile, content, msgId);
					// 发送成功
					if ("0".equals(ret)) {
						MsgQueue.getSendedMessageQueue().add(msg);
						msgService.updateMessageSuccess(msgId);// 立即更新为成功
					}
					// 不成功直接归入失败队列,这样可以记录原因
					else {
						msgService.updateMessageFailure(msgId, ret);// 立即更新为失败
					}

				} else {
					msgService.updateMessageFailure(msgId,
							"接收方的手机号为空或找不到接收方的手机号");// 立即更新为失败
				}

			}

			// MessagePusher.getInstance().addSendedMessage(msg);
			msgService.updateMessageRetryTimes(msgId, retriedTimes + 1);// 立即更新失败次数
		} else {
			msgService.updateMessageFailure(msgId, "此消息发送次数3次没发送成功,归入失败消息");// 立即更新为失败
			// addErrorMessage(msgId, "此消息发送次数3次没发送成功,归入失败消息");
		}
	}

//	public void addMessage(Msg msg, Integer priority) {
//		if (priority == MsgConstant.Priority.LOWEST)
//			message1Queue.add(msg);
//		else if (priority == MsgConstant.Priority.LOW)
//			message2Queue.add(msg);
//		else if (priority == MsgConstant.Priority.NORMAL)
//			message3Queue.add(msg);
//		else if (priority == MsgConstant.Priority.HIGH)
//			message4Queue.add(msg);
//		else if (priority == MsgConstant.Priority.HIGHEST)
//			message5Queue.add(msg);
//		else
//			message3Queue.add(msg);
//
//	}
//
//	public void addSendedMessage(Long msgid) {
//		sendedMessageQueue.add(msgid);
//	}
//
//	public void addErrorMessage(Long msgId, String errReason) {
//		ErrorMessage erm = new ErrorMessage();
//		erm.setMsgId(msgId);
//		erm.setErrorReason(errReason);
//		errorMessageQueue.add(erm);
//	}

}
