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
* @ClassName: Message 
* @Description: 消息定义表
* @author 曹文波
* @date 2014年10月11日 上午10:47:49 
*
 */
@Entity
@Table(name = "tb_xa_message")
@ApiModel(value="消息定义表")
@QueryDao(method=BaseConstant.DAO.JPA)
public class Message extends BaseEntity{
	
		  	    @ApiModelProperty(value="渠道,渠道")
		private String channel;
	    	 		  	    @ApiModelProperty(value="接收者,接收者")
		private Long receiverId;
	    	 		  	    @ApiModelProperty(value="发送者,发送者")
		private Long senderId;
	    	 		  	    @ApiModelProperty(value="发送日期,发送日期")
		private Date sendDate;
	    	 		  	    @ApiModelProperty(value="标题,标题")
		private String title;
	    	 		  	    @ApiModelProperty(value="内容,内容")
		private String content;
	    	 		  	    @ApiModelProperty(value="接收者类型,接收者类型")
		private Integer receiverType;
	    	 		  	    @ApiModelProperty(value="优先级,优先级")
		private Integer priority;
	    	 		  	    @ApiModelProperty(value="重试次数,重试次数")
		private Integer retryTimes;
	    	 		  	    @ApiModelProperty(value="手机,手机")
		private String mobile;
	    	 		  	    @ApiModelProperty(value="发送状态,发送状态")
		private Integer mstatus;
	    	 		  	    @ApiModelProperty(value="失败原因,失败原因")
		private String failureReason;
	    	 		  	    @ApiModelProperty(value="接受者群号,接受者群号")
		private Long groupId;
	    	 		
	
		 		@Column(nullable=false,length=50)
		public String getChannel(){
			return channel;
		}
		
		public void setChannel(String channel){
			this.channel=channel;
		}
	 		 		@Column(nullable=false,length=10)
		public Long getReceiverId(){
			return receiverId;
		}
		
		public void setReceiverId(Long receiverId){
			this.receiverId=receiverId;
		}
	 		 		@Column(nullable=false,length=10)
		public Long getSenderId(){
			return senderId;
		}
		
		public void setSenderId(Long senderId){
			this.senderId=senderId;
		}
	 		 		@Column(nullable=true,length=10)
		public Date getSendDate(){
			return sendDate;
		}
		
		public void setSendDate(Date sendDate){
			this.sendDate=sendDate;
		}
	 		 		@Column(nullable=false,length=500)
		public String getTitle(){
			return title;
		}
		
		public void setTitle(String title){
			this.title=title;
		}
	 		 		@Column(nullable=false,length=2000)
		public String getContent(){
			return content;
		}
		
		public void setContent(String content){
			this.content=content;
		}
	 		 		@Column(nullable=false,length=10)
		public Integer getReceiverType(){
			return receiverType;
		}
		
		public void setReceiverType(Integer receiverType){
			this.receiverType=receiverType;
		}
	 		 		@Column(nullable=false,length=10)
		public Integer getPriority(){
			return priority;
		}
		
		public void setPriority(Integer priority){
			this.priority=priority;
		}
	 		 		@Column(nullable=true,length=10)
		public Integer getRetryTimes(){
			return retryTimes;
		}
		
		public void setRetryTimes(Integer retryTimes){
			this.retryTimes=retryTimes;
		}
	 		 		@Column(nullable=true,length=50)
		public String getMobile(){
			return mobile;
		}
		
		public void setMobile(String mobile){
			this.mobile=mobile;
		}
	 		 		@Column(nullable=false,length=10)
		public Integer getMstatus(){
			return mstatus;
		}
		
		public void setMstatus(Integer mstatus){
			this.mstatus=mstatus;
		}
	 		 		@Column(nullable=true,length=500)
		public String getFailureReason(){
			return failureReason;
		}
		
		public void setFailureReason(String failureReason){
			this.failureReason=failureReason;
		}
	 		 		@Column(nullable=true,length=10)
		public Long getGroupId(){
			return groupId;
		}
		
		public void setGroupId(Long groupId){
			this.groupId=groupId;
		}
	 		

}
