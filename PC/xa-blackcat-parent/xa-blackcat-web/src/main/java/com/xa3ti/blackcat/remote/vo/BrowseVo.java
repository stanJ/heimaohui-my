package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: BrowseVo
 * @Description:浏览记录 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="浏览记录Vo对象")
public class BrowseVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="账号,账号")
	private String userId;
		@ApiModelProperty(value="浏览时间,浏览时间")
	private Date btime;
		@ApiModelProperty(value="专家id,专家id")
	private String expertId;
		
	public BrowseVo(Long id   ,String userId    ,Date btime    ,String expertId  ) {
		this.tid = id;
		 	 this.userId = userId;
	 		 	 this.btime = btime;
	 		 	 this.expertId = expertId;
	 		}
	
	public BrowseVo() {
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
	
		 		public String getUserId(){
			return userId;
		}
		
		public void setUserId(String userId){
			this.userId=userId;
		}
	 		 		public Date getBtime(){
			return btime;
		}
		
		public void setBtime(Date btime){
			this.btime=btime;
		}
	 		 		public String getExpertId(){
			return expertId;
		}
		
		public void setExpertId(String expertId){
			this.expertId=expertId;
		}
	 		
}

