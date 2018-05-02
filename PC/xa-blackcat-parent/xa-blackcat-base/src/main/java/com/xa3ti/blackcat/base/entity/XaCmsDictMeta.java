/**
 *
 */
package com.xa3ti.blackcat.base.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @author nijie
 */
@Entity
@Table(name = "tb_ms_cms_dict_meta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class XaCmsDictMeta {

    private static final long serialVersionUID = -2588141205901945887L;

    private Long id;
    /**
     * 字典KEY.
     */
    private String key;

    /**
     * 父字典KEY.
     */
    private String parentKey;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "key", nullable = false, length = 20)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "parent_key", length = 20)
    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }


}
