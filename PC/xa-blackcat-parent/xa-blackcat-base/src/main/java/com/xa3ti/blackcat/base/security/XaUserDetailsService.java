package com.xa3ti.blackcat.base.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.exception.LoginLockException;
import com.xa3ti.blackcat.base.repository.XaCmsUserRepository;
import com.xa3ti.blackcat.base.util.DateProcessUtil;


/**
 * 登录权限验证service
 * @author zj
 *
 */
@Service("MsUserDetailsService")
public class XaUserDetailsService implements UserDetailsService {
    protected static final String ROLE_PREFIX = "ROLE_";
    protected static final GrantedAuthority DEFAULT_USER_ROLE = new SimpleGrantedAuthority(ROLE_PREFIX + "USER");

	@Autowired
	XaCmsUserRepository xaCmsUserRepository;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		XaUserDetails msUserDetails = new XaUserDetails();
		try {
			XaCmsUser user;
			List<XaCmsUser> userList = xaCmsUserRepository.findByUserName(new String(username.getBytes("ISO-8859-1"),"UTF-8"));
			if(userList.size() > 0 && userList.get(0).getStatus() == XaConstant.UserStatus.status_lock){
				throw new LoginLockException("您输入的账号已被锁定");
			}
			/*if(userList.size() > 0 && userList.get(0).getIsAdmin() != 1){
				throw new LoginAdminException("您的账号不是管理员");
			}*/
			if(userList.size() > 0 && userList.get(0) != null){
				user = userList.get(0);
				user.setLastLoginDate(DateProcessUtil.getToday(DateProcessUtil.YYYYMMDDHHMMSS));
				xaCmsUserRepository.save(user);
				msUserDetails.setUsername(user.getUserName());
				msUserDetails.setPassword(user.getPassword());
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
				authorities.add(authority);
				//设置用户oauth通过token访问的权限
				authorities.add(DEFAULT_USER_ROLE);
				authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "UNITY"));
				authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "MOBILE"));
				msUserDetails.setAuthorities(authorities);
				
				
				//msUserDetails.setToken(TokenCenter.issueToken(user.getUserId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msUserDetails;
	}
}
