package com.thirdparty.wxpay;

import java.io.UnsupportedEncodingException;
import java.util.List;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.thirdparty.wxpay.util.MD5;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.util.MD5Util;
import com.xa3ti.blackcat.base.util.XaUtil;

/**
 * @author hchen
 * @see 微信支付核心
 *
 */
public class WxpayCore {

	/**
	 * 微信下单
	 * @param porVo
	 * @return
	 * @throws WxpayException
	 */
	public static XaResult<PlaceOrderResponseVo> placeOrder(PlaceOrderRequestVo porVo) throws WxpayException{
		XaResult<PlaceOrderResponseVo> xr=new XaResult<PlaceOrderResponseVo>();
		String sign=createSign(porVo);
		String orderXml=createOrderXml(porVo, sign);
		HttpResponse response = HttpRequest.post(WxpayConfig.placeOrderUrl).bodyText(orderXml).send();
		String bodyText = response.bodyText();		//微信服务器返回的字符串
		// 将iso-8859-1的字符串转换为utf-8编码的字符串
		Element rootElement;
		try {
			String resultStr = new String(bodyText.getBytes("iso-8859-1"), "utf-8");
			System.out.println(resultStr);
			rootElement = XmlParse.parseXml2RootElement(resultStr);
			PlaceOrderResponseVo responseVo = WxpayCore.parsePlaceOrderResponseXml(rootElement);
			if(XaUtil.isNotEmpty(responseVo.getPrepay_id())){
				//申请订单成功
				System.out.println("预付款订单ID："+responseVo.getPrepay_id());
				//将结果返回给app
				xr.setObject(responseVo);
			}else{
				//申请订单失败
				System.err.println("下单失败：失败原因："+responseVo.getReturn_msg()+"  "+responseVo.getErr_code_des());
				throw new WxpayException("下单失败：失败原因："+responseVo.getReturn_msg()+"  "+responseVo.getErr_code_des());
			}
		} catch (DocumentException e) {
			throw new WxpayException("下单失败：失败原因："+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new WxpayException("下单失败：失败原因："+e.getMessage());
		}
		return xr;
	}
	
	
	/**
	 * 根据配置和提交的参数生成签名
	 * @return
	 * @throws WxpayException 
	 */
	private static String createSign(PlaceOrderRequestVo porVo) throws WxpayException{
		StringBuffer sb=new StringBuffer();
		if(XaUtil.isEmpty(WxpayConfig.appId)){
			throw new WxpayException("未填写微信支付应用的appId");
		}
		if(XaUtil.isEmpty(WxpayConfig.key)){
			throw new WxpayException("未填写微信支付应用的商户的key");
		}
		//签名时需要key需要按ascll码的顺序组合，即按abcdefgh.....的顺序组合 
		sb.append("appid="+WxpayConfig.appId);
		if(XaUtil.isNotEmpty(porVo.getAttach())){
			sb.append("&attach="+porVo.getAttach());
		}
		if(XaUtil.isNotEmpty(porVo.getBody())){
			sb.append("&body="+porVo.getBody());
		}
		if(XaUtil.isNotEmpty(porVo.getDevice_info())){
			sb.append("&device_info="+porVo.getDevice_info());
		}
		if(XaUtil.isNotEmpty(WxpayConfig.mch_id)){
			sb.append("&mch_id="+WxpayConfig.mch_id);
		}
		if(XaUtil.isNotEmpty(porVo.getNonce_str())){
			sb.append("&nonce_str="+porVo.getNonce_str());
		}
		if(XaUtil.isNotEmpty(WxpayConfig.notify_url)){
			sb.append("&notify_url="+WxpayConfig.notify_url);
		}
		if(XaUtil.isNotEmpty(porVo.getOut_trade_no())){
			sb.append("&out_trade_no="+porVo.getOut_trade_no());
		}
		if(XaUtil.isNotEmpty(porVo.getSpbill_create_ip())){
			sb.append("&spbill_create_ip="+porVo.getSpbill_create_ip());
		}
		if(XaUtil.isNotEmpty(porVo.getTotal_fee())){
//			sb.append("&total_fee="+porVo.getTotal_fee());
			sb.append("&total_fee="+1);				//测试使用1分钱
		}
		if(XaUtil.isNotEmpty(porVo.getTrade_type())){
			sb.append("&trade_type="+porVo.getTrade_type());
		}
		//如果还需要其它条件，可扩展porVo这个对象
		///////////////////////////////////////////////////////////////////////
		//拼接上key
		sb.append("&key="+WxpayConfig.key);
		System.out.println("计算签名之前:"+sb.toString());
		String sign = MD5.MD5Encode(sb.toString()).toUpperCase();
		sign = MD5Util.getMD5String(sb.toString()).toUpperCase();
		System.out.println("计算签名之后得到的签名:"+sign);
		return sign;
	}
	
	/**
	 * 创建订单xml字符串
	 * @return
	 * @throws WxpayException
	 */
	private static String createOrderXml(PlaceOrderRequestVo porVo,String sign) throws WxpayException{
		
		StringBuffer sb=new StringBuffer();
		if(XaUtil.isEmpty(WxpayConfig.appId)){
			throw new WxpayException("未填写微信支付应用的appId");
		}
		if(XaUtil.isEmpty(WxpayConfig.key)){
			throw new WxpayException("未填写微信支付应用的商户的key");
		}
		sb.append("<xml>\n");
		sb.append("<appid>"+WxpayConfig.appId+"</appid>\n");
		if(XaUtil.isNotEmpty(porVo.getAttach())){
			sb.append("<attach>"+porVo.getAttach()+"</attach>\n");
		}
		if(XaUtil.isNotEmpty(porVo.getBody())){
			sb.append("<body>"+porVo.getBody()+"</body>\n");
		}
		if(XaUtil.isNotEmpty(porVo.getDevice_info())){
			sb.append("<device_info>"+porVo.getDevice_info()+"</device_info>\n");
		}
		if(XaUtil.isNotEmpty(WxpayConfig.mch_id)){
			sb.append("<mch_id>"+WxpayConfig.mch_id+"</mch_id>\n");
		}
		if(XaUtil.isNotEmpty(porVo.getNonce_str())){
			sb.append("<nonce_str>"+porVo.getNonce_str()+"</nonce_str>\n");
		}
		if(XaUtil.isNotEmpty(WxpayConfig.notify_url)){
			sb.append("<notify_url>"+WxpayConfig.notify_url+"</notify_url>\n");
		}
		if(XaUtil.isNotEmpty(porVo.getOut_trade_no())){
			sb.append("<out_trade_no>"+porVo.getOut_trade_no()+"</out_trade_no>\n");
		}
		if(XaUtil.isNotEmpty(porVo.getSpbill_create_ip())){
			sb.append("<spbill_create_ip>"+porVo.getSpbill_create_ip()+"</spbill_create_ip>\n");
		}
		if(XaUtil.isNotEmpty(porVo.getTotal_fee())){
			sb.append("<total_fee>"+porVo.getTotal_fee()+"</total_fee>\n");
//			sb.append("<total_fee>1</total_fee>\n");			//测试设置1分钱
		}
		if(XaUtil.isNotEmpty(porVo.getTrade_type())){
			sb.append("<trade_type>"+porVo.getTrade_type()+"</trade_type>\n");
		}
		////////////////////////////////////////////////////////////////////////
		//如果增加了新的字段，可在此处进行扩展
		////////////////////////////////////////////////////////////////////////
		sb.append("<sign>"+sign+"</sign>\n");
		sb.append("</xml>");
		System.out.println("提交参数xml:"+sb.toString());
		return sb.toString();
	}
	
	/**
	 * 解析微信返回的数据
	 * @param root
	 * @return
	 * @throws DocumentException
	 */
	private static PlaceOrderResponseVo parsePlaceOrderResponseXml(Element root) throws DocumentException{
		PlaceOrderResponseVo porVo=new PlaceOrderResponseVo();
		List<Element> children = root.elements();
		for (Element element : children) {
			if(PlaceOrderResponseVo.return_code_key.equals(element.getName())){
				porVo.setReturn_code(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.return_msg_key.equals(element.getName())){
				porVo.setReturn_msg(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.appid_key.equals(element.getName())){
				porVo.setAppid(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.mch_id_key.equals(element.getName())){
				porVo.setMch_id(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.device_info_key.equals(element.getName())){
				porVo.setDevice_info(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.nonce_str_key.equals(element.getName())){
				porVo.setNonce_str(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.sign_key.equals(element.getName())){
				porVo.setSign(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.result_code_key.equals(element.getName())){
				porVo.setResult_code(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.err_code_key.equals(element.getName())){
				porVo.setErr_code(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.err_code_des_key.equals(element.getName())){
				porVo.setErr_code_des(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.trade_type_key.equals(element.getName())){
				porVo.setTrade_type(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.prepay_id_key.equals(element.getName())){
				porVo.setPrepay_id(element.getTextTrim());
			}
			if(PlaceOrderResponseVo.code_url_key.equals(element.getName())){
				porVo.setCode_url(element.getTextTrim());
			}
		}
		return porVo;
	}
}
