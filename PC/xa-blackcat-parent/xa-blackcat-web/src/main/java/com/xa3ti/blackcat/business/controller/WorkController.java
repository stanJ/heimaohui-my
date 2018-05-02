package com.xa3ti.blackcat.business.controller;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.controller.BaseController;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.business.entity.Work;
import com.xa3ti.blackcat.business.service.WorkService;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.ThumbnailImage;

import com.xa3ti.blackcat.proxy.invoke.ParamaterMap;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;
import com.xa3ti.blackcat.base.util.Token;


/**
 * @Title: WorkController.java
 * @Package com.xa3ti.webstart.business.controller
 * @Description: 合作记录控制器
 * @author hchen
 * @date 2014年10月14日 下午6:35:35
 * @version V1.0
 */
@Controller
@RequestMapping("/cms/work")
public class WorkController extends BaseController {

	@Autowired
	private WorkService workService;
	
	//@InitBinder
	//public void initBinder(WebDataBinder binder) {
	//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//    dateFormat.setLenient(false);

	//    // true passed to CustomDateEditor constructor means convert empty String to null
	//    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	//}
	
	/**
	 * @Title: 				findWorkNEStatusPage
	 * @Description: 		分页查询Work	 * @param request		有需要的拿去使用
	 * @param nextPage		页号
	 * @param pageSize		页长
	 * @param status		默认查询非删除3的数据,具体类型参照XaConstant.Status
	 * @param sortDate		排序拼接字符串,类似[{property:'createTime',direction:'DESC'}],默认createTime排序
	 * @param jsonFilter	过滤拼接字符串
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findWorkNEStatusPage",method=RequestMethod.POST)
	public XaResult<Page<Work>> findWorkNEStatusPage(
			HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer nextPage,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "3") Integer status,
			@RequestParam(defaultValue = "[{property:'createTime',direction:'DESC'}]") String sortData,
			//过滤字段,字段名:jsonFilter,选填,默认:{},示例:{'search_EQ_field1':'value1','search_EQ_field2':'value2'},字段名称拼接规则search_为固定查询标识,EQ为等于,filed为字段名
			//EQ等于, IN包含, ISNULL空, LIKE, GT大于, LT小于, GTE大于等于, LTE小于等于, NE不等于,LIKEIGNORE非like 
			@RequestParam(defaultValue = "{}") String jsonFilter
		) throws BusinessException{
		//Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, sortData);
		//Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
		//return workService.findWorkNEStatusByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findWorkNEStatusByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(workService);
		Result r=invoker.invoke(invocation);
		
		XaResult<Page<Work>> x=(XaResult<Page<Work>>)r.getResult();
		
		XaResult<Page<Work>> xa=new XaResult<Page<Work>> ();
		xa.setObject(x.getObject());
		
		return xa;
	}
	
	/**
	 * @Title: 				findWorkEQStatusPage
	 * @Description: 		(预留)分页查询Work	 * @param request		有需要的拿去使用
	 * @param nextPage		页号
	 * @param pageSize		页长
	 * @param status		默认查询正常状态1的数据,具体类型参照XaConstant.Status
	 * @param sortDate		排序拼接字符串,类似[{property:'createTime',direction:'DESC'}],默认createTime排序
	 * @param jsonFilter	过滤拼接字符串
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findWorkEQStatusPage",method=RequestMethod.POST)
	public XaResult<Page<Work>> findWorkEQStatusPage(
			HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer nextPage,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "1") Integer status,
			@RequestParam(defaultValue = "[{property:'createTime',direction:'DESC'}]") String sortData,
			@RequestParam(defaultValue = "{}") String jsonFilter
		) throws BusinessException{
		//Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, sortData);
		//Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
		//return workService.findWorkEQStatusByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findWorkEQStatusByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(workService);
		Result r=invoker.invoke(invocation);
		
		XaResult<Page<Work>> x=(XaResult<Page<Work>>)r.getResult();
		
		XaResult<Page<Work>> xa=new XaResult<Page<Work>> ();
		xa.setObject(x.getObject());
		return xa;
	}
	
	/**
	 * @Title: 			findWorkSelectTypes
	 * @Description: 	获取查询项中需要获取数据字典的操作
	 * @param request	有需要的拿去使用	
	 * @return    		XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findWorkSelectTypes",method=RequestMethod.POST)
	public XaResult<Work> findWorkSelectTypes(
			HttpServletRequest request
			
		) throws BusinessException{
		return new XaResult<Work>();
	}
	
	/**
	 * @Title: 			findWorkById
	 * @Description: 	根据ID查找单条实体
	 * @param request	有需要的拿去使用	
	 * @param modelId	对象tid
	 * @return    		XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findWorkById",method=RequestMethod.POST)
	public XaResult<Work> findWorkById(
			HttpServletRequest request,
			@RequestParam  String  modelId
		) throws BusinessException{
		//return workService.findWork(modelId);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"modelId"},new Object[]{modelId});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findWork",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(workService);
		Result r=invoker.invoke(invocation);
        XaResult<Work> x=(XaResult<Work>)r.getResult();
		XaResult<Work> xa=new XaResult<Work> ();
		xa.setObject(x.getObject());
		return xa;
	}
	/**
	 * @Title: 			saveWork	 * @Description: 	新增一条实体
	 * @param model		对象
	 * @param request	有需要的拿去使用	
	 * @return    		XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="saveWork",method=RequestMethod.POST)
	public XaResult<Work> saveWork(
				 		  @RequestParam(value = "customerId"  ) String customerId,
		 				 		  @RequestParam(value = "serial"  ) Integer serial,
		 				 		  @RequestParam(value = "linker"  ) String linker,
		 				 		  @RequestParam(value = "expertName"  ) String expertName,
		 				 		  @RequestParam(value = "actionDate"   , required = false ) Date actionDate,
		 				 		  @RequestParam(value = "actionAddress"   , required = false ) String actionAddress,
		 				 		  @RequestParam(value = "comment"   , required = false ) String comment,
		 				 		  @RequestParam(value = "pics"   , required = false ) String pics,
		 				@RequestParam(value = "token") String token,
		HttpServletRequest request
	) throws BusinessException{
		//Long userId = WebUitl.getCmsLoginedUserId(request);
		Token t=getToken(token);
		Work model = new Work();
				 			model.setCustomerId(customerId);
		 				 			model.setSerial(serial);
		 				 			model.setLinker(linker);
		 				 			model.setExpertName(expertName);
		 				 			model.setActionDate(actionDate);
		 				 			model.setActionAddress(actionAddress);
		 				 			model.setComment(comment);
		 				 			model.setPics(pics);
		 				
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId"},new Object[]{t.getUserId(),model});
		ServiceInvocation invocation=new ServiceInvocation(request,"createWork",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(workService);
		Result r=invoker.invoke(invocation);
		XaResult<Work> x=(XaResult<Work>)r.getResult();
		XaResult<Work> xa=new XaResult<Work> ();
	 	xa.setObject(x.getObject());
		return xa; 				 			
		//return workService.createWork(userId,model);
	}
	
	/**
	 * @Title: 			updateWork	 * @Description: 	修改一条实体
	 * @param model		对象
	 * @param request	有需要的拿去使用	
	 * @return    		XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="updateWork",method=RequestMethod.POST)
	public XaResult<Work> updateWork(
				 		  @RequestParam(value = "customerId"   ) String customerId,
		 				 		  @RequestParam(value = "serial"   ) Integer serial,
		 				 		  @RequestParam(value = "linker"   ) String linker,
		 				 		  @RequestParam(value = "expertName"   ) String expertName,
		 				 		  @RequestParam(value = "actionDate"   , required = false  ) Date actionDate,
		 				 		  @RequestParam(value = "actionAddress"   , required = false  ) String actionAddress,
		 				 		  @RequestParam(value = "comment"   , required = false  ) String comment,
		 				 		  @RequestParam(value = "pics"   , required = false  ) String pics,
		 				@RequestParam(value = "tid")  String  tid,
		@RequestParam(value = "token") String token,
		HttpServletRequest request
	) throws BusinessException{
		//Long userId = WebUitl.getCmsLoginedUserId(request);
		Token t=getToken(token);
		Work model = new Work();
				 			model.setCustomerId(customerId);
		 				 			model.setSerial(serial);
		 				 			model.setLinker(linker);
		 				 			model.setExpertName(expertName);
		 				 			model.setActionDate(actionDate);
		 				 			model.setActionAddress(actionAddress);
		 				 			model.setComment(comment);
		 				 			model.setPics(pics);
		 				model.setTid(tid);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId"},new Object[]{t.getUserId(),model});
		ServiceInvocation invocation=new ServiceInvocation(request,"updateWork",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(workService);
		Result r=invoker.invoke(invocation);
		XaResult<Work> x=(XaResult<Work>)r.getResult();
		XaResult<Work> xa=new XaResult<Work> ();
		xa.setObject(x.getObject());
		return xa;
				 			
		//return workService.updateWork(userId,model);
	}
	
	/**
	 * @Title: 				operateWorkById
	 * @Description: 		操作一个实体状态,根据status进行操作
	 * @param request		有需要的拿去使用
	 * @param modelId		对象id
	 * @param status 		操作类型,status类型见XaConstant.Status,默认删除操作
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="operateWorkById",method=RequestMethod.POST)
	public XaResult<Work> operateWorkById(
			HttpServletRequest request,
			@RequestParam  String  modelId,
			@RequestParam(defaultValue = "3") Integer status,
			@RequestParam(value = "token",required=false) String token
		) throws BusinessException{
		//Long userId = WebUitl.getCmsLoginedUserId(request);
		Token t=getToken(token);
		
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId","status"},new Object[]{t.getUserId(),modelId,status});
	
		ServiceInvocation invocation=new ServiceInvocation(request,"operateWork",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(workService);
		Result r=invoker.invoke(invocation);
		
		XaResult x=(XaResult)r.getResult();
		
		return x;
		//return workService.operateWork(userId,modelId,status);
	}
	
	/**
	 * @Title: 				multiOperateWorkByIds
	 * @Description: 		批量操作多个实体状态,根据status进行操作
	 * @param request		有需要的拿去使用
	 * @param modelIds    	多个实体id,中间用逗号隔开
	 * @param status 		操作类型,status类型见XaConstant.Status,默认删除操作
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="operateWorkByIds",method=RequestMethod.POST)
	public XaResult<Work> operateWorkByIds(
			HttpServletRequest request,
			@RequestParam String modelIds,
			@RequestParam(defaultValue = "3") Integer status,
			@RequestParam(value = "token",required=false) String token
		) throws BusinessException{
		Token t=getToken(token);
		
		//Long userId = WebUitl.getCmsLoginedUserId(request);
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelIds","status"},new Object[]{t.getUserId(),modelIds,status});
		
		
		ServiceInvocation invocation=new ServiceInvocation(request,"multiOperateWork",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(workService);
		Result r=invoker.invoke(invocation);
		
		XaResult x=(XaResult)r.getResult();
		
		return x;
		
		//return workService.multiOperateWork(userId,modelIds,status);
	}
	
	/**
	 * @Title: 				upload
	 * @Description: 		图片上传
	 * @param photoFile		文件名称
	 * @param request		有需要的拿去使用
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="photoUpload",method=RequestMethod.POST)
	public String photoUpload(
		@RequestParam(value = "photoFile", required = false) MultipartFile photoFile, 
		HttpServletRequest request
	){
		XaResult<Map<String, Object>> xr=new XaResult<Map<String, Object>>();
		String root=request.getSession().getServletContext().getRealPath("/");
		String picturePath = "/upload/work";
		String ext =photoFile.getOriginalFilename().substring(photoFile.getOriginalFilename().lastIndexOf("."));
		String newName = new Date().getTime()+ext;
		//////////Thumbnail and file server support //////////////
		root = Settings.getInstance().getString("storage.base.path") + "/";
		String rp=Settings.getInstance().getString("storage.original_image.path") +picturePath;
		File filedict = new File(root+rp);
		if(!filedict.exists()){
			filedict.mkdirs();
		}
		File targetFile=new File(root+rp+File.separator+newName);
		//////////////////////////////////////////////////////////
		
		try {
			if(StringUtils.equalsIgnoreCase(ext, ".jpg") || StringUtils.equalsIgnoreCase(ext, ".png")){
				photoFile.transferTo(targetFile);
				BufferedImage bimg = ImageIO.read(targetFile);
				int width = bimg.getWidth();
				int height = bimg.getHeight();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pictureHeight",height);
				map.put("pictureWidth",width);
				map.put("picturePath",rp+"/"+newName);
//				return XaConstant.Code.success+":"+picturePath+"/"+newName+":"+height+":"+width;
				////////////Thumbnail and file server support ///////////
							long size = photoFile.getSize();
							if (size > 200 * 1024) {
								String thumbnailPath=Settings.getInstance().getString("storage.base.path") + "/" + 
										Settings.getInstance().getString("storage.thumbnail_image.path") +picturePath+File.separator+newName;
							    String relativePath=Settings.getInstance().getString("storage.thumbnail_image.path") +picturePath+File.separator+newName;
								String tUrl = ThumbnailImage.createThumbnailImage(thumbnailPath,relativePath, root+picturePath+File.separator+newName);
								if(tUrl!=null){
									map.put("pictureHeight",Settings.getInstance().getString("thumbnail.height"));
									map.put("pictureWidth",Settings.getInstance().getString("thumbnail.width"));
									map.put("picturePath",tUrl);
								}
							}
				//////////////////////////////////////////////////
				xr.success(map);
				return JSON.toJSONString(xr);
			}
			else{
//				return XaConstant.Code.error+":上传文件类型不允许,请上传jpg/png图片";
				xr.setCode(XaConstant.Code.error);
				xr.setMessage("上传文件类型不允许,请上传jpg/png图片");
				return JSON.toJSONString(xr);
			}
		} catch (IllegalStateException e) {
//			return XaConstant.Code.error+":图片上传失败";
			xr.setCode(XaConstant.Code.error);
			xr.setMessage("图片上传失败:"+e.getMessage());
			return JSON.toJSONString(xr);
		} catch (IOException e) {
//			return XaConstant.Code.error+":图片上传失败";
			xr.setCode(XaConstant.Code.error);
			xr.setMessage("图片上传失败:"+e.getMessage());
			return JSON.toJSONString(xr);
		} catch (Exception e) {
//			return XaConstant.Code.error+":图片上传失败";
			xr.setCode(XaConstant.Code.error);
			xr.setMessage("图片上传失败:"+e.getMessage());
			return JSON.toJSONString(xr);
		}
	}
}

