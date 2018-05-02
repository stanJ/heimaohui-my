/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.message.channel.sms;

import java.util.HashMap;
import java.util.List;

/**
 * @author nijie
 *
 */
public class SMSServiceImpl implements SMSService{
	
	private static SMSServiceImpl impl;
	
	public static SMSServiceImpl getInstance(){
		if(impl==null){
			impl=new SMSServiceImpl();
		}
		return impl;
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.message.channel.sms.SMSService#sendSMSSingeMobile(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public String sendSMSSingeMobile(String mobile, String content, Long msgId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.message.channel.sms.SMSService#sendSMSMultipleMobiles(java.lang.String[], java.lang.String, java.lang.Long)
	 */
	@Override
	public String sendSMSMultipleMobiles(String[] mobiles, String content,
			Long msgId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.message.channel.sms.SMSService#getReport()
	 */
	@Override
	public List<HashMap> getReport() {
		// TODO Auto-generated method stub
		return null;
	}

}
