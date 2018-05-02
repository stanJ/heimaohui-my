/**
 * 
 */
package com.xa3ti.blackcat.message.channel.sms;



import java.util.HashMap;
import java.util.List;

/**
 * @author nijie
 *
 */
public interface SMSService{
	
	public String sendSMSSingeMobile(String mobile,String content,Long msgId);
	
	public String sendSMSMultipleMobiles(String[] mobiles,String content,Long msgId);
	
	public List<HashMap> getReport();
	
}
