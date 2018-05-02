/**
 * 
 */
package com.xa3ti.blackcat.proxy.invoke;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.Token;

/**
 * @author nijie
 *
 */
public interface Invoker  {
	
	/**
     * get service interface.
     * 
     * @return service interface.
     */
    Class getInterface();

    /**
     * invoke.
     * 
     * @param invocation
     * @return result
     * @throws RpcException
     */
    public Result invoke(Invocation invocation) throws BusinessException;
    
    
  
}
