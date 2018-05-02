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



import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.Dict;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.annotation.TableJoin;

/**
 * 
* @ClassName: Feedback 
* @Description: 意见反馈定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_ms_cms_feedback")
         @ApiModel(value="意见反馈定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class Feedback extends BaseEntity{
	
		  	    @ApiModelProperty(value="用户Id,用户Id")
		private Long userId;
	    	 		  	    @ApiModelProperty(value="反馈时间,反馈时间")
		private Date fdate;
	    	 		  	    @ApiModelProperty(value="反馈内容,反馈内容")
		private String content;
	    	 		
	
		 		@Column(nullable=true,length=10)
		public Long getUserId(){
			return userId;
		}
		
		public void setUserId(Long userId){
			this.userId=userId;
		}
	 		 		@Column(nullable=false,length=10)
		public Date getFdate(){
			return fdate;
		}
		
		public void setFdate(Date fdate){
			this.fdate=fdate;
		}
	 		 		@Column(nullable=false,length=2000)
		public String getContent(){
			return content;
		}
		
		public void setContent(String content){
			this.content=content;
		}
	 		

}
