package com.xa3ti.blackcat.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.BaseEntity;

/**
 * 
* @ClassName: UserExtend 
* @Description: 用户扩展定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_userextend")
@ApiModel(value="用户扩展定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class UserExtend extends BaseEntity{
	
		  	    @ApiModelProperty(value="姓名,姓名")
		private String name;
	    	 		  	    @ApiModelProperty(value="公司,公司")
		private String company;
	    	 		  	    @ApiModelProperty(value="用户类型,用户类型")
		private Long userType;
	    	 		  	    @ApiModelProperty(value="用户id,用户id")
		private String userId;
	    	 		  	    
	    	 		  	 @ApiModelProperty(value="联系方式,联系方式")
	    	 			private String contact;
	    	 		
	
	    	 		  	 @ApiModelProperty(value="密码修改次数,密码修改次数")
		    	 			private Integer passwordUpdateTimes;
		    	 		
	    	 		  	 
	    	 		  	 
	    	 		  	 
	    	 		  	 
	    	 		  	 
	    	 		  	 
	    	 		  	@Column(nullable=false,length=10,columnDefinition = "int default 0")	 
	    	 		  	public Integer getPasswordUpdateTimes() {
							return passwordUpdateTimes;
						}

						public void setPasswordUpdateTimes(Integer passwordUpdateTimes) {
							this.passwordUpdateTimes = passwordUpdateTimes;
						}

						@Column(nullable=true,length=50)	 
		 		public String getContact() {
							return contact;
						}

						public void setContact(String contact) {
							this.contact = contact;
						}

				@Column(nullable=false,length=50)
		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name=name;
		}
	 		 		@Column(nullable=true,length=200)
		public String getCompany(){
			return company;
		}
		
		public void setCompany(String company){
			this.company=company;
		}
	 		 		@Column(nullable=false,length=10)
		public Long getUserType(){
			return userType;
		}
		
		public void setUserType(Long userType){
			this.userType=userType;
		}
	 		 		@Column(nullable=false,length=32)
		public String getUserId(){
			return userId;
		}
		
		public void setUserId(String userId){
			this.userId=userId;
		}
	 		

}
