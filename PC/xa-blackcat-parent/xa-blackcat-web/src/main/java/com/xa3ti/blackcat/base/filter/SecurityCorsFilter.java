/**
 * 
 */
package com.xa3ti.blackcat.base.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xa3ti.blackcat.base.util.Settings;



/**
 * @author nijie
 *
 */
public class SecurityCorsFilter extends OncePerRequestFilter  {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SecurityCorsFilter.class);
	
    static final String ORIGIN = "Origin";
    static final String allowHosts=Settings.getInstance().getString("crossdomain.allowHosts");
    
    static String[] allowHostArray=null;
    static{
    	if(!StringUtil.isBlank(allowHosts)){
    		allowHostArray=allowHosts.split(",");
    	}
    }
   
	/* (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        System.out.println(request.getMethod());
        String remoteHost=request.getRemoteHost();
        LOGGER.info("remoteHost--->"+remoteHost);
        
        
        String origin=request.getHeader(ORIGIN);
        LOGGER.info("origin--->"+origin);
        
        
        boolean allowCross=false;
        if(allowHostArray!=null){
        	/*for(String host:allowHostArray){
        		if(remoteHost.equals(host)){
        			allowCross=true;
        		    break;	
        		}
        		
        		if(host.equals(origin)){
        			allowCross=true;
        		    break;	
        		}
        	}*/


				int cnum = allowHosts.indexOf(remoteHost);
				if(cnum != -1){
					allowCross=true;
				}
				if(null !=origin){
					int onum = allowHosts.indexOf(origin);
					if(onum !=-1){
						allowCross=true;
					}
				}

			/*
			if(allowHosts.indexOf(remoteHost) != -1 ){
				allowCross=true;
			}
			if(allowHosts.indexOf(origin) != -1){
				allowCross=true;
			}*/
        }
        
        
        
        
        if(allowCross){
	        //if (request.getHeader(ORIGIN) == null || request.getHeader(ORIGIN).equals("null")) {
	            response.setHeader("Access-Control-Allow-Origin", "*");
	            response.setHeader("Access-Control-Allow-Credentials", "true");
	            response.setHeader("Access-Control-Max-Age", "10");
	
	            String reqHead = request.getHeader("Access-Control-Request-Headers");
	
	            if (!StringUtils.isEmpty(reqHead)) {
	                response.addHeader("Access-Control-Allow-Headers", reqHead);
	            }
	       // }
        }
        
        
        if (request.getMethod().equals("OPTIONS")) {
            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
        filterChain.doFilter(request, response);
        }
	}
}