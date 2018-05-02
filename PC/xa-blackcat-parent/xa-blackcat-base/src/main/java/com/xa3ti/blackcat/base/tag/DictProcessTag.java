/**
 * 
 */
package com.xa3ti.blackcat.base.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang3.StringUtils;

import com.xa3ti.blackcat.business.DataDict.CacheDict;
import com.xa3ti.blackcat.business.DataDict.Dict;
import com.xa3ti.blackcat.business.DataDict.DictMeta;

/**
 * @author nijie
 *
 */
public class DictProcessTag extends BaseTag implements Tag {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			String[] ids = null;
			
			if (!StringUtils.isBlank(super.getId())) {
				try {
					ids = super.getId().split(",");
				} catch (Exception e) {

				}
			}
			
			
			DictMeta dictMeta = CacheDict.getDictMetaByKey(super.getKey());
			List<DictMeta> dictMetaList=new ArrayList<DictMeta>();
			while(dictMeta!=null){
				dictMetaList.add(dictMeta);
				dictMeta=dictMeta.getParent();
			}
			Collections.reverse(dictMetaList);
			DictMeta[] dictMetaArray=new DictMeta[dictMetaList.size()];
			dictMetaList.toArray(dictMetaArray);
			
			
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append("	function processDict_" + super.getKey()
					+ "(data) {\n");
			strBuffer.append("if (data != undefined) {\n");
			strBuffer.append("for (var i = 0; i < data.length; i++) {\n");
			strBuffer.append("var oneRow = data[i];\n");

			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					String key=((DictMeta)(dictMetaArray[i])).getKey();
					//String value=CacheDict.getDictValueByKeyCode(key,values[i]);
					strBuffer.append("if (oneRow." + ids[i] + ") {\n");
					//strBuffer.append("	oneRow." + ids[i]+"=\""++"\";\n");
					strBuffer.append("}\n");
				}
			}

			strBuffer.append("}\n");
			strBuffer.append("}\n");
			strBuffer.append("return data;\n");
			strBuffer.append("}");

			super.getPageContext().getOut().write(strBuffer.toString());
		} catch (IOException ex) {
			throw new JspTagException("IO Error:" + ex.getMessage());
		}
		return 0;
	}

}
