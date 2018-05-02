package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: CooperateVo
 * @Description:配合度 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="配合度Vo对象")
public class CooperateVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="专家id,专家id")
	private String expertId;
		@ApiModelProperty(value="客户名称,客户名称")
	private String customerName;
		@ApiModelProperty(value="活动时间,活动时间")
	private Date actionDate;
		@ApiModelProperty(value="活动地址,活动地址")
	private String actionAddress;
		@ApiModelProperty(value="合同价,合同价")
	private Double contractPrice;
		@ApiModelProperty(value="成本价,成本价")
	private Double costPrice;
		@ApiModelProperty(value="专家联系人,专家联系人")
	private String expertContactor;
		@ApiModelProperty(value="评价,评价")
	private String comment;
		@ApiModelProperty(value="现场照片,现场照片")
	private String pics;
		
	public CooperateVo(Long id   ,String expertId    ,String customerName    ,Date actionDate    ,String actionAddress    ,Double contractPrice    ,Double costPrice    ,String expertContactor    ,String comment    ,String pics  ) {
		this.tid = id;
		 	 this.expertId = expertId;
	 		 	 this.customerName = customerName;
	 		 	 this.actionDate = actionDate;
	 		 	 this.actionAddress = actionAddress;
	 		 	 this.contractPrice = contractPrice;
	 		 	 this.costPrice = costPrice;
	 		 	 this.expertContactor = expertContactor;
	 		 	 this.comment = comment;
	 		 	 this.pics = pics;
	 		}
	
	public CooperateVo() {
		super();
	}
	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
		 		public String getExpertId(){
			return expertId;
		}
		
		public void setExpertId(String expertId){
			this.expertId=expertId;
		}
	 		 		public String getCustomerName(){
			return customerName;
		}
		
		public void setCustomerName(String customerName){
			this.customerName=customerName;
		}
	 		 		public Date getActionDate(){
			return actionDate;
		}
		
		public void setActionDate(Date actionDate){
			this.actionDate=actionDate;
		}
	 		 		public String getActionAddress(){
			return actionAddress;
		}
		
		public void setActionAddress(String actionAddress){
			this.actionAddress=actionAddress;
		}
	 		 		public Double getContractPrice(){
			return contractPrice;
		}
		
		public void setContractPrice(Double contractPrice){
			this.contractPrice=contractPrice;
		}
	 		 		public Double getCostPrice(){
			return costPrice;
		}
		
		public void setCostPrice(Double costPrice){
			this.costPrice=costPrice;
		}
	 		 		public String getExpertContactor(){
			return expertContactor;
		}
		
		public void setExpertContactor(String expertContactor){
			this.expertContactor=expertContactor;
		}
	 		 		public String getComment(){
			return comment;
		}
		
		public void setComment(String comment){
			this.comment=comment;
		}
	 		 		public String getPics(){
			return pics;
		}
		
		public void setPics(String pics){
			this.pics=pics;
		}
	 		
}

