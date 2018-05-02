/**
 * 
 */
package com.xa3ti.blackcat.business.DataDict;

import java.io.Serializable;
import java.util.List;

/**
 * @author nijie
 *
 */
public class Dict implements Serializable{
	
	private Long id;
	/** 字典KEY. */
	private String key;

	/** 字典码. */
	private String code;
	
	/** 字典值 **/
	private String value;
	
	/**子字典*/
	private List<Dict> children;
	
	/**父字典*/
	private String parentCode;
	
	/** 是否叶子节点*/
	private int isLeaf;
	
	

	public int getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public List<Dict> getChildren() {
		return children;
	}

	public void setChildren(List<Dict> children) {
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
