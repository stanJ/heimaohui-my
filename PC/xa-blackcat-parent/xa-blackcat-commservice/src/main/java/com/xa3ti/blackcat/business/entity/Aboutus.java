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


import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.Dict;
import com.xa3ti.blackcat.base.annotation.TableJoin;

/**
 * 
* @ClassName: Aboutus 
* @Description: 关于我们定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_ms_cms_aboutus")
@ApiModel(value="关于我们定义表")
@QueryDao(method=BaseConstant.DAO.MYBATIS)
public class Aboutus extends BaseEntity{
	
		  	    @ApiModelProperty(value="Logo,Logo")
		private String logo;
	    	 		  	    @ApiModelProperty(value="轮播图,轮播图")
		private String pics;
	    	 		  	    @ApiModelProperty(value="版本号,版本号")
		private String versionno;
	    	 		  	    @ApiModelProperty(value="电话,电话")
		private String tel;
	    	 		  	    @ApiModelProperty(value="内容,内容")
		private String content;
	    	 		
	
		 		@Column(nullable=true,length=500)
		public String getLogo(){
			return logo;
		}
		
		public void setLogo(String logo){
			this.logo=logo;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getPics(){
			return pics;
		}
		
		public void setPics(String pics){
			this.pics=pics;
		}
	 		 		@Column(nullable=true,length=50)
		public String getVersionno(){
			return versionno;
		}
		
		public void setVersionno(String versionno){
			this.versionno=versionno;
		}
	 		 		@Column(nullable=true,length=50)
		public String getTel(){
			return tel;
		}
		
		public void setTel(String tel){
			this.tel=tel;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getContent(){
			return content;
		}
		
		public void setContent(String content){
			this.content=content;
		}
	 		

}
