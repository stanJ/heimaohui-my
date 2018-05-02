package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: NewsVo
 * @Description:消息 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="消息Vo对象")
public class NewsVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="内容,内容")
	private String content;
		
	public NewsVo(Long id   ,String content  ) {
		this.tid = id;
		 	 this.content = content;
	 		}
	
	public NewsVo() {
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
	
		 		public String getContent(){
			return content;
		}
		
		public void setContent(String content){
			this.content=content;
		}
	 		
}

