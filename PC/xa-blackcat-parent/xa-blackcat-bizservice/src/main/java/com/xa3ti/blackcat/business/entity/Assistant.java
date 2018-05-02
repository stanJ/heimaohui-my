package com.xa3ti.blackcat.business.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Time;
import java.util.Date;

import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.util.XaUtil;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.entity.BaseEntity;

import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.Dict;
import com.xa3ti.blackcat.base.annotation.TableJoin;

/**
 * 
* @ClassName: Assistant 
* @Description: 助手信息定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_assistant")
@ApiModel(value="助手信息定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class Assistant extends BaseEntity{
	
		  	    @Depend(name="Expert.id" , showname="Expert.name")
	    @ApiModelProperty(value="专家id,专家id")
		private String expertId; 
	    	 		  	    @ApiModelProperty(value="序号,序号")
		private Integer serial;
	    	 		  	    @ApiModelProperty(value="名称,名称")
		private String name;
	    	 		  	    @ApiModelProperty(value="出生日期,出生日期")
		private Date birth;
	    	 		  	    @ApiModelProperty(value="联系方式,联系方式")
		private String contact;
	    	 		  	    @ApiModelProperty(value="微信,微信")
		private String wechat;
	    	 		  	    @Dict(name="SEX")
	    @ApiModelProperty(value="性别,性别")
		private String gender;
	    	 		  	    @ApiModelProperty(value="证件号,证件号")
		private String certificate;
	    	 		  	    @ApiModelProperty(value="邮箱,邮箱")
		private String email;
	    	 		
	
		 		@Column(nullable=false,length=32)
		public String getExpertId(){
			return expertId;
		}
		
		public void setExpertId(String expertId){
			this.expertId=expertId;
		}
	 		 		@Column(nullable=true,length=10)
		public Integer getSerial(){
			return serial;
		}
		
		public void setSerial(Integer serial){
			this.serial=serial;
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
	 		 		@Column(nullable=true,length=200)
		public String getContact(){
			return contact;
		}
		
		public void setContact(String contact){
			this.contact=contact;
		}
	 		 		@Column(nullable=true,length=50)
		public String getWechat(){
			return wechat;
		}
		
		public void setWechat(String wechat){
			this.wechat=wechat;
		}
	 		 		@Column(nullable=true,length=10)
		public String getGender(){
			return gender;
		}
		
		public void setGender(String gender){
			this.gender=gender;
		}
	 		 		@Column(nullable=true,length=50)
		public String getCertificate(){
			return certificate;
		}
		
		public void setCertificate(String certificate){
			this.certificate=certificate;
		}
	 		 		@Column(nullable=true,length=50)
		public String getEmail(){
			return email;
		}
		
		public void setEmail(String email){
			this.email=email;
		}
	 		

}
