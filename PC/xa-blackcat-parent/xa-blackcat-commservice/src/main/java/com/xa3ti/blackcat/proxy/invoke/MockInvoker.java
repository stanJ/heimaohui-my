/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import java.net.URL;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.Token;

/**
 * @author nijie
 *
 */
public class MockInvoker <T> implements Invoker {

    URL      url;
    //Class<T> type;
    boolean  hasException = false;

    
    public MockInvoker(){
    }
    
    
    public MockInvoker(URL url){
        this.url = url;
        //type = (Class<T>) DemoService.class;
    }

    public MockInvoker(URL url, boolean hasException){
        this.url = url;
        //type = (Class<T>) DemoService.class;
        this.hasException = hasException;
    }

    public Class<T> getInterface() {
        //return type;
    	return null;
    }

    public URL getUrl() {
        return url;
    }

    public boolean isAvailable() {
        return false;
    }

    public Result invoke(Invocation invocation) throws BusinessException {
        return null;
    }

    public void destroy() {
    }


	

	

}
