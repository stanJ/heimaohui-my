package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: ExpertCategoryVo
 * @Description:专家类别 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="专家类别Vo对象")
public class ExpertCategoryVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="名称,名称")
	private String name;
		@ApiModelProperty(value="备注,备注")
	private String comment;
		
	public ExpertCategoryVo(Long id   ,String name    ,String comment  ) {
		this.tid = id;
		 	 this.name = name;
	 		 	 this.comment = comment;
	 		}
	
	public ExpertCategoryVo() {
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
	 		 		public String getComment(){
			return comment;
		}
		
		public void setComment(String comment){
			this.comment=comment;
		}
	 		
}

