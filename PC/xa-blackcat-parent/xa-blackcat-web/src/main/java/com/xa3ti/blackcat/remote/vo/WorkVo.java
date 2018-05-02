package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: WorkVo
 * @Description:合作记录 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="合作记录Vo对象")
public class WorkVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="客户id,客户id")
	private String customerId;
		@ApiModelProperty(value="序号,序号")
	private Integer serial;
		@ApiModelProperty(value="对接人,对接人")
	private String linker;
		@ApiModelProperty(value="专家名称,专家名称")
	private String expertName;
		@ApiModelProperty(value="活动时间,活动时间")
	private Date actionDate;
		@ApiModelProperty(value="活动地点,活动地点")
	private String actionAddress;
		@ApiModelProperty(value="评价,评价")
	private String comment;
		@ApiModelProperty(value="现场照片,现场照片")
	private String pics;
		
	public WorkVo(Long id   ,String customerId    ,Integer serial    ,String linker    ,String expertName    ,Date actionDate    ,String actionAddress    ,String comment    ,String pics  ) {
		this.tid = id;
		 	 this.customerId = customerId;
	 		 	 this.serial = serial;
	 		 	 this.linker = linker;
	 		 	 this.expertName = expertName;
	 		 	 this.actionDate = actionDate;
	 		 	 this.actionAddress = actionAddress;
	 		 	 this.comment = comment;
	 		 	 this.pics = pics;
	 		}
	
	public WorkVo() {
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
	
		 		public String getCustomerId(){
			return customerId;
		}
		
		public void setCustomerId(String customerId){
			this.customerId=customerId;
		}
	 		 		public Integer getSerial(){
			return serial;
		}
		
		public void setSerial(Integer serial){
			this.serial=serial;
		}
	 		 		public String getLinker(){
			return linker;
		}
		
		public void setLinker(String linker){
			this.linker=linker;
		}
	 		 		public String getExpertName(){
			return expertName;
		}
		
		public void setExpertName(String expertName){
			this.expertName=expertName;
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

