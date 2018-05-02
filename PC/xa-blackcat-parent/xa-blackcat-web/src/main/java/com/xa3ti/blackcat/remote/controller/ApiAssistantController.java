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

import com.xa3ti.blackcat.business.util.Encriptor;
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
import com.xa3ti.blackcat.business.service.AssistantService;
import com.xa3ti.blackcat.business.entity.Assistant;

import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.ThumbnailImage;

import com.xa3ti.blackcat.proxy.invoke.ParamaterMap;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;
import com.xa3ti.blackcat.base.util.Token;

/**
 * @Title: ApiAssistantController.java
 * @Package com.xa3ti.blackcat.remote.controller
 * @Description: 助手信息信息接口
 * @author hchen
 * @date 2014年8月20日 下午3:03:06
 * @version V1.0
 */
@Api(value = "Assistant", description = "助手信息接口", position = 10)
@Controller
@RequestMapping("/m/assistant")
public class ApiAssistantController extends BaseController {

	@Autowired
	private AssistantService assistantService;
	
	
	//@InitBinder
	//public void initBinder(WebDataBinder binder) {
	//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//    dateFormat.setLenient(false);

	//    // true passed to CustomDateEditor constructor means convert empty String to null
	//    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	//}
	
	/**
	 * @Title: 			findAssistantList
	 * @Description: 	查询所有助手信息信息
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="查询所有助手信息",notes="查询所有助手信息信息,当返回的code=1时，取出object进行显示,存放为数组对象")
	@ResponseBody
	@RequestMapping(value="findAssistantList",method=RequestMethod.POST)
	public XaResult<List<Object>> findAssistantList(
		@ApiParam("页号,字段名:nextPage,默认0,从第0页开始") @RequestParam(defaultValue = "0") Integer nextPage,
		@ApiParam("页长,字段名:pageSize,默认10") @RequestParam(defaultValue = "10") Integer pageSize,
		@ApiParam("数据状态,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认正常数据状态1") @RequestParam(defaultValue = "1") Integer status,
		@ApiParam("排序字段,字段名:sortData,选填,默认:[{property:'createTime',direction:'DESC'}]") @RequestParam(defaultValue = "[{property:'createTime',direction:'DESC'}]") String sortData,
		@ApiParam("过滤字段,字段名:jsonFilter,选填,默认:{},示例:{'search_EQ_field1':'value1','search_EQ_field2':'value2'},字段名称拼接规则search_为固定查询标识,EQ为等于,filed为字段名,不建议前端采用这种方式,建议前端使用指定查询字段名") @RequestParam(defaultValue = "{}") String jsonFilter,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
		HttpServletRequest request
	) throws BusinessException{
		Token t=getToken(token);
		//Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, sortData);
		//Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
		//return assistantService.findAssistantVoByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findAssistantVoByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(assistantService);
		Result r=invoker.invoke(invocation);
		
		XaResult<List<Assistant>> x=(XaResult<List<Assistant>>)r.getResult();
		
		XaResult<List<Assistant>> xa=new XaResult<List<Assistant>> ();
		xa.setObject(x.getObject());

		XaResult<List<Object>> xx=Encriptor.interceptAssistantByTokenType(xa, t.getType());

		return xx;
	}
	
	
	/**
	 * @Title: 				findAssistantEQStatusPage
	 * @Description: 		(预留)分页查询Assistant	 * @param request		有需要的拿去使用
	 * @param nextPage		页号
	 * @param pageSize		页长
	 * @param status		默认查询正常状态1的数据,具体类型参照XaConstant.Status
	 * @param sortDate		排序拼接字符串,类似[{property:'createTime',direction:'DESC'}],默认createTime排序
	 * @param jsonFilter	过滤拼接字符串
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findAssistantEQStatusPage",method=RequestMethod.POST)
	public XaResult<Page<Object>> findAssistantEQStatusPage(
			HttpServletRequest request,
			@ApiParam("页号,字段名:nextPage,默认0,从第0页开始") @RequestParam(defaultValue = "0") Integer nextPage,
			@ApiParam("页长,字段名:pageSize,默认10") @RequestParam(defaultValue = "10") Integer pageSize,
			@ApiParam("数据状态,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认正常数据状态1") @RequestParam(defaultValue = "1") Integer status,
			@ApiParam("排序字段,字段名:sortData,选填,默认:[{property:'createTime',direction:'DESC'}]") @RequestParam(defaultValue = "[{property:'createTime',direction:'DESC'}]") String sortData,
			@ApiParam("过滤字段,字段名:jsonFilter,选填,默认:{},示例:{'search_EQ_field1':'value1','search_EQ_field2':'value2'},字段名称拼接规则search_为固定查询标识,EQ为等于,filed为字段名,不建议前端采用这种方式,建议前端使用指定查询字段名") @RequestParam(defaultValue = "{}") String jsonFilter,
		   @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
		   ) throws BusinessException{
		   Token t=getToken(token);
		//Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, sortData);
		//Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
		//return assistantService.findAssistantEQStatusByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findAssistantEQStatusByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(assistantService);
		Result r=invoker.invoke(invocation);
		
		XaResult<Page<Assistant>> x=(XaResult<Page<Assistant>>)r.getResult();
		
		XaResult<Page<Assistant>> xa=new XaResult<Page<Assistant>> ();
		xa.setObject(x.getObject());

		XaResult<Page<Object>> xx=Encriptor.interceptAssistantPageByTokenType(xa, t.getType());

		return xx;
	}
	
	
	/**
	 * @Title: 				findAssistantById
	 * @Description: 		根据ID查找单条实体
	 * @param modelId		对象id,一般是tid
	 * @return    			XaResult<T>
	 */
	@ApiOperation(value="根据tid查询助手信息",notes="查询助手信息详细信息,当返回的code=1时，取出object进行显示,存放为单体对象")
	@ResponseBody
	@RequestMapping(value="findAssistantById",method=RequestMethod.POST)
	public XaResult<Assistant> findAssistantById(
		@ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId")  String  modelId,
		 @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
		HttpServletRequest request
	) throws BusinessException{
	    Token t=getToken(token);
		//return assistantService.findAssistant(modelId);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"modelId"},new Object[]{modelId});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findAssistant",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(assistantService);
		Result r=invoker.invoke(invocation);
        XaResult<Assistant> x=(XaResult<Assistant>)r.getResult();
		XaResult<Assistant> xa=new XaResult<Assistant> ();
		xa.setObject((Assistant)Encriptor.decriptModelByTokenType(xa.getObject(), t.getType()));
		//xa.setObject(x.getObject());
		return xa;
	}
	
