/**
 * 
 */
package com.xa3ti.blackcat.message;

/**
 * @author nijie
 *
 */
public class MsgConstant {

	/**
	 * 前段类型
	 * 
	 * @author nijie
	 *
	 */
	public static class FRONTEND {
		public static String web = "web";
		public static String mis = "mis";
	}
    /**
     * 发送渠道 
     * @author nijie
     *
     */
	public static class CHANNEL {
		public static final String SYS = "sys";//系统
		public static final String SMS = "sms";//短信
		public static final String WECHAT = "wechat";//微信消息
	}

	/**
	 * 接收者类型 可根据业务自定义
	 * 
	 * @author nijie
	 *
	 */
	public static class ReceiverType {
		public static final Integer TYPE_WEBCLIENT = 0;
		public static final Integer TYPE_2 = 1;
		public static final Integer TYPE_3 = 2;

	}

	/**
	 * 发送状态
	 * 
	 * @author nijie
	 *
	 */
	public static class Status {
		public static Integer UNSEND = 0;
		public static Integer SUCCESS = 1;
		public static Integer FAILURE = 2;
	}

	/**
	 * 消息优先级
	 * 
	 * @author nijie
	 *
	 */
	public static class Priority {
		public static Integer HIGHEST = 5;
		public static Integer HIGH = 4;
		public static Integer NORMAL = 3;
		public static Integer LOW = 2;
		public static Integer LOWEST = 1;

	}

}
