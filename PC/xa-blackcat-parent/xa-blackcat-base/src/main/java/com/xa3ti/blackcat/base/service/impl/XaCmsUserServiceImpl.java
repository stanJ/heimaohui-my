package com.xa3ti.blackcat.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaCmsResource;
import com.xa3ti.blackcat.base.entity.XaCmsRole;
import com.xa3ti.blackcat.base.entity.XaCmsRoleResource;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.repository.XaCmsResourceRepository;
import com.xa3ti.blackcat.base.repository.XaCmsRoleRepository;
import com.xa3ti.blackcat.base.repository.XaCmsRoleResourceRepository;
import com.xa3ti.blackcat.base.repository.XaCmsUserRepository;
import com.xa3ti.blackcat.base.service.XaCmsUserService;
import com.xa3ti.blackcat.base.util.AESCryptography;
import com.xa3ti.blackcat.base.util.DateProcessUtil;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.MD5Util;
import com.xa3ti.blackcat.base.util.MyPage;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.base.vo.FirstLevelMenu;
import com.xa3ti.blackcat.base.vo.MenuData;
import com.xa3ti.blackcat.base.vo.SecondLevelMenu;

/**
 * @Title: XaCmsUserServiceImpl.java
 * @Package com.xa3ti.hhrz.business.service.impl
 * @Description: 后台用户服务类
 * @author hchen
 * @date 2014年8月2日 上午9:46:30
 * @version V1.0
 */
