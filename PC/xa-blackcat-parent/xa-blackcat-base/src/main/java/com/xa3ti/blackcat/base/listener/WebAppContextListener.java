/**
 * 
 */
package com.xa3ti.blackcat.base.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xa3ti.blackcat.base.util.ContextUtil;

/**
 * @author nijie
 *
 */
public class WebAppContextListener implements ServletContextListener{

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ContextUtil.setContext(WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()));
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
