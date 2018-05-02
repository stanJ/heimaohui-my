package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: CustomerVo
 * @Description:客户 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="客户Vo对象")
public class CustomerVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="名称,名称")
	private String name;
		@ApiModelProperty(value="公司负责人,公司负责人")
	private String manager;
		@ApiModelProperty(value="联系方式,联系方式")
	private String contact;
		@ApiModelProperty(value="邮箱,邮箱")
	private String email;
		@ApiModelProperty(value="微信,微信")
	private String wechat;
		@ApiModelProperty(value="所在省市,所在省市")
	private String province,city,district;
		@ApiModelProperty(value="客户评价,客户评价")
	private String comment;
		@ApiModelProperty(value="服务地区,服务地区")
	private String serviceArea;
		@ApiModelProperty(value="服务对象,服务对象")
	private String serviceObject;
		@ApiModelProperty(value="公司简介,公司简介")
	private String intro;
		@ApiModelProperty(value="附件,附件")
	private String file;
		
	public CustomerVo(Long id   ,String name    ,String manager    ,String contact    ,String email    ,String wechat    ,String province  ,String city  ,String district    ,String comment    ,String serviceArea    ,String serviceObject    ,String intro    ,String file  ) {
		this.tid = id;
		 	 this.name = name;
	 		 	 this.manager = manager;
	 		 	 this.contact = contact;
	 		 	 this.email = email;
	 		 	 this.wechat = wechat;
	 		 	 this.province = province;
	 	 this.city = city;
	 	 this.district = district;
	 		 	 this.comment = comment;
	 		 	 this.serviceArea = serviceArea;
	 		 	 this.serviceObject = serviceObject;
	 		 	 this.intro = intro;
	 		 	 this.file = file;
	 		}
	
	public CustomerVo() {
		super();
	}
	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
		 		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name=name;
		}
	 		 		public String getManager(){
			return manager;
		}
		
		public void setManager(String manager){
			this.manager=manager;
		}
	 		 		public String getContact(){
			return contact;
		}
		
		public void setContact(String contact){
			this.contact=contact;
		}
	 		 		public String getEmail(){
			return email;
		}
		
		public void setEmail(String email){
			this.email=email;
		}
	 		 		public String getWechat(){
			return wechat;
		}
		
		public void setWechat(String wechat){
			this.wechat=wechat;
		}
	 		 		public String getProvince(){
			return province;
		}
		
		public void setProvince(String province){
			this.province=province;
		}
	 		public String getCity(){
			return city;
		}
		
		public void setCity(String city){
			this.city=city;
		}
	 		public String getDistrict(){
			return district;
		}
		
		public void setDistrict(String district){
			this.district=district;
		}
	 		 		public String getComment(){
			return comment;
		}
		
		public void setComment(String comment){
			this.comment=comment;
		}
	 		 		public String getServiceArea(){
			return serviceArea;
		}
		
		public void setServiceArea(String serviceArea){
			this.serviceArea=serviceArea;
		}
	 		 		public String getServiceObject(){
			return serviceObject;
		}
		
		public void setServiceObject(String serviceObject){
			this.serviceObject=serviceObject;
		}
	 		 		public String getIntro(){
			return intro;
		}
		
		public void setIntro(String intro){
			this.intro=intro;
		}
	 		 		public String getFile(){
			return file;
		}
		
		public void setFile(String file){
			this.file=file;
		}
	 		
}

