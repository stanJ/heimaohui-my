package com.xa3ti.blackcat.base.entity;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 用户实体.
 */
@Entity
@Table(name = "tb_ms_cms_dict")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class XaCmsDict implements java.io.Serializable {

    private static final long serialVersionUID = -2588141205901945887L;

    private Long id;
    /**
     * 字典KEY.
     */
    private String key;

    /**
     * 字典码.
     */
    private String code;

    /**
     * 字典值 *
     */
    private String value;

    /**
     * 父字典
     */
    private String parentCode;

    /**
     * 是否叶子节点
     */
    private int isLeaf;


    @Column(name = "is_leaf")
    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
    }

    @Column(name = "parent_code", nullable = true, length = 50)
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentId) {
        this.parentCode = parentCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户状态. 1.正常 0.锁定 9.删除
     */
    private int status;

    @Column(name = "key", nullable = false, length = 20)
    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "value", nullable = false, length = 100)
    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


}