package com.xa3ti.blackcat.base.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.xa3ti.blackcat.event.DistributeCenter;
import com.xa3ti.blackcat.event.LoginEvent;



public class MySimpleUrlAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {


		LoginEvent e=new LoginEvent();
		e.setAgent("");
		e.setAuthSuccess(false);
		e.setLoginTime(java.lang.System.currentTimeMillis());
		e.setUsername((String)request.getParameter("username"));
		e.setPassword((String)request.getParameter("password"));
		
		DistributeCenter.distributeEvent(e);
		
		super.onAuthenticationFailure(request, response, exception);
	}

}
