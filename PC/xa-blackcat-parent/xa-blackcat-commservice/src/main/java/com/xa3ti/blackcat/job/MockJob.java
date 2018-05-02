/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * @author nijie
 *
 */
public class MockJob implements Job {
	private static final Logger logger = Logger.getLogger(MockJob.class); 
	
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		logger.info("SimpleJob says: " + jobKey + " executing at " + new Date());
		System.out.println("SimpleJob says: " + jobKey + " executing at " + new Date());
	}  

}
