package com.xa3ti.blackcat.business.entity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.Dict;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 
* @ClassName: Expert 
* @Description: 专家定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_expert")
@ApiModel(value="专家定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class Expert extends BaseEntity{
	
		  	    @ApiModelProperty(value="姓名,姓名")
		private String name;
	    	 		  	    @ApiModelProperty(value="出生日期,出生日期")
		private Date birth;
	    	 		  	    @ApiModelProperty(value="联系方式,联系方式")
		private String contact;
	    	 		  	    @Dict(name="PROVINCE")
	    @ApiModelProperty(value="居住地省,市,区,居住地省,市,区")
		private String province;
	    	  	    @Dict(name="CITY")
	    @ApiModelProperty(value="居住地省,市,区,居住地省,市,区")
		private String city;
	    	  	    @Dict(name="DISTRICT")
	    @ApiModelProperty(value="居住地省,市,区,居住地省,市,区")
		private String district;
	    	 		  	    @Dict(name="SEX")
	    @ApiModelProperty(value="性别,性别")
		private String gender;
	    	 		  	    @ApiModelProperty(value="证件号,证件号")
		private String certificate;
	    	 		  	    @ApiModelProperty(value="邮箱,邮箱")
		private String email;
	    	 		  	    @ApiModelProperty(value="微信,微信")
		private String wechat;
	    	 		  	    @Depend(name="ExpertCategory.id" , showname="ExpertCategory.name")
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

		@ApiModelProperty(value="附件名称,附件名称")
		private String fileName;

	@Column(nullable=true,length=200)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(nullable=false,length=50)
		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name=name;
		}
	 		 		@Column(nullable=true,length=10)
		public Date getBirth(){
			return birth;
		}
		
		public void setBirth(Date birth){
			this.birth=birth;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getContact(){
			return contact;
		}
		
		public void setContact(String contact){
			this.contact=contact;
		}
	 		 		@Column(nullable=true,length=10)
		public String getProvince(){
			return province;
		}
		
		public void setProvince(String province){
			this.province=province;
		}
	 		@Column(nullable=true,length=10)
		public String getCity(){
			return city;
		}
		
		public void setCity(String city){
			this.city=city;
		}
	 		@Column(nullable=true,length=10)
		public String getDistrict(){
			return district;
		}
		
		public void setDistrict(String district){
			this.district=district;
		}
	 		 		@Column(nullable=true,length=10)
		public String getGender(){
			return gender;
		}
		
		public void setGender(String gender){
			this.gender=gender;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getCertificate(){
			return certificate;
		}
		
		public void setCertificate(String certificate){
			this.certificate=certificate;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getEmail(){
			return email;
		}
		
		public void setEmail(String email){
			this.email=email;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getWechat(){
			return wechat;
		}
		
		public void setWechat(String wechat){
			this.wechat=wechat;
		}
	 		 		@Column(nullable=true,length=500)
		public String getCategory(){
			return category;
		}
		
		public void setCategory(String category){
			this.category=category;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getSocialFunction(){
			return socialFunction;
		}
		
		public void setSocialFunction(String socialFunction){
			this.socialFunction=socialFunction;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getComment(){
			return comment;
		}
		
		public void setComment(String comment){
			this.comment=comment;
		}
	 		 		@Column(nullable=true,length=200)
		public String getFilePath(){
			return filePath;
		}
		
		public void setFilePath(String filePath){
			this.filePath=filePath;
		}
	 		 		@Column(nullable=true,length=200)
		public String getOtherCategory(){
			return otherCategory;
		}
		
		public void setOtherCategory(String otherCategory){
			this.otherCategory=otherCategory;
		}
	 		

}
