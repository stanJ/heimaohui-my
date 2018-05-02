package com.xa3ti.blackcat.business.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 权限验证之后的filter类
 * @author zj
 *
 */
public class WebHtmlFilter implements Filter{

	/**
	 * filter主方法
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(req, resp);
	}

	/**
	 * init
	 */
	public void init(FilterConfig cfg) throws ServletException {
	}
	
	/**
	 * destroy
	 */
	public void destroy() {
	}
}
