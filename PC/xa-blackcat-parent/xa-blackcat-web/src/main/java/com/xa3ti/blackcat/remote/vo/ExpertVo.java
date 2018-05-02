package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: ExpertVo
 * @Description:专家 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="专家Vo对象")
public class ExpertVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="姓名,姓名")
	private String name;
		@ApiModelProperty(value="出生日期,出生日期")
	private Date birth;
		@ApiModelProperty(value="联系方式,联系方式")
	private String contact;
		@ApiModelProperty(value="居住地省,市,区,居住地省,市,区")
	private String province,city,district;
		@ApiModelProperty(value="性别,性别")
	private String gender;
		@ApiModelProperty(value="证件号,证件号")
	private String certificate;
		@ApiModelProperty(value="邮箱,邮箱")
	private String email;
		@ApiModelProperty(value="微信,微信")
	private String wechat;
		@ApiModelProperty(value="专家类别,专家类别")
	private String category;
		@ApiModelProperty(value="社会职务,社会职务")
	private String socialFunction;
		@ApiModelProperty(value="备注,备注")
	private String comment;
		@ApiModelProperty(value="附件路径,附件路径")
	private String filePath;
		@ApiModelProperty(value="专家类别其他,专家类别其他")
	private String otherCategory;
		
	public ExpertVo(Long id   ,String name    ,Date birth    ,String contact    ,String province  ,String city  ,String district    ,String gender    ,String certificate    ,String email    ,String wechat    ,String category    ,String socialFunction    ,String comment    ,String filePath    ,String otherCategory  ) {
		this.tid = id;
		 	 this.name = name;
	 		 	 this.birth = birth;
	 		 	 this.contact = contact;
	 		 	 this.province = province;
	 	 this.city = city;
	 	 this.district = district;
	 		 	 this.gender = gender;
	 		 	 this.certificate = certificate;
	 		 	 this.email = email;
	 		 	 this.wechat = wechat;
	 		 	 this.category = category;
	 		 	 this.socialFunction = socialFunction;
	 		 	 this.comment = comment;
	 		 	 this.filePath = filePath;
	 		 	 this.otherCategory = otherCategory;
	 		}
	
	public ExpertVo() {
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
	 		 		public Date getBirth(){
			return birth;
		}
		
		public void setBirth(Date birth){
			this.birth=birth;
		}
	 		 		public String getContact(){
			return contact;
		}
		
		public void setContact(String contact){
			this.contact=contact;
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
	 		 		public String getGender(){
			return gender;
		}
		
		public void setGender(String gender){
			this.gender=gender;
		}
	 		 		public String getCertificate(){
			return certificate;
		}
		
		public void setCertificate(String certificate){
			this.certificate=certificate;
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
	 		 		public String getCategory(){
			return category;
		}
		
		public void setCategory(String category){
			this.category=category;
		}
	 		 		public String getSocialFunction(){
			return socialFunction;
		}
		
		public void setSocialFunction(String socialFunction){
			this.socialFunction=socialFunction;
		}
	 		 		public String getComment(){
			return comment;
		}
		
		public void setComment(String comment){
			this.comment=comment;
		}
	 		 		public String getFilePath(){
			return filePath;
		}
		
		public void setFilePath(String filePath){
			this.filePath=filePath;
		}
	 		 		public String getOtherCategory(){
			return otherCategory;
		}
		
		public void setOtherCategory(String otherCategory){
			this.otherCategory=otherCategory;
		}
	 		
}

