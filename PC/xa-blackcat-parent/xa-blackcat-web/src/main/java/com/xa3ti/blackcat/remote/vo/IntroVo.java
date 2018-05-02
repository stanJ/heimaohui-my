package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: IntroVo
 * @Description:公司简介 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="公司简介Vo对象")
public class IntroVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="文件名称,文件名称")
	private String fileName;
		@ApiModelProperty(value="上传时间,上传时间")
	private Date uptime;
		@ApiModelProperty(value="文件路径,文件路径")
	private String filePath;
		
	public IntroVo(Long id   ,String fileName    ,Date uptime    ,String filePath  ) {
		this.tid = id;
		 	 this.fileName = fileName;
	 		 	 this.uptime = uptime;
	 		 	 this.filePath = filePath;
	 		}
	
	public IntroVo() {
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
	
		 		public String getFileName(){
			return fileName;
		}
		
		public void setFileName(String fileName){
			this.fileName=fileName;
		}
	 		 		public Date getUptime(){
			return uptime;
		}
		
		public void setUptime(Date uptime){
			this.uptime=uptime;
		}
	 		 		public String getFilePath(){
			return filePath;
		}
		
		public void setFilePath(String filePath){
			this.filePath=filePath;
		}
	 		
}

