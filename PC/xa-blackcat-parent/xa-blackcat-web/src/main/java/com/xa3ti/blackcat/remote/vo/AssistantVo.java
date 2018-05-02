package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: AssistantVo
 * @Description:助手信息 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="助手信息Vo对象")
public class AssistantVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="专家id,专家id")
	private String expertId;
		@ApiModelProperty(value="序号,序号")
	private Integer serial;
		@ApiModelProperty(value="名称,名称")
	private String name;
		@ApiModelProperty(value="出生日期,出生日期")
	private Date birth;
		@ApiModelProperty(value="联系方式,联系方式")
	private String contact;
		@ApiModelProperty(value="微信,微信")
	private String wechat;
		@ApiModelProperty(value="性别,性别")
	private String gender;
		@ApiModelProperty(value="证件号,证件号")
	private String certificate;
		@ApiModelProperty(value="邮箱,邮箱")
	private String email;
		
	public AssistantVo(Long id   ,String expertId    ,Integer serial    ,String name    ,Date birth    ,String contact    ,String wechat    ,String gender    ,String certificate    ,String email  ) {
		this.tid = id;
		 	 this.expertId = expertId;
	 		 	 this.serial = serial;
	 		 	 this.name = name;
	 		 	 this.birth = birth;
	 		 	 this.contact = contact;
	 		 	 this.wechat = wechat;
	 		 	 this.gender = gender;
	 		 	 this.certificate = certificate;
	 		 	 this.email = email;
	 		}
	
	public AssistantVo() {
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
	 		 		public Integer getSerial(){
			return serial;
		}
		
		public void setSerial(Integer serial){
			this.serial=serial;
		}
	 		 		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name=name;
		}
	 		 		public Date getBirth(){
			return birth;
		}
		
		public void setBirth(Date birth){
			this.birth=birth;
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
	 		 		public String getGender(){
			return gender;
		}
		
		public void setGender(String gender){
			this.gender=gender;
		}
	 		 		public String getCertificate(){
			return certificate;
		}
		
		public void setCertificate(String certificate){
			this.certificate=certificate;
		}
	 		 		public String getEmail(){
			return email;
		}
		
		public void setEmail(String email){
			this.email=email;
		}
	 		
}

