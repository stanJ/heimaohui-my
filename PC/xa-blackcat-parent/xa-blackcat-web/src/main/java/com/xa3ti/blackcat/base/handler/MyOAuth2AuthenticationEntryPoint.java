package com.xa3ti.blackcat.base.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;


public class MyOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("dddddddddddddddddddddddddd");
	}

	

}
