/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.job;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xa3ti.blackcat.base.util.ContextUtil;
import com.xa3ti.blackcat.message.Msg;
import com.xa3ti.blackcat.message.MsgConstant;
import com.xa3ti.blackcat.message.MsgPusher;
import com.xa3ti.blackcat.message.MsgQueue;
import com.xa3ti.blackcat.message.channel.sms.SMSService;
import com.xa3ti.blackcat.message.channel.sms.SMSServiceImpl;
import com.xa3ti.blackcat.message.channel.sys.Client;
import com.xa3ti.blackcat.message.channel.sys.ErrorMessage;
import com.xa3ti.blackcat.message.persistence.MsgService;
import com.xa3ti.blackcat.message.persistence.MsgServiceImpl;

/**
 * @author nijie
 *
 */
@Component
public class MsgSenderJob implements Job {
	private static final Logger logger = Logger.getLogger(MsgSenderJob.class);

	MsgPusher msgPusher;

	MsgService msgService;

	// @PostConstruct
	// public void construct(){
	// msgService=(MsgService)ContextUtil.getContext().getBean("MsgService");
	// msgPusher=(MsgPusher)ContextUtil.getContext().getBean("MsgPusher");
	// }

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		msgService = (MsgService) ContextUtil.getContext()
				.getBean("MsgService");
		msgPusher = (MsgPusher) ContextUtil.getContext().getBean("MsgPusher");

		JobKey jobKey = context.getJobDetail().getKey();
		logger.info("SimpleJob says: " + jobKey + " executing at " + new Date());
		System.out.println("SimpleJob says: " + jobKey + " executing at "
				+ new Date());

		JobDetail lowestThread = JobBuilder.newJob(LowestThread.class)
				.withIdentity("LowestThread", "group2").build();
		JobDetail lowThread = JobBuilder.newJob(LowThread.class)
				.withIdentity("LowThread", "group2").build();
		JobDetail normalThread = JobBuilder.newJob(NormalThread.class)
				.withIdentity("NormalThread", "group2").build();
		JobDetail highThread = JobBuilder.newJob(HighThread.class)
				.withIdentity("HighThread", "group2").build();
		JobDetail highestThread = JobBuilder.newJob(HighestThread.class)
				.withIdentity("HighestThread", "group2").build();
		
		Trigger trigger1 = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger1", "group2")
				.withPriority(5)
				.startNow()
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(1)
								.repeatForever()).build();
		
		
		Trigger trigger2 = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger2", "group2")
				.withPriority(6)
				.startNow()
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(1)
								.repeatForever()).build();
		
		Trigger trigger3 = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger3", "group2")
				.withPriority(7)
				.startNow()
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(1)
								.repeatForever()).build();
		
		Trigger trigger4 = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger4", "group2")
				.withPriority(8)
				.startNow()
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(1)
								.repeatForever()).build();
		
		Trigger trigger5 = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger5", "group2")
				.withPriority(9)
				.startNow()
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(1)
								.repeatForever()).build();
		
		try {
			context.getScheduler().scheduleJob(lowestThread, trigger1);
			context.getScheduler().scheduleJob(lowThread, trigger2);
			context.getScheduler().scheduleJob(normalThread, trigger3);
			context.getScheduler().scheduleJob(highThread, trigger4);
			context.getScheduler().scheduleJob(highestThread, trigger5);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public class LowestThread implements Job {
		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {

			//while (true) {
				try {
					LinkedBlockingQueue<Msg> queue = msgPusher
							.getMessageFromQueue(MsgConstant.Priority.LOWEST);
					if (!queue.isEmpty()) {
						logger.debug("LowestThread queue.isEmpty()==>");
						while (!queue.isEmpty()) {

							Msg msg = queue.poll();
							logger.debug("send message==>" + msg.getId());
							msgPusher.pushMessageToClient(msg);

						}
					} else {
						try {
							// 睡眠时间
							logger.debug("LowestThread sleep 1s==>");
							Thread.sleep(1000);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			//}

		}
	}

	public class LowThread implements Job {
		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {

			//while (true) {
				try {
					LinkedBlockingQueue<Msg> queue = msgPusher
							.getMessageFromQueue(MsgConstant.Priority.LOW);
					if (!queue.isEmpty()) {
						logger.debug("LowThread queue.isEmpty()==>");
						while (!queue.isEmpty()) {

							Msg msg = queue.poll();
							logger.debug("send message==>" + msg.getId());
							msgPusher.pushMessageToClient(msg);

						}
					} else {
						try {
							// 睡眠时间
							logger.debug("LowThread sleep 1s==>");
							Thread.sleep(1000);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			// }

		}
	}

	public class NormalThread implements Job {
		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {

			//while (true) {
				try {
					LinkedBlockingQueue<Msg> queue = msgPusher
							.getMessageFromQueue(MsgConstant.Priority.NORMAL);
					if (!queue.isEmpty()) {
						logger.debug("NormalThread queue.isEmpty()==>");
						while (!queue.isEmpty()) {

							Msg msg = queue.poll();
							logger.debug("send message==>" + msg.getId());
							msgPusher.pushMessageToClient(msg);

						}
					} else {
						try {
							// 睡眠时间
							logger.debug("NormalThread sleep 1s==>");
							Thread.sleep(1000);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			//}

		}
	}

	public class HighThread implements Job {
		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {

			//while (true) {
				try {
					LinkedBlockingQueue<Msg> queue = msgPusher
							.getMessageFromQueue(MsgConstant.Priority.HIGH);
					if (!queue.isEmpty()) {
						logger.debug("HighThread queue.isEmpty()==>");
						while (!queue.isEmpty()) {

							Msg msg = queue.poll();
							logger.debug("send message==>" + msg.getId());
							msgPusher.pushMessageToClient(msg);

						}
					} else {
						try {
							// 睡眠时间
							logger.debug("HighThread sleep 1s==>");
							Thread.sleep(1000);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			//}

		}
	}

	public class HighestThread implements Job {
		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {

			///while (true) {
				try {
					LinkedBlockingQueue<Msg> queue = msgPusher
							.getMessageFromQueue(MsgConstant.Priority.HIGHEST);
					if (!queue.isEmpty()) {
						logger.debug("HighestThread queue.isEmpty()==>");
						while (!queue.isEmpty()) {

							Msg msg = queue.poll();
							logger.debug("send message==>" + msg.getId());
							msgPusher.pushMessageToClient(msg);

						}
					} else {
						try {
							// 睡眠时间
							logger.debug("HighestThread sleep 1s==>");
							Thread.sleep(1000);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			//}

		}
	}

}
