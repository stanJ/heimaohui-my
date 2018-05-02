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
* @ClassName: News 
* @Description: 消息定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_news")
@ApiModel(value="消息定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class News extends BaseEntity{
	
		  	    @ApiModelProperty(value="内容,内容")
		private String content;
		  	  @ApiModelProperty(value="是否置顶,是否置顶")
	 			private Boolean isTop;
	 		  	    
	 		
	 		  	 
	 		  	 
	 		  	 
	 		  	@Column(nullable=false,length=10)
		public Boolean getIsTop() {
					return isTop;
				}

				public void setIsTop(Boolean isTop) {
					this.isTop = isTop;
				}
	
		 		@Column(nullable=false,length=40)
		public String getContent(){
			return content;
		}
		
		public void setContent(String content){
			this.content=content;
		}
	 		

}
