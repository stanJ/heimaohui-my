/**
 * 
 */
package com.xa3ti.blackcat.message;

import java.util.Date;

/**
 * @author nijie
 *
 */
public class Msg {
	private Long id;
	private String channel;
	private Long clinicId;
	private String content;
	private Integer mstatus;
	private Long receiverId;
	private Date send_date;
	private String sender;
	private String title;

	private Integer retryTimes;

	private Integer receiverType;

	private String mobile;

	private Integer priority;
	
	
	private String failureReason;
	
	
	

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(Integer receiverType) {
		this.receiverType = receiverType;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMstatus() {
		return mstatus;
	}

	public void setMstatus(Integer mstatus) {
		this.mstatus = mstatus;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Date getSend_date() {
		return send_date;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
