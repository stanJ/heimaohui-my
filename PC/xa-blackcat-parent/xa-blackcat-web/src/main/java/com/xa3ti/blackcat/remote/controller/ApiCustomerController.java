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
import org.springframework.beans.BeanUtils;
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
import com.xa3ti.blackcat.business.service.BrowseService;
import com.xa3ti.blackcat.business.service.CustomerService;
import com.xa3ti.blackcat.business.util.BizConstant;
import com.xa3ti.blackcat.business.entity.Browse;
import com.xa3ti.blackcat.business.entity.Customer;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.ThumbnailImage;
import com.xa3ti.blackcat.proxy.invoke.ParamaterMap;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;
import com.xa3ti.blackcat.base.util.Token;

/**
 * @Title: ApiCustomerController.java
 * @Package com.xa3ti.blackcat.remote.controller
 * @Description: 客户信息接口
 * @author hchen
 * @date 2014年8月20日 下午3:03:06
 * @version V1.0
 */
@Api(value = "Customer", description = "客户接口", position = 10)
@Controller
@RequestMapping("/m/customer")
public class ApiCustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	private BrowseService browseService;
	
	//@InitBinder
	//public void initBinder(WebDataBinder binder) {
	//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//    dateFormat.setLenient(false);

	//    // true passed to CustomDateEditor constructor means convert empty String to null
	//    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	//}
	
	/**
	 * @Title: 			findCustomerList
	 * @Description: 	查询所有客户信息
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="查询所有客户",notes="查询所有客户信息,当返回的code=1时，取出object进行显示,存放为数组对象")
	@ResponseBody
	@RequestMapping(value="findCustomerList",method=RequestMethod.POST)
	public XaResult<List<Object>> findCustomerList(
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
		//return customerService.findCustomerVoByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findCustomerVoByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		
		XaResult<List<Customer>> x=(XaResult<List<Customer>>)r.getResult();
		
		XaResult<List<Customer>> xa=new XaResult<List<Customer>> ();
		xa.setObject(x.getObject());
		
		XaResult<List<Object>> xx= Encriptor.interceptCustomerByTokenType(xa, t.getType());
		
		return xx;
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
	public XaResult<Page<Object>> findCustomerEQStatusPage(
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
		//return customerService.findCustomerEQStatusByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findCustomerEQStatusByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		
		XaResult<Page<Customer>> x=(XaResult<Page<Customer>>)r.getResult();
		
		XaResult<Page<Customer>> xa=new XaResult<Page<Customer>> ();
		xa.setObject(x.getObject());
		
		XaResult<Page<Object>> xx=Encriptor.interceptCustomerPageByTokenType(xa, t.getType());
		
		return xx;
	}
	
	
	/**
	 * @Title: 				findCustomerById
	 * @Description: 		根据ID查找单条实体
	 * @param modelId		对象id,一般是tid
	 * @return    			XaResult<T>
	 */
	@ApiOperation(value="根据tid查询客户",notes="查询客户详细信息,当返回的code=1时，取出object进行显示,存放为单体对象")
	@ResponseBody
	@RequestMapping(value="findCustomerById",method=RequestMethod.POST)
	public XaResult<Customer> findCustomerById(
		@ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId")  String  modelId,
		 @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
		HttpServletRequest request
	) throws BusinessException{
	    Token t=getToken(token);
		//return customerService.findCustomer(modelId);
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"modelId"},new Object[]{modelId});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findCustomer",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
        XaResult<Customer> x=(XaResult<Customer>)r.getResult();
		XaResult<Customer> xa=new XaResult<Customer> ();
		//xa.setObject(x.getObject());
		
		
		Customer newObject=new Customer();
		BeanUtils.copyProperties(x.getObject(),newObject);
		
		xa.setObject((Customer)Encriptor.decriptModelByTokenType(newObject, t.getType()));
		
		if(!Encriptor.checkBrowsed(t.getUserId(), t.getType(), BizConstant.BrowseType.CUSTOMER, modelId)
	    		&&!Encriptor.checkCanBrowse(t.getUserId(), t.getType(), BizConstant.BrowseType.CUSTOMER))
	    	 throw new BusinessException("当天浏览次数已超出");
		
		///////////////////////记录浏览次数//////////////////
		Browse browse=new Browse();
		browse.setUserId(t.getUserId());
		browse.setBtime(new Date());
		browse.setBtype(BizConstant.BrowseType.CUSTOMER);
		browse.setExpertId(modelId);
		browseService.createBrowse(t.getUserId(), browse);
		///////////////////////////////////////////////////////

		return xa;
	}
	
	
	/**
	 * @Title: 				findCustomerById
	 * @Description: 		根据ID查找单条实体
	 * @param modelId		对象id,一般是tid
	 * @return    			XaResult<T>
	 */
	@ApiOperation(value="根据tid查询客户",notes="查询客户详细信息,当返回的code=1时，取出object进行显示,存放为单体对象")
	@ResponseBody
	@RequestMapping(value="findCustomerJoinedById",method=RequestMethod.POST)
	public XaResult<Map<String,Object>> findCustomerJoinedById(
		@ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId")  String  modelId,
		 @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
		HttpServletRequest request
	) throws BusinessException{
	    Token t=getToken(token);
		//return customerService.findCustomer(modelId);
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"modelId"},new Object[]{modelId});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findCustomerJoined",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
        XaResult<Map<String,Object>> x=(XaResult<Map<String,Object>>)r.getResult();
		XaResult<Map<String,Object>> xa=new XaResult<Map<String,Object>> ();
		//xa.setObject(x.getObject());
		
		xa.setObject((Map<String,Object>)Encriptor.decriptModelByTokenType(x.getObject(), t.getType()));
		
		if(!Encriptor.checkBrowsed(t.getUserId(), t.getType(), BizConstant.BrowseType.CUSTOMER, modelId)
	    		&&!Encriptor.checkCanBrowse(t.getUserId(), t.getType(), BizConstant.BrowseType.CUSTOMER))
	    	 throw new BusinessException("当天浏览次数已超出");
		
		///////////////////////记录浏览次数//////////////////
		Browse browse=new Browse();
		browse.setUserId(t.getUserId());
		browse.setBtime(new Date());
		browse.setBtype(BizConstant.BrowseType.CUSTOMER);
		browse.setExpertId(modelId);
		browseService.createBrowse(t.getUserId(), browse);
		///////////////////////////////////////////////////////

		return xa;
	}
	
	/**
	 * @Title: save		saveCustomer	 * @Description: 	新增一条实体
	 * @param userId	操作人编号
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="新增客户",notes="供前端前期填充数据测试使用,上线后删除;新增客户,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="saveCustomer",method=RequestMethod.POST)
	public XaResult<Customer> saveCustomer(
				 		   @ApiParam("名称,字段名:name") @RequestParam(value = "name"  ) String name,
		 				 		   @ApiParam("公司负责人,字段名:manager") @RequestParam(value = "manager"   , required = false ) String manager,
		 				 		   @ApiParam("联系方式,字段名:contact") @RequestParam(value = "contact"   , required = false ) String contact,
		 				 		   @ApiParam("邮箱,字段名:email") @RequestParam(value = "email"   , required = false ) String email,
		 				 		   @ApiParam("微信,字段名:wechat") @RequestParam(value = "wechat"   , required = false ) String wechat,
		 				 		   @ApiParam("所在省市,字段名:province") @RequestParam(value = "province"   , required = false ) String province,
		 		   @ApiParam("所在省市,字段名:city") @RequestParam(value = "city"   , required = false ) String city,
		 		   @ApiParam("所在省市,字段名:district") @RequestParam(value = "district"   , required = false ) String district,
		 				 		   @ApiParam("客户评价,字段名:comment") @RequestParam(value = "comment"   , required = false ) String comment,
		 				 		   @ApiParam("服务地区,字段名:serviceArea") @RequestParam(value = "serviceArea"   , required = false ) String serviceArea,
		 				 		   @ApiParam("服务对象,字段名:serviceObject") @RequestParam(value = "serviceObject"   , required = false ) String serviceObject,
		 				 		   @ApiParam("公司简介,字段名:intro") @RequestParam(value = "intro"   , required = false ) String intro,
		 				 		   @ApiParam("附件,字段名:file") @RequestParam(value = "file"   , required = false ) String file,
		 				HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
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
	 	xa.setObject(x.getObject());
		return xa; 			
		
		//return customerService.createCustomer(t.getUserId(),model);
	}
	
	/**
	 * @Title: 			updateCustomer	 * @Description: 	修改一条实体
	 * @param userId	操作人id
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="修改客户",notes="修改客户,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="updateCustomer",method=RequestMethod.POST)
	public XaResult<Customer> updateCustomer(
				 		  @ApiParam("名称,字段名:name") @RequestParam(value = "name" ) String name,
		 				 		  @ApiParam("公司负责人,字段名:manager") @RequestParam(value = "manager"  , required = false ) String manager,
		 				 		  @ApiParam("联系方式,字段名:contact") @RequestParam(value = "contact"  , required = false ) String contact,
		 				 		  @ApiParam("邮箱,字段名:email") @RequestParam(value = "email"  , required = false ) String email,
		 				 		  @ApiParam("微信,字段名:wechat") @RequestParam(value = "wechat"  , required = false ) String wechat,
		 				 		  @ApiParam("所在省市,字段名:province") @RequestParam(value = "province"  , required = false ) String province,
		 		  @ApiParam("所在省市,字段名:city") @RequestParam(value = "city"  , required = false ) String city,
		 		  @ApiParam("所在省市,字段名:district") @RequestParam(value = "district"  , required = false ) String district,
		 				 		  @ApiParam("客户评价,字段名:comment") @RequestParam(value = "comment"  , required = false ) String comment,
		 				 		  @ApiParam("服务地区,字段名:serviceArea") @RequestParam(value = "serviceArea"  , required = false ) String serviceArea,
		 				 		  @ApiParam("服务对象,字段名:serviceObject") @RequestParam(value = "serviceObject"  , required = false ) String serviceObject,
		 				 		  @ApiParam("公司简介,字段名:intro") @RequestParam(value = "intro"  , required = false ) String intro,
		 				 		  @ApiParam("附件,字段名:file") @RequestParam(value = "file"  , required = false ) String file,
		 				@ApiParam("id,字段名:id") @RequestParam(value = "tid")  String  tid,
		HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
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
		xa.setObject(x.getObject());
		return xa;
				 		
		//return customerService.updateCustomer(t.getUserId(),model);
	}
	
	/**
	 * @Title: operateCustomerById
	 * @Description: 		操作一个实体状态,根据status进行操作
	 * @param modelId		对象id,一般对应的tid
	 * @param status 		操作类型:-1锁定,0无效,1正常,2发布,3删除,默认删除操作 参考XaConstant.Status
	 * @return    			XaResult<T>
	 */
	@ApiOperation(value="更改实体状态",notes="操作一个实体状态,根据status进行操作")
	@ResponseBody
	@RequestMapping(value="operateCustomerById",method=RequestMethod.POST)
	public XaResult<Customer> operateCustomerById(
		HttpServletRequest request,
		@ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId")  String  modelId,
		@ApiParam("操作类型,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认删除操作") @RequestParam(defaultValue = "3") Integer status,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
			Token t=getToken(token);
			
			ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId","status"},new Object[]{t.getUserId(),modelId,status});
	
		ServiceInvocation invocation=new ServiceInvocation(request,"operateCustomer",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(customerService);
		Result r=invoker.invoke(invocation);
		
		XaResult x=(XaResult)r.getResult();
		
		return x;
		//return customerService.operateCustomer(t.getUserId(),modelId,status);
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

