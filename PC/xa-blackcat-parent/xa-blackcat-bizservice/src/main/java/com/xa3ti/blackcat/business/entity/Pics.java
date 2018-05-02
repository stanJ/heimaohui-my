package com.xa3ti.blackcat.business.entity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 曹文波
 * @ClassName: Pics
 * @Description: 宣传照片定义表
 * @date 2014年10月11日 上午10:47:49
 */
@Entity
@Table(name = "tb_xa_pics")
@ApiModel(value = "宣传照片定义表")
@QueryDao(method = BaseConstant.DAO.DYNASQL)
public class Pics extends BaseEntity {

    @Depend(name = "Expert.id", showname = "Expert.name")
    @ApiModelProperty(value = "专家id,专家id")
    private String expertId;
    @ApiModelProperty(value = "序号,序号")
    private Integer serial;
    @ApiModelProperty(value = "照片,照片")
    private String pic;


    @Column(nullable = false, length = 32)
    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    @Column(nullable = true, length = 10)
    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    @Column(nullable = false, length = 200)
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


}
