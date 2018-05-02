/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.business.util;

import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.AESCryptography;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.util.ContextUtil;
import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.business.entity.Assistant;
import com.xa3ti.blackcat.business.entity.Contactor;
import com.xa3ti.blackcat.business.entity.Customer;
import com.xa3ti.blackcat.business.entity.Expert;
import com.xa3ti.blackcat.business.service.BrowseService;
import jodd.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nijie
 *
 */
public class Encriptor {
	public static final Integer GM_MAX_BROWSE_NUMS = 20;
	public static final Integer USER_MAX_BROWSE_NUMS = 5;

	public static final String secretWord = "XXXXXX";

	public static Object encriptModel(Object model, List<String> propList) {
	
		AESCryptography crypto = new AESCryptography();

		for (String prop : propList) {
			String getMethodName = "get" + prop.substring(0, 1).toUpperCase()
					+ prop.substring(1);
			String setMethodName = "set" + prop.substring(0, 1).toUpperCase()
					+ prop.substring(1);

			// String wechat =
			// (String)AnnotationUtil.invokeMethodOnObject(model,
			// "getWechat", null);
			// String certificate =
			// (String)AnnotationUtil.invokeMethodOnObject(model,
			// "getCertificate", null);
			try {
				String value = (String) AnnotationUtil.invokeMethodOnObject(
						model, getMethodName, null);
				if (!StringUtil.isBlank(value))
					AnnotationUtil.invokeMethodOnObject(model, setMethodName,
							new Object[] { crypto.byte2String(crypto
									.encrypt(value.getBytes())) });
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// if (!StringUtil.isBlank(wechat))
			// AnnotationUtil.invokeMethodOnObject(model, "setWechat", new
			// Object[]{new String(crypto.encrypt(email.getBytes()))});
			//
			//
			// if (!StringUtil.isBlank(email))
			// AnnotationUtil.invokeMethodOnObject(model, "setCertificate",
			// new Object[]{new String(crypto.encrypt(email.getBytes()))});
			//
		}

		return model;
	}

	public static Object decriptModel(Object model, List<String> dePropList,
			List<String> secretPropList) {
		
		AESCryptography crypto = new AESCryptography();

		if (dePropList != null) {
			for (String prop : dePropList) {
				String getMethodName = "get"
						+ prop.substring(0, 1).toUpperCase()
						+ prop.substring(1);
				String setMethodName = "set"
						+ prop.substring(0, 1).toUpperCase()
						+ prop.substring(1);
				try {

					if (model instanceof Map) {
						String value = (String) ((Map) model).get(prop);
						if (!StringUtil.isBlank(value))
							((Map) model).put(
									prop,
									new String(crypto.decrypt(crypto
											.String2byte(value))));

					} else {
						String value = (String) AnnotationUtil
								.invokeMethodOnObject(model, getMethodName,
										null);
						if (!StringUtil.isBlank(value))

							// value = URLDecoder.decode(value,"utf-8");
							AnnotationUtil
									.invokeMethodOnObject(
											model,
											setMethodName,
											new Object[] { new String(
													crypto.decrypt(crypto
															.String2byte(value))) });
					}
				} catch (Exception e) {

				}
			}

		}

		if (secretPropList != null) {
			for (String prop : secretPropList) {
				String getMethodName = "get"
						+ prop.substring(0, 1).toUpperCase()
						+ prop.substring(1);
				String setMethodName = "set"
						+ prop.substring(0, 1).toUpperCase()
						+ prop.substring(1);

				try {
					if (model instanceof Map) {
						String value = (String) ((Map) model).get(prop);
						if (!StringUtil.isBlank(value))
							((Map) model).put(prop, new String(secretWord));

					} else {
						String value = (String) AnnotationUtil
								.invokeMethodOnObject(model, getMethodName,
										null);
						if (!StringUtil.isBlank(value)) {
							AnnotationUtil.invokeMethodOnObject(model,
									setMethodName, new Object[] { new String(
											secretWord) });
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return model;
	}

	public static XaResult<List<Object>> interceptByTokenType(
			XaResult<List<Expert>> xa, Integer tokenType) {

		AESCryptography crypto = new AESCryptography();

		XaResult<List<Object>> rtnXa = new XaResult<List<Object>>();
		List<Object> aList = new ArrayList<Object>();
		List<Expert> list = xa.getObject();
		try {
			for (Object expert : list) {
				aList.add(decriptModelByTokenType(expert, tokenType));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rtnXa.setObject(aList);
		return rtnXa;
	}



	public static XaResult<List<Object>> interceptAssistantByTokenType(
			XaResult<List<Assistant>> xa, Integer tokenType) {

		AESCryptography crypto = new AESCryptography();

		XaResult<List<Object>> rtnXa = new XaResult<List<Object>>();
		List<Object> aList = new ArrayList<Object>();
		List<Assistant> list = xa.getObject();
		try {
			for (Object assistant : list) {
				aList.add(decriptModelByTokenType(assistant, tokenType));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rtnXa.setObject(aList);
		return rtnXa;
	}

	public static Object decriptModelByTokenType(Object o, Integer tokenType) {
		Object deModel = null;

		if (TokenCenter.TOKEN_TYPE_SUPERADMIN.equals(tokenType)) {
			List<String> propList = new ArrayList<String>();
			propList.add("email");
			propList.add("wechat");
			propList.add("certificate");
			propList.add("contact");
			deModel = decriptModel(o, propList, null);

		} else if (TokenCenter.TOKEN_TYPE_ADMIN.equals(tokenType)) {
			List<String> propList = new ArrayList<String>();
			propList.add("email");
			propList.add("wechat");
			propList.add("certificate");
			propList.add("contact");
			deModel = decriptModel(o, propList, null);
		} else if (TokenCenter.TOKEN_TYPE_GM.equals(tokenType)) {
			List<String> propList = new ArrayList<String>();
			propList.add("email");

			List<String> spropList = new ArrayList<String>();
			spropList.add("wechat");
			spropList.add("certificate");
			spropList.add("contact");
			deModel = decriptModel(o, propList, spropList);
		} else if (TokenCenter.TOKEN_TYPE_LOGINUSER.equals(tokenType)) {
			List<String> propList = new ArrayList<String>();
			propList.add("email");
			propList.add("wechat");
			propList.add("certificate");
			propList.add("contact");
			deModel = decriptModel(o, null, propList);
		} else {
			List<String> propList = new ArrayList<String>();
			propList.add("email");
			propList.add("wechat");
			propList.add("certificate");
			propList.add("contact");
			deModel = decriptModel(o, null, propList);
		}

		return deModel;
	}


	public static XaResult<List<Object>> interceptContactorByTokenType(
			XaResult<List<Contactor>> xa, Integer tokenType) {

		AESCryptography crypto = new AESCryptography();

		XaResult<List<Object>> rtnXa = new XaResult<List<Object>>();
		List<Object> aList = new ArrayList<Object>();
		List<Contactor> list = xa.getObject();
		try {

			for (Object contactor : list) {
				aList.add(decriptModelByTokenType(contactor, tokenType));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rtnXa.setObject(aList);
		return rtnXa;
	}

	public static XaResult<List<Object>> interceptCustomerByTokenType(
			XaResult<List<Customer>> xa, Integer tokenType) {

		AESCryptography crypto = new AESCryptography();

		XaResult<List<Object>> rtnXa = new XaResult<List<Object>>();
		List<Object> aList = new ArrayList<Object>();
		List<Customer> list = xa.getObject();
		try {

			for (Object expert : list) {
				aList.add(decriptModelByTokenType(expert, tokenType));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rtnXa.setObject(aList);
		return rtnXa;
	}

	public static XaResult<Page<Object>> interceptPageByTokenType(
			XaResult<Page<Expert>> xa, Integer tokenType) {
		Page<Expert> p = xa.getObject();
		Long totalElements = p.getTotalElements();
		int totalPages = p.getTotalPages();

		List<Expert> list = p.getContent();

		XaResult<List<Expert>> xx = new XaResult<List<Expert>>();
		xx.setObject(list);
		XaResult<List<Object>> x = interceptByTokenType(xx, tokenType);

		List<Object> aList = x.getObject();
		Pageable pagable = new Pageable(p.getNumber(), p.getSize(), p.getSort());
		Page<Object> pp = new PageImpl(aList, pagable, totalElements);

		XaResult<Page<Object>> xxx = new XaResult<Page<Object>>();
		xxx.setObject(pp);

		return xxx;

	}


	public static XaResult<Page<Object>> interceptAssistantPageByTokenType(
			XaResult<Page<Assistant>> xa, Integer tokenType) {
		Page<Assistant> p = xa.getObject();
		Long totalElements = p.getTotalElements();
		int totalPages = p.getTotalPages();

		List<Assistant> list = p.getContent();

		XaResult<List<Assistant>> xx = new XaResult<List<Assistant>>();
		xx.setObject(list);
		XaResult<List<Object>> x = interceptAssistantByTokenType(xx, tokenType);

		List<Object> aList = x.getObject();
		Pageable pagable = new Pageable(p.getNumber(), p.getSize(), p.getSort());
		Page<Object> pp = new PageImpl(aList, pagable, totalElements);

		XaResult<Page<Object>> xxx = new XaResult<Page<Object>>();
		xxx.setObject(pp);

		return xxx;

	}

	public static XaResult<Page<Object>> interceptContactorPageByTokenType(
			XaResult<Page<Contactor>> xa, Integer tokenType) {
		Page<Contactor> p = xa.getObject();
		Long totalElements = p.getTotalElements();
		int totalPages = p.getTotalPages();

		List<Contactor> list = p.getContent();

		XaResult<List<Contactor>> xx = new XaResult<List<Contactor>>();
		xx.setObject(list);
		XaResult<List<Object>> x = interceptContactorByTokenType(xx, tokenType);

		List<Object> aList = x.getObject();
		Pageable pagable = new Pageable(p.getNumber(), p.getSize(), p.getSort());
		Page<Object> pp = new PageImpl(aList, pagable, totalElements);

		XaResult<Page<Object>> xxx = new XaResult<Page<Object>>();
		xxx.setObject(pp);

		return xxx;

	}

	public static XaResult<Page<Object>> interceptCustomerPageByTokenType(
			XaResult<Page<Customer>> xa, Integer tokenType) {
		Page<Customer> p = xa.getObject();

		List<Customer> list = p.getContent();

		XaResult<List<Customer>> xx = new XaResult<List<Customer>>();
		xx.setObject(list);
		XaResult<List<Object>> x = interceptCustomerByTokenType(xx, tokenType);

		List<Object> aList = x.getObject();
		Pageable pagable = new Pageable(p.getNumber(), p.getSize(), p.getSort());
		Page<Object> pp = new PageImpl(aList, pagable, p.getTotalElements());

		XaResult<Page<Object>> xxx = new XaResult<Page<Object>>();
		xxx.setObject(pp);

		return xxx;

	}

	public static boolean checkCanBrowse(String uid, Integer tokenType,
			Integer browseType) throws BusinessException {
		BrowseService browseService = (BrowseService) ContextUtil.getContext()
				.getBean("BrowseService");
		Integer bnums = browseService
				.getBrowseNums(uid, new Date(), browseType);
		if (TokenCenter.TOKEN_TYPE_SUPERADMIN.equals(tokenType)) {

			return true;
		} else if (TokenCenter.TOKEN_TYPE_ADMIN.equals(tokenType)) {
			return true;
		} else if (TokenCenter.TOKEN_TYPE_GM.equals(tokenType)) {
			if (bnums < GM_MAX_BROWSE_NUMS)
				return true;
			else
				return false;
		} else if (TokenCenter.TOKEN_TYPE_LOGINUSER.equals(tokenType)) {
			if (bnums < USER_MAX_BROWSE_NUMS)
				return true;
			else
				return false;
		} else {
			return false;
		}

	}

	public static boolean checkBrowsed(String uid, Integer tokenType,
			Integer browseType, String modelId) throws BusinessException {
		BrowseService browseService = (BrowseService) ContextUtil.getContext()
				.getBean("BrowseService");
		return browseService.checkBrowsed(uid, new Date(), browseType, modelId);
	}
}
