/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.exception.TokenException;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.business.util.TokenCenter;

/**
 * @author nijie
 *
 */
public class SecurityFilter extends AbstractFilter {

	private static List<String> ignoreTokenUrlList = new ArrayList<String>();

	/**
	 * @param sort
	 */
	SecurityFilter(int sort) {
		super(sort);
		// TODO Auto-generated constructor stub
	}

	static {
		String igTokenPatterns = Settings.getInstance().getString(
				"ignore.token.pattern");
		if (!StringUtils.isBlank(igTokenPatterns)) {
			String[] s = igTokenPatterns.split(",");
			for (String _s : s) {
				ignoreTokenUrlList.add(_s);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.proxy.bean.Filter#invoke(com.xa3ti.blackcat.proxy.
	 * bean.Invoker, com.xa3ti.blackcat.proxy.bean.Invocation)
	 */
	@Override
	public Result invoke(Invoker invoker, Invocation invocation)
			throws BusinessException {
		System.out.println("SecurityFilter invoked");

		if (invocation instanceof ServiceInvocation) {
			boolean ignoreToken = false;
			ServiceInvocation si = (ServiceInvocation) invocation;
			HttpServletRequest rq = si.getRequest();
			String url = rq.getRequestURI();

			for (String s : ignoreTokenUrlList) {
				if (url.contains(s)) {
					ignoreToken = true;
					break;
				}
			}
			
			si.getAttachments().put(
					BaseConstant.ATTACTMENT_KEY_IGNORETOKEN_FLAG, ignoreToken);

			if (!ignoreToken) {
				String token = rq.getParameter("token");
				if (!StringUtils.isBlank(token)) {
					Token t;
					try {
						t = TokenCenter.getToken(token);
						si.getAttachments().put(
								BaseConstant.ATTACTMENT_KEY_TOKEN, t);
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
