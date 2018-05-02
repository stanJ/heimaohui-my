/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.event;

import java.util.Date;

/**
 * @author nijie
 *
 */
public interface Event {
	
	public String getSource();
	
	public Date getDateTime();
	
	public Object getObject();

}
