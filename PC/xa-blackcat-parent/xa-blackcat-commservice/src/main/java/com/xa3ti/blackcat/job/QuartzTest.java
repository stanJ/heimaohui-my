/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * @author nijie
 *
 */
public class QuartzTest {

	public static void main(String[] args) {

		try {
			
//			String paths[] = { "application-quartz.xml" };
//			ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
			
			
			
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();

			JobDetail job1 = JobBuilder.newJob(MsgFetchJob.class)
					.withIdentity("job1", "group1").build();

			JobDetail job2 = JobBuilder.newJob(MsgSenderJob.class)
					.withIdentity("job1", "group1").build();
			
			// Trigger the job to run now, and then repeat every 1 seconds
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(
							simpleSchedule().withIntervalInSeconds(1)
									.repeatForever()).build();
			
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job1, trigger);
			scheduler.scheduleJob(job2, trigger);

			JobDetail  job = JobBuilder.newJob(MockJob.class)
					.withIdentity("job2", "group1").build();

			trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("trigger2", "group1")
					.withSchedule(
							CronScheduleBuilder.cronSchedule("15 0/2 * * * ?"))
					.build();

			scheduler.scheduleJob(job, trigger);

			//scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
