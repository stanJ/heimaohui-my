/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.exception.TokenException;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.business.util.TokenCenter;

/**
 * @author nijie
 *
 */
public class ConcurrentFilter extends AbstractFilter {

	private static List<String> ignoreConcurrentUrlList = new ArrayList<String>();

	/**
	 * @param sort
	 */
	ConcurrentFilter(int sort) {
		super(sort);
	}

	static {

		String igConcurrentPatterns = Settings.getInstance().getString(
				"ignore.concurrent.access.pattern");
		if (!StringUtils.isBlank(igConcurrentPatterns)) {
			String[] s = igConcurrentPatterns.split(",");
			for (String _s : s) {
				ignoreConcurrentUrlList.add(_s);
			}
		}

	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConcurrentFilter.class);

	private Long concurrentTimeLimit = 2 * 1000l;

	@Override
	public Result invoke(Invoker invoker, Invocation invocation)
			throws BusinessException {
		System.out.println("ConcurrentFilter invoked");

		if (invocation instanceof ServiceInvocation) {
			boolean ignoreConcurrent = false;
			ServiceInvocation si = (ServiceInvocation) invocation;
			
			Boolean ignoreTokenFlag=(Boolean)si.getAttachment(BaseConstant.ATTACTMENT_KEY_IGNORETOKEN_FLAG);
			if(ignoreTokenFlag!=null&&ignoreTokenFlag){
				return invoker.invoke(invocation);
			}
			
			HttpServletRequest rq = si.getRequest();
			String url = rq.getRequestURI();
			String currUrl = rq.getRequestURL().toString();
			String referer = rq.getHeader("Referer");

			Map<String,String[]> params = rq.getParameterMap();
			for (String s : ignoreConcurrentUrlList) {
				if (url.contains(s)) {
					ignoreConcurrent = true;
					break;
				}
			}
			//
			//ignoreConcurrent = ignoreConcurrentUrlList.contains(url);
			si.getAttachments().put(
					BaseConstant.ATTACTMENT_KEY_IGNORECONCURRENT_FLAG, ignoreConcurrent);
			
			if (!ignoreConcurrent) {
				String token = rq.getParameter("token");
				if (!StringUtils.isBlank(token)) {

					try {
						//Token t = TokenCenter.getToken(token);
						Long t1 = TokenCenter.getTokenURLPair(token, url);
						if (t1 == null) {
							TokenCenter.putTokenURLPair(token, url,
									java.lang.System.currentTimeMillis());
						} else {
							Long t2 = java.lang.System.currentTimeMillis();
							if (null != referer && referer.equals(currUrl)) {
								if (t2 - t1 <= concurrentTimeLimit) {
									LOGGER.debug("DEBUG===ERRORCODE:"
											+ TokenCenter.CODE_TOKEN_RESUBMITTED
											+ ",Token:" + token);
//								LOGGER.debug("DEBUG===token:" + t.getToken()
//										+ ",userId:" + t.getUserId()
//										+ ",agent:" + t.getAgent() + ",type:"
//										+ t.getType() + ",duration:"
//										+ t.getDuration() + ",removeTime:"
//										+ t.getRemoveTime()
//										+ ",lastActionTime:"
//										+ t.getLastActionTime()
//										+ ",generateTime:"
//										+ t.getGenerateTime());
									LOGGER.debug("DEBUG============================================================");

									throw new TokenException(
											TokenCenter.CODE_TOKEN_RESUBMITTED,
											"Token:" + token + ",URL:" + url
													+ "重复访问,访问拒绝!");
								} else {
									TokenCenter.putTokenURLPair(token, url,
											java.lang.System.currentTimeMillis());
								}
							}
						}
						
					} catch (TokenException e) {
						throw new BusinessException(e.getErrorCode(),
								e.getMessage());
					}

				}
			}
		}

		return invoker.invoke(invocation);
	}

}
