package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: PicsVo
 * @Description:宣传照片 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="宣传照片Vo对象")
public class PicsVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="专家id,专家id")
	private String expertId;
		@ApiModelProperty(value="序号,序号")
	private Integer serial;
		@ApiModelProperty(value="照片,照片")
	private String pic;
		
	public PicsVo(Long id   ,String expertId    ,Integer serial    ,String pic  ) {
		this.tid = id;
		 	 this.expertId = expertId;
	 		 	 this.serial = serial;
	 		 	 this.pic = pic;
	 		}
	
	public PicsVo() {
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
	 		 		public String getPic(){
			return pic;
		}
		
		public void setPic(String pic){
			this.pic=pic;
		}
	 		
}

