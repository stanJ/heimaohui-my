package com.xa3ti.blackcat.base.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 系统资源角色对应实体.
 */
@Entity
@Table(name = "tb_ms_cms_role_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class XaCmsRoleResource {

    /**
     * The version.
     */
    private Long version;

    /**
     * 资源角色Id.
     */
    private Long roleResourceId;

    /**
     * 对应角色.
     */
    private Long roleId;

    /**
     * 对应资源.
     */
    private Long resourceId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getRoleResourceId() {
        return roleResourceId;
    }

    public void setRoleResourceId(Long roleResourceId) {
        this.roleResourceId = roleResourceId;
    }

}
