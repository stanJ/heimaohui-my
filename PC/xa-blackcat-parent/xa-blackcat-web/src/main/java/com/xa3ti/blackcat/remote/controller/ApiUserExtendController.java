package com.xa3ti.blackcat.remote.controller;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.xa3ti.blackcat.business.model.UserRegister;
import com.xa3ti.blackcat.business.service.UserExtendService;
import com.xa3ti.blackcat.business.entity.UserExtend;
import com.xa3ti.blackcat.base.util.Captcha;
import com.xa3ti.blackcat.base.util.FileUtil;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.ThumbnailImage;
import com.xa3ti.blackcat.proxy.invoke.ParamaterMap;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvocation;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;
import com.xa3ti.blackcat.base.util.Token;

/**
 * @Title: ApiUserExtendController.java
 * @Package com.xa3ti.blackcat.remote.controller
 * @Description: 用户扩展信息接口
 * @author hchen
 * @date 2014年8月20日 下午3:03:06
 * @version V1.0
 */
@Api(value = "UserExtend", description = "用户扩展接口", position = 10)
@Controller
@RequestMapping("/m/userExtend")
public class ApiUserExtendController extends BaseController {

	@Autowired
	private UserExtendService userExtendService;
	
	
	//@InitBinder
	//public void initBinder(WebDataBinder binder) {
	//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//    dateFormat.setLenient(false);

	//    // true passed to CustomDateEditor constructor means convert empty String to null
	//    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	//}
	
	/**
	 * @Title: 			findUserExtendList
	 * @Description: 	查询所有用户扩展信息
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="查询所有用户扩展",notes="查询所有用户扩展信息,当返回的code=1时，取出object进行显示,存放为数组对象")
	@ResponseBody
	@RequestMapping(value="findUserExtendList",method=RequestMethod.POST)
	public XaResult<List<UserExtend>> findUserExtendList(
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
		//return userExtendService.findUserExtendVoByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findUserExtendVoByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
		Result r=invoker.invoke(invocation);
		
		XaResult<List<UserExtend>> x=(XaResult<List<UserExtend>>)r.getResult();
		
		XaResult<List<UserExtend>> xa=new XaResult<List<UserExtend>> ();
		xa.setObject(x.getObject());
		return xa;
	}
	
	
	/**
	 * @Title: 				findUserExtendEQStatusPage
	 * @Description: 		(预留)分页查询UserExtend	 * @param request		有需要的拿去使用
	 * @param nextPage		页号
	 * @param pageSize		页长
	 * @param status		默认查询正常状态1的数据,具体类型参照XaConstant.Status
	 * @param sortDate		排序拼接字符串,类似[{property:'createTime',direction:'DESC'}],默认createTime排序
	 * @param jsonFilter	过滤拼接字符串
	 * @return    			XaResult<T>
	 */
	@ResponseBody
	@RequestMapping(value="findUserExtendEQStatusPage",method=RequestMethod.POST)
	public XaResult<Page<UserExtend>> findUserExtendEQStatusPage(
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
		//return userExtendService.findUserExtendEQStatusByFilter(status, filterParams, pageable);
		
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"nextPage","pageSize","status","sortData","jsonFilter"},new Object[]{nextPage,pageSize,status,sortData,jsonFilter});
		
		ServiceInvocation invocation=new ServiceInvocation(request,"findUserExtendEQStatusByFilter",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
		Result r=invoker.invoke(invocation);
		
		XaResult<Page<UserExtend>> x=(XaResult<Page<UserExtend>>)r.getResult();
		
		XaResult<Page<UserExtend>> xa=new XaResult<Page<UserExtend>> ();
		xa.setObject(x.getObject());
		return xa;
	}
	
	