@Service("xaCmsUserService")
public class XaCmsUserServiceImpl extends BaseService implements
		XaCmsUserService {

	private static final Logger log = Logger
			.getLogger(XaCmsUserServiceImpl.class);

	@Autowired
	private XaCmsUserRepository xaCmsUserRepository;

	@Autowired
	private XaCmsRoleRepository xaCmsRoleRepository;

	@Autowired
	private XaCmsRoleResourceRepository xaCmsRoleResourceRepository;

	@Autowired
	private XaCmsResourceRepository xaCmsResourceRepository;

	@Override
	@Transactional(readOnly = false)
	public XaResult<XaCmsUser> saveCmsUser(XaCmsUser xaCmsUser)
			throws BusinessException {
		List<XaCmsUser> userList = xaCmsUserRepository.findByUserName(xaCmsUser
				.getUserName());
		if (userList != null && userList.size() > 0) {
			throw new BusinessException("该用户名已经在注册,请使用其他用户名注册");
		}
		xaCmsUser.setStatus(XaConstant.UserStatus.status_normal);

		xaCmsUser.setPassword(MD5Util.getMD5String(xaCmsUser.getPassword()));

		// //AES加密md5密码
		try {
			AESCryptography crypto = new AESCryptography();
			xaCmsUser.setPassword(crypto.byte2String(crypto.encrypt(xaCmsUser
					.getPassword().getBytes())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// //

		xaCmsUser.setRegistDate(DateProcessUtil
				.getToday(DateProcessUtil.YYYYMMDDHHMMSS));
		// xaCmsUser.setRoleId(Long.parseLong(roleIds));
		xaCmsUser = xaCmsUserRepository.save(xaCmsUser);
		log.info("增加一条用户记录：" + xaCmsUser.getUserId());
		// createUserRole(xaCmsUser.getUserId(), roleIds);
		XaResult<XaCmsUser> result = new XaResult<XaCmsUser>();
		result.setObject(xaCmsUser);
		return result;
	}

	/**
	 * @Title: createUserRole
	 * @Description: 创建用户角色关系
	 * @param userId
	 * @param roleIds
	 */
	/*
	 * private void createUserRole(long userId, String roleIds) throws
	 * BusinessException { String[] ids=roleIds.split(","); for (int i = 0; i <
	 * ids.length; i++) { long roleId=Long.parseLong(ids[i]); XaCmsRole xcr =
	 * xaCmsRoleRepository.findOne(roleId); if(xcr!=null &&
	 * XaConstant.RoleStatus.status_normal==xcr.getStatus()){ XaCmsUserRole
	 * xur=new XaCmsUserRole(); xur.setRoleId(roleId); xur.setUserId(userId);
	 * xaCmsUserRoleRepository.save(xur); log.info("增加一条用户角色关系记录："+xur.getId());
	 * } } }
	 */

	@Override
	public Page<XaCmsUser> getCmsUserByCondition(
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		filters.put("status", new SearchFilter("status", Operator.NE, 9));
		Page<XaCmsUser> page = xaCmsUserRepository.findAll(
				DynamicSpecifications.bySearchFilter(filters.values(),
						XaCmsUser.class), pageable);
		List<XaCmsUser> list = page.getContent();
		/*
		 * for(XaCmsUser user : list){ List<XaCmsRole>
		 * xcurList=xaCmsRoleRepository.findRoleListByUserId(user.getUserId());
		 * user.setRoleList(xcurList); }
		 */
		int count = xaCmsUserRepository.findAll(
				DynamicSpecifications.bySearchFilter(filters.values(),
						XaCmsUser.class)).size();
		MyPage<XaCmsUser> resultPage = new MyPage<XaCmsUser>(
				pageable.getPageNumber(), pageable.getPageSize(), list, count);
		return resultPage;
	}

	@Override
	@Transactional(readOnly = false)
	public void delCmsUserByIds(String uids) throws BusinessException {
		String[] ids = uids.split(",");
		for (int j = 0; j < ids.length; j++) {
			XaCmsUser xaCmsUser = xaCmsUserRepository.findOne(ids[j]);
			if (xaCmsUser != null) {
				xaCmsUser.setStatus(XaConstant.UserStatus.status_delete);
				xaCmsUserRepository.save(xaCmsUser);
			}
		}
	}

	@Override
	public void operateCmsUserById(String id, int status)
			throws BusinessException {
		XaCmsUser xaCmsUser = xaCmsUserRepository.findOne(id);
		xaCmsUser.setStatus(status);
		xaCmsUserRepository.save(xaCmsUser);
	}

	@Override
	public int updateCmsUserPassword(String uid, String oldPassword,
			String newPassword) throws BusinessException {

		
		AESCryptography crypto = new AESCryptography();

		XaCmsUser xaCmsUser = xaCmsUserRepository.findOne(uid);
		if (xaCmsUser == null)
			return -1;

		try {
			String storeOldPassword = xaCmsUser.getPassword();
			String md5Password = new String(crypto.decrypt(storeOldPassword
					.getBytes()));

			if (MD5Util.getMD5String(oldPassword).equals(md5Password)) {
				xaCmsUser.setPassword(MD5Util.getMD5String(newPassword));

				// //AES加密md5密码
				xaCmsUser.setPassword(crypto.byte2String(crypto.encrypt(xaCmsUser
						.getPassword().getBytes())));

				xaCmsUserRepository.save(xaCmsUser);
				return 1;
			} else {
				return 0;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// //
		return 0;
	}

	@Override
	public XaResult<XaCmsUser> updateCmsUserNotPassword(XaCmsUser xaCmsUser)
			throws BusinessException {
		XaCmsUser oldXaCmsUser = xaCmsUserRepository.findOne(xaCmsUser
				.getUserId());
		if (oldXaCmsUser != null) {
			if (StringUtils.isNotEmpty(xaCmsUser.getPassword())) {
				oldXaCmsUser.setPassword(MD5Util.getMD5String(xaCmsUser
						.getPassword()));
				
				try{
				// //AES加密md5密码
				
				AESCryptography crypto = new AESCryptography();
				xaCmsUser.setPassword(crypto.byte2String(crypto.encrypt(xaCmsUser
						.getPassword().getBytes())));
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			oldXaCmsUser.setRoleId(xaCmsUser.getRoleId());
			oldXaCmsUser.setUserType(xaCmsUser.getUserType());
			oldXaCmsUser.setNickName(xaCmsUser.getNickName());
			xaCmsUserRepository.save(oldXaCmsUser);
			log.info("修改用户：" + oldXaCmsUser.getUserId() + "成功");
			// 删除原来的角色关系
			/*
			 * List<XaCmsUserRole>
			 * xcurList=xaCmsUserRoleRepository.findXacmsUserRoleByUserId
			 * (oldXaCmsUser.getUserId()); if(xcurList.size()>0)
			 * xaCmsUserRoleRepository.delete(xcurList);
			 * log.info("删除用户角色关系："+oldXaCmsUser.getUserId()+"成功");
			 */
			// createUserRole(xaCmsUser.getUserId(), rids);

		}
		XaResult<XaCmsUser> result = new XaResult<XaCmsUser>();
		result.setObject(oldXaCmsUser);
		return result;
	}

	@Override
	public List<XaCmsRole> getMyXaCmsRoleListByUserId(String uid)
			throws BusinessException {
		return null;
	}

	@Override
	public List<XaCmsResource> getMyXaCmsResourceListByUserId(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveUserRoleList(String uid, Integer[] roleIds)
			throws BusinessException {
		return 0;
	}

	@Override
	public Map<String, Object> getUserAndRole(String userId)
			throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isBlank(userId)) {
			map.put("user", null);
			map.put("userRole", null);
		} else {
			XaCmsUser xcu = xaCmsUserRepository.findOne(userId);
			map.put("user", xcu);
			// List<XaCmsUserRole>
			// xcurList=xaCmsUserRoleRepository.findXacmsUserRoleByUserId(userId);
			// map.put("userRole", xcurList);
		}
		List<XaCmsRole> xcrList = xaCmsRoleRepository
				.findAllXaCmsRole(XaConstant.RoleStatus.status_normal);
		map.put("role", xcrList);
		return map;
	}

	@Override
	public XaCmsUser findXaCmsUserByUserName(String userName, int status)
			throws BusinessException {
		XaCmsUser user = xaCmsUserRepository.findByUserName(userName, status);
		return user;
	}

	@Override
	public MenuData createUserResourceByUserId(String userId)
			throws BusinessException {

		// List<XaCmsUserRole>
		// urList=xaCmsUserRoleRepository.findXacmsUserRoleByUserId(userId);
		// //获取用户的角色关系记录，遍历关系，查找对应的资源
		XaCmsUser user = xaCmsUserRepository.findOne(userId);
		List<Long> resourceIdList = new ArrayList<Long>(); // 该用户拥有的资源ID集合
		if (user == null || user.getRoleId() == null) {
			throw new BusinessException("用户不存在或无权限");
		}
		List<XaCmsRoleResource> xcrrList = xaCmsRoleResourceRepository
				.findRoleResourceByRoleId(user.getRoleId());
		for (int j = 0; j < xcrrList.size(); j++) {
			XaCmsRoleResource xcrr = xcrrList.get(j);
			// 将该角色对应的资源ID集合去掉重复后存放在资源列表中。
			if (!resourceIdList.contains(xcrr.getResourceId()))
				resourceIdList.add(xcrr.getResourceId());
		}
		// 获取所有的一级菜单资源
		List<XaCmsResource> firstLevelResourceList = xaCmsResourceRepository
				.findParentResourceByStatus(
						XaConstant.ResourcesStatus.status_normal,
						XaConstant.ResourceShowType.menu_level);
		// 根据上面计算出的用户拥有的资源ID集合，和系统中所有的一级菜单比较，拿到拥有的一级资源集合
		List<XaCmsResource> returnFistResourceList = new ArrayList<XaCmsResource>(); // 该角色对应的一级菜单
		for (XaCmsResource xaCmsResource : firstLevelResourceList) {
			if (resourceIdList.contains(xaCmsResource.getResourceId())) {
				returnFistResourceList.add(xaCmsResource);
			}
		}
		List<FirstLevelMenu> fisrstList = new ArrayList<FirstLevelMenu>();
		for (XaCmsResource xaCmsResource : returnFistResourceList) {
			// 遍历拥有的一级资源，查询该资源下的所有有用的二级资源
			List<XaCmsResource> sencondXaCmsResourceList = xaCmsResourceRepository
					.findResourceByParentIdAndStatus(
							xaCmsResource.getResourceId(),
							XaConstant.ResourcesStatus.status_normal);
			List<SecondLevelMenu> slMenuList = new ArrayList<SecondLevelMenu>();
			for (XaCmsResource xaCmsResource2 : sencondXaCmsResourceList) {
				if (resourceIdList.contains(xaCmsResource2.getResourceId())) {
					// 和拥有的资源ID比较，如果包含，将该资源转换成二级菜单
					String menuUrl = "";
					if (xaCmsResource2.getResourceUrl() != null
							&& xaCmsResource2.getResourceUrl().length() > 1) {
						menuUrl = xaCmsResource2.getResourceUrl().substring(1); // 去掉第一个“/”
					}
					SecondLevelMenu slmenu = new SecondLevelMenu(
							xaCmsResource2.getResourceId() + "",
							xaCmsResource2.getResourceName(), menuUrl);
					slMenuList.add(slmenu);
				}
			}
			FirstLevelMenu flmenu = new FirstLevelMenu();
			flmenu.setId(xaCmsResource.getResourceId() + "");
			flmenu.setText(xaCmsResource.getResourceName());
			flmenu.setIcon(xaCmsResource.getIcon());
			flmenu.setList(slMenuList);

			fisrstList.add(flmenu);
		}

		// 要返回的菜单对象
		MenuData menuData = new MenuData();
		menuData.setTitle("导航栏");
		menuData.setItems(fisrstList);
		return menuData;
	}

	@Override
	public Boolean findUserNameExists(String userName) throws BusinessException {
		XaCmsUser user = xaCmsUserRepository.findByUserName(userName,
				XaConstant.UserStatus.status_normal);
		if (user != null)
			return false;
		return true;
	}

	@Override
	public XaCmsUser findUserNameExists(String userName,String pw) throws BusinessException {
		XaCmsUser user = xaCmsUserRepository.findByUserName(userName,
				XaConstant.UserStatus.status_normal);


		return user;
	}

}
