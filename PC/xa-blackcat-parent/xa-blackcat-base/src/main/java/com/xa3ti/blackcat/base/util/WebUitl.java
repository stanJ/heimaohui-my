package com.xa3ti.blackcat.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jodd.util.StringUtil;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.alibaba.fastjson.JSON;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaCmsUser;

/**
 * 
 * Web处理常用方法（基础）
 * 
 */

public class WebUitl {

	private static String SORT_ASC = "ASC";
	@SuppressWarnings("unused")
	private static String SORT_DESC = "DESC";

	/**
	 * 构造分页参数（排序）.
	 * 
	 * @param pageNumber
	 *            the page number
	 * @param sort
	 *            the sort
	 * @return the page request
	 */
	// public static PageRequest buildPageRequest(int pageNumber, String sort) {
	// List<Sort.Order> orders = new ArrayList<Sort.Order>();
	// if (XaUtil.isNotEmpty(sort)) {
	// PageSort[] pageSorts = JSON.parseObject(sort, PageSort[].class);
	// for (PageSort pageSort : pageSorts) {
	// orders.add(new Order(Sort.Direction.valueOf(pageSort
	// .getDirection()), pageSort.getProperty()));
	// }
	// Sort sorts = new Sort(orders);
	// return new PageRequest(pageNumber - 1, 1, sorts);
	// } else {
	// return new PageRequest(pageNumber - 1, 1);
	// }
	// }
	//
	// /**
	// * 构造分页参数
	// *
	// * @param pago页
	// * @param pageSize
	// * 页面大小(默认为10条)
	// * @param sort
	// * 排序字段
	// * @return
	// */
	// public static PageRequest buildPageRequest(int pageNumber, int pageSize,
	// String sort) {
	//
	// List<Sort.Order> orders = new ArrayList<Sort.Order>();
	// if (XaUtil.isEmpty(pageSize)) {
	// pageSize = 10;
	// }
	// if (XaUtil.isNotEmpty(sort)) {
	// PageSort[] pageSorts = JSON.parseObject(sort, PageSort[].class);
	// for (PageSort pageSort : pageSorts) {
	// if(SORT_ASC.equalsIgnoreCase(pageSort.getDirection())){
	// orders.add(new Order(Sort.Direction.ASC, pageSort.getProperty()));
	// }else{
	// orders.add(new Order(Sort.Direction.DESC, pageSort.getProperty()));
	// }
	// }
	// Sort sorts = new Sort(orders);
	// return new PageRequest(pageNumber , pageSize, sorts);
	// } else {
	// return new PageRequest(pageNumber , pageSize);
	// }
	// }

	public static Pageable buildPageRequest(int pageNumber, String sort) {
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
		if (XaUtil.isNotEmpty(sort)) {
			PageSort[] pageSorts = JSON.parseObject(sort, PageSort[].class);
			for (PageSort pageSort : pageSorts) {
				orders.add(new Order(Sort.Direction.valueOf(pageSort
						.getDirection()), pageSort.getProperty()));
			}
			Sort sorts = new Sort(orders);
			return new Pageable(pageNumber - 1, 1, sorts);
		} else {
			return new Pageable(pageNumber - 1, 1);
		}
	}

	/**
	 * 构造分页参数
	 * 
	 * @param pago页
	 * @param pageSize
	 *            页面大小(默认为10条)
	 * @param sort
	 *            排序字段
	 * @return
	 */
	public static Pageable buildPageRequest(int pageNumber, int pageSize,
			String sort) {

		List<Sort.Order> orders = new ArrayList<Sort.Order>();
		if (XaUtil.isEmpty(pageSize)) {
			pageSize = 10;
		}
		if (XaUtil.isNotEmpty(sort)) {
			PageSort[] pageSorts = JSON.parseObject(sort, PageSort[].class);
			for (PageSort pageSort : pageSorts) {
				if (SORT_ASC.equalsIgnoreCase(pageSort.getDirection())) {
					orders.add(new Order(Sort.Direction.ASC, pageSort
							.getProperty()));
				} else {
					orders.add(new Order(Sort.Direction.DESC, pageSort
							.getProperty()));
				}
			}
			Sort sorts = new Sort(orders);
			return new Pageable(pageNumber, pageSize, sorts);
		} else {
			return new Pageable(pageNumber, pageSize);
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParametersStartingWith(
			String jsonFilter, String prefix) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (XaUtil.isNotEmpty(jsonFilter) && XaUtil.isNotEmpty(prefix)) {
			Map<String, String> map = JSON.parseObject(jsonFilter,
					HashMap.class);
			for (String key : map.keySet()) {
				if (key.startsWith(prefix)) {
					String unprefixed = key.substring(prefix.length());
					if (unprefixed.indexOf("__") != -1) {
						unprefixed = unprefixed.replace("__", ".");
					}
					Object value = map.get(key);
					searchParams.put(unprefixed, value);
				}
			}
		}
		return searchParams;
	}

	public static String getCmsLoginedUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		XaCmsUser user = (XaCmsUser) session
				.getAttribute(XaConstant.SessionKey.currentUser);
		if (XaUtil.isNotEmpty(user)) {
			return user.getUserId();
		}
		return "-1";
	}

}
