package com.xa3ti.blackcat.base.util.cache;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.XaUtil;

/**
 * 验证码处理工具类（主类）
 * @author zj
 *
 */
public class VeriCodeUtil {
	/**
	 * 生成验证码,通过异常返回错误信息
	 * @param phoneNumber
	 * @param userType
	 * @return
	 * @throws BusinessException
	 */
	public static String getVericode(String phoneNumber)
			throws BusinessException {
		int validTime = 300;
		ExpiredCache<VeriCode> cache = CacheFactory.getExpiredcache();
		if (StringUtils.isEmpty(phoneNumber)) {
			return null;
		}
		String keyword = phoneNumber;
		if (XaUtil.isNotEmpty(cache.get(keyword))) {
			long time = cache.get(keyword).getTimes();
			// 60秒为app请求空余5秒缓冲时间
			time = time + 55000l;
			if (time > Calendar.getInstance().getTimeInMillis()) {
				throw new BusinessException("获取验证码超时");
			}
			return cache.get(keyword).getVericode();
		} else {
			String number = VerifyCoder.genVerifyCode();
			VeriCode vericode = new VeriCode(phoneNumber, number);
			cache.put(keyword, vericode, validTime);
			return vericode.getVericode();
		}
	}

	/**
	 * 验证码检测,通过异常返回错误信息
	 * @param phoneNumber
	 * @param userType
	 * @param vericode
	 * @return
	 * @throws BusinessException
	 */
	public static boolean checkVericode(String phoneNumber,
			String vericode) throws BusinessException {
		if (StringUtils.isEmpty(phoneNumber)) {
			return false;
		}
		String keyword = phoneNumber;
		ExpiredCache<VeriCode> cache = CacheFactory.getExpiredcache();
		VeriCode vericodeVo = cache.get(keyword);
		if (vericodeVo == null) {
			throw new BusinessException("验证码已过期");
		}
		if (!vericode.equals(vericodeVo.getVericode())) {
			throw new BusinessException("请输入正确的4位数字验证码");
		}
		cache.remove(keyword);
		return true;
	}
	
	public static void main(String[] strs){
		try {
			String vcode = VeriCodeUtil.getVericode("13699021074");
			System.out.println(vcode);
			System.out.println(VeriCodeUtil.checkVericode("13699021074", vcode));
			System.out.println(VeriCodeUtil.getVericode("13699021074"));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