	/**
	 * @Title: save		saveAssistant	 * @Description: 	新增一条实体
	 * @param userId	操作人编号
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="新增助手信息",notes="供前端前期填充数据测试使用,上线后删除;新增助手信息,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="saveAssistant",method=RequestMethod.POST)
	public XaResult<Assistant> saveAssistant(
				 		   @ApiParam("专家id,字段名:expertId") @RequestParam(value = "expertId"  ) String expertId,
		 				 		   @ApiParam("序号,字段名:serial") @RequestParam(value = "serial"  , required = false  ) Integer serial,
		 				 		   @ApiParam("名称,字段名:name") @RequestParam(value = "name"  ) String name,
		 				 		   @ApiParam("出生日期,字段名:birth") @RequestParam(value = "birth"   , required = false ) Date birth,
		 				 		   @ApiParam("联系方式,字段名:contact") @RequestParam(value = "contact"   , required = false ) String contact,
		 				 		   @ApiParam("微信,字段名:wechat") @RequestParam(value = "wechat"   , required = false ) String wechat,
		 				 		   @ApiParam("性别,字段名:gender") @RequestParam(value = "gender"   , required = false ) String gender,
		 				 		   @ApiParam("证件号,字段名:certificate") @RequestParam(value = "certificate"   , required = false ) String certificate,
		 				 		   @ApiParam("邮箱,字段名:email") @RequestParam(value = "email"   , required = false ) String email,
		 				HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
		Token t=getToken(token);
		Assistant model = new Assistant();
				 			model.setExpertId(expertId);
		 				 			model.setSerial(serial);
		 				 			model.setName(name);
		 				 			model.setBirth(birth);
		 				 			model.setContact(contact);
		 				 			model.setWechat(wechat);
		 				 			model.setGender(gender);
		 				 			model.setCertificate(certificate);
		 				 			model.setEmail(email);
		 				
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId"},new Object[]{t.getUserId(),model});
		ServiceInvocation invocation=new ServiceInvocation(request,"createAssistant",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(assistantService);
		Result r=invoker.invoke(invocation);
		XaResult<Assistant> x=(XaResult<Assistant>)r.getResult();
		XaResult<Assistant> xa=new XaResult<Assistant> ();
	 	xa.setObject(x.getObject());
		return xa; 			
		
		//return assistantService.createAssistant(t.getUserId(),model);
	}
	
	/**
	 * @Title: 			updateAssistant	 * @Description: 	修改一条实体
	 * @param userId	操作人id
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="修改助手信息",notes="修改助手信息,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="updateAssistant",method=RequestMethod.POST)
	public XaResult<Assistant> updateAssistant(
				 		  @ApiParam("专家id,字段名:expertId") @RequestParam(value = "expertId" ) String expertId,
		 				 		  @ApiParam("序号,字段名:serial") @RequestParam(value = "serial" , required = false  ) Integer serial,
		 				 		  @ApiParam("名称,字段名:name") @RequestParam(value = "name" ) String name,
		 				 		  @ApiParam("出生日期,字段名:birth") @RequestParam(value = "birth"  , required = false ) Date birth,
		 				 		  @ApiParam("联系方式,字段名:contact") @RequestParam(value = "contact"  , required = false ) String contact,
		 				 		  @ApiParam("微信,字段名:wechat") @RequestParam(value = "wechat"  , required = false ) String wechat,
		 				 		  @ApiParam("性别,字段名:gender") @RequestParam(value = "gender"  , required = false ) String gender,
		 				 		  @ApiParam("证件号,字段名:certificate") @RequestParam(value = "certificate"  , required = false ) String certificate,
		 				 		  @ApiParam("邮箱,字段名:email") @RequestParam(value = "email"  , required = false ) String email,
		 				@ApiParam("id,字段名:id") @RequestParam(value = "tid")  String  tid,
		HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
		Token t=getToken(token);
		Assistant model = new Assistant();
				 			model.setExpertId(expertId);
		 				 			model.setSerial(serial);
		 				 			model.setName(name);
		 				 			model.setBirth(birth);
		 				 			model.setContact(contact);
		 				 			model.setWechat(wechat);
		 				 			model.setGender(gender);
		 				 			model.setCertificate(certificate);
		 				 			model.setEmail(email);
		 				model.setTid(tid);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId"},new Object[]{t.getUserId(),model});
		ServiceInvocation invocation=new ServiceInvocation(request,"updateAssistant",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(assistantService);
		Result r=invoker.invoke(invocation);
		XaResult<Assistant> x=(XaResult<Assistant>)r.getResult();
		XaResult<Assistant> xa=new XaResult<Assistant> ();
		xa.setObject(x.getObject());
		return xa;
				 		
		//return assistantService.updateAssistant(t.getUserId(),model);
	}
	
	/**
	 * @Title: operateAssistantById
	 * @Description: 		操作一个实体状态,根据status进行操作
	 * @param modelId		对象id,一般对应的tid
	 * @param status 		操作类型:-1锁定,0无效,1正常,2发布,3删除,默认删除操作 参考XaConstant.Status
	 * @return    			XaResult<T>
	 */
	@ApiOperation(value="更改实体状态",notes="操作一个实体状态,根据status进行操作")
	@ResponseBody
	@RequestMapping(value="operateAssistantById",method=RequestMethod.POST)
	public XaResult<Assistant> operateAssistantById(
		HttpServletRequest request,
		@ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId")  String  modelId,
		@ApiParam("操作类型,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认删除操作") @RequestParam(defaultValue = "3") Integer status,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
			Token t=getToken(token);
			
			ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId","status"},new Object[]{t.getUserId(),modelId,status});
	
		ServiceInvocation invocation=new ServiceInvocation(request,"operateAssistant",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(assistantService);
		Result r=invoker.invoke(invocation);
		
		XaResult x=(XaResult)r.getResult();
		
		return x;
		//return assistantService.operateAssistant(t.getUserId(),modelId,status);
	}
	
	/**
	 * @Title: 				upload
	 * @Description: 		图片上传
	 * @param photoFile		文件名称
	 * @param request		
	 * @return    			XaResult<T>
	 */
	@ApiOperation(value="图片上传",notes="异步图片上传,返回上传图片的地址、宽、高")
	@ResponseBody
	@RequestMapping(value="photoUpload",method=RequestMethod.POST)
	public XaResult<Map<String, Object>> photoUpload(
		@ApiParam("上传的图片,字段名:photoFile,必填") @RequestParam(value = "photoFile") MultipartFile photoFile, 
		HttpServletRequest request
	) throws BusinessException{
		XaResult<Map<String, Object>> result = new XaResult<Map<String, Object>>();
		String root=request.getSession().getServletContext().getRealPath("/");
		String picturePath = "/upload/assistant";
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
				
				result.setObject(map);
				return result;
			}
			else{
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

