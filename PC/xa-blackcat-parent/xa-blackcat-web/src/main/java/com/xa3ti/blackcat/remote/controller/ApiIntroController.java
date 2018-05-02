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
import com.xa3ti.blackcat.business.service.IntroService;
import com.xa3ti.blackcat.business.entity.Intro;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.ThumbnailImage;
import com.xa3ti.blackcat.proxy.invoke.ParamaterMap;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;
import com.xa3ti.blackcat.base.util.Token;

/**
 * @Title: ApiIntroController.java
 * @Package com.xa3ti.blackcat.remote.controller
 * @Description: 公司简介信息接口
 * @date 2014年8月20日 下午3:03:06
 */
@Api(value = "Intro", description = "公司简介接口", position = 10)
@Controller
@RequestMapping("/m/intro")
public class ApiIntroController extends BaseController {

    @Autowired
    private IntroService introService;


    //@InitBinder
    //public void initBinder(WebDataBinder binder) {
    //    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //    dateFormat.setLenient(false);

    //    // true passed to CustomDateEditor constructor means convert empty String to null
    //    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    //}

    /**
     * @return XaResult<T>
     * @Title: findIntroList
     * @Description: 查询所有公司简介信息
     */
    @ApiOperation(value = "查询所有公司简介", notes = "查询所有公司简介信息,当返回的code=1时，取出object进行显示,存放为数组对象")
    @ResponseBody
    @RequestMapping(value = "findIntroList", method = RequestMethod.POST)
    public XaResult<List<Intro>> findIntroList(
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
        //return introService.findIntroVoByFilter(status, filterParams, pageable);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"nextPage", "pageSize", "status", "sortData", "jsonFilter"}, new Object[]{nextPage, pageSize, status, sortData, jsonFilter});

        ServiceInvocation invocation = new ServiceInvocation(request, "findIntroVoByFilter", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(introService);
        Result r = invoker.invoke(invocation);

        XaResult<List<Intro>> x = (XaResult<List<Intro>>) r.getResult();

        XaResult<List<Intro>> xa = new XaResult<List<Intro>>();
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
     * @Title: findIntroEQStatusPage
     * @Description: (预留)分页查询Intro     * @param request		有需要的拿去使用
     */
    @ResponseBody
    @RequestMapping(value = "findIntroEQStatusPage", method = RequestMethod.POST)
    public XaResult<Page<Intro>> findIntroEQStatusPage(
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
        //return introService.findIntroEQStatusByFilter(status, filterParams, pageable);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"nextPage", "pageSize", "status", "sortData", "jsonFilter"}, new Object[]{nextPage, pageSize, status, sortData, jsonFilter});

        ServiceInvocation invocation = new ServiceInvocation(request, "findIntroEQStatusByFilter", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(introService);
        Result r = invoker.invoke(invocation);

        XaResult<Page<Intro>> x = (XaResult<Page<Intro>>) r.getResult();

        XaResult<Page<Intro>> xa = new XaResult<Page<Intro>>();
        xa.setObject(x.getObject());
        return xa;
    }


    /**
     * @param modelId 对象id,一般是tid
     * @return XaResult<T>
     * @Title: findIntroById
     * @Description: 根据ID查找单条实体
     */
    @ApiOperation(value = "根据tid查询公司简介", notes = "查询公司简介详细信息,当返回的code=1时，取出object进行显示,存放为单体对象")
    @ResponseBody
    @RequestMapping(value = "findIntroById", method = RequestMethod.POST)
    public XaResult<Intro> findIntroById(
            @ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId") String modelId,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
            HttpServletRequest request
    ) throws BusinessException {
        Token t = getToken(token);
        //return introService.findIntro(modelId);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"modelId"}, new Object[]{modelId});

        ServiceInvocation invocation = new ServiceInvocation(request, "findIntro", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(introService);
        Result r = invoker.invoke(invocation);
        XaResult<Intro> x = (XaResult<Intro>) r.getResult();
        XaResult<Intro> xa = new XaResult<Intro>();
        xa.setObject(x.getObject());
        return xa;
    }

    /**
     * @param userId 操作人编号
     * @param model  操作对象
     * @return XaResult<T>
     * @Title: save        saveIntro	 * @Description: 	新增一条实体
     */
    @ApiOperation(value = "新增公司简介", notes = "供前端前期填充数据测试使用,上线后删除;新增公司简介,当返回的code=1时，保存成功后object返回对象")
    @ResponseBody
    @RequestMapping(value = "saveIntro", method = RequestMethod.POST)
    public XaResult<Intro> saveIntro(
            @ApiParam("文件名称,字段名:fileName") @RequestParam(value = "fileName") String fileName,
            @ApiParam("上传时间,字段名:uptime") @RequestParam(value = "uptime") Date uptime,
            @ApiParam("是否置顶,字段名:isTop") @RequestParam(value = "isTop") Boolean isTop,
            @ApiParam("文件路径,字段名:filePath") @RequestParam(value = "filePath") String filePath,
            HttpServletRequest request,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
    ) throws BusinessException {
        Token t = getToken(token);
        Intro model = new Intro();
        model.setFileName(fileName);
        model.setUptime(uptime);
        model.setFilePath(filePath);
        model.setIsTop(isTop);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"userId", "modelId"}, new Object[]{t.getUserId(), model});
        ServiceInvocation invocation = new ServiceInvocation(request, "createIntro", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(introService);
        Result r = invoker.invoke(invocation);
        XaResult<Intro> x = (XaResult<Intro>) r.getResult();
        XaResult<Intro> xa = new XaResult<Intro>();
        xa.setObject(x.getObject());
        return xa;

        //return introService.createIntro(t.getUserId(),model);
    }

    /**
     * @param userId 操作人id
     * @param model  操作对象
     * @return XaResult<T>
     * @Title: updateIntro     * @Description: 	修改一条实体
     */
    @ApiOperation(value = "修改公司简介", notes = "修改公司简介,当返回的code=1时，保存成功后object返回对象")
    @ResponseBody
    @RequestMapping(value = "updateIntro", method = RequestMethod.POST)
    public XaResult<Intro> updateIntro(
            @ApiParam("文件名称,字段名:fileName") @RequestParam(value = "fileName") String fileName,
            @ApiParam("上传时间,字段名:uptime") @RequestParam(value = "uptime") Date uptime,
            @ApiParam("是否置顶,字段名:isTop") @RequestParam(value = "isTop") Boolean isTop,
            @ApiParam("文件路径,字段名:filePath") @RequestParam(value = "filePath") String filePath,
            @ApiParam("id,字段名:id") @RequestParam(value = "tid") String tid,
            HttpServletRequest request,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
    ) throws BusinessException {
        Token t = getToken(token);
        Intro model = new Intro();
        model.setFileName(fileName);
        model.setUptime(uptime);
        model.setFilePath(filePath);
        model.setIsTop(isTop);
        model.setTid(tid);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"userId", "modelId"}, new Object[]{t.getUserId(), model});
        ServiceInvocation invocation = new ServiceInvocation(request, "updateIntro", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(introService);
        Result r = invoker.invoke(invocation);
        XaResult<Intro> x = (XaResult<Intro>) r.getResult();
        XaResult<Intro> xa = new XaResult<Intro>();
        xa.setObject(x.getObject());
        return xa;

        //return introService.updateIntro(t.getUserId(),model);
    }

    /**
     * @param modelId 对象id,一般对应的tid
     * @param status  操作类型:-1锁定,0无效,1正常,2发布,3删除,默认删除操作 参考XaConstant.Status
     * @return XaResult<T>
     * @Title: operateIntroById
     * @Description: 操作一个实体状态, 根据status进行操作
     */
    @ApiOperation(value = "更改实体状态", notes = "操作一个实体状态,根据status进行操作")
    @ResponseBody
    @RequestMapping(value = "operateIntroById", method = RequestMethod.POST)
    public XaResult<Intro> operateIntroById(
            HttpServletRequest request,
            @ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId") String modelId,
            @ApiParam("操作类型,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认删除操作") @RequestParam(defaultValue = "3") Integer status,
            @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
    ) throws BusinessException {
        Token t = getToken(token);

        ParamaterMap paramaterMap = new ParamaterMap(new String[]{"userId", "modelId", "status"}, new Object[]{t.getUserId(), modelId, status});

        ServiceInvocation invocation = new ServiceInvocation(request, "operateIntro", paramaterMap, null);
        ServiceInvoker invoker = new ServiceInvoker(introService);
        Result r = invoker.invoke(invocation);

        XaResult x = (XaResult) r.getResult();

        return x;
        //return introService.operateIntro(t.getUserId(),modelId,status);
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
        String picturePath = "/upload/intro";
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

