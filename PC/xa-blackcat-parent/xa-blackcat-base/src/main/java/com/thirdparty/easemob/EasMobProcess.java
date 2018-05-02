package com.thirdparty.easemob;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.json.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class EasMobProcess {

	private static Logger log = Logger.getLogger("EasMobProcess");
	private static String tokenKey = "access_token";;
	
	public static String getEasmobToken()
	{		
		String token = (String) MemcachedUtil.get(tokenKey);	
		
		if( token != null && !token.equals(""))
		{
			token = MemcachedUtil.get(tokenKey).toString();
		}
		else
		{
			try
			{
				Properties config = new Properties();
				config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
			
				String easmobCreateGroupUrl = config.get("easmob_url").toString() + "/token";
				//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
				
				Map<String,String> httpHeads = new HashMap<String,String>();
				httpHeads.put("Content-Type", "application/json;charset=UTF-8");
				httpHeads.put("Accept", "application/json");
				
				String strParam = "{\"grant_type\": \"client_credentials\","
						+ "\"client_id\": \"YXA6iRk_YK0REeSroReMPJJkIQ\","
						+ "\"client_secret\": \"YXA6QtWzXGX2LrM9pR9nDBR4r7TvvfE\"}";
				
				String strResult = URLConnectionHelper.sendPost(easmobCreateGroupUrl, httpHeads, strParam);
	
				if (strResult != null && !strResult.equals(""))
				{				
					JSONObject objJson = new JSONObject(strResult);
					//JSONObject objData = objJson.getJSONObject("data");
												
					token = objJson.getString("access_token");
				}	
				
				MemcachedUtil.add(tokenKey, token, 2000000);
				
			}catch (IOException e) {
	        	log.error("config file exception！", e);
	        	return null;
			}catch (JSONException ex) {
				log.error("result value exception！", ex);
	        	return null;
			}
		}
		
		return "Bearer "+ token;
	}
	
	public static Map<String,String> registEasMobSimpleUser(String easmobAccount,String easmobPassword)
	{		
		try
		{
			Properties config = new Properties();
			config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
		
			String easmobRegistUserUrl = config.get("easmob_url").toString() + "/users";
			//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
			String easmobAuthorizationToken = getEasmobToken();
			
			Map<String,String> httpHeads = new HashMap<String,String>();
			httpHeads.put("Content-Type", "application/json");
			httpHeads.put("Accept", "application/json");
			httpHeads.put("Authorization", easmobAuthorizationToken);
			
			String strParam = "{\"username\":\"" + easmobAccount + "\",\"password\":\"" + easmobPassword + "\"}";
			String strResult = URLConnectionHelper.sendPost(easmobRegistUserUrl, httpHeads, strParam);
			
			Map<String,String> mapResult = new HashMap<String,String>();
			
			if (strResult != null && !strResult.equals(""))
			{				
				JSONObject objJson = new JSONObject(strResult);
				JSONArray lstEntities = objJson.getJSONArray("entities");
				
				JSONObject objEntity = lstEntities.getJSONObject(0);
				
				mapResult.put("uuid", objEntity.getString("uuid"));
				mapResult.put("type", objEntity.getString("type"));
				mapResult.put("created", String.valueOf(objEntity.getDouble("created")));
				mapResult.put("modified", String.valueOf(objEntity.getDouble("modified")));
				mapResult.put("username", objEntity.getString("username"));
				mapResult.put("password", easmobPassword);
				mapResult.put("activated", String.valueOf(objEntity.getBoolean("activated")));				
			}
			
			return mapResult;
			
		}catch (IOException e) {
        	log.error("config file exception！", e);
        	return null;
		}catch (JSONException ex) {
			log.error("result value exception！", ex);
        	return null;
		}
	}
	
	public static String createChatGroup(String groupName,String groupDesc,String ownerEasMobAccount)
	{		
		try
		{
			Properties config = new Properties();
			config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
		
			String easmobCreateGroupUrl = config.get("easmob_url").toString() + "/chatgroups";
			//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
			String easmobAuthorizationToken = getEasmobToken();
			
			Map<String,String> httpHeads = new HashMap<String,String>();
			httpHeads.put("Content-Type", "application/json;charset=UTF-8");
			httpHeads.put("Accept", "application/json");
			httpHeads.put("Authorization", easmobAuthorizationToken);
			
			String strParam = "{\"groupname\":\"" + groupName + "\",\"desc\":\"" + groupDesc 
					+ "\",\"public\":true,\"maxusers\":300,\"approval\":true,\"owner\":\"" 
					+ ownerEasMobAccount + "\"}";
			String strResult = URLConnectionHelper.sendPost(easmobCreateGroupUrl, httpHeads, strParam);
					
			if (strResult != null && !strResult.equals(""))
			{				
				JSONObject objJson = new JSONObject(strResult);
				JSONObject objData = objJson.getJSONObject("data");
											
				return objData.getString("groupid");
			}			
		}catch (IOException e) {
        	log.error("config file exception！", e);
        	return null;
		}catch (JSONException ex) {
			log.error("result value exception！", ex);
        	return null;
		}
		
		return null;
	}
	
	public String toUnicode(String str){
        char[]arChar=str.toCharArray();
        int iValue=0;
        String uStr="";
        for(int i=0;i<arChar.length;i++){
            iValue=(int)str.charAt(i);           
            if(iValue<=256){
              // uStr+="& "+Integer.toHexString(iValue)+";";
                uStr+="\\"+Integer.toHexString(iValue);
            }else{
              // uStr+="&#x"+Integer.toHexString(iValue)+";";
                uStr+="\\u"+Integer.toHexString(iValue);
            }
        }
        return uStr;
    }

	
	public static String addUserInChatGroup(String easmobGroupID,String easmobAccount)
	{		
		try
		{
			Properties config = new Properties();
			config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
		
			String easmobCreateGroupUrl = config.get("easmob_url").toString() + "/chatgroups/" + easmobGroupID + "/users/" + easmobAccount;
			//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
			String easmobAuthorizationToken = getEasmobToken();
			
			Map<String,String> httpHeads = new HashMap<String,String>();
			httpHeads.put("Content-Type", "application/json");
			httpHeads.put("Accept", "application/json");
			httpHeads.put("Authorization", easmobAuthorizationToken);
			
			String strParam = "";
			String strResult = URLConnectionHelper.sendPost(easmobCreateGroupUrl, httpHeads, strParam);
					
			if (strResult != null && !strResult.equals(""))
			{				
				JSONObject objJson = new JSONObject(strResult);
				JSONObject objData = objJson.getJSONObject("data");
											
				return objData.getString("result");
			}			
		}catch (IOException e) {
        	log.error("config file exception！", e);
        	return null;
		}catch (JSONException ex) {
			log.error("result value exception！", ex);
        	return null;
		}
		
		return null;
	}
	
	public static String deleteUserInChatGroup(String easmobGroupID,String easmobAccount)
	{		
		try
		{
			Properties config = new Properties();
			config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
		
			String easmobCreateGroupUrl = config.get("easmob_url").toString() + "/chatgroups/" + easmobGroupID + "/users/" + easmobAccount;
			//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
			
			String easmobAuthorizationToken = getEasmobToken();
			
			Map<String,String> httpHeads = new HashMap<String,String>();
			httpHeads.put("Content-Type", "application/json");
			httpHeads.put("Accept", "application/json");
			httpHeads.put("Authorization", easmobAuthorizationToken);
						
			String strResult = URLConnectionHelper.sendDelete(easmobCreateGroupUrl, httpHeads);
					
			if (strResult != null && !strResult.equals(""))
			{				
				JSONObject objJson = new JSONObject(strResult);
				JSONObject objData = objJson.getJSONObject("data");
											
				return objData.getString("result");
			}			
		}catch (IOException e) {
        	log.error("config file exception！", e);
        	return null;
		}catch (JSONException ex) {
			log.error("result value exception！", ex);
        	return null;
		}
		
		return null;
	}
	
	public static String deleteChatGroup(String easmobGroupID)
	{		
		try
		{
			Properties config = new Properties();
			config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
		
			String easmobCreateGroupUrl = config.get("easmob_url").toString() + "/chatgroups/" + easmobGroupID;
			//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
			
			String easmobAuthorizationToken = getEasmobToken();
			
			Map<String,String> httpHeads = new HashMap<String,String>();
			httpHeads.put("Content-Type", "application/json");
			httpHeads.put("Accept", "application/json");
			httpHeads.put("Authorization", easmobAuthorizationToken);
						
			String strResult = URLConnectionHelper.sendDelete(easmobCreateGroupUrl, httpHeads);
					
			if (strResult != null && !strResult.equals(""))
			{				
				JSONObject objJson = new JSONObject(strResult);
				JSONObject objData = objJson.getJSONObject("data");
											
				return objData.getString("success");
			}			
		}catch (IOException e) {
        	log.error("config file exception！", e);
        	return null;
		}catch (JSONException ex) {
			log.error("result value exception！", ex);
        	return null;
		}
		
		return null;
	}
	
	public static String changePassword(String easmobAccount,String easmobPassword)
	{		
		try
		{
			Properties config = new Properties();
			config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
		
			String easmobCreateGroupUrl = config.get("easmob_url").toString() + "/users/" + easmobAccount + "/password";
			//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
			String easmobAuthorizationToken = getEasmobToken();
			
			Map<String,String> httpHeads = new HashMap<String,String>();
			httpHeads.put("Content-Type", "application/json");
			httpHeads.put("Accept", "application/json");
			httpHeads.put("Authorization", easmobAuthorizationToken);
						
			String strParam = "{\"newpassword\":\"" + easmobPassword + "\"}";
			
			String strResult = URLConnectionHelper.sendPut(easmobCreateGroupUrl, httpHeads,strParam);
					
			return strResult;
			
		}catch (IOException e) {
        	log.error("config file exception！", e);
        	return null;
		}		
	}
	
	public static String sendMsg(String easmobAccount, String sendUser, String msg, String ext)
	{		
		try
		{
			Properties config = new Properties();
			config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
		
			String easmobCreateGroupUrl = config.get("easmob_url").toString() + "/messages";
			//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
			String easmobAuthorizationToken = getEasmobToken();
			String small_secretary_system = config.get("small_secretary_system").toString().toLowerCase();
			if(StringUtils.isNotEmpty(sendUser)){
				small_secretary_system = sendUser;
			}
			Map<String,String> httpHeads = new HashMap<String,String>();
			httpHeads.put("Content-Type", "application/json;charset=UTF-8");
			httpHeads.put("Accept", "application/json");
			httpHeads.put("Authorization", easmobAuthorizationToken);
						
			String strParam = "{"
								+ "\"target_type\" : \"users\", "
								+ "\"target\" : ["+easmobAccount+"], "
								+ "\"msg\" : {\"type\" : \"txt\", \"msg\" : \""+msg+"\"},"
								+ "\"from\" : \""+small_secretary_system+"\","
								+ ext
							+ "}";
			
			String strResult = URLConnectionHelper.sendPost(easmobCreateGroupUrl, httpHeads,strParam);
					
			return strResult;
			
		}catch (IOException e) {
        	log.error("config file exception！", e);
        	return null;
		}		
	}
	
	
//	public static String chatMessages(String easmobAccount, String msg)
//	{		
//		try
//		{
//			Properties config = new Properties();
//			config.load(MemcachedUtil.class.getResourceAsStream("/config.properties"));
//		
//			String easmobCreateGroupUrl = config.get("easmob_url").toString() + "/chatMessages";
//			//String easmobAuthorizationToken = config.get("easmob_access_token").toString();
//			String easmobAuthorizationToken = getEasmobToken();
//			
//			easmobCreateGroupUrl += "?ql=select+*+where+timestamp1430928000000";
//			
//			Map<String,String> httpHeads = new HashMap<String,String>();
//			httpHeads.put("Content-Type", "application/json");
//			httpHeads.put("Accept", "application/json");
//			httpHeads.put("Authorization", easmobAuthorizationToken);
////						
////			String strParam = "{"
////								+ "\"target_type\" : \"users\", "
////								+ "\"target\" : [\""+easmobAccount+"\"], "
////								+ "\"msg\" : {\"type\" : \"txt\", \"msg\" : \""+msg+"\"},"
////								+ "\"from\" : \"smallsecretarysystem\""
////							+ "}";
//			
//			String strResult = URLConnectionHelper.sendGet(easmobCreateGroupUrl, httpHeads, "");
//					
//			return strResult;
//			
//		}catch (IOException e) {
//        	log.error("config file exception！", e);
//        	return null;
//		}		
//	}
	
	public static String getEasMobAccount(String userID)
	{
		return EncryptionHelp.MD5(UUID.randomUUID().toString()).toUpperCase();
		//return EncryptionHelp.MD5("B5E" + userID + "test").toUpperCase();
	}
	
	public static String getEasMobPassword(String userID)
	{
		return EncryptionHelp.MD5(userID).toUpperCase();
		//return password;
	}
}
