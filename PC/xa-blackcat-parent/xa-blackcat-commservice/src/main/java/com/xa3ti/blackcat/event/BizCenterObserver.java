/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.event;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.InvokerObserver;
import com.xa3ti.blackcat.proxy.invoke.filter.Filter;

/**
 * @author nijie
 *
 */
public class BizCenterObserver implements InvokerObserver {

	private static LinkedList<Invoker> invokers=new LinkedList<Invoker>();
	
	private static ReentrantLock lock;
	
	
	@Override
	public void addInvoker(Invoker invoker) {
		lock.lock();
        try {
        	if(!invokers.contains(invoker)){
        		invokers.add(invoker);
        	}
        } finally {
            lock.unlock();
        }
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.InvokerObserver#removeInvoker(com.xa3ti.blackcat.proxy.bean.Invoker)
	 */
	@Override
	public void removeInvoker(Invoker invoker) {
		lock.lock();
        try {
        	if(invokers.contains(invoker)){
        		invokers.remove(invoker);
        	}
        } finally {
            lock.unlock();
        }
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.proxy.bean.InvokerObserver#notify(com.xa3ti.blackcat.proxy.bean.Filter)
	 */
	@Override
	public void notify(Filter theFirstFilter) {
		
	}

}
