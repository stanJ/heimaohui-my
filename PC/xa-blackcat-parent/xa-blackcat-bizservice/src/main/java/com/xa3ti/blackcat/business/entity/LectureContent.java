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
* @ClassName: LectureContent 
* @Description: 演讲内容定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_lecturecontent")
@ApiModel(value="演讲内容定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class LectureContent extends BaseEntity{
	
		  	    @Depend(name="Expert.id" , showname="Expert.name")
	    @ApiModelProperty(value="专家id,专家id")
		private String expertId; 
	    	 		  	    @ApiModelProperty(value="序号,序号")
		private Integer serial;
	    	 		  	    @ApiModelProperty(value="内容,内容")
		private String content;
	    	 		
	
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
	 		 		@Column(nullable=false,length=2000)
		public String getContent(){
			return content;
		}
		
		public void setContent(String content){
			this.content=content;
		}
	 		

}
