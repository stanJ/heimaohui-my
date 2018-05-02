package com.xa3ti.blackcat.business.entity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 曹文波
 * @ClassName: Intro
 * @Description: 公司简介定义表
 * @date 2014年10月11日 上午10:47:49
 */
@Entity
@Table(name = "tb_xa_intro")
@ApiModel(value = "公司简介定义表")
@QueryDao(method = BaseConstant.DAO.DYNASQL)
public class Intro extends BaseEntity {

    @ApiModelProperty(value = "文件名称,文件名称")
    private String fileName;
    @ApiModelProperty(value = "上传时间,上传时间")
    private Date uptime;
    @ApiModelProperty(value = "文件路径,文件路径")
    private String filePath;

    @ApiModelProperty(value = "是否置顶,是否置顶")
    private Boolean isTop;


    @Column(nullable = false, length = 10)
    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    @Column(nullable = false, length = 50)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(nullable = false, length = 10)
    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    @Column(nullable = false, length = 200)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
