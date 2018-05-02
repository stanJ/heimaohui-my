/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.event;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.ContextUtil;
import com.xa3ti.blackcat.business.service.LogService;

/**
 * @author nijie
 *
 */
@Component
public class DistributeCenter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DistributeCenter.class);

	@Autowired
	LogService logService;
		
	ScheduledThreadPoolExecutor se = new ScheduledThreadPoolExecutor(5);
	private static ConcurrentLinkedQueue<Event> queue = new ConcurrentLinkedQueue<Event>();

	private EventProcessor eventProcessor = new EventProcessor("eventProcessor");

	@PostConstruct
	public void init() {
		//eventProcessor.start();
		se.scheduleAtFixedRate(eventProcessor, 0, 1, TimeUnit.SECONDS);
	}

	public static void distributeEvent(Event event) {
		queue.add(event);
	}

	public class EventProcessor extends TimerTask {
		private String name;

		EventProcessor(String name) {
			this.name = name;
		}

		@Override
		public void run() {
		

			//while (true) {

				Event e = queue.poll();
				while (e != null) {
					System.out.println("fetch a event src-->" + e.getSource());

					if (e instanceof ServiceEvent) {
						ServiceEvent se = (ServiceEvent) e;

						Boolean needLog = (Boolean) se.getInvocation()
								.getAttachment(BaseConstant.LOG.NEEDLOG);

						if (needLog!=null&&needLog) {
							try {
								logService.logEvent(e);
							} catch (BusinessException e1) {
								e1.printStackTrace();
							}
						}
					}else if(e instanceof LoginEvent){
						try {
							logService.logEvent(e);
						} catch (BusinessException e1) {
							e1.printStackTrace();
						}
					}
					e = queue.poll();
				}

			//}

		}

	}

}
