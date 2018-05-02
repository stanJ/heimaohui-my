/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke;

import com.xa3ti.blackcat.proxy.invoke.filter.Filter;

/**
 * @author nijie
 *
 */
public interface InvokerObserver {
	
	
    public void addInvoker(Invoker invoker);
    
    public void removeInvoker(Invoker invoker);
    
    
    public void notify(Filter theFirstFilter);

}
