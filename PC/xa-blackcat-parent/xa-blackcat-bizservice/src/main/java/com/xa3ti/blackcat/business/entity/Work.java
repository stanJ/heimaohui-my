package com.xa3ti.blackcat.business.entity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 
* @ClassName: Work 
* @Description: 合作记录定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_work")
@ApiModel(value="合作记录定义表")
@QueryDao(method=BaseConstant.DAO.DYNASQL)
public class Work extends BaseEntity{
	
		  	    @ApiModelProperty(value="客户id,客户id")
		private String customerId;
	    	 		  	    @ApiModelProperty(value="序号,序号")
		private Integer serial;
	    	 		  	    @ApiModelProperty(value="对接人,对接人")
		private String linker;
	    	 		  	    @ApiModelProperty(value="专家名称,专家名称")
		private String expertName;

	@ApiModelProperty(value="专家id,专家id")
	    private String expertId;
	    	 		  	    @ApiModelProperty(value="活动时间,活动时间")
		private Date actionDate;
	    	 		  	    @ApiModelProperty(value="活动地点,活动地点")
		private String actionAddress;
	    	 		  	    @ApiModelProperty(value="评价,评价")
		private String comment;
	    	 		  	    @ApiModelProperty(value="现场照片,现场照片")
		private String pics;
	    	 	
	    	 		  	    
	    	 		  	 @ApiModelProperty(value="合同价,合同价")
	    	 			private Double contractPrice;
	    	 		  	 
	    	 		  	@ApiModelProperty(value="创建人,创建人")
	    	 			private String creatorName;

	@Column(nullable=true,length=50)
	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	@Column(nullable=true,length=50)
		 		public String getCreatorName() {
							return creatorName;
						}

						public void setCreatorName(String creatorName) {
							this.creatorName = creatorName;
						}

	    	 		  	
	    	 		  	 
	    	 		  	@Column(nullable=true,length=10)
	    	 			public Double getContractPrice(){
	    	 				return contractPrice;
	    	 			}
	    	 			
	    	 			public void setContractPrice(Double contractPrice){
	    	 				this.contractPrice=contractPrice;
	    	 			}
	    	 		  	 
	
		 		@Column(nullable=false,length=32)
		public String getCustomerId(){
			return customerId;
		}
		
		public void setCustomerId(String customerId){
			this.customerId=customerId;
		}
	 		 		@Column(nullable=true,length=10)
		public Integer getSerial(){
			return serial;
		}
		
		public void setSerial(Integer serial){
			this.serial=serial;
		}
	 		 		@Column(nullable=false,length=10)
		public String getLinker(){
			return linker;
		}
		
		public void setLinker(String linker){
			this.linker=linker;
		}
	 		 		@Column(nullable=false,length=20)
		public String getExpertName(){
			return expertName;
		}
		
		public void setExpertName(String expertName){
			this.expertName=expertName;
		}
	 		 		@Column(nullable=true,length=10)
		public Date getActionDate(){
			return actionDate;
		}
		
		public void setActionDate(Date actionDate){
			this.actionDate=actionDate;
		}
	 		 		@Column(nullable=true,length=10)
		public String getActionAddress(){
			return actionAddress;
		}
		
		public void setActionAddress(String actionAddress){
			this.actionAddress=actionAddress;
		}
	 		 		@Column(nullable=true,length=10)
		public String getComment(){
			return comment;
		}
		
		public void setComment(String comment){
			this.comment=comment;
		}
	 		 		@Column(nullable=true,length=2000)
		public String getPics(){
			return pics;
		}
		
		public void setPics(String pics){
			this.pics=pics;
		}
	 		

}
