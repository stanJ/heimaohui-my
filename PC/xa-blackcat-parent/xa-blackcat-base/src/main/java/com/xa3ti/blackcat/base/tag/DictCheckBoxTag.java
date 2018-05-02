/**
 * 
 */
package com.xa3ti.blackcat.base.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang3.StringUtils;

import com.xa3ti.blackcat.business.DataDict.CacheDict;
import com.xa3ti.blackcat.business.DataDict.Dict;
import com.xa3ti.blackcat.business.DataDict.DictMeta;

/**
 * @author nijie
 *
 */
public class DictCheckBoxTag extends BaseTag implements Tag {

	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try{
			List<Dict> dictList=CacheDict.getDictByKey(super.getKey());
			StringBuffer strBuffer=new StringBuffer();
			String idPrefix=!StringUtils.isBlank(super.getId())?super.getId():"_radio_"+super.getKey();
			String name=!StringUtils.isBlank(super.getName())?super.getName():"_radio_"+super.getKey();
			int index=0;
			for(Dict dict:dictList){
				//strBuffer.append("&nbsp;&nbsp;");
				strBuffer.append(dict.getValue());
				strBuffer.append("<input type='checkbox' id='"+idPrefix+"_"+index+"' name='"+name+"' value='"+dict.getCode()+"'/>\n");
				index++;
			}
			super.getPageContext().getOut().write(strBuffer.toString());
		} catch (IOException ex) {
			throw new JspTagException("IO Error:" + ex.getMessage());
		}
		return 0;
	}

	
}
