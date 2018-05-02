/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.job;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import com.xa3ti.blackcat.job.MsgSenderJob.HighThread;
import com.xa3ti.blackcat.job.MsgSenderJob.HighestThread;
import com.xa3ti.blackcat.job.MsgSenderJob.LowThread;
import com.xa3ti.blackcat.job.MsgSenderJob.LowestThread;
import com.xa3ti.blackcat.job.MsgSenderJob.NormalThread;

/**
 * @author nijie
 *
 */
@Component
public class MsgStarter {
	private static final Logger logger = Logger.getLogger(MsgStarter.class); 
	
	//@PostConstruct
	public static void start() {
		try {
			logger.info("MsgStarter started...");
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();

			JobDetail job1 = JobBuilder.newJob(MsgFetchJob.class)
					.withIdentity("MsgFetchJob", "group1").build();

//			JobDetail job2 = JobBuilder.newJob(MsgSenderJob.class)
//					.withIdentity("MsgSenderJob", "group1").build();
//			
//			scheduler.addJob(job2, true);
//			scheduler.triggerJob(job2.getKey());
			
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
			
			
			scheduler.scheduleJob(lowestThread, trigger1);
			scheduler.scheduleJob(lowThread, trigger2);
			scheduler.scheduleJob(normalThread, trigger3);
			scheduler.scheduleJob(highThread, trigger4);
			scheduler.scheduleJob(highestThread, trigger5);
			
			
			// Trigger the job to run now, and then repeat every 1 seconds
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(
							simpleSchedule().withIntervalInSeconds(1)
									.repeatForever()).build();
			scheduler.scheduleJob(job1, trigger);
			
			
			
			
//			Trigger trigger2 = TriggerBuilder
//					.newTrigger()
//					.withIdentity("trigger2", "group1")
//					.startNow().withSchedule(
//							simpleSchedule().withRepeatCount(1).withIntervalInSeconds(1000)).build();
//			scheduler.scheduleJob(job2, trigger2);
			//scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