//	/**
//	 * @Title: 				findUserExtendById
//	 * @Description: 		根据ID查找单条实体
//	 * @param modelId		对象id,一般是tid
//	 * @return    			XaResult<T>
//	 */
//	@ApiOperation(value="根据tid查询用户扩展",notes="查询用户扩展详细信息,当返回的code=1时，取出object进行显示,存放为单体对象")
//	@ResponseBody
//	@RequestMapping(value="findUserExtendById",method=RequestMethod.POST)
//	public XaResult<UserExtend> findUserExtendById(
//		@ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId")  String  modelId,
//		 @ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
//		HttpServletRequest request
//	) throws BusinessException{
//	    Token t=getToken(token);
//		//return userExtendService.findUserExtend(modelId);
//		
//		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"modelId"},new Object[]{modelId});
//		
//		ServiceInvocation invocation=new ServiceInvocation(request,"findUserExtend",paramaterMap,null);
//		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
//		Result r=invoker.invoke(invocation);
//        XaResult<UserExtend> x=(XaResult<UserExtend>)r.getResult();
//		XaResult<UserExtend> xa=new XaResult<UserExtend> ();
//		xa.setObject(x.getObject());
//		return xa;
//	}
	
	/**
	 * @Title: save		saveUserExtend	 * @Description: 	新增一条实体
	 * @param userId	操作人编号
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
//	@ApiOperation(value="新增用户扩展",notes="供前端前期填充数据测试使用,上线后删除;新增用户扩展,当返回的code=1时，保存成功后object返回对象")
//	@ResponseBody
//	@RequestMapping(value="saveUserExtend",method=RequestMethod.POST)
//	public XaResult<UserExtend> saveUserExtend(
//				 		   @ApiParam("姓名,字段名:name") @RequestParam(value = "name") String name,
//		 				 		   @ApiParam("公司,字段名:company") @RequestParam(value = "company") String company,
//		 				 		   @ApiParam("用户类型,字段名:userType") @RequestParam(value = "userType") Long userType,
//		 				 		   @ApiParam("用户id,字段名:userId") @RequestParam(value = "userId") String userId,
//		 				HttpServletRequest request,
//		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
//	) throws BusinessException{
//		Token t=getToken(token);
//		UserExtend model = new UserExtend();
//				 			model.setName(name);
//		 				 			model.setCompany(company);
//		 				 			model.setUserType(userType);
//		 				 			model.setUserId(userId);
//		 				
//		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId"},new Object[]{t.getUserId(),model});
//		ServiceInvocation invocation=new ServiceInvocation(request,"createUserExtend",paramaterMap,null);
//		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
//		Result r=invoker.invoke(invocation);
//		XaResult<UserExtend> x=(XaResult<UserExtend>)r.getResult();
//		XaResult<UserExtend> xa=new XaResult<UserExtend> ();
//	 	xa.setObject(x.getObject());
//		return xa; 			
//		
//		//return userExtendService.createUserExtend(t.getUserId(),model);
//	}
//	
	
	/**
	 * @Title: save		saveUserExtend	 * @Description: 	新增一条实体
	 * @param userId	操作人编号
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="注册用户",notes="供前端前期填充数据测试使用,上线后删除;新增用户扩展,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="registerUser",method=RequestMethod.POST)
	public XaResult<UserExtend> registerUser(
			@ApiParam("账号,字段名:userName") @RequestParam(value = "userName") String userName,
			@ApiParam("密码,字段名:password") @RequestParam(value = "password") String password,
		 				 		   @ApiParam("公司,字段名:company") @RequestParam(value = "company",required=false) String company,
		 				 		 @ApiParam("联系方式,字段名:contact") @RequestParam(value = "contact",required=false) String contact,
		 				 		   @ApiParam("用户类型,字段名:userType") @RequestParam(value = "userType") Integer userType,
		 				 		   
		 				HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
		Token t=getToken(token);
		
		
		UserRegister ur=new UserRegister();
		ur.setUsername(userName);
		ur.setPassword(password);
		ur.setCompany(company);
		ur.setContact(contact);
		ur.setUserType(userType);
		 				
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","UserRegister"},new Object[]{t.getUserId(),ur});
		ServiceInvocation invocation=new ServiceInvocation(request,"registerUser",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
		Result r=invoker.invoke(invocation);
		XaResult<UserExtend> x=(XaResult<UserExtend>)r.getResult();
		XaResult<UserExtend> xa=new XaResult<UserExtend> ();
	 	xa.setObject(x.getObject());
		return xa; 			
		
		//return userExtendService.createUserExtend(t.getUserId(),model);
	}
	
	
	/**
	 * @Title: save		saveUserExtend	 * @Description: 	新增一条实体
	 * @param userId	操作人编号
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="修改用户",notes="供前端前期填充数据测试使用,上线后删除;新增用户扩展,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="modifyUser",method=RequestMethod.POST)
	public XaResult<UserExtend> modifyUser(
			@ApiParam("账号,字段名:userName") @RequestParam(value = "userName") String userName,
			@ApiParam("密码,字段名:password") @RequestParam(value = "password",required=false) String password,
			@ApiParam("新密码,字段名:newPassword") @RequestParam(value = "newPassword",required=false) String newPassword,
		 				 		   @ApiParam("公司,字段名:company") @RequestParam(value = "company",required=false) String company,
		 				 		 @ApiParam("联系方式,字段名:contact") @RequestParam(value = "contact",required=false) String contact,
		 				 		   @ApiParam("用户类型,字段名:userType") @RequestParam(value = "userType") Integer userType,
		 				 		   
		 				HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
		Token t=getToken(token);
		
		
		UserRegister ur=new UserRegister();
		ur.setUsername(userName);
		ur.setPassword(password);
		ur.setCompany(company);
		ur.setContact(contact);
		ur.setUserType(userType);
		ur.setNewPassword(newPassword);
		 				
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","UserRegister"},new Object[]{t.getUserId(),ur});
		ServiceInvocation invocation=new ServiceInvocation(request,"modifyUser",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
		Result r=invoker.invoke(invocation);
		XaResult<UserExtend> x=(XaResult<UserExtend>)r.getResult();
		XaResult<UserExtend> xa=new XaResult<UserExtend> ();
	 	xa.setObject(x.getObject());
		return xa; 			
		
		//return userExtendService.createUserExtend(t.getUserId(),model);
	}
	
	
	
	/**
	 * @Title: save		saveUserExtend	 * @Description: 	新增一条实体
	 * @param userId	操作人编号
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="修改用户",notes="供前端前期填充数据测试使用,上线后删除;新增用户扩展,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="resetPassword",method=RequestMethod.POST)
	public XaResult<UserExtend> resetPassword(
			@ApiParam("账号,字段名:userName") @RequestParam(value = "userName") String userName,
			@ApiParam("新密码,字段名:newPassword") @RequestParam(value = "newPassword") String newPassword,
		 			
		 				 		   
		 				HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
		Token t=getToken(token);
		
		
		UserRegister ur=new UserRegister();
		ur.setUsername(userName);
		ur.setNewPassword(newPassword);
		 				
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","UserRegister"},new Object[]{t.getUserId(),ur});
		ServiceInvocation invocation=new ServiceInvocation(request,"resetPassword",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
		Result r=invoker.invoke(invocation);
		XaResult<UserExtend> x=(XaResult<UserExtend>)r.getResult();
		XaResult<UserExtend> xa=new XaResult<UserExtend> ();
	 	xa.setObject(x.getObject());
		return xa; 			
		
		//return userExtendService.createUserExtend(t.getUserId(),model);
	}
	
	
	@ApiOperation(value="修改用户",notes="供前端前期填充数据测试使用,上线后删除;新增用户扩展,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="deleteUser",method=RequestMethod.POST)
	public XaResult<UserExtend> deleteUser(
			@ApiParam("账号,字段名:userName") @RequestParam(value = "userName") String userName,
		 				HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
		Token t=getToken(token);
		
		 				
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","userName"},new Object[]{t.getUserId(),userName});
		ServiceInvocation invocation=new ServiceInvocation(request,"deleteUser",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
		Result r=invoker.invoke(invocation);
		XaResult<UserExtend> x=(XaResult<UserExtend>)r.getResult();
		XaResult<UserExtend> xa=new XaResult<UserExtend> ();
	 	xa.setObject(x.getObject());
		return xa; 			
		
		//return userExtendService.createUserExtend(t.getUserId(),model);
	}
	
	
	@ApiOperation(value="查找某用户",notes="供前端前期填充数据测试使用,上线后删除;新增用户扩展,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="findUser",method=RequestMethod.POST)
	public XaResult<UserRegister> findUser(
			@ApiParam("账号,字段名:userName") @RequestParam(value = "userName") String userName,
		 				HttpServletRequest request,
		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
	) throws BusinessException{
		Token t=getToken(token);
		
		 				
		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","userName"},new Object[]{t.getUserId(),userName});
		ServiceInvocation invocation=new ServiceInvocation(request,"findUser",paramaterMap,null);
		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
		Result r=invoker.invoke(invocation);
		XaResult<UserRegister> x=(XaResult<UserRegister>)r.getResult();
		XaResult<UserRegister> xa=new XaResult<UserRegister> ();
	 	xa.setObject(x.getObject());
		return xa; 			
		
		//return userExtendService.createUserExtend(t.getUserId(),model);
	}
	
	
	/**
	 * @Title: save		requestVeriPic	 * @Description: 	新增一条实体
	 * @param userId	操作人编号
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="请求验证码图片",notes="供前端前期填充数据测试使用,上线后删除;新增用户扩展,当返回的code=1时，保存成功后object返回对象")
	@RequestMapping(value="requestVericode",method=RequestMethod.GET)
	public void requestVericode(
		 				HttpServletRequest request,
		 				HttpServletResponse response
	) throws BusinessException{
		OutputStream outsteam = null;
		Captcha vCode = new Captcha(120,40,5,100);  
        try {  
        	String code=vCode.getCode();
            System.out.println(code);  
            UUID uuid=UUID.randomUUID();
            Captcha.putCaptcha(uuid.toString(), code);
            Cookie c=new Cookie("uuid",uuid.toString());
			c.setPath("/");
            response.addCookie(c);
            response.setContentType("image/jpeg");
            response.setHeader("Pragma","no-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setIntHeader("Expires",-1);

			outsteam = response.getOutputStream();
            ImageIO.write(vCode.getBuffImg(),"JPEG",outsteam);

        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
			try{
				if(null != outsteam  ){
					outsteam.close();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}



	
	
	/**
	 * @Title: save		requestVeriPic	 * @Description: 	新增一条实体
	 * @param userId	操作人编号
	 * @param model		操作对象
	 * @return    		XaResult<T>
	 */
	@ApiOperation(value="验证验证码图片",notes="供前端前期填充数据测试使用,上线后删除;新增用户扩展,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="verifyVeriPic",method=RequestMethod.POST)
	public XaResult<HashMap> verifyVeriPic(
			@ApiParam("uuid,字段名:uuid") @RequestParam(value = "uuid") String uuid,
			@ApiParam("验证码,字段名:code") @RequestParam(value = "code") String code,
		 				HttpServletRequest request,
		 				HttpServletResponse response
	) throws BusinessException{
		XaResult<HashMap> xa=new XaResult<HashMap> ();
	 	
		HashMap map=new HashMap();
        String vericode=Captcha.getCaptcha(uuid);
        if(code.toLowerCase().equals(vericode.toLowerCase())) {
        	Date d=com.xa3ti.blackcat.base.util.DateUtil.addMinute(new Date(), 2);//2分钟后过期
        	Captcha.putVeri(uuid, d);
        	map.put("success", true);	
        }else{
        	xa.setCode(0);
        	map.put("success", false);	
        }
            
        xa.setObject(map);
        return xa;
	}
	
	
//	/**
//	 * @Title: 			updateUserExtend	 * @Description: 	修改一条实体
//	 * @param userId	操作人id
//	 * @param model		操作对象
//	 * @return    		XaResult<T>
//	 */
//	@ApiOperation(value="修改用户扩展",notes="修改用户扩展,当返回的code=1时，保存成功后object返回对象")
//	@ResponseBody
//	@RequestMapping(value="updateUserExtend",method=RequestMethod.POST)
//	public XaResult<UserExtend> updateUserExtend(
//				 		  @ApiParam("姓名,字段名:name") @RequestParam(value = "name") String name,
//		 				 		  @ApiParam("公司,字段名:company") @RequestParam(value = "company") String company,
//		 				 		  @ApiParam("用户类型,字段名:userType") @RequestParam(value = "userType") Long userType,
//		 				 		  @ApiParam("用户id,字段名:userId") @RequestParam(value = "userId") String userId,
//		 				@ApiParam("id,字段名:id") @RequestParam(value = "tid")  String  tid,
//		HttpServletRequest request,
//		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
//	) throws BusinessException{
//		Token t=getToken(token);
//		UserExtend model = new UserExtend();
//				 			model.setName(name);
//		 				 			model.setCompany(company);
//		 				 			model.setUserType(userType);
//		 				 			model.setUserId(userId);
//		 				model.setTid(tid);
//		
//		ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId"},new Object[]{t.getUserId(),model});
//		ServiceInvocation invocation=new ServiceInvocation(request,"updateUserExtend",paramaterMap,null);
//		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
//		Result r=invoker.invoke(invocation);
//		XaResult<UserExtend> x=(XaResult<UserExtend>)r.getResult();
//		XaResult<UserExtend> xa=new XaResult<UserExtend> ();
//		xa.setObject(x.getObject());
//		return xa;
//				 		
//		//return userExtendService.updateUserExtend(t.getUserId(),model);
//	}
	
//	/**
//	 * @Title: operateUserExtendById
//	 * @Description: 		操作一个实体状态,根据status进行操作
//	 * @param modelId		对象id,一般对应的tid
//	 * @param status 		操作类型:-1锁定,0无效,1正常,2发布,3删除,默认删除操作 参考XaConstant.Status
//	 * @return    			XaResult<T>
//	 */
//	@ApiOperation(value="更改实体状态",notes="操作一个实体状态,根据status进行操作")
//	@ResponseBody
//	@RequestMapping(value="operateUserExtendById",method=RequestMethod.POST)
//	public XaResult<UserExtend> operateUserExtendById(
//		HttpServletRequest request,
//		@ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId")  String  modelId,
//		@ApiParam("操作类型,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认删除操作") @RequestParam(defaultValue = "3") Integer status,
//		@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token
//	) throws BusinessException{
//			Token t=getToken(token);
//			
//			ParamaterMap paramaterMap=new ParamaterMap(new String[]{"userId","modelId","status"},new Object[]{t.getUserId(),modelId,status});
//	
//		ServiceInvocation invocation=new ServiceInvocation(request,"operateUserExtend",paramaterMap,null);
//		ServiceInvoker invoker=new ServiceInvoker(userExtendService);
//		Result r=invoker.invoke(invocation);
//		
//		XaResult x=(XaResult)r.getResult();
//		
//		return x;
//		//return userExtendService.operateUserExtend(t.getUserId(),modelId,status);
//	}
//	
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
		String picturePath = "/upload/userExtend";
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
	
	
	
	
	@ApiOperation(value="文件上传",notes="异步文件上传,返回上传文件地址")
	@ResponseBody
	@RequestMapping(value="fileUpload",method=RequestMethod.POST)
	public XaResult<Map<String, Object>> fileUpload(
		@ApiParam("上传的文件,字段名:file,必填") @RequestParam(value = "file") MultipartFile file, 
		HttpServletRequest request
	) throws BusinessException{
		XaResult<Map<String, Object>> result = new XaResult<Map<String, Object>>();
		String root=request.getSession().getServletContext().getRealPath("/");
		String picturePath = "/upload/file";
		String ext =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
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
			
				file.transferTo(targetFile);
				HashMap map=new HashMap();
				map.put("filePath",rp+"/"+newName);
				
				
				result.setObject(map);
				return result;
			
		} catch (IllegalStateException e) {
			throw new BusinessException("文件上传失败");
		} catch (IOException e) {
			throw new BusinessException("文件上传失败");
		} catch (Exception e) {
			throw new BusinessException("文件上传失败");
		}
	}
	
	
	
	@ApiOperation(value="下载文件",notes="下载文件,当返回的code=1时，取出object进行显示,存放为数组对象")
	@RequestMapping(value="download",method=RequestMethod.GET)
	public void download(
			@ApiParam("下载文件url,字段名:urlString,必填") @RequestParam(value = "urlString") String  urlString,
			@ApiParam("下载文件名称,字段名:filename,必填") @RequestParam(value = "filename") String  filename,
			@ApiParam("用户令牌,字段名:token") @RequestParam(value = "token") String token,
		HttpServletRequest request,
		HttpServletResponse response
		) throws BusinessException{
		Token t=getToken(token);
		try {
			FileUtil.download(urlString,filename,response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}

