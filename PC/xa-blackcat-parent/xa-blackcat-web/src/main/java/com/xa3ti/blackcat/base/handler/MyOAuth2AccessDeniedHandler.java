package com.xa3ti.blackcat.base.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

public class MyOAuth2AccessDeniedHandler extends OAuth2AccessDeniedHandler  {

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response, AccessDeniedException authException)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.handle(request, response, authException);
	}

}
