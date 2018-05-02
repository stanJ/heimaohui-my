/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.remote.security;

import java.util.Date;
import java.util.Timer;

import com.xa3ti.blackcat.business.util.BizConstant;
import com.xa3ti.blackcat.business.util.TokenCenter;
import com.xa3ti.blackcat.business.util.TokenCenter.Task_TokenRecycle;

/**
 * @author nijie
 *
 */
public class TokenAssist {
	
	static{
		System.out.println("TokenCenter");
		Timer timer = new Timer("TokenCenter TokenCenter Timer");
		
		Date next = com.xa3ti.blackcat.base.util.DateUtil.addMinute(new Date(), 1);
		
		timer.schedule(new Task_TokenRecycle(), next,
				1000 * 30 * 1); // 每半分钟执行一次
		
	}
	

	public static Integer getTokenTypeByRole(Long roleId){
		if(BizConstant.Role.SUPERMANAGER.equals(roleId))
			return TokenCenter.TOKEN_TYPE_SUPERADMIN;
		else if(BizConstant.Role.MANAGER.equals(roleId))
			return TokenCenter.TOKEN_TYPE_ADMIN;
		else if(BizConstant.Role.GM.equals(roleId))
			return TokenCenter.TOKEN_TYPE_GM;
		else if(BizConstant.Role.USER.equals(roleId))
			return TokenCenter.TOKEN_TYPE_LOGINUSER;
		else 
			return null;
		
	}
}
