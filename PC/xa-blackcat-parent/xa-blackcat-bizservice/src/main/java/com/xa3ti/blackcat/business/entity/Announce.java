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
* @ClassName: Announce 
* @Description: 公告定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_announce")
@ApiModel(value="公告定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class Announce extends BaseEntity{
	
		  	    @ApiModelProperty(value="年份,年份")
		private String ayear;
	    	 		  	    @ApiModelProperty(value="成交单数,成交单数")
		private Double yearOrders;
	    	 		  	    @ApiModelProperty(value="年月,年月")
		private String byear;
	    	 		  	    @ApiModelProperty(value="月份,月份")
		private String bmonth;
	    	 		  	    @ApiModelProperty(value="月份成交单数,月份成交单数")
		private Double monthOrders;
	    	 		
	
		 		@Column(nullable=false,length=10)
		public String getAyear(){
			return ayear;
		}
		
		public void setAyear(String ayear){
			this.ayear=ayear;
		}
	 		 		@Column(nullable=false,length=10)
		public Double getYearOrders(){
			return yearOrders;
		}
		
		public void setYearOrders(Double yearOrders){
			this.yearOrders=yearOrders;
		}
	 		 		@Column(nullable=false,length=10)
		public String getByear(){
			return byear;
		}
		
		public void setByear(String byear){
			this.byear=byear;
		}
	 		 		@Column(nullable=false,length=2)
		public String getBmonth(){
			return bmonth;
		}
		
		public void setBmonth(String bmonth){
			this.bmonth=bmonth;
		}
	 		 		@Column(nullable=false,length=10)
		public Double getMonthOrders(){
			return monthOrders;
		}
		
		public void setMonthOrders(Double monthOrders){
			this.monthOrders=monthOrders;
		}
	 		

}
