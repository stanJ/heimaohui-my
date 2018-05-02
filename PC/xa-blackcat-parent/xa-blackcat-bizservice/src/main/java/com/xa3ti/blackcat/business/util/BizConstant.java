/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.business.util;

import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.repository.XaCmsUserRepository;
import com.xa3ti.blackcat.base.service.XaCmsUserService;
import com.xa3ti.blackcat.base.util.AESCryptography;
import com.xa3ti.blackcat.base.util.MD5Util;
import com.xa3ti.blackcat.business.entity.Constant;
import com.xa3ti.blackcat.business.repository.ConstantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author nijie
 *
 */

@Component
public class BizConstant {
	
	@Autowired
	ConstantRepository constantRepository;
	
	public static final String[] innerClassList = { "Role","BrowseType"};
	
	public static String[] usedDict={"SEX"};

	public static String   SYS_SUPERADMIN_USERID="1";
	
	@Autowired
	XaCmsUserService xaCmsUserService;
	
	@Autowired
	XaCmsUserRepository xaCmsUserRepository;
	
	public static class Role{
		public static  Long SUPERMANAGER=1l;
		public static  Long MANAGER=2l;
		public static  Long GM=3l;
		public static  Long USER=4l;
	}
	
	public static class BrowseType{
		public static final Integer EXPERT=1;
		public static final Integer CUSTOMER=2;
	}
	
	
	private void resetSuperadminPassword(){
		try {
			XaCmsUser xaCmsUser=xaCmsUserService.findXaCmsUserByUserName("superadmin", XaConstant.UserStatus.status_normal);

	        AESCryptography crypto = new AESCryptography();
	        String md5=MD5Util.getMD5String("5tgb6yhn7ujm");
	        byte[] dataBytes =
	        		md5.getBytes("utf-8");

	        byte[] encBytes = crypto.encrypt(dataBytes);
	        System.out.println(crypto.byte2String(encBytes));
	        
	        xaCmsUser.setPassword(crypto.byte2String(encBytes));
	        
	        xaCmsUserRepository.save(xaCmsUser);
		
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@PostConstruct
	public void loadConstants() {

		//resetSuperadminPassword();

	    Constant c = constantRepository.findConstantByKey("role.supermanager");
		if (c != null) {
			BizConstant.Role.SUPERMANAGER = Long.parseLong(c
					.getConstantValue());
		}
		c = constantRepository.findConstantByKey("role.manager");
		if (c != null) {
			BizConstant.Role.MANAGER = Long.parseLong(c
					.getConstantValue());
		}
		c = constantRepository.findConstantByKey("role.gm");
		if (c != null) {
			BizConstant.Role.GM = Long.parseLong(c
					.getConstantValue());
		}
		c = constantRepository.findConstantByKey("role.user");
		if (c != null) {
			BizConstant.Role.USER = Long.parseLong(c
					.getConstantValue());
		}
		
		
	}
	
	
	public static HashMap getConstants(String innerClassName) {
		BizConstant c = new BizConstant();
		HashMap m = new HashMap();
		try {
			Class<?> cls = Class
					.forName("com.xa3ti.blackcat.business.util.BizConstant$"
							+ innerClassName);
			Object obj = cls.newInstance();
			Field[] fds = cls.getFields();

			HashMap map = new HashMap();
			for (Field f : fds) {
				String name = f.getName();
				String value = String.valueOf(f.get(obj));
				map.put(name, value);
			}
			m.put(innerClassName, map);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m;
	}
	
	
	public static HashMap getAllConstants() {
		HashMap map = new HashMap();
		try {
			Class<?> cls = Class
					.forName("com.xa3ti.blackcat.business.util.BizConstant");
			Object obj = cls.newInstance();
			Field fd = cls.getField("innerClassList");

			String[] values = (String[]) (fd.get(obj));
			for (String v : values) {
				HashMap m = getConstants(v);

				Iterator it = m.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					map.put(key, m.get(key));
				}
			}

			Field[] fds = cls.getDeclaredFields();
			if (fds != null && fds.length > 0) {
				for (Field fdd : fds) {
					if(!"commonPassword".equals(fdd.getName()))
					map.put(fdd.getName(), fdd.get(obj));
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}
	
}
