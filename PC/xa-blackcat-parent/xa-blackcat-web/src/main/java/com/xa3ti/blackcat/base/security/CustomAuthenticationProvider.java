/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.exception.LoginAdminException;
import com.xa3ti.blackcat.base.exception.LoginLockException;
import com.xa3ti.blackcat.base.repository.XaCmsUserRepository;
import com.xa3ti.blackcat.base.util.AESCryptography;
import com.xa3ti.blackcat.base.util.MD5Util;

/**
 * @author nijie
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	XaCmsUserRepository xaCmsUserRepository;

	@Autowired
	UserDetailsService MsUserDetailsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		if (!authentication.isAuthenticated()) {
			String name = authentication.getName();
			String password = authentication.getCredentials().toString();

			AESCryptography crypto = new AESCryptography();

			try {

				List<XaCmsUser> userList = xaCmsUserRepository
						.findByUserName(name);
				if (userList.size() > 0
						&& userList.get(0).getStatus() == XaConstant.UserStatus.status_lock) {
					throw new LoginLockException("您输入的账号已被锁定");
				}
				if (userList.size() > 0 && userList.get(0) != null) {
					XaCmsUser user = userList.get(0);
					String md5Password = new String(crypto.decrypt(crypto.String2byte(user.getPassword())));
					if (MD5Util.getMD5String(password).equals(md5Password)) {

						// use the credentials
						// and authenticate against the third-party system

						XaUserDetails xaUserDetails = (XaUserDetails) MsUserDetailsService
								.loadUserByUsername(user.getUserName());

						UsernamePasswordAuthenticationToken authetincation = new UsernamePasswordAuthenticationToken(
								xaUserDetails, name,xaUserDetails.getAuthorities());

						
						return authetincation;
					} else {
						throw new LoginAdminException("用户名密码错误");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			throw new LoginAdminException("用户名密码错误");
			
		} else
			return authentication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.AuthenticationProvider#supports
	 * (java.lang.Class)
	 */
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}