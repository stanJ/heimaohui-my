package com.xa3ti.blackcat.business.entity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.xa3ti.blackcat.base.annotation.Dict;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 曹文波
 * @ClassName: Customer
 * @Description: 客户定义表
 * @date 2014年10月11日 上午10:47:49
 */
@Entity
@Table(name = "tb_xa_customer")
@ApiModel(value = "客户定义表")
@QueryDao(method = BaseConstant.DAO.DYNASQL)
public class Customer extends BaseEntity {

    @ApiModelProperty(value = "名称,名称")
    private String name;
    @ApiModelProperty(value = "公司负责人,公司负责人")
    private String manager;
    @ApiModelProperty(value = "联系方式,联系方式")
    private String contact;
    @ApiModelProperty(value = "邮箱,邮箱")
    private String email;
    @ApiModelProperty(value = "微信,微信")
    private String wechat;
    @Dict(name = "PROVINCE")
    @ApiModelProperty(value = "所在省市,所在省市")
    private String province;
    @Dict(name = "CITY")
    @ApiModelProperty(value = "所在省市,所在省市")
    private String city;
    @Dict(name = "DISTRICT")
    @ApiModelProperty(value = "所在省市,所在省市")
    private String district;
    @ApiModelProperty(value = "客户评价,客户评价")
    private String comment;
    @ApiModelProperty(value = "服务地区,服务地区")
    private String serviceArea;
    @ApiModelProperty(value = "服务对象,服务对象")
    private String serviceObject;
    @ApiModelProperty(value = "公司简介,公司简介")
    private String intro;
    @ApiModelProperty(value = "附件,附件")
    private String file;


    @Column(nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = true, length = 50)
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Column(nullable = true, length = 2000)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(nullable = true, length = 2000)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = true, length = 2000)
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Column(nullable = true, length = 10)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(nullable = true, length = 10)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(nullable = true, length = 10)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(nullable = true, length = 20)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(nullable = true, length = 20)
    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    @Column(nullable = true, length = 20)
    public String getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(String serviceObject) {
        this.serviceObject = serviceObject;
    }

    @Column(nullable = true, length = 100)
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Column(nullable = true, length = 200)
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


}
