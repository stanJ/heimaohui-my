package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: UserExtendVo
 * @Description:用户扩展 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="用户扩展Vo对象")
public class UserExtendVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="姓名,姓名")
	private String name;
		@ApiModelProperty(value="公司,公司")
	private String company;
		@ApiModelProperty(value="用户类型,用户类型")
	private Long userType;
		@ApiModelProperty(value="用户id,用户id")
	private String userId;
		
	public UserExtendVo(Long id   ,String name    ,String company    ,Long userType    ,String userId  ) {
		this.tid = id;
		 	 this.name = name;
	 		 	 this.company = company;
	 		 	 this.userType = userType;
	 		 	 this.userId = userId;
	 		}
	
	public UserExtendVo() {
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
	
		 		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name=name;
		}
	 		 		public String getCompany(){
			return company;
		}
		
		public void setCompany(String company){
			this.company=company;
		}
	 		 		public Long getUserType(){
			return userType;
		}
		
		public void setUserType(Long userType){
			this.userType=userType;
		}
	 		 		public String getUserId(){
			return userId;
		}
		
		public void setUserId(String userId){
			this.userId=userId;
		}
	 		
}

