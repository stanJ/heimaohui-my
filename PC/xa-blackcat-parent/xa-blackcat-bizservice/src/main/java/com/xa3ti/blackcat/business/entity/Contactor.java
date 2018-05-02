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
* @ClassName: Contactor 
* @Description: 客户联系人定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_contactor")
@ApiModel(value="客户联系人定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class Contactor extends BaseEntity{
	
		  	    @ApiModelProperty(value="客户id,客户id")
		private String customerId;
	    	 		  	    @ApiModelProperty(value="其他联系人,其他联系人")
		private String name;
	    	 		  	    @ApiModelProperty(value="联系方式,联系方式")
		private String contact;
	    	 		  	    @ApiModelProperty(value="微信,微信")
		private String wechat;
	    	 		  	    @ApiModelProperty(value="邮箱,邮箱")
		private String email;
	    	 		  	    @ApiModelProperty(value="序号,序号")
		private Integer serial;
	    	 		
	
		 		@Column(nullable=false,length=32)
		public String getCustomerId(){
			return customerId;
		}
		
		public void setCustomerId(String customerId){
			this.customerId=customerId;
		}
	 		 		@Column(nullable=false,length=50)
		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name=name;
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
	 		 		@Column(nullable=true,length=50)
		public String getEmail(){
			return email;
		}
		
		public void setEmail(String email){
			this.email=email;
		}
	 		 		@Column(nullable=true,length=10)
		public Integer getSerial(){
			return serial;
		}
		
		public void setSerial(Integer serial){
			this.serial=serial;
		}
	 		

}
