package com.xa3ti.blackcat.business.model;

import com.xa3ti.blackcat.base.model.BaseModel;
import com.xa3ti.blackcat.base.util.AnnotationUtil;

public class Aboutus implements BaseModel {
    private Long id;

    private String createTime;

    private Long createUser;

    private String modifyTime;

    private Long modifyUser;

    private Integer status;

    private Integer version;

    private String tel;

    private String versionno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime == null ? null : modifyTime.trim();
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getVersionno() {
        return versionno;
    }

    public void setVersionno(String versionno) {
        this.versionno = versionno == null ? null : versionno.trim();
    }

    public Object convertToEntity(String fullyClassPathName) {
        Object destObj = null;
	try {
		destObj = Class.forName(fullyClassPathName).newInstance();
		org.springframework.beans.BeanUtils.copyProperties(this, destObj);
       AnnotationUtil.invokeMethodOnObject(destObj, "setTid", this.getId());
	} catch (InstantiationException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
   catch (Exception e) {
	    e.printStackTrace();
   }
	return destObj;

    }
}