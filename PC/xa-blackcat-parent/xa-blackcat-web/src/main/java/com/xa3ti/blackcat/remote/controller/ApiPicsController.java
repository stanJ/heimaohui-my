package com.xa3ti.blackcat.remote.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.xa3ti.blackcat.base.controller.BaseController;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.business.service.PicsService;
import com.xa3ti.blackcat.business.entity.Pics;

import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.ThumbnailImage;

import com.xa3ti.blackcat.proxy.invoke.ParamaterMap;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;
import com.xa3ti.blackcat.base.util.Token;

/**
 * @author hchen
 * @version V1.0
 * @Title: ApiPicsController.java
 * @Package com.xa3ti.blackcat.remote.controller
 * @Description: 宣传照片信息接口
 * @date 2014年8月20日 下午3:03:06
 */
@Api(value = "Pics", description = "宣传照片接口", position = 10)
@Controller
@RequestMapping("/m/pics")
public class ApiPicsController extends BaseController {

    @Autowired
    private PicsService picsService;


    //@InitBinder
    //public void initBinder(WebDataBinder binder) {
    //    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //    dateFormat.setLenient(false);

    //    // true passed to CustomDateEditor constructor means convert empty String to null
    //    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    //}

    /**
     * @return XaResult<T>
     * @Title: findPicsList
     * @Description: 查询所有宣传照片信息
     */
    @ApiOperation(value = "查询所有宣传照片", notes = "查询所有宣传照片信息,当返回的code=1时，取出object进行显示,存放为数组对象")
    @ResponseBody
    @RequestMapping(value = "findPicsList", method = RequestMethod.POST)
    public XaResult<List<Pics>> findPicsList(
            @ApiParam("页号,字段名:nextPage,默认0,从第0页开始") @RequestParam(defaultValue = "0") Integer nextPage,
            @ApiParam("页长,字段名:pageSize,默认10") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam("数据状态,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认正常数据状态1") @RequestParam(defaultValue = "1") Integer status,
            @ApiParam("排序字段,字段名:sortData,选填,默认:[{property:'createTime',direction:'DESC'}]") @RequestParam(defaultValue = "[{property:'createTime',direction:'DESC'}]") String sortData,
            @ApiParam("过滤字段,字段名:jsonFilter,选填,默认:{},示例:{'search_EQ_field1':'value1','search_EQ_field2':'value2'},字段名称拼接规则search_为固定查询标识,EQ为等于,filed为字段名,不建议前端采用这种方式,建议前端使用指定查询字段名") @RequestParam(defaultValue = "{}") String jsonFilter,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
            HttpServletRequest request
    ) throws BusinessException {
        Token t = getToken(token);
        //Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, sortData);
        //Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
        //return picsService.findPicsVoByFilter(status, filterParams, pageable);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"nextPage", "pageSize", "status", "sortData", "jsonFilter"}, new Object[]{nextPage, pageSize, status, sortData, jsonFilter});

        ServiceInvocation invocation = new ServiceInvocation(request, "findPicsVoByFilter", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(picsService);
        Result r = invoker.invoke(invocation);

        XaResult<List<Pics>> x = (XaResult<List<Pics>>) r.getResult();

        XaResult<List<Pics>> xa = new XaResult<List<Pics>>();
        xa.setObject(x.getObject());
        return xa;
    }


    /**
     * @param nextPage   页号
     * @param pageSize   页长
     * @param status     默认查询正常状态1的数据,具体类型参照XaConstant.Status
     * @param sortDate   排序拼接字符串,类似[{property:'createTime',direction:'DESC'}],默认createTime排序
     * @param jsonFilter 过滤拼接字符串
     * @return XaResult<T>
     * @Title: findPicsEQStatusPage
     * @Description: (预留)分页查询Pics     * @param request		有需要的拿去使用
     */
    @ResponseBody
    @RequestMapping(value = "findPicsEQStatusPage", method = RequestMethod.POST)
    public XaResult<Page<Pics>> findPicsEQStatusPage(
            HttpServletRequest request,
            @ApiParam("页号,字段名:nextPage,默认0,从第0页开始") @RequestParam(defaultValue = "0") Integer nextPage,
            @ApiParam("页长,字段名:pageSize,默认10") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam("数据状态,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认正常数据状态1") @RequestParam(defaultValue = "1") Integer status,
            @ApiParam("排序字段,字段名:sortData,选填,默认:[{property:'createTime',direction:'DESC'}]") @RequestParam(defaultValue = "[{property:'createTime',direction:'DESC'}]") String sortData,
            @ApiParam("过滤字段,字段名:jsonFilter,选填,默认:{},示例:{'search_EQ_field1':'value1','search_EQ_field2':'value2'},字段名称拼接规则search_为固定查询标识,EQ为等于,filed为字段名,不建议前端采用这种方式,建议前端使用指定查询字段名") @RequestParam(defaultValue = "{}") String jsonFilter,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
    ) throws BusinessException {
        Token t = getToken(token);
        //Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, sortData);
        //Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
        //return picsService.findPicsEQStatusByFilter(status, filterParams, pageable);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"nextPage", "pageSize", "status", "sortData", "jsonFilter"}, new Object[]{nextPage, pageSize, status, sortData, jsonFilter});

        ServiceInvocation invocation = new ServiceInvocation(request, "findPicsEQStatusByFilter", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(picsService);
        Result r = invoker.invoke(invocation);

        XaResult<Page<Pics>> x = (XaResult<Page<Pics>>) r.getResult();

        XaResult<Page<Pics>> xa = new XaResult<Page<Pics>>();
        xa.setObject(x.getObject());
        return xa;
    }


    /**
     * @param modelId 对象id,一般是tid
     * @return XaResult<T>
     * @Title: findPicsById
     * @Description: 根据ID查找单条实体
     */
    @ApiOperation(value = "根据tid查询宣传照片", notes = "查询宣传照片详细信息,当返回的code=1时，取出object进行显示,存放为单体对象")
    @ResponseBody
    @RequestMapping(value = "findPicsById", method = RequestMethod.POST)
    public XaResult<Pics> findPicsById(
            @ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId") String modelId,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
            HttpServletRequest request
    ) throws BusinessException {
        Token t = getToken(token);
        //return picsService.findPics(modelId);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"modelId"}, new Object[]{modelId});

        ServiceInvocation invocation = new ServiceInvocation(request, "findPics", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(picsService);
        Result r = invoker.invoke(invocation);
        XaResult<Pics> x = (XaResult<Pics>) r.getResult();
        XaResult<Pics> xa = new XaResult<Pics>();
        xa.setObject(x.getObject());
        return xa;
    }

    /**
     * @param userId 操作人编号
     * @param model  操作对象
     * @return XaResult<T>
     * @Title: save        savePics	 * @Description: 	新增一条实体
     */
    @ApiOperation(value = "新增宣传照片", notes = "供前端前期填充数据测试使用,上线后删除;新增宣传照片,当返回的code=1时，保存成功后object返回对象")
    @ResponseBody
    @RequestMapping(value = "savePics", method = RequestMethod.POST)
    public XaResult<Pics> savePics(
            @ApiParam("专家id,字段名:expertId") @RequestParam(value = "expertId") String expertId,
            @ApiParam("序号,字段名:serial") @RequestParam(value = "serial", required = false) Integer serial,
            @ApiParam("照片,字段名:pic") @RequestParam(value = "pic") String pic,
            HttpServletRequest request,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
    ) throws BusinessException {
        Token t = getToken(token);
        Pics model = new Pics();
        model.setExpertId(expertId);
        model.setSerial(serial);
        model.setPic(pic);
        model.setStatus(1);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"userId", "modelId"}, new Object[]{t.getUserId(), model});
        ServiceInvocation invocation = new ServiceInvocation(request, "createPics", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(picsService);
        Result r = invoker.invoke(invocation);
        XaResult<Pics> x = (XaResult<Pics>) r.getResult();
        XaResult<Pics> xa = new XaResult<Pics>();
        xa.setObject(x.getObject());
        return xa;

        //return picsService.createPics(t.getUserId(),model);
    }

    /**
     * @param userId 操作人id
     * @param model  操作对象
     * @return XaResult<T>
     * @Title: updatePics     * @Description: 	修改一条实体
     */
    @ApiOperation(value = "修改宣传照片", notes = "修改宣传照片,当返回的code=1时，保存成功后object返回对象")
    @ResponseBody
    @RequestMapping(value = "updatePics", method = RequestMethod.POST)
    public XaResult<Pics> updatePics(
            @ApiParam("专家id,字段名:expertId") @RequestParam(value = "expertId") String expertId,
            @ApiParam("序号,字段名:serial") @RequestParam(value = "serial", required = false,defaultValue = "1") Integer serial,
            @ApiParam("照片,字段名:pic") @RequestParam(value = "pic") String pic,
            @ApiParam("id,字段名:id") @RequestParam(value = "tid") String tid,
            HttpServletRequest request,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
    ) throws BusinessException {
        Token t = getToken(token);
        Pics model = new Pics();
        model.setExpertId(expertId);
        model.setSerial(serial);
        model.setPic(pic);
        model.setTid(tid);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"userId", "modelId"}, new Object[]{t.getUserId(), model});
        ServiceInvocation invocation = new ServiceInvocation(request, "updatePics", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(picsService);
        Result r = invoker.invoke(invocation);
        XaResult<Pics> x = (XaResult<Pics>) r.getResult();
        XaResult<Pics> xa = new XaResult<Pics>();
        xa.setObject(x.getObject());
        return xa;

        //return picsService.updatePics(t.getUserId(),model);
    }

    /**
     * @param modelId 对象id,一般对应的tid
     * @param status  操作类型:-1锁定,0无效,1正常,2发布,3删除,默认删除操作 参考XaConstant.Status
     * @return XaResult<T>
     * @Title: operatePicsById
     * @Description: 操作一个实体状态, 根据status进行操作
     */
    @ApiOperation(value = "更改实体状态", notes = "操作一个实体状态,根据status进行操作")
    @ResponseBody
    @RequestMapping(value = "operatePicsById", method = RequestMethod.POST)
    public XaResult<Pics> operatePicsById(
            HttpServletRequest request,
            @ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId") String modelId,
            @ApiParam("操作类型,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认删除操作") @RequestParam(defaultValue = "3") Integer status,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
    ) throws BusinessException {
        Token t = getToken(token);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"userId", "modelId", "status"}, new Object[]{t.getUserId(), modelId, status});

