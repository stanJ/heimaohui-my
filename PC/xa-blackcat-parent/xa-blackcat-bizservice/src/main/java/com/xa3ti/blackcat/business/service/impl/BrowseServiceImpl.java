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
import com.xa3ti.blackcat.business.entity.Browse;
import com.xa3ti.blackcat.business.repository.BrowseRepository;
import com.xa3ti.blackcat.business.service.BrowseService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("BrowseService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class BrowseServiceImpl extends BaseService implements BrowseService {
	public static Logger  log = Logger.getLogger(BrowseServiceImpl.class);

	@Autowired
	private BrowseRepository browseRepository;

	@Override
	public XaResult<Page<Browse>> findBrowseNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Browse.class.getName(), QueryDao.class);
		Page<Browse> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			page  = browseRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Browse.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, Browse.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Browse.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
		
		
		 
		XaResult<Page<Browse>> xr = new XaResult<Page<Browse>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Browse>> findBrowseEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Browse.class.getName(), QueryDao.class);
		Page<Browse> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = browseRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Browse.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Browse.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Browse.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
					
		XaResult<Page<Browse>> xr = new XaResult<Page<Browse>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Browse>> findBrowseVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Browse.class.getName(), QueryDao.class);
		Page<Browse> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = browseRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Browse.class), pageable);
		
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Browse.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Browse.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			//
		}
					
		XaResult<List<Browse>> xr = new XaResult<List<Browse>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Browse> createBrowse( String  userId,Browse model) throws BusinessException {

		XaResult<Browse> xr = new XaResult<Browse>();
		//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if(userId != null){
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");
		Browse obj = browseRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Browse> updateBrowse( String  userId,Browse model) throws BusinessException {
		Browse obj = browseRepository.findOne(model.getTid());
		XaResult<Browse> xr = new XaResult<Browse>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setUserId(model.getUserId());
			 						 			     obj.setBtime(model.getBtime());
			 						 			     obj.setExpertId(model.getExpertId());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);
			obj = browseRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Browse> operateBrowse(
			 String  userId, String  modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		Browse obj = browseRepository.findByTidAndStatusNot(modelId,status);
		XaResult<Browse> xr = new XaResult<Browse>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = browseRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Browse> findBrowse( String  modelId) throws BusinessException {
		Browse obj = browseRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<Browse> xr = new XaResult<Browse>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Browse> multiOperateBrowse(
			 String  userId,String modelIds,Integer status) throws BusinessException {
		XaResult<Browse> xr = new XaResult<Browse>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					Browse obj = browseRepository.findByTidAndStatusNot( id ,status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = browseRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				Browse obj = browseRepository.findByTidAndStatusNot( modelIds ,status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = browseRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.business.service.BrowseService#getBrowseNums(java.lang.String, java.util.Date)
	 */
	@Override
	public Integer getBrowseNums(String uid, Date curDate,Integer btype)
			throws BusinessException {
		Date firstDate=com.xa3ti.blackcat.base.util.DateUtil.todayFirstPointDate(curDate);
		Date tommorow=com.xa3ti.blackcat.base.util.DateUtil.addDay(curDate, 1);
		Date tommorowFirstDate=com.xa3ti.blackcat.base.util.DateUtil.todayFirstPointDate(tommorow);
		
		return browseRepository.findBrowseByUserIdDate(uid, firstDate, tommorowFirstDate,btype);
		
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.business.service.BrowseService#checkBrowsed(java.lang.String, java.util.Date, java.lang.Integer, java.lang.String)
	 */
	@Override
	public Boolean checkBrowsed(String uid, Date curDate, Integer btype,
			String modelId) throws BusinessException {
		Date firstDate=com.xa3ti.blackcat.base.util.DateUtil.todayFirstPointDate(curDate);
		Date tommorow=com.xa3ti.blackcat.base.util.DateUtil.addDay(curDate, 1);
		Date tommorowFirstDate=com.xa3ti.blackcat.base.util.DateUtil.todayFirstPointDate(tommorow);
		
		List<Browse> list= browseRepository.findBrowseByUserId(uid, firstDate, tommorowFirstDate,btype,modelId);
		return list!=null&&list.size()>0?true:false;
	}

	@Override
	public boolean checkCanBrowsed(Browse browse,Integer tokenType) throws BusinessException {
		boolean flag = false;
		try {
			if (null != browse) {
				String url = browse.getUserUrl();
				//判断是否设置，且有效
                Map<String,Object> resMp = getUserTokenURLCount(url, tokenType);
				if(null != resMp && resMp.size()>0) {
					Integer cnt = getBrowerCountByUser(browse);
					Integer tokenCnt = resMp.get("use_count") == null ? 0 : Integer.valueOf(resMp.get("use_count").toString());
					flag = cnt > tokenCnt;
				}

			}
		}catch (Exception e){
			throw new BusinessException(e.getMessage());
		}
		return flag;
	}

	@Override
	public void checkSaveBrowsed(Browse browse) throws BusinessException{
		Integer count = 0;
		if (null != browse) {
			String url = browse.getUserUrl();

			String userParams = browse.getUserParams();
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(browse.getBtime());
			if (null != userParams && userParams.trim().length() > 0) {
				StringBuffer sql = new StringBuffer();
				sql.append("select COUNT(1)  from tb_xa_browse t \n");
				sql.append(" where t.user_url like '%").append(url).append("%' \n");
				sql.append("   and t.user_id = '").append(browse.getUserId()).append("' \n");
				sql.append("   and t.user_params = '"+userParams+"'  \n");
				sql.append("   and TO_DAYS(t.btime)=TO_DAYS(str_to_date('").append(time).append("','%Y-%m-%d %H:%i:%s')) ");

				count = DBHelper.count(sql.toString());
				if(count == 0){
					createBrowse(browse.getUserId(),browse);
				}
			}
		}
	}

	public Map<String,Object> findUserBrowseInfo(Browse browse,Integer tokenType) throws BusinessException{
		Map<String,Object> resMp = null;
		try {
			if (null != browse) {
				String url = browse.getUserUrl();
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(browse.getBtime());
				StringBuffer sql = new StringBuffer();
				sql.append(" select user_params from tb_xa_browse b                       								  \n");
				sql.append("  where b.user_url like '%").append(url).append("%' 										  \n");
				sql.append("   and TO_DAYS(b.btime) =TO_DAYS(str_to_date('").append(time).append("','%Y-%m-%d %H:%i:%s')) \n");
				sql.append("   and b.user_id = '").append(browse.getUserId()).append("' 								  \n");
				sql.append("  order by b.btime desc                                         							  \n");

				List<Map<String,Object> > list = DBHelper.query(sql.toString());
				if(null != list && list.size()>0){
					resMp = getUserTokenURLCount(browse.getUserUrl(),tokenType);
					resMp.put("params",list);
				}
			}
		}catch (Exception e){
			throw new BusinessException(e.getMessage());
		}
		return resMp;
	}

	public Map<String,Object> findUserBrowseParentInfo(String url,String userId, Integer type) throws BusinessException{
		Map<String,Object> resMp = null;
		String chkUrl = "";
		try {
		   if( url.indexOf("/m/expert/findExpertEQStatusPage") != -1){
			    chkUrl = "/m/expert/findExpertById";
		   } else if(url.indexOf("/m/customer/findCustomerEQStatusPage")!= -1){
			   chkUrl = "/m/customer/findCustomerById";
		   }
           if(null != chkUrl && chkUrl.trim().length()>0) {
			   String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			   StringBuffer sql = new StringBuffer();
			   sql.append(" select user_params from tb_xa_browse t                       							  		\n");
			   sql.append("  where t.user_url like '%").append(chkUrl).append("%' 										  		\n");
			   sql.append("   and TO_DAYS(t.btime) =TO_DAYS(str_to_date('").append(time).append("','%Y-%m-%d %H:%i:%s')) 	\n");
			   sql.append("   and t.user_id = '").append(userId).append("' 													\n");
			   sql.append("  order by t.btime desc                                         									\n");


			   List<Map<String, Object>> list = DBHelper.query(sql.toString());
			   if (null != list && list.size() > 0) {
				   resMp = getUserTokenURLCount(chkUrl, type);
				   resMp.put("params", list);
			   }
		   }
		}catch (Exception e){
			throw new BusinessException(e.getMessage());
		}
		return resMp;
	}

	@Override
	public void exportBrowseEQStatusByFilter(Integer status, Map<String, Object> filterParams, Pageable pageable, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		XaResult<Page<Browse>> xa=findBrowseEQStatusByFilter(status,filterParams,pageable);

		Page<Browse> page=xa.getObject();

		List<Browse> list=page.getContent();


		List<String> headerList=new ArrayList<String>();

		headerList.add("帐号");
		headerList.add("时间");
		List<String> displayList=new ArrayList<String>();
		displayList.add("userName");
		displayList.add("btime");

		String fileName=ExcelExportUtil.createExcel(request,"浏览记录","浏览记录",headerList,list,displayList,Browse.class);

		ExcelExportUtil.downLoadExcel(fileName,request,response);

	}

	/**
	 * 获取用户的设置次数
	 * @param tokenType
	 * @return
	 */
	private   Map<String,Object>  getUserTokenURLCount(String url,Integer tokenType) throws Exception {
		Map<String, Object> resMp = null;
		if(null != url && null != tokenType) {

			StringBuffer sql = new StringBuffer();
			sql.append("select use_count from tb_xa_token_url  \n");
			sql.append(" where token_type = ").append(tokenType).append(" \n");
			sql.append(" and  position( url in '" + url + "' ) != 0  \n");
			sql.append(" and status = 1 \n");

			resMp = DBHelper.load(sql.toString());

		}
		return resMp;
	}

	public boolean isValidataUrl(String url,Integer tokenType){
		boolean flag = false;
		Map<String, Object> resMp = null;
		try {
			resMp = getUserTokenURLCount(url,tokenType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null != resMp && resMp.size()>0){
			flag = true;

		}
		System.out.println("######(isValidataUrl:"+true+")########");
		return flag;
	}



	private Integer getBrowerCountByUser(Browse browse){
		Integer count = 0;
		if (null != browse) {
			String url = browse.getUserUrl();

			String userParams = browse.getUserParams();
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(browse.getBtime());

			if (null != userParams && userParams.trim().length() > 0) {
				StringBuffer sql = new StringBuffer();
				sql.append("select COUNT(1)  from tb_xa_browse t \n");
				sql.append(" where t.user_url like '%").append(url).append("%' \n");
				sql.append("   and t.user_id = '").append(browse.getUserId()).append("' \n");
				sql.append("   and     t.user_params <> '"+userParams+"'  \n");
				sql.append("   and TO_DAYS(t.btime)=TO_DAYS(str_to_date('").append(time).append("','%Y-%m-%d %H:%i:%s')) ");


				count = DBHelper.count(sql.toString());
			}
		}
		return count;
	}

}
