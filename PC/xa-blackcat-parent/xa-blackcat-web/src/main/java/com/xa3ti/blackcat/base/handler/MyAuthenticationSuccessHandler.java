package com.xa3ti.blackcat.base.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.exception.TokenException;
import com.xa3ti.blackcat.base.security.XaUserDetails;
import com.xa3ti.blackcat.base.service.XaCmsUserService;
import com.xa3ti.blackcat.base.util.JsonUtil;
import com.xa3ti.blackcat.business.entity.UserExtend;
import com.xa3ti.blackcat.business.repository.UserExtendRepository;
import com.xa3ti.blackcat.business.util.BizConstant;
import com.xa3ti.blackcat.event.DistributeCenter;
import com.xa3ti.blackcat.event.LoginEvent;
import com.xa3ti.blackcat.business.util.TokenCenter;
import com.xa3ti.blackcat.remote.security.TokenAssist;




public class MyAuthenticationSuccessHandler extends
SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	XaCmsUserService xaCmsUserService;
	
	@Autowired
	UserExtendRepository userExtendRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {


		if("true".equals(request.getHeader("X-Ajax-call"))){

			HashMap map = new HashMap();
			XaUserDetails user = (XaUserDetails) authentication.getPrincipal();

			try {
				XaCmsUser xaCmsUser = xaCmsUserService
						.findXaCmsUserByUserName(user.getUsername(),
								XaConstant.UserStatus.status_normal);
				xaCmsUser.setPassword("");
				map.put("user", xaCmsUser);

//				String openId = request.getParameter("openId");
//				if (!StringUtils.isBlank(openId)) {
//					userExtendService.bindOpenIdIfNotBinded(
//							xaCmsUser.getUserId(), openId,false);
//				}
//
				if(!BizConstant.SYS_SUPERADMIN_USERID.equals(xaCmsUser.getUserId())){
					UserExtend ue = userExtendRepository
							.findUserExtendByUserId(xaCmsUser.getUserId());
					map.put("userExtend", ue);
				}
				
				
				String agent=request.getHeader("Agent");
				
				try {
					map.put("token", TokenCenter.issueToken(xaCmsUser.getUserId(),agent,TokenAssist.getTokenTypeByRole(user.getRoleId())));
				} catch (TokenException e) {
					throw new BusinessException("issue token failure");
				}
				
			} catch (BusinessException e) {
				
				XaResult<String> xa = new XaResult<String>();
				xa.setCode(0);
				xa.setObject("登录错误");
				xa.setMessage(e.getMessage());

				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");

				response.getWriter().print(JsonUtil.toJson(xa));
				response.getWriter().flush();
				
				throw new ServletException(e.getMessage());
			}

			map.put("authentication", authentication);
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(JSON.toJSON(map));

			response.getWriter().flush();
		
		}
			
		else{	
			XaUserDetails user = (XaUserDetails) authentication.getPrincipal();
			try {
				String token = TokenCenter.issueToken(user.getUserId(),TokenCenter.AGENT_WEB, TokenAssist.getTokenTypeByRole(user.getRoleId()));
						request.getSession().setAttribute("token",token );
			} catch (TokenException e1) {
				throw new ServletException(e1.getMessage());
			}
	        
			
			LoginEvent e=new LoginEvent();
			e.setAgent("");
			e.setAuthSuccess(true);
			e.setLoginTime(java.lang.System.currentTimeMillis());
			e.setUsername(user.getUsername());
			
			DistributeCenter.distributeEvent(e);
			
	        super.onAuthenticationSuccess(request, response, authentication);
		}
	}
	
	
	
	

}