        ServiceInvocation invocation = new ServiceInvocation(request, "operatePics", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(picsService);
        Result r = invoker.invoke(invocation);

        XaResult x = (XaResult) r.getResult();

        return x;
        //return picsService.operatePics(t.getUserId(),modelId,status);
    }

    /**
     * @param photoFile 文件名称
     * @param request
     * @return XaResult<T>
     * @Title: upload
     * @Description: 图片上传
     */
    @ApiOperation(value = "图片上传", notes = "异步图片上传,返回上传图片的地址、宽、高")
    @ResponseBody
    @RequestMapping(value = "photoUpload", method = RequestMethod.POST)
    public XaResult<Map<String, Object>> photoUpload(
            @ApiParam("上传的图片,字段名:photoFile,必填") @RequestParam(value = "photoFile") MultipartFile photoFile,
            HttpServletRequest request
    ) throws BusinessException {
        XaResult<Map<String, Object>> result = new XaResult<Map<String, Object>>();
        String root = request.getSession().getServletContext().getRealPath("/");
        String picturePath = "/upload/pics";
        String ext = photoFile.getOriginalFilename().substring(photoFile.getOriginalFilename().lastIndexOf("."));
        String newName = new Date().getTime() + ext;


        //////////Thumbnail and file server support //////////////
        root = Settings.getInstance().getString("storage.base.path") + "/";
        String rp = Settings.getInstance().getString("storage.original_image.path") + picturePath;
        File filedict = new File(root + rp);
        if (!filedict.exists()) {
            filedict.mkdirs();
        }
        File targetFile = new File(root + rp + File.separator + newName);
        //////////////////////////////////////////////////////////

        try {
            if (StringUtils.equalsIgnoreCase(ext, ".jpg") || StringUtils.equalsIgnoreCase(ext, ".png")) {
                photoFile.transferTo(targetFile);
                BufferedImage bimg = ImageIO.read(targetFile);
                int width = bimg.getWidth();
                int height = bimg.getHeight();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("pictureHeight", height);
                map.put("pictureWidth", width);
                map.put("picturePath", rp + "/" + newName);

                ////////////Thumbnail and file server support ///////////
                long size = photoFile.getSize();
                if (size > 200 * 1024) {
                    String thumbnailPath = Settings.getInstance().getString("storage.base.path") + "/" +
                            Settings.getInstance().getString("storage.thumbnail_image.path") + picturePath + File.separator + newName;
                    String relativePath = Settings.getInstance().getString("storage.thumbnail_image.path") + picturePath + File.separator + newName;
                    String tUrl = ThumbnailImage.createThumbnailImage(thumbnailPath, relativePath, root + picturePath + File.separator + newName);
                    if (tUrl != null) {
                        map.put("pictureHeight", Settings.getInstance().getString("thumbnail.height"));
                        map.put("pictureWidth", Settings.getInstance().getString("thumbnail.width"));
                        map.put("picturePath", tUrl);
                    }
                }
                //////////////////////////////////////////////////

                result.setObject(map);
                return result;
            } else {
                throw new BusinessException("上传文件类型不允许,请上传jpg/png图片");
            }
        } catch (IllegalStateException e) {
            throw new BusinessException("图片上传失败");
        } catch (IOException e) {
            throw new BusinessException("图片上传失败");
        } catch (Exception e) {
            throw new BusinessException("图片上传失败");
        }
    }
}

