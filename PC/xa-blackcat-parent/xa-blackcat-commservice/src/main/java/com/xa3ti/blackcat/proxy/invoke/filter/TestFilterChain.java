/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;



import org.junit.Test;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.proxy.invoke.MockInvocation;
import com.xa3ti.blackcat.proxy.invoke.MockInvoker;

/**
 * @author nijie
 *
 */
public class TestFilterChain {

	@Test
	public void test() {
		ConcurrentFilter cf=new ConcurrentFilter(1);
		SecurityFilter sf=new SecurityFilter(2);
		ParamaterRefactor pr=new ParamaterRefactor(3);
		ProtocolFilter pf=new ProtocolFilter(4);
		
		FilterRegister.registerFilter(cf);
		FilterRegister.registerFilter(sf);
		FilterRegister.registerFilter(pr);
		FilterRegister.registerFilter(pf);
		
		
		try {
			FilterRegister.buildFilterChain(new MockInvoker()).invoke(new MockInvocation());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
