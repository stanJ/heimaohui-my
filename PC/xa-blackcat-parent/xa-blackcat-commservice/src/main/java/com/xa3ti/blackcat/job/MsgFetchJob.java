/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.job;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xa3ti.blackcat.base.util.ContextUtil;
import com.xa3ti.blackcat.message.Msg;
import com.xa3ti.blackcat.message.MsgConstant;
import com.xa3ti.blackcat.message.MsgPusher;
import com.xa3ti.blackcat.message.MsgQueue;
import com.xa3ti.blackcat.message.persistence.MsgService;

/**
 * @author nijie
 *
 */
@Component
public class MsgFetchJob implements Job {
	private static final Logger logger = Logger.getLogger(MsgFetchJob.class); 
	

	MsgPusher msgPusher;
	

	MsgService msgService;
	
//	@PostConstruct
//	public void construct(){
//		 msgService=(MsgService)ContextUtil.getContext().getBean("MsgService");
//		 msgPusher=(MsgPusher)ContextUtil.getContext().getBean("MsgPusher");
//	}
	    
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		logger.info("SimpleJob says: " + jobKey + " executing at " + new Date());
		System.out.println("SimpleJob says: " + jobKey + " executing at " + new Date());
		
		 msgService=(MsgService)ContextUtil.getContext().getBean("MsgService");
		 msgPusher=(MsgPusher)ContextUtil.getContext().getBean("MsgPusher");
//		while (true) {
//			try {
//				Thread.sleep(1000);
//			} catch (Exception e) {
//
//			}

			msgService.getMessage(MsgConstant.Priority.HIGHEST);
			msgService.getMessage(MsgConstant.Priority.HIGH);
			msgService.getMessage(MsgConstant.Priority.NORMAL);
			msgService.getMessage(MsgConstant.Priority.LOW);
			msgService.getMessage(MsgConstant.Priority.LOWEST);
			
			// 设置成功状态
			LinkedBlockingQueue<Msg> sendedQueue =MsgQueue.getSendedMessageQueue();
			if (!sendedQueue.isEmpty()) {
				while (!sendedQueue.isEmpty()) {
					logger.debug("set message success status==>");
					Msg _msg = sendedQueue.poll();
					msgService.updateMessageSuccess(_msg.getId());

				}
			}

			// 设置失败状态
			LinkedBlockingQueue<Msg> errorQueue =MsgQueue.getErrorMessageQueue();
			if (!errorQueue.isEmpty()) {
				while (!errorQueue.isEmpty()) {
					logger.debug("set message failure status==>");
					Msg err = errorQueue.poll();
					msgService.updateMessageFailure(err.getId(), err.getFailureReason());
				}
			}
			
		   
//		}
		
	}  
}
