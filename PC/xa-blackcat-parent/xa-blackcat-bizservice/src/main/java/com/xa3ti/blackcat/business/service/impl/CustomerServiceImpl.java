package com.xa3ti.blackcat.business.service.impl;

import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.annotation.XALogger;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.util.*;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.business.entity.Customer;
import com.xa3ti.blackcat.business.repository.CustomerRepository;
import com.xa3ti.blackcat.business.service.CustomerService;
import com.xa3ti.blackcat.business.util.Encriptor;
import jodd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("CustomerService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class CustomerServiceImpl extends BaseService implements CustomerService {
	public static Logger log = Logger.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public XaResult<Page<Customer>> findCustomerNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				Customer.class.getName(), QueryDao.class);
		Page<Customer> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE,
					status));
			page = customerRepository
					.findAll(
							DynamicSpecifications.bySearchFilter(
									filters.values(), Customer.class), pageable);

		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status!=" + status
						+ ")";
				page = QuerySqlExecutor.find(null, Customer.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE,
						status));

				page = QuerySqlExecutor.find(filters.values(), Customer.class,
						pageable);
			}
		} else if (BaseConstant.DAO.MYBATIS.equals(an.method())) {
			//
		}

		XaResult<Page<Customer>> xr = new XaResult<Page<Customer>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Customer>> findCustomerEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				Customer.class.getName(), QueryDao.class);
		Page<Customer> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ,
					status));
			page = customerRepository
					.findAll(
							DynamicSpecifications.bySearchFilter(
									filters.values(), Customer.class), pageable);

		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status==" + status
						+ ")";
				page = QuerySqlExecutor.find(null, Customer.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ,
						status));

				page = QuerySqlExecutor.find(filters.values(), Customer.class,
						pageable);
			}
		} else if (BaseConstant.DAO.MYBATIS.equals(an.method())) {
			//
		}

		XaResult<Page<Customer>> xr = new XaResult<Page<Customer>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Customer>> findCustomerVoByFilter(Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				Customer.class.getName(), QueryDao.class);
		Page<Customer> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ,
					status));
			page = customerRepository
					.findAll(
							DynamicSpecifications.bySearchFilter(
									filters.values(), Customer.class), pageable);

		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status==" + status
						+ ")";
				page = QuerySqlExecutor.find(null, Customer.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ,
						status));

				page = QuerySqlExecutor.find(filters.values(), Customer.class,
						pageable);
			}
		} else if (an.method().equals(BaseConstant.DAO.MYBATIS)) {
			//
		}

		XaResult<List<Customer>> xr = new XaResult<List<Customer>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need = true)
	public XaResult<Customer> createCustomer(String userId, Customer model)
			throws BusinessException {

		XaResult<Customer> xr = new XaResult<Customer>();
		// 此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if (userId != null) {
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");

		List<String> propList = new ArrayList<String>();
		propList.add("email");
		propList.add("wechat");
		propList.add("contact");

		model = (Customer) Encriptor.encriptModel(model, propList);// 加密

		if(!StringUtil.isBlank(model.getName())){
			Customer customer=customerRepository.findCustomerByName(model.getName());
			if(customer!=null)
				throw new BusinessException("客户"+model.getName()+"已经存在,不能创建客户");
		}

		Customer obj = customerRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need = true)
	public XaResult<Customer> updateCustomer(String userId, Customer model)
			throws BusinessException {
		Customer obj = customerRepository.findOne(model.getTid());
		XaResult<Customer> xr = new XaResult<Customer>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setName(model.getName());
			obj.setManager(model.getManager());
			obj.setContact(model.getContact());
			obj.setEmail(model.getEmail());
			obj.setWechat(model.getWechat());
			obj.setProvince(model.getProvince());
			obj.setCity(model.getCity());
			obj.setDistrict(model.getDistrict());
			obj.setComment(model.getComment());
			obj.setServiceArea(model.getServiceArea());
			obj.setServiceObject(model.getServiceObject());
			obj.setIntro(model.getIntro());
			obj.setFile(model.getFile());
			// 此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);

			List<String> propList = new ArrayList<String>();
			propList.add("email");
			propList.add("wechat");
			propList.add("contact");

			obj = (Customer) Encriptor.encriptModel(obj, propList);// 加密


			if(!StringUtil.isBlank(obj.getName())){
				Customer customer=customerRepository.findCustomerByName(obj.getName());
				if(customer!=null&&!customer.getTid().equals(obj.getTid()))
					throw new BusinessException("客户"+model.getName()+"已经存在,不能更新客户");
			}


			obj = customerRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	public XaResult<Customer> updateCustomerModifyTime(String id, Date modifyTime, String modifyDesc, String modifyUserId) throws BusinessException {
		Customer obj = customerRepository.findOne(id);
		XaResult<Customer> xr = new XaResult<Customer>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setModifyDescription(modifyDesc);
			obj.setModifyTime(com.xa3ti.blackcat.base.util.DateUtil.DateToString(modifyTime, "yyyy-MM-dd HH:mm:ss"));
			obj.setModifyUser(modifyUserId);
			obj = customerRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need = true)
	public XaResult<Customer> operateCustomer(String userId, String modelId,
			Integer status) throws BusinessException {
		if (status == null) {
			status = XaConstant.Status.delete;
		}
		Customer obj = customerRepository
				.findByTidAndStatusNot(modelId, status);
		XaResult<Customer> xr = new XaResult<Customer>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = customerRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Customer> findCustomer(String modelId)
			throws BusinessException {
		Customer obj = customerRepository.findByTidAndStatusNot(modelId,
				XaConstant.Status.delete);
		XaResult<Customer> xr = new XaResult<Customer>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need = true)
	public XaResult<Customer> multiOperateCustomer(String userId,
			String modelIds, Integer status) throws BusinessException {
		XaResult<Customer> xr = new XaResult<Customer>();
		if (status == null) {
			status = XaConstant.Status.delete;
		}
		if (modelIds != null) {
			if (StringUtils.indexOf(modelIds, ",") > 0) {
				String[] ids = modelIds.split(",");
				for (String id : ids) {
					Customer obj = customerRepository.findByTidAndStatusNot(id,
							status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = customerRepository.save(obj);
					} else {
						throw new BusinessException(
								XaConstant.Message.object_not_find);
					}
				}
			} else {
				Customer obj = customerRepository.findByTidAndStatusNot(
						modelIds, status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = customerRepository.save(obj);
				} else {
					throw new BusinessException(
							XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.business.service.CustomerService#findCustomerJoined
	 * (java.lang.String)
	 */
	@Override
	public XaResult<Map<String,Object>> findCustomerJoined(String modelId)
			throws BusinessException {
		XaResult<Map<String,Object>> xr = new XaResult<Map<String,Object>>();

		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		filters.put("status", new SearchFilter("status", Operator.EQ,
				XaConstant.Status.valid));
		filters.put("tid", new SearchFilter("tid", Operator.EQ, modelId));

		Page<Customer> expertPage = QuerySqlExecutor.find(filters.values(),
				Customer.class, null);

		List<Customer> list = expertPage.getContent();

		if (list != null && list.size() > 0) {
			xr.setObject(ClassUtil.transBean2Map(list.get(0)));
		}else{
			xr.setCode(0);
			xr.setMessage(XaConstant.Message.object_not_find);
			xr.setObject(new HashMap<String,Object>());
		}

		return xr;
	}

}
