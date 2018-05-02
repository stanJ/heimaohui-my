package com.thirdparty.wxpay;

/**
 * @author hchen
 * @see 微信支付异常类
 *
 */
public class WxpayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WxpayException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WxpayException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public WxpayException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WxpayException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WxpayException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
