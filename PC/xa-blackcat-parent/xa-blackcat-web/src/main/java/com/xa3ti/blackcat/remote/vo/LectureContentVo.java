package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: LectureContentVo
 * @Description:演讲内容 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="演讲内容Vo对象")
public class LectureContentVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="专家id,专家id")
	private String expertId;
		@ApiModelProperty(value="序号,序号")
	private Integer serial;
		@ApiModelProperty(value="内容,内容")
	private String content;
		
	public LectureContentVo(Long id   ,String expertId    ,Integer serial    ,String content  ) {
		this.tid = id;
		 	 this.expertId = expertId;
	 		 	 this.serial = serial;
	 		 	 this.content = content;
	 		}
	
	public LectureContentVo() {
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
	 		 		public String getContent(){
			return content;
		}
		
		public void setContent(String content){
			this.content=content;
		}
	 		
}

