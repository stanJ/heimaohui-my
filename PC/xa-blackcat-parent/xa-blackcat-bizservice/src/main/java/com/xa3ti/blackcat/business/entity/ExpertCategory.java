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
* @ClassName: ExpertCategory 
* @Description: 专家类别定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_expertcategory")
@ApiModel(value="专家类别定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class ExpertCategory extends BaseEntity{
	
		  	    @ApiModelProperty(value="名称,名称")
		private String name;
	    	 		  	    @ApiModelProperty(value="备注,备注")
		private String comment;
	    	 		
	
		 		@Column(nullable=false,length=50)
		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name=name;
		}
	 		 		@Column(nullable=true,length=200)
		public String getComment(){
			return comment;
		}
		
		public void setComment(String comment){
			this.comment=comment;
		}
	 		

}
