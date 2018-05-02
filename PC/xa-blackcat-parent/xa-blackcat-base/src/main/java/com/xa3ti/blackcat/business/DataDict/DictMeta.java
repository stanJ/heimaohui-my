/**
 * 
 */
package com.xa3ti.blackcat.business.DataDict;

/**
 * @author nijie
 *
 */
public class DictMeta {
	/**字典KEY */
	private String key;
	
	/**父字典KEY*/
	private String parentKey;
	
	/** 父字典元实体*/
	private DictMeta parent=null;
	
	/** 子字典元实体*/
	private DictMeta child=null;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public DictMeta getParent() {
		return parent;
	}

	public void setParent(DictMeta parent) {
		this.parent = parent;
	}

	public DictMeta getChild() {
		return child;
	}

	public void setChild(DictMeta child) {
		this.child = child;
	}
	
	

}
