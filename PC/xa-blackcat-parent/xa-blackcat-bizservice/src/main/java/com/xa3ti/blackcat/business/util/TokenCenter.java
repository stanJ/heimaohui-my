/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.business.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xa3ti.blackcat.base.exception.TokenException;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.base.util.TokenUtil;



/**
 * @author nijie
 *
 */
public class TokenCenter {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenCenter.class);
	
	public static final String MYTOKENTAG="MyToken";
	
	public static final Integer TIMEOUT=30*1000*60;//30分钟超时
	
	public static final Integer FOREVER=-1;//30分钟超时
	
	public static final Integer ANNOYMOUS_TIMEOUT=24*60*1000*60;//1天
	
	public static final Integer TOKEN_TYPE_LOGINUSER=1;
	public static final Integer TOKEN_TYPE_ANONYMOUSUSER=2;
	
	public static final Integer TOKEN_TYPE_SUPERADMIN=3;
	public static final Integer TOKEN_TYPE_ADMIN=4;
	public static final Integer TOKEN_TYPE_GM=5;
	
	
	public static final String AGENT_IOS="ios";
	public static final String AGENT_ANDROID="android";
	public static final String AGENT_WEB="web";
	public static final String AGENT_WECHAT="wechat";
	
	
	public static final Integer CODE_NO_TOKEN=101; //没有令牌
	public static final Integer CODE_TOKEN_INVALID=102;//令牌无效
	public static final Integer CODE_TOKEN_EXPIRED=103;//令牌超时
	public static final Integer CODE_TOKEN_RESUBMITTED=104;//重复提交
	public static final Integer CODE_ANNOYMOUS_ACCESS_DENIED=105;//匿名方式访问拒绝
	public static final Integer CODE_DOCTOR_ACCESS_DENIED=108;//医生方式访问拒绝
	
	public static final Integer CODE_ANNOYMOUS_TOKEN_USEDUP=106;//匿名令牌用完
	
	public static final Integer CODE_ANNOYMOUS_TOKEN_APPLY_DENIED=107;//匿名令牌申请拒绝
	
	public static final Integer CODE_SCOPE_ACCESS_DENIED=999;//访问数据不符合业务规则
	
	
	
	public static Long anonymousCounter=   10000000000l;//100亿 不会和登录令牌混淆
	
	public static Long anonymousCounterMax=999999999999999990l;//100亿 不会和登录令牌混淆
	
	
	
	private static ConcurrentMap<String, Token> tokenMap = new ConcurrentHashMap<String, Token>();
	
	//private static ConcurrentMap<TokenURLPair, Long> tokenURLMap = new ConcurrentHashMap<TokenURLPair, Long>();
	
	private static ConcurrentMap<String, ConcurrentMap<TokenURLPair, Long>> tokenURLMap = new ConcurrentHashMap<String, ConcurrentMap<TokenURLPair, Long>>();
	
	private static ConcurrentMap<String, Token> diedTokenMap = new ConcurrentHashMap<String, Token>();
	
	public static class DoctorToken extends Token{
		private Long clinicId;
		private Long doctorId;
		private Long employeeId;
		public Long getClinicId() {
			return clinicId;
		}
		public void setClinicId(Long clinicId) {
			this.clinicId = clinicId;
		}
		public Long getDoctorId() {
			return doctorId;
		}
		public void setDoctorId(Long doctorId) {
			this.doctorId = doctorId;
		}
		public Long getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(Long employeeId) {
			this.employeeId = employeeId;
		}
		
		
		
	}
	
	public static class TokenURLPair{
		String token;
		String url;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		TokenURLPair(String token,String url){
			this.token=token;
			this.url=url;
		}
		
		@Override
		public int hashCode(){
			int result = 17;  
	        result = result * 31 + token.hashCode();  
	        result = result * 31 + url.hashCode();  
	        return result;
	        
			
		}
		
		@Override
		 public boolean equals(Object obj) {
			if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;

	        TokenURLPair _o = (TokenURLPair) obj;
		        if(token.equals(_o.getToken())&&url.equals(_o.getUrl()))
		        	return true;
		        else 
		        	return false;
		}
		
	}
	
	
	public static Long getTokenURLPair(String token,String url) throws TokenException {
		
		ConcurrentMap<TokenURLPair,Long> tp=tokenURLMap.get(token);
		if(tp!=null){
			return tp.get(new TokenCenter.TokenURLPair(token,url));
		}else{
			return null;
		}
		
		
	}
	
	public static void putTokenURLPair(String token,String url,Long time) throws TokenException {
		
		ConcurrentMap<TokenURLPair,Long> tp=tokenURLMap.get(token);
		if(tp!=null){
			 tp.put(new TokenCenter.TokenURLPair(token,url),time);
		}else{
		    ConcurrentMap<TokenURLPair,Long> map=new ConcurrentHashMap<TokenURLPair,Long>();
			map.put(new TokenCenter.TokenURLPair(token,url),time);
			tokenURLMap.put(token,map);
		}
	}
	


	public static Token getToken(String token) throws TokenException {
		Token t = tokenMap.get(token);
		if (t == null){
			LOGGER.debug("DEBUG===ERRORCODE:"+TokenCenter.CODE_TOKEN_INVALID+",Token:"+token);
			LOGGER.debug("DEBUG============================================================");
			throw new TokenException(CODE_TOKEN_INVALID,"令牌" + token + "无效!");
		}
		else{
			Long lastActionTime=t.getLastActionTime();
			Long dur=java.lang.System.currentTimeMillis()-lastActionTime;
			
			if(!FOREVER.equals(t.getDuration())&&dur>t.getDuration()&&(!AGENT_IOS.equals(t.getAgent())
					&&!AGENT_ANDROID.equals(t.getAgent())
					&&!AGENT_WECHAT.equals(t.getAgent()))){
				//tokenMap.remove(t);
				removeTokenAndTokenUrl(token);
				LOGGER.debug("DEBUG===ERRORCODE:"+TokenCenter.CODE_TOKEN_EXPIRED+",Token:"+token);
				LOGGER.debug("token:"+t.getToken()+",userId:"+t.getUserId()+",agent:"+t.getAgent()+",type:"+t.getType()+",duration:"+t.getDuration()+",removeTime:"+t.getRemoveTime()+",lastActionTime:"+t.getLastActionTime()+",generateTime:"+t.getGenerateTime());
				LOGGER.debug("DEBUG============================================================");
				throw new TokenException(CODE_TOKEN_EXPIRED,"令牌" + token + "超时!");
			}else{
				t.setLastActionTime(java.lang.System.currentTimeMillis());
			}
			return t;
		}

	}
	
	
	public static void putToken(String token,Token t) throws TokenException {
	     tokenMap.put(token,t);
	}
	
	public static void removeTokenAndTokenUrl(String token) throws TokenException {
		 LOGGER.debug("DEBUG===removeTokenAndTokenUrl-->"+token);
		 Token t=tokenMap.get(token);
	     
	     t.setStatus(0);
	     t.setRemoveTime(java.lang.System.currentTimeMillis());
	     diedTokenMap.put(token, t);
	     
	     tokenURLMap.remove(token);
	     tokenMap.remove(token);
	     
	     
	}

	public static String issueToken(String userId,String agent,Integer tokenType) throws TokenException {
		try {
			Token t = new Token();
			t.setType(tokenType);
			t.setGenerateTime(java.lang.System.currentTimeMillis());
			t.setLastActionTime(java.lang.System.currentTimeMillis());
			t.setDuration(TIMEOUT); 
			t.setUserId(userId);
			t.setAgent(agent);
			String token = TokenUtil.generateToken(userId);
			t.setToken(token);
			tokenMap.put(token, t);
			return token;
		} catch (Exception e) {
			throw new TokenException(e.getMessage());
		}
	}
	
	public static  List<Token> getTokenForDebug(String token) {
	    List<Token> list=new ArrayList<Token>();
	    if(!StringUtil.isBlank(token)){
			Token surviveToken= tokenMap.get(token);
			if(surviveToken!=null){
				list.add(surviveToken);
			}
			Token diedToken= diedTokenMap.get(token);
			if(diedToken!=null){
				list.add(diedToken);
			}
	    }else{
	    	list.addAll(tokenMap.values());
	    	list.addAll(diedTokenMap.values());
	    }
		
		return list;
	}
	
	
	
	public static String issueAnonymousToken(String agent) throws TokenException {
		try {
			Token t = new Token();
			t.setType(TOKEN_TYPE_ANONYMOUSUSER);
			t.setGenerateTime(java.lang.System.currentTimeMillis());
			t.setLastActionTime(java.lang.System.currentTimeMillis());
			t.setDuration(ANNOYMOUS_TIMEOUT); 
			//t.setUserId(userId);
			t.setAgent(agent);
			
			synchronized(anonymousCounter){//竞争式拿令牌
				++anonymousCounter;
			}
			
			if(anonymousCounter>anonymousCounterMax)
				throw new TokenException(CODE_ANNOYMOUS_TOKEN_USEDUP,"匿名令牌空间已全部用完");
			
			t.setUserId(String.valueOf(anonymousCounter));
			String token = TokenUtil.generateToken(String.valueOf(anonymousCounter));
			t.setToken(token);
			tokenMap.put(token, t);
			return token;
		} catch (Exception e) {
			throw new TokenException(e.getMessage());
		}
	}
	
	
	
	
	
	public static class Task_TokenRecycle extends TimerTask {

		@Override
		public void run() {
			System.out.println("DEBUG===Blackcat Task_TokenRecycle--run-->");
			Set<String> set=tokenMap.keySet();
			for(String t:set){
				//String t=(String)it.next();
				try{
				Token token=getToken(t);
				if(token!=null){
					Long lastActionTime=token.getLastActionTime();
					Long dur=java.lang.System.currentTimeMillis()-lastActionTime;
					
					if(!FOREVER.equals(token.getDuration())&&dur>token.getDuration()&&(!AGENT_IOS.equals(token.getAgent())
							&&!AGENT_ANDROID.equals(token.getAgent())
							&&!AGENT_WECHAT.equals(token.getAgent()))){
						//tokenMap.remove(t);
						LOGGER.debug("DEBUG===token:"+token.getToken()+",userId:"+token.getUserId()+",agent:"+token.getAgent()+",type:"+token.getType()+",duration:"+token.getDuration()+",removeTime:"+token.getRemoveTime()+",lastActionTime:"+token.getLastActionTime()+",generateTime:"+token.getGenerateTime());
						LOGGER.debug("DEBUG============================================================");
						
						removeTokenAndTokenUrl(t);
					}
					
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
			//彻底删除 被移除的token
			Set<String> set2=diedTokenMap.keySet();
			for(String t:set2){
				//String t=(String)it2.next();
				try{
				Token token=diedTokenMap.get(t);
				if(token!=null){
					Long removeTime=token.getRemoveTime();
					Long dur=java.lang.System.currentTimeMillis()-removeTime;
					
					if(dur>10*60*1000){
						diedTokenMap.remove(token);
					}
					
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String[] args){
		
		try {
			TokenCenter.putTokenURLPair("1234", "asfdsfadsfsafd", 1213l);
			TokenCenter.putTokenURLPair("1234", "asfdsfadsfsafd", 23432l);
			TokenCenter.putTokenURLPair("1234", "asfdsfadsfsafd", 34534l);
			TokenCenter.putTokenURLPair("1234", "asfdsfadsfsafd", 5456l);
			TokenCenter.putTokenURLPair("1234", "asfdsfadsfsafd", 78797l);
			
			System.out.println(tokenURLMap.size());
			System.out.println(TokenCenter.getTokenURLPair("1234", "asfdsfadsfsafd"));
			
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}