package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: ContactorVo
 * @Description:客户联系人 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="客户联系人Vo对象")
public class ContactorVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="客户id,客户id")
	private String customerId;
		@ApiModelProperty(value="其他联系人,其他联系人")
	private String name;
		@ApiModelProperty(value="联系方式,联系方式")
	private String contact;
		@ApiModelProperty(value="微信,微信")
	private String wechat;
		@ApiModelProperty(value="邮箱,邮箱")
	private String email;
		@ApiModelProperty(value="序号,序号")
	private Integer serial;
		
	public ContactorVo(Long id   ,String customerId    ,String name    ,String contact    ,String wechat    ,String email    ,Integer serial  ) {
		this.tid = id;
		 	 this.customerId = customerId;
	 		 	 this.name = name;
	 		 	 this.contact = contact;
	 		 	 this.wechat = wechat;
	 		 	 this.email = email;
	 		 	 this.serial = serial;
	 		}
	
	public ContactorVo() {
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
	 		 		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name=name;
		}
	 		 		public String getContact(){
			return contact;
		}
		
		public void setContact(String contact){
			this.contact=contact;
		}
	 		 		public String getWechat(){
			return wechat;
		}
		
		public void setWechat(String wechat){
			this.wechat=wechat;
		}
	 		 		public String getEmail(){
			return email;
		}
		
		public void setEmail(String email){
			this.email=email;
		}
	 		 		public Integer getSerial(){
			return serial;
		}
		
		public void setSerial(Integer serial){
			this.serial=serial;
		}
	 		
}

