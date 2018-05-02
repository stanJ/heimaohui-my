package com.xa3ti.blackcat.base.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户实体.
 */
@Entity
@Table(name = "tb_ms_cms_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class XaCmsUser implements java.io.Serializable {

    private static final long serialVersionUID = -2588141205901945887L;

    /**
     * 用户Id.
     */
    private String userId;

    /**
     * 用户名.
     */
    private String userName;

    /**
     * 角色id *
     */
    private Long roleId;

    /**
     * 角色对象 *
     */
    private XaCmsRole role;

    /**
     * 昵称 *
     */
    private String nickName;

    /**
     * 联系电话 *
     */
    private String mobile;

    /**
     * 邮箱 *
     */
    private String email;

    /**
     * 说明 *
     */
    private String remark;

    /**
     * 1、系统管理员;其他的自定义
     */
    private Integer userType;

    /**
     * 用户状态. 1.正常 0.锁定 9.删除
     */
    private int status;

    /**
     * 密码.
     */
    private String password;

    /**
     * 注册时间 *
     */
    private String registDate;

    /**
     * 最后登录时间 *
     */
    private String lastLoginDate;

    //	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    @Basic(optional = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "roleId", referencedColumnName = "roleId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public XaCmsRole getRole() {
        return role;
    }

    public void setRole(XaCmsRole role) {
        this.role = role;
    }

    @Column(length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(length = 50)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(length = 200)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(length = 50)
    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    @Column(length = 50)
    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Column(length = 50)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}