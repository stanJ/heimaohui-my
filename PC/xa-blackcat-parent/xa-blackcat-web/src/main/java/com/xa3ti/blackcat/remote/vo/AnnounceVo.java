package com.xa3ti.blackcat.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.sql.Time;
/**
 * @ClassName: AnnounceVo
 * @Description:公告 Vo
 * @author hchen
 * @date 2014年8月13日 下午1:40:23
 *
 */
@ApiModel(value="公告Vo对象")
public class AnnounceVo {

	@ApiModelProperty(value="主键，自动增长")
	private Long tid;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 		@ApiModelProperty(value="年份,年份")
	private String ayear;
		@ApiModelProperty(value="成交单数,成交单数")
	private Double yearOrders;
		@ApiModelProperty(value="年月,年月")
	private String byear;
		@ApiModelProperty(value="月份,月份")
	private String bmonth;
		@ApiModelProperty(value="月份成交单数,月份成交单数")
	private Double monthOrders;
		
	public AnnounceVo(Long id   ,String ayear    ,Double yearOrders    ,String byear    ,String bmonth    ,Double monthOrders  ) {
		this.tid = id;
		 	 this.ayear = ayear;
	 		 	 this.yearOrders = yearOrders;
	 		 	 this.byear = byear;
	 		 	 this.bmonth = bmonth;
	 		 	 this.monthOrders = monthOrders;
	 		}
	
	public AnnounceVo() {
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
	
		 		public String getAyear(){
			return ayear;
		}
		
		public void setAyear(String ayear){
			this.ayear=ayear;
		}
	 		 		public Double getYearOrders(){
			return yearOrders;
		}
		
		public void setYearOrders(Double yearOrders){
			this.yearOrders=yearOrders;
		}
	 		 		public String getByear(){
			return byear;
		}
		
		public void setByear(String byear){
			this.byear=byear;
		}
	 		 		public String getBmonth(){
			return bmonth;
		}
		
		public void setBmonth(String bmonth){
			this.bmonth=bmonth;
		}
	 		 		public Double getMonthOrders(){
			return monthOrders;
		}
		
		public void setMonthOrders(Double monthOrders){
			this.monthOrders=monthOrders;
		}
	 		
}

