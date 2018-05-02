package com.thirdparty.wxpay;

public class WxpayConfig {

	/**
	 * 微信开放平台中，申请应用的appId,以wx开头
	 */
	public static final String appId="wxce64d159a6ba27a7";
	/**
	 * 商户号  pay.weixin.qq.com中申请的商户号
	 */
	public static final String mch_id="1237307002";
	/**
	 * 商户申请微信支付成功后收到的key
	 */
	public static final String key="TaisiTesT2000517970732STUang9988";
	
	/**
	 * 微信支付成功后的回调地址
	 */
	//public static final String notify_url="http://www.3tixa.com:21012/taisiwang/m/orderTable/wxpayCallback";
	public static final String notify_url="http://120.24.60.56:8080/taisiwang/m/orderTable/wxpayCallback";
	
	/**
	 * 统一下单地址，不用修改
	 */
	public static final String placeOrderUrl="https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	
}
