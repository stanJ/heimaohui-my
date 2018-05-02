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
import com.xa3ti.blackcat.business.entity.Customer;
import com.xa3ti.blackcat.business.service.CustomerService;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.ThumbnailImage;

import com.xa3ti.blackcat.proxy.invoke.ParamaterMap;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;
import com.xa3ti.blackcat.base.util.Token;


/**
 * @Title: CustomerController.java
 * @Package com.xa3ti.webstart.business.controller
 * @Description: 客户控制器
 * @author hchen
 * @date 2014年10月14日 下午6:35:35
 * @version V1.0
 */
@Controller
@RequestMapping("/cms/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	
	//@InitBinder
	//public void initBinder(WebDataBinder binder) {
	//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//    dateFormat.setLenient(false);

	//    // true passed to CustomDateEditor constructor means convert empty String to null
	//    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	//}
	
	/**
	 * @Title: 				findCustomerNEStatusPage
	 * @Description: 		分页查询Customer	 * @param request		有需要的拿去使用
	 * @param nextPage		页号
	 * @param pageSize		页长
	 * @param status		默认查询非删除3的数据,具体类型参照XaConstant.Status
	 * @param sortDate		排序拼接字符串,类似[{property:'createTime',direction:'DESC'}],默认createTime排序
	 * @param jsonFilter	过滤拼接字符串
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findCustomerNEStatusPage",method=RequestMethod.POST)
	public XaResult<Page<Customer>> findCustomerNEStatusPage(
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
		//return customerService.findCustomerNEStatusByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findCustomerNEStatusByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		
		XaResult<Page<Customer>> x=(XaResult<Page<Customer>>)r.getResult();
		
		XaResult<Page<Customer>> xa=new XaResult<Page<Customer>> ();
		xa.setObject(x.getObject());
		
		return xa;
	}
	
	/**
	 * @Title: 				findCustomerEQStatusPage
	 * @Description: 		(预留)分页查询Customer	 * @param request		有需要的拿去使用
	 * @param nextPage		页号
	 * @param pageSize		页长
	 * @param status		默认查询正常状态1的数据,具体类型参照XaConstant.Status
	 * @param sortDate		排序拼接字符串,类似[{property:'createTime',direction:'DESC'}],默认createTime排序
	 * @param jsonFilter	过滤拼接字符串
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findCustomerEQStatusPage",method=RequestMethod.POST)
	public XaResult<Page<Customer>> findCustomerEQStatusPage(
			HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer nextPage,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "1") Integer status,
			@RequestParam(defaultValue = "[{property:'createTime',direction:'DESC'}]") String sortData,
			@RequestParam(defaultValue = "{}") String jsonFilter
		) throws BusinessException{
		//Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, sortData);
		//Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
		//return customerService.findCustomerEQStatusByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findCustomerEQStatusByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		
		XaResult<Page<Customer>> x=(XaResult<Page<Customer>>)r.getResult();
		
		XaResult<Page<Customer>> xa=new XaResult<Page<Customer>> ();
		xa.setObject(x.getObject());
		return xa;
	}
	
	/**
	 * @Title: 			findCustomerSelectTypes
	 * @Description: 	获取查询项中需要获取数据字典的操作
	 * @param request	有需要的拿去使用	
	 * @return    		XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findCustomerSelectTypes",method=RequestMethod.POST)
	public XaResult<Customer> findCustomerSelectTypes(
			HttpServletRequest request
			
		) throws BusinessException{
		return new XaResult<Customer>();
	}
	
	/**
	 * @Title: 			findCustomerById
	 * @Description: 	根据ID查找单条实体
	 * @param request	有需要的拿去使用	
	 * @param modelId	对象tid
	 * @return    		XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findCustomerById",method=RequestMethod.POST)
	public XaResult<Customer> findCustomerById(
			HttpServletRequest request,
			@RequestParam  String  modelId
		) throws BusinessException{
		//return customerService.findCustomer(modelId);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"modelId"},new Object[]{modelId});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findCustomer",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
        XaResult<Customer> x=(XaResult<Customer>)r.getResult();
		XaResult<Customer> xa=new XaResult<Customer> ();
		xa.setObject(x.getObject());
		return xa;
	}
	/**
	 * @Title: 			saveCustomer	 * @Description: 	新增一条实体
	 * @param model		对象
	 * @param request	有需要的拿去使用	
	 * @return    		XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="saveCustomer",method=RequestMethod.POST)
	public XaResult<Customer> saveCustomer(
				 		  @RequestParam(value = "name"  ) String name,
		 				 		  @RequestParam(value = "manager"   , required = false ) String manager,
		 				 		  @RequestParam(value = "contact"   , required = false ) String contact,
		 				 		  @RequestParam(value = "email"   , required = false ) String email,
		 				 		  @RequestParam(value = "wechat"   , required = false ) String wechat,
		 				 		  @RequestParam(value = "province"   , required = false ) String province,
		 		  @RequestParam(value = "city"   , required = false ) String city,
		 		  @RequestParam(value = "district"   , required = false ) String district,
		 				 		  @RequestParam(value = "comment"   , required = false ) String comment,
		 				 		  @RequestParam(value = "serviceArea"   , required = false ) String serviceArea,
		 				 		  @RequestParam(value = "serviceObject"   , required = false ) String serviceObject,
		 				 		  @RequestParam(value = "intro"   , required = false ) String intro,
		 				 		  @RequestParam(value = "file"   , required = false ) String file,
		 				@RequestParam(value = "token") String token,
		HttpServletRequest request
	) throws BusinessException{
		//Long userId = WebUitl.getCmsLoginedUserId(request);
		Token t=getToken(token);
		Customer model = new Customer();
				 			model.setName(name);
		 				 			model.setManager(manager);
		 				 			model.setContact(contact);
		 				 			model.setEmail(email);
		 				 			model.setWechat(wechat);
		 				 			model.setProvince(province);
		 			model.setCity(city);
		 			model.setDistrict(district);
		 				 			model.setComment(comment);
		 				 			model.setServiceArea(serviceArea);
		 				 			model.setServiceObject(serviceObject);
		 				 			model.setIntro(intro);
		 				 			model.setFile(file);
		 				
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId"},new Object[]{t.getUserId(),model});
		ServiceInvocation invocation=new ServiceInvocation(request,"createCustomer",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		XaResult<Customer> x=(XaResult<Customer>)r.getResult();
		XaResult<Customer> xa=new XaResult<Customer> ();

		//xa.setObject((Customer)Encriptor.decriptModelByTokenType(x.getObject(), t.getType()));
		/////
	 	xa.setObject(x.getObject());
		return xa; 				 			
		//return customerService.createCustomer(userId,model);
	}
	
	/**
	 * @Title: 			updateCustomer	 * @Description: 	修改一条实体
	 * @param model		对象
	 * @param request	有需要的拿去使用	
	 * @return    		XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="updateCustomer",method=RequestMethod.POST)
	public XaResult<Customer> updateCustomer(
				 		  @RequestParam(value = "name"   ) String name,
		 				 		  @RequestParam(value = "manager"   , required = false  ) String manager,
		 				 		  @RequestParam(value = "contact"   , required = false  ) String contact,
		 				 		  @RequestParam(value = "email"   , required = false  ) String email,
		 				 		  @RequestParam(value = "wechat"   , required = false  ) String wechat,
		 				 		  @RequestParam(value = "province"   , required = false  ) String province,
		 		  @RequestParam(value = "city"   , required = false  ) String city,
		 		  @RequestParam(value = "district"   , required = false  ) String district,
		 				 		  @RequestParam(value = "comment"   , required = false  ) String comment,
		 				 		  @RequestParam(value = "serviceArea"   , required = false  ) String serviceArea,
		 				 		  @RequestParam(value = "serviceObject"   , required = false  ) String serviceObject,
		 				 		  @RequestParam(value = "intro"   , required = false  ) String intro,
		 				 		  @RequestParam(value = "file"   , required = false  ) String file,
		 				@RequestParam(value = "tid")  String  tid,
		@RequestParam(value = "token") String token,
		HttpServletRequest request
	) throws BusinessException{
		//Long userId = WebUitl.getCmsLoginedUserId(request);
		Token t=getToken(token);
		Customer model = new Customer();
				 			model.setName(name);
		 				 			model.setManager(manager);
		 				 			model.setContact(contact);
		 				 			model.setEmail(email);
		 				 			model.setWechat(wechat);
		 				 			model.setProvince(province);
		 			model.setCity(city);
		 			model.setDistrict(district);
		 				 			model.setComment(comment);
		 				 			model.setServiceArea(serviceArea);
		 				 			model.setServiceObject(serviceObject);
		 				 			model.setIntro(intro);
		 				 			model.setFile(file);
		 				model.setTid(tid);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId"},new Object[]{t.getUserId(),model});
		ServiceInvocation invocation=new ServiceInvocation(request,"updateCustomer",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		XaResult<Customer> x=(XaResult<Customer>)r.getResult();
		XaResult<Customer> xa=new XaResult<Customer> ();
		//xa.setObject((Customer)Encriptor.decriptModelByTokenType(x.getObject(), t.getType()));
		xa.setObject(x.getObject());
		return xa;
				 			
		//return customerService.updateCustomer(userId,model);
	}
	
	/**
	 * @Title: 				operateCustomerById
	 * @Description: 		操作一个实体状态,根据status进行操作
	 * @param request		有需要的拿去使用
	 * @param modelId		对象id
	 * @param status 		操作类型,status类型见XaConstant.Status,默认删除操作
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="operateCustomerById",method=RequestMethod.POST)
	public XaResult<Customer> operateCustomerById(
			HttpServletRequest request,
			@RequestParam  String  modelId,
			@RequestParam(defaultValue = "3") Integer status,
			@RequestParam(value = "token",required=false) String token
		) throws BusinessException{
		//Long userId = WebUitl.getCmsLoginedUserId(request);
		Token t=getToken(token);
		
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId","status"},new Object[]{t.getUserId(),modelId,status});
	
		ServiceInvocation invocation=new ServiceInvocation(request,"operateCustomer",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		
		XaResult x=(XaResult)r.getResult();
		
		return x;
		//return customerService.operateCustomer(userId,modelId,status);
	}
	
	/**
	 * @Title: 				multiOperateCustomerByIds
	 * @Description: 		批量操作多个实体状态,根据status进行操作
	 * @param request		有需要的拿去使用
	 * @param modelIds    	多个实体id,中间用逗号隔开
	 * @param status 		操作类型,status类型见XaConstant.Status,默认删除操作
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="operateCustomerByIds",method=RequestMethod.POST)
	public XaResult<Customer> operateCustomerByIds(
			HttpServletRequest request,
			@RequestParam String modelIds,
			@RequestParam(defaultValue = "3") Integer status,
			@RequestParam(value = "token",required=false) String token
		) throws BusinessException{
		Token t=getToken(token);
		
		//Long userId = WebUitl.getCmsLoginedUserId(request);
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelIds","status"},new Object[]{t.getUserId(),modelIds,status});
		
		
		ServiceInvocation invocation=new ServiceInvocation(request,"multiOperateCustomer",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		
		XaResult x=(XaResult)r.getResult();
		
		return x;
		
		//return customerService.multiOperateCustomer(userId,modelIds,status);
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
		String picturePath = "/upload/customer";
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

