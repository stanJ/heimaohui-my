/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.model;

import com.xa3ti.blackcat.base.util.Pageable;

/**
 * @author nijie
 *
 */
public interface BaseExampleModel {
	
	public void setPageable(Pageable pageable);
    public Pageable getPageable();


}
