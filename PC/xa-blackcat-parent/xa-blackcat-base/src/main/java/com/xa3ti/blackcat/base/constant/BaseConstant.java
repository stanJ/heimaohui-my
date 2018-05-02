/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.constant;

/**
 * @author nijie
 *
 */
public class BaseConstant {
	
	public final static String CLASS_PACKAGE = "com.xa3ti.blackcat.business.entity.,com.xa3ti.blackcat.base.entity.";

	
	public final static String SQLFILTERKEY="SQLFILTERKEY";
	
	public final static String ATTACTMENT_KEY_AGENT="ATTACTMENT_KEY_AGENT";
	public final static String ATTACTMENT_KEY_TOKEN="ATTACTMENT_KEY_TOKEN";
	
	public final static String ATTACTMENT_KEY_IGNORETOKEN_FLAG="ATTACTMENT_KEY_IGNORETOKEN_FLAG";
	public final static String ATTACTMENT_KEY_IGNORECONCURRENT_FLAG="ATTACTMENT_KEY_IGNORECONCURRENT_FLAG";
	
	
	public final static String AGENT_WEB_PLATFORM="AGENT_WEB_PLATFORM";

	public class DAO {
		public final static String JPA = "JPA";
		public final static String DYNASQL = "DYNASQL";

		public final static String HIBERNATE = "HIBERNATE";
		public final static String MYBATIS = "MYBATIS";
	}

	public class LOG {
		public final static String NEEDLOG = "NEEDLOG";
		public final static String NONEEDLOG = "NONEEDLOG";
	}

}
