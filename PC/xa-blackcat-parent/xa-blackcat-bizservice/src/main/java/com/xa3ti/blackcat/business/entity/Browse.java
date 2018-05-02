package com.xa3ti.blackcat.business.entity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.JoinField;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 曹文波
 * @ClassName: Browse
 * @Description: 浏览记录定义表
 * @date 2014年10月11日 上午10:47:49
 */
@Entity
@Table(name = "tb_xa_browse")
@ApiModel(value = "浏览记录定义表")
@QueryDao(method = BaseConstant.DAO.DYNASQL)
public class Browse extends BaseEntity {

    @ApiModelProperty(value = "账号,账号")
    private String userId;

    @javax.persistence.Transient
    @ApiModelProperty(value = "用户姓名,用户姓名")
    @Depend(name = "XaCmsUser.userId", showname = "XaCmsUser.userName")
    @JoinField(selffield="userId",joinfield="userId")
    private String userName;

    @ApiModelProperty(value = "浏览时间,浏览时间")
    private Date btime;
    @Depend(name = "Expert.id", showname = "Expert.name")
    @ApiModelProperty(value = "专家id,专家id")
    private String expertId;


    @ApiModelProperty(value = "浏览类型,浏览类型")
    private Integer btype;

    @ApiModelProperty(value = "用户请求URL,用户请求URL")
    private String userUrl;

    @ApiModelProperty(value = "用户请求参数,用户请求参数")
    private String userParams;


    @Column(nullable = false, length = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(nullable = false, length = 10)
    public Date getBtime() {
        return btime;
    }

    public void setBtime(Date btime) {
        this.btime = btime;
    }

    @Column(nullable = true, length = 32)
    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    @Column(nullable = false, length = 10)
    public Integer getBtype() {
        return btype;
    }

    public void setBtype(Integer btype) {
        this.btype = btype;
    }
    @javax.persistence.Transient
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(nullable = true, length = 4000)
    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    @Column(nullable = true, length = 4000)
    public String getUserParams() {
        return userParams;
    }

    public void setUserParams(String userParams) {
        this.userParams = userParams;
    }
}
