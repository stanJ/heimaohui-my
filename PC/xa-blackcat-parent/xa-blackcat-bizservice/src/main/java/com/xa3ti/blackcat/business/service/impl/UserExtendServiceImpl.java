package com.xa3ti.blackcat.business.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.xa3ti.blackcat.base.util.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;

import com.xa3ti.blackcat.base.annotation.XALogger;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.repository.XaCmsUserRepository;
import com.xa3ti.blackcat.base.service.XaCmsUserService;
import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.util.AESCryptography;
import com.xa3ti.blackcat.base.util.DateProcessUtil;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.MD5Util;
import com.xa3ti.blackcat.base.util.QuerySqlExecutor;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.annotation.TableJoin;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.util.XaUtil;
import com.xa3ti.blackcat.business.entity.UserExtend;
import com.xa3ti.blackcat.business.model.UserRegister;
import com.xa3ti.blackcat.business.repository.UserExtendRepository;
import com.xa3ti.blackcat.business.service.UserExtendService;
import com.xa3ti.blackcat.base.constant.BaseConstant;

@Service("UserExtendService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class UserExtendServiceImpl extends BaseService implements
		UserExtendService {
	public static Logger log = Logger.getLogger(UserExtendServiceImpl.class);

	@Autowired
	private UserExtendRepository userExtendRepository;

	@Autowired
	private XaCmsUserService xaCmsUserService;

	@Autowired
	private XaCmsUserRepository xaCmsUserRepository;

	@Override
	public XaResult<Page<UserExtend>> findUserExtendNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				UserExtend.class.getName(), QueryDao.class);
		Page<UserExtend> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE,
					status));
			page = userExtendRepository.findAll(DynamicSpecifications
					.bySearchFilter(filters.values(), UserExtend.class),
					pageable);

		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status!=" + status
						+ ")";
				page = QuerySqlExecutor.find(null, UserExtend.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE,
						status));

				page = QuerySqlExecutor.find(filters.values(),
						UserExtend.class, pageable);
			}
		} else if (BaseConstant.DAO.MYBATIS.equals(an.method())) {
			//
		}

		XaResult<Page<UserExtend>> xr = new XaResult<Page<UserExtend>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<UserExtend>> findUserExtendEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				UserExtend.class.getName(), QueryDao.class);
		Page<UserExtend> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ,
					status));
			page = userExtendRepository.findAll(DynamicSpecifications
					.bySearchFilter(filters.values(), UserExtend.class),
					pageable);

		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status==" + status
						+ ")";
				page = QuerySqlExecutor.find(null, UserExtend.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ,
						status));

				page = QuerySqlExecutor.find(filters.values(),
						UserExtend.class, pageable);
			}
		} else if (BaseConstant.DAO.MYBATIS.equals(an.method())) {
			//
		}

		XaResult<Page<UserExtend>> xr = new XaResult<Page<UserExtend>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<UserExtend>> findUserExtendVoByFilter(Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				UserExtend.class.getName(), QueryDao.class);
		Page<UserExtend> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ,
					status));
			page = userExtendRepository.findAll(DynamicSpecifications
					.bySearchFilter(filters.values(), UserExtend.class),
					pageable);

		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status==" + status
						+ ")";
				page = QuerySqlExecutor.find(null, UserExtend.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ,
						status));

				page = QuerySqlExecutor.find(filters.values(),
						UserExtend.class, pageable);
			}
		} else if (an.method().equals(BaseConstant.DAO.MYBATIS)) {
			//
		}

		XaResult<List<UserExtend>> xr = new XaResult<List<UserExtend>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need = true)
	public XaResult<UserExtend> createUserExtend(String userId, UserExtend model)
			throws BusinessException {

		XaResult<UserExtend> xr = new XaResult<UserExtend>();
		// 此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if (userId != null) {
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");
		UserExtend obj = userExtendRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need = true)
	public XaResult<UserExtend> updateUserExtend(String userId, UserExtend model)
			throws BusinessException {
		UserExtend obj = userExtendRepository.findOne(model.getTid());
		XaResult<UserExtend> xr = new XaResult<UserExtend>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setName(model.getName());
			obj.setCompany(model.getCompany());
			obj.setUserType(model.getUserType());
			obj.setUserId(model.getUserId());
			// 此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);
			obj = userExtendRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need = true)
	public XaResult<UserExtend> operateUserExtend(String userId,
			String modelId, Integer status) throws BusinessException {
		if (status == null) {
			status = XaConstant.Status.delete;
		}
		UserExtend obj = userExtendRepository.findByTidAndStatusNot(modelId,
				status);
		XaResult<UserExtend> xr = new XaResult<UserExtend>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = userExtendRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<UserExtend> findUserExtend(String modelId)
			throws BusinessException {
		UserExtend obj = userExtendRepository.findByTidAndStatusNot(modelId,
				XaConstant.Status.delete);
		XaResult<UserExtend> xr = new XaResult<UserExtend>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need = true)
	public XaResult<UserExtend> multiOperateUserExtend(String userId,
			String modelIds, Integer status) throws BusinessException {
		XaResult<UserExtend> xr = new XaResult<UserExtend>();
		if (status == null) {
			status = XaConstant.Status.delete;
		}
		if (modelIds != null) {
			if (StringUtils.indexOf(modelIds, ",") > 0) {
				String[] ids = modelIds.split(",");
				for (String id : ids) {
					UserExtend obj = userExtendRepository
							.findByTidAndStatusNot(id, status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = userExtendRepository.save(obj);
					} else {
						throw new BusinessException(
								XaConstant.Message.object_not_find);
					}
				}
			} else {
				UserExtend obj = userExtendRepository.findByTidAndStatusNot(
						modelIds, status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = userExtendRepository.save(obj);
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
	 * com.xa3ti.blackcat.business.service.UserExtendService#registerUser(com
	 * .xa3ti.blackcat.business.model.UserResiger)
	 */
	@Override
	public XaResult<UserExtend> registerUser(String userId,
			UserRegister userRegister) throws BusinessException {
		XaCmsUser cmsUser = new XaCmsUser();

		cmsUser.setStatus(XaConstant.UserStatus.status_normal);
		cmsUser.setUserName(userRegister.getUsername());
		cmsUser.setNickName(userRegister.getUsername());

		cmsUser.setRegistDate(DateProcessUtil
				.getToday(DateProcessUtil.YYYYMMDDHHMMSS));

		cmsUser.setUserType(1);

		cmsUser.setPassword(userRegister.getPassword());
		// AESCryptography crypto = new AESCryptography();
		// try {
		// cmsUser.setPassword(crypto.byte2String(crypto.encrypt(cmsUser
		// .getPassword().getBytes("utf-8"))));
		// } catch (Exception e) {
		// throw new BusinessException(e.getMessage());
		// }

		cmsUser.setRoleId(Long.valueOf(userRegister.getUserType()));

		XaResult<XaCmsUser> xa = xaCmsUserService.saveCmsUser(cmsUser);
		XaCmsUser user = xa.getObject();

		UserExtend ue = new UserExtend();

		ue.setCompany(userRegister.getCompany());
		ue.setContact(userRegister.getContact());
		ue.setName(userRegister.getUsername());
		ue.setUserType(Long.valueOf(userRegister.getUserType()));
		ue.setUserId(user.getUserId());
		ue.setPasswordUpdateTimes(0);

		ue.setCreateUser(userId);
		ue.setCreateTime(com.xa3ti.blackcat.base.util.DateUtil.DateToString(
				new Date(), "yyyy-MM-dd HH:mm:ss"));

		ue.setModifyUser(userId);
		ue.setModifyTime(com.xa3ti.blackcat.base.util.DateUtil.DateToString(
				new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		UserExtend obj = userExtendRepository.save(ue);
		XaResult<UserExtend> x = new XaResult<UserExtend>();
		x.setObject(obj);
		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.business.service.UserExtendService#modifyUser(com.
	 * xa3ti.blackcat.business.model.UserResiger)
	 */
	@Override
	public XaResult<UserExtend> modifyUser(String userId,
			UserRegister userRegister) throws BusinessException {
		String userName = userRegister.getUsername();

		XaCmsUser xaCmsUser = xaCmsUserRepository.findByUserName(userName,
				XaConstant.UserStatus.status_normal);

		if (xaCmsUser == null)
			throw new BusinessException("找不到该账户");

		String uId = xaCmsUser.getUserId();
		UserExtend ue = userExtendRepository.findUserExtendByUserId(uId);

		String password = userRegister.getPassword();
		boolean updPassword = false;
		if (!StringUtil.isBlank(password)
				|| !StringUtil.isBlank(userRegister.getNewPassword())) {// 修改密码
			AESCryptography crypto = new AESCryptography();
			try {
				if (!StringUtil.isBlank(password)) {// 输入了旧密码 则校验旧密码
					String md5Password = new String(crypto.decrypt(crypto
							.String2byte(xaCmsUser.getPassword())));
					if (!md5Password.equals(MD5Util.getMD5String(userRegister
							.getPassword()))) {
						throw new BusinessException("旧密码不对");
					}
				}

				if (!StringUtil.isBlank(userRegister.getNewPassword())) {
					xaCmsUser.setPassword(MD5Util.getMD5String(userRegister
							.getNewPassword()));
					xaCmsUser.setPassword(crypto.byte2String(crypto
							.encrypt(xaCmsUser.getPassword().getBytes())));

					updPassword = true;

				}
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			xaCmsUserRepository.save(xaCmsUser);
			if (ue.getPasswordUpdateTimes() == null)
				ue.setPasswordUpdateTimes(1);
			else
				ue.setPasswordUpdateTimes(ue.getPasswordUpdateTimes() + 1);

			ue = userExtendRepository.save(ue);
		} else {// 修改账号信息

			if (ue != null) {
				ue.setCompany(userRegister.getCompany());
				ue.setContact(userRegister.getContact());
				ue.setUserType(Long.valueOf(userRegister.getUserType()));

				ue.setModifyUser(userId);
				ue.setModifyTime(com.xa3ti.blackcat.base.util.DateUtil
						.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				ue.setModifyDescription("修改账号");

				ue = userExtendRepository.save(ue);
			}
		}
		XaResult<UserExtend> xa = new XaResult<UserExtend>();

		xa.setObject(ue);
		return xa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.business.service.UserExtendService#deleteUser(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public XaResult<UserExtend> deleteUser(String userId, String userName)
			throws BusinessException {
		XaCmsUser xaCmsUser = xaCmsUserRepository.findByUserName(userName,
				XaConstant.UserStatus.status_normal);

		if (xaCmsUser == null)
			throw new BusinessException("找不到该账户");

		xaCmsUser.setStatus(XaConstant.UserStatus.status_delete);

		xaCmsUserRepository.save(xaCmsUser);

		UserExtend ue = userExtendRepository.findUserExtendByUserId(xaCmsUser
				.getUserId());
		if (ue != null) {
			ue.setStatus(XaConstant.Status.delete);
			ue.setModifyUser(userId);
			ue.setModifyTime(com.xa3ti.blackcat.base.util.DateUtil
					.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			ue.setModifyDescription("删除账号");
			ue = userExtendRepository.save(ue);
		}
		
		XaResult<UserExtend> xa=new XaResult<UserExtend>();
		xa.setObject(ue);
		return xa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.business.service.UserExtendService#findUser(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public XaResult<UserRegister> findUser(String userId, String userName)
			throws BusinessException {
		XaCmsUser xaCmsUser = xaCmsUserRepository.findByUserName(userName,
				XaConstant.UserStatus.status_normal);
		if (xaCmsUser == null)
			throw new BusinessException("找不到该账户");

		UserRegister ur = new UserRegister();
		ur.setUsername(xaCmsUser.getUserName());
		// ur.setPassword(xaCmsUser.getPassword());

		UserExtend ue = userExtendRepository.findUserExtendByUserId(xaCmsUser
				.getUserId());
		if (ue != null) {
			ur.setCompany(ue.getCompany());
			ur.setContact(ue.getContact());
			ur.setUserType(Integer.valueOf(String.valueOf(ue.getUserType())));
		}
		XaResult<UserRegister> xa = new XaResult<UserRegister>();
		xa.setObject(ur);
		return xa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.business.service.UserExtendService#resetPassword(java
	 * .lang.String, com.xa3ti.blackcat.business.model.UserRegister)
	 */
	@Override
	public XaResult<UserExtend> resetPassword(String userId,
			UserRegister userRegister) throws BusinessException {
		String userName = userRegister.getUsername();

		XaCmsUser xaCmsUser = xaCmsUserRepository.findByUserName(userName,
				XaConstant.UserStatus.status_normal);

		if (xaCmsUser == null)
			throw new BusinessException("找不到该账户");

		String uId = xaCmsUser.getUserId();
		UserExtend ue = userExtendRepository.findUserExtendByUserId(uId);
		if (!StringUtil.isBlank(userRegister.getNewPassword())) {// 修改密码
			AESCryptography crypto = new AESCryptography();
			try {
				xaCmsUser.setPassword(MD5Util.getMD5String(userRegister
						.getNewPassword()));
				xaCmsUser.setPassword(crypto.byte2String(crypto
						.encrypt(xaCmsUser.getPassword().getBytes())));

			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			xaCmsUserRepository.save(xaCmsUser);

			ue.setPasswordUpdateTimes(0);

			ue = userExtendRepository.save(ue);
		} else {
			throw new BusinessException("新密码不能为空");
		}

		XaResult<UserExtend> xa = new XaResult<UserExtend>();

		xa.setObject(ue);
		return xa;
	}

}
