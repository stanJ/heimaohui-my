package com.xa3ti.blackcat.base.filter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.exception.LoginAdminException;
import com.xa3ti.blackcat.base.util.Captcha;

/**
 * 登录用户验证处理类
 * @author zj
 *
 */
public class MyUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		// TODO Auto-generated method stub

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        String uuid=request.getParameter("uuid");
        if(!StringUtil.isBlank(uuid)){
        	Date d=Captcha.getVeri(uuid);
        	if(d==null||java.lang.System.currentTimeMillis()>d.getTime())
        		throw new LoginAdminException("验证码错误或已经过期");
        	else{
        		Captcha.removeCaptcha(uuid);
        		Captcha.removeVeri(uuid);
        	}
        }
        
        
        username = username.trim();
        String requestKey = request.getParameter(XaConstant.Login.SPRING_REQUEST_KEY);
        if(StringUtils.isBlank(requestKey)||StringUtils.isEmpty(requestKey)){
        	requestKey = String.valueOf(XaConstant.Login.REQUEST_TYPE_MOBILE);
        }
       // UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username+":"+requestKey, password);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
	}

}
