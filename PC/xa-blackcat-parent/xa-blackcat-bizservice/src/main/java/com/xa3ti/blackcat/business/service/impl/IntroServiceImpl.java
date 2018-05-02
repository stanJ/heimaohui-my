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
import com.xa3ti.blackcat.business.entity.Intro;
import com.xa3ti.blackcat.business.repository.IntroRepository;
import com.xa3ti.blackcat.business.service.IntroService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("IntroService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class IntroServiceImpl extends BaseService implements IntroService {
    public static Logger log = Logger.getLogger(IntroServiceImpl.class);

    @Autowired
    private IntroRepository introRepository;

    @Override
    public XaResult<Page<Intro>> findIntroNEStatusByFilter(
            Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
        QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
                Intro.class.getName(), QueryDao.class);
        Page<Intro> page = null;
        if (BaseConstant.DAO.JPA.equals(an.method())) {

            Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
            if (status == null) {// 默认显示非删除的所有数据
                status = XaConstant.Status.delete;
            }
            filters.put("status", new SearchFilter("status", Operator.NE, status));
            page = introRepository.findAll(DynamicSpecifications
                    .bySearchFilter(filters.values(), Intro.class), pageable);

        } else if (BaseConstant.DAO.DYNASQL.equals(an.method())) {
            String sqlFilter = (String) filterParams.get(BaseConstant.SQLFILTERKEY);
            if (!StringUtils.isBlank(sqlFilter)) { //混合查
                sqlFilter = sqlFilter.substring(1);
                sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);//去除{}
                sqlFilter = "(" + sqlFilter + ")" + " and (status!=" + status + ")";
                page = QuerySqlExecutor.find(null, Intro.class,
                        pageable, sqlFilter);
            } else {//传统and查
                Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
                if (status == null) {// 默认显示非删除的所有数据
                    status = XaConstant.Status.delete;
                }
                filters.put("status", new SearchFilter("status", Operator.NE, status));

                page = QuerySqlExecutor.find(filters.values(), Intro.class,
                        pageable);
            }
        } else if (BaseConstant.DAO.MYBATIS.equals(an.method())) {
            //
        }


        XaResult<Page<Intro>> xr = new XaResult<Page<Intro>>();
        xr.setObject(page);
        return xr;
    }

    @Override
    public XaResult<Page<Intro>> findIntroEQStatusByFilter(
            Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
        QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
                Intro.class.getName(), QueryDao.class);
        Page<Intro> page = null;
        if (BaseConstant.DAO.JPA.equals(an.method())) {

            Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
            if (status == null) {// 默认显示非删除的所有数据
                status = XaConstant.Status.valid;
            }
            filters.put("status", new SearchFilter("status", Operator.EQ, status));
            page = introRepository.findAll(DynamicSpecifications
                    .bySearchFilter(filters.values(), Intro.class), pageable);

        } else if (BaseConstant.DAO.DYNASQL.equals(an.method())) {
            String sqlFilter = (String) filterParams.get(BaseConstant.SQLFILTERKEY);
            if (!StringUtils.isBlank(sqlFilter)) { //混合查
                sqlFilter = sqlFilter.substring(1);
                sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);//去除{}
                sqlFilter = "(" + sqlFilter + ")" + " and (status==" + status + ")";
                page = QuerySqlExecutor.find(null, Intro.class,
                        pageable, sqlFilter);
            } else {//传统and查
                Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
                if (status == null) {// 默认显示非删除的所有数据
                    status = XaConstant.Status.valid;
                }
                filters.put("status", new SearchFilter("status", Operator.EQ, status));

                page = QuerySqlExecutor.find(filters.values(), Intro.class,
                        pageable);
            }
        } else if (BaseConstant.DAO.MYBATIS.equals(an.method())) {
            //
        }

        XaResult<Page<Intro>> xr = new XaResult<Page<Intro>>();
        xr.setObject(page);
        return xr;
    }

    @Override
    public XaResult<List<Intro>> findIntroVoByFilter(
            Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
        QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
                Intro.class.getName(), QueryDao.class);
        Page<Intro> page = null;
        if (an.method().equals(BaseConstant.DAO.JPA)) {

            Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
            if (status == null) {// 默认显示非删除的所有数据
                status = XaConstant.Status.delete;
            }
            filters.put("status", new SearchFilter("status", Operator.EQ, status));
            page = introRepository.findAll(DynamicSpecifications
                    .bySearchFilter(filters.values(), Intro.class), pageable);

        } else if (an.method().equals(BaseConstant.DAO.DYNASQL)) {
            String sqlFilter = (String) filterParams.get(BaseConstant.SQLFILTERKEY);
            if (!StringUtils.isBlank(sqlFilter)) { //混合查
                sqlFilter = sqlFilter.substring(1);
                sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);//去除{}
                sqlFilter = "(" + sqlFilter + ")" + " and (status==" + status + ")";
                page = QuerySqlExecutor.find(null, Intro.class,
                        pageable, sqlFilter);
            } else {//传统and查
                Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
                if (status == null) {// 默认显示非删除的所有数据
                    status = XaConstant.Status.valid;
                }
                filters.put("status", new SearchFilter("status", Operator.EQ, status));

                page = QuerySqlExecutor.find(filters.values(), Intro.class,
                        pageable);
            }
        } else if (an.method().equals(BaseConstant.DAO.MYBATIS)) {
            //
        }

        XaResult<List<Intro>> xr = new XaResult<List<Intro>>();
        xr.setObject(page.getContent());
        return xr;
    }

    @Override
    @XALogger(need = true)
    public XaResult<Intro> createIntro(String userId, Intro model) throws BusinessException {

        XaResult<Intro> xr = new XaResult<Intro>();
        //此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
        if (userId != null) {
            model.setCreateUser(userId);
            model.setModifyUser(userId);
        }
        model.setModifyDescription("初始创建");
        Intro obj = introRepository.save(model);
        xr.setObject(obj);
        return xr;
    }

    @Override
    @XALogger(need = true)
    public XaResult<Intro> updateIntro(String userId, Intro model) throws BusinessException {
        Intro obj = introRepository.findOne(model.getTid());
        XaResult<Intro> xr = new XaResult<Intro>();
        if (XaUtil.isNotEmpty(obj)) {
            obj.setFileName(model.getFileName());
            obj.setUptime(model.getUptime());
            obj.setFilePath(model.getFilePath());
            obj.setIsTop(model.getIsTop());
            //此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
            obj.setModifyUser(userId);
            obj = introRepository.save(obj);
            xr.setObject(obj);
        } else {
            throw new BusinessException(XaConstant.Message.object_not_find);
        }

        return xr;
    }

    @Override
    @XALogger(need = true)
    public XaResult<Intro> operateIntro(
            String userId, String modelId, Integer status) throws BusinessException {

        log.info("modelId:"+modelId+";status:"+status);
        if (status == null) {
            status = XaConstant.Status.delete;
        }
        Intro obj = introRepository.findByTidAndStatusNot(modelId);
        XaResult<Intro> xr = new XaResult<Intro>();
        if (XaUtil.isNotEmpty(obj)) {
            obj.setStatus(status);
            obj.setModifyUser(userId);
            obj = introRepository.save(obj);
            xr.setObject(obj);
        } else {
            throw new BusinessException(XaConstant.Message.object_not_find);
        }
        return xr;
    }

    @Override
    public XaResult<Intro> findIntro(String modelId) throws BusinessException {
        Intro obj = introRepository.findByTidAndStatusNot(modelId, XaConstant.Status.delete);
        XaResult<Intro> xr = new XaResult<Intro>();
        if (XaUtil.isNotEmpty(obj)) {
            xr.setObject(obj);
        } else {
            throw new BusinessException(XaConstant.Message.object_not_find);
        }
        return xr;
    }

    @Override
    @XALogger(need = true)
    public XaResult<Intro> multiOperateIntro(
            String userId, String modelIds, Integer status) throws BusinessException {
        XaResult<Intro> xr = new XaResult<Intro>();
        if (status == null) {
            status = XaConstant.Status.delete;
        }
        if (modelIds != null) {
            if (StringUtils.indexOf(modelIds, ",") > 0) {
                String[] ids = modelIds.split(",");
                for (String id : ids) {
                    Intro obj = introRepository.findByTidAndStatusNot(id, status);
                    if (XaUtil.isNotEmpty(obj)) {
                        obj.setStatus(status);
                        obj.setModifyUser(userId);
                        obj = introRepository.save(obj);
                    } else {
                        throw new BusinessException(XaConstant.Message.object_not_find);
                    }
                }
            } else {
                Intro obj = introRepository.findByTidAndStatusNot(modelIds, status);
                if (XaUtil.isNotEmpty(obj)) {
                    obj.setStatus(status);
                    obj.setModifyUser(userId);
                    obj = introRepository.save(obj);
                } else {
                    throw new BusinessException(XaConstant.Message.object_not_find);
                }
            }
        }
        return xr;
    }


}
