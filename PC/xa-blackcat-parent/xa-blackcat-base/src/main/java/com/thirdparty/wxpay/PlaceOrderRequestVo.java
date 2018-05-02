package com.thirdparty.wxpay;

/**
 * @author hchen
 * @see  申请微信支付时传递的对象,与每笔交易相关的参数，不包含集成微信所需的参数，如appId,key等
 *
 */
public class PlaceOrderRequestVo {

	/**
	 * 随机字符串，不长于32位
	 */
	private String nonce_str ;
	/**
	 * 商品或支付单简要描述 
	 */
	private String body ;
	/**
	 *  附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据 
	 */
	private String attach ;
	/**
	 * 商户系统内部的订单号,32个字符内、可包含字母
	 */
	private String out_trade_no ;
	
	
	/**
	 * 订单总金额，只能为整数,以分为单位
	 */
	private Integer total_fee ;
	
	
	/**
	 *  取值如下：JSAPI，NATIVE，APP，WAP,
	 */
	private String trade_type ;
	
	/**
	 * 设备号：终端设备号(游戏wap支付此字段必传)
	 */
	private  String device_info="";
	
	/**
	 * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	 */
	private String spbill_create_ip ;

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	
}
