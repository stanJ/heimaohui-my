/**
 * 
 */
package com.xa3ti.blackcat.message.channel.sys;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;

import org.comet4j.core.CometConnection;
import org.json.JSONObject;



/**
 * @author nijie
 *
 */
public class Client {
	
	private AsyncContext context;  
    private JSONObject json;  
    private Long userId;  
    
    private CometConnection conn;
    
    private String frontend;
    
    

	public String getFrontend() {
		return frontend;
	}

	public void setFrontend(String frontend) {
		this.frontend = frontend;
	}

	public CometConnection getConn() {
		return conn;
	}

	public void setConn(CometConnection conn) {
		this.conn = conn;
	}

	public AsyncContext getContext() {  
        return context;  
    }  
  
    public JSONObject getJson() {  
        return json;  
    }  
  
    public Long getUserId() {  
        return userId;  
    }  
  
    public Client(AsyncContext context, JSONObject json, Long userId) {  
        this.context = context;  
        this.json = json;  
        this.userId = userId;  
    }  
    
    
    public Client(String frontend, Long userId){
    	this.frontend=frontend;
    	this.userId=userId;
    }
    
    public void send(JSONObject json) {  
        try {  
            ServletResponse rep = context.getResponse();  
            rep.setContentType("application/json;charset=UTF-8");  
            rep.setCharacterEncoding("UTF-8");  
            rep.getWriter().write(json.toString());  
        } catch (Exception e) {  
        }  
    }  
  

}
