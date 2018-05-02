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
public class DictRadioTag extends BaseTag implements Tag {

	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try{
			StringBuffer strBuffer=new StringBuffer();
			
			
			strBuffer.append("<script type='text/javascript'>\n");
            strBuffer.append("function setDict_"+super.getKey()+"(v){\n");
			strBuffer.append("var val=v.split(\",\");\n");
			strBuffer.append("$(\"input[name='"+super.getId()+"']\").each(function (i, n) {\n");
					strBuffer.append("   if($(n).val()==val){\n");
					strBuffer.append("         $(n).attr(\"checked\",\"checked\"); \n");
					strBuffer.append("          }\n");
							strBuffer.append("});\n");
			strBuffer.append("}\n");
			
			strBuffer.append("</script>\n");
			
			String[] titleTags = null;
			if (!StringUtils.isBlank(super.getTitletag())) {
				titleTags = super.getTitletag().split("(\\s)+");
			}

			if (titleTags != null && titleTags.length > 0) {
				for (int i = 0; i < titleTags.length; i++) {
					strBuffer.append("<" + titleTags[i]);
					if ((i == titleTags.length - 1)
							&& !StringUtils.isBlank(super.getTitletagclass())) {
						strBuffer.append(" class=\"" + super.getTitletagclass() + "\"");
					}
					strBuffer.append(">");
				}
				strBuffer.append(super.getTitle() + ":");
				for (int i = titleTags.length - 1; i >= 0; i--) {
					strBuffer.append("</" + titleTags[i] + ">");
				}
			} else {
				strBuffer.append(super.getTitle() + ":");
			}

			if (!StringUtils.isBlank(super.getOuttag())) {
				strBuffer.append("<" + super.getOuttag());
				if (!StringUtils.isBlank(super.getOuttagstyle())) {
					strBuffer.append(" style=\"" + super.getOuttagstyle()
							+ "\"");
				}
				strBuffer.append(">");
			}
			///////////////////////////////////////////////////////////////
			List<Dict> dictList=CacheDict.getDictByKey(super.getKey());
			
			String idPrefix=!StringUtils.isBlank(super.getId())?super.getId():"_radio_"+super.getKey();
			String name=!StringUtils.isBlank(super.getName())?super.getName():"_radio_"+super.getKey();
			int index=0;
			for(Dict dict:dictList){
				//strBuffer.append("&nbsp;&nbsp;");
				strBuffer.append(dict.getValue()+"");
				strBuffer.append("<input type='radio' id='"+idPrefix+"_"+index+"' name='"+name+"' value='"+dict.getCode()+"'/>\n");
				index++;
			}
			
			 ////////////////////////////////////////////////////////////
			if (!StringUtils.isBlank(super.getOuttag())) {
				strBuffer.append("</" + super.getOuttag() + ">");
			}
			super.getPageContext().getOut().write(strBuffer.toString());
		} catch (IOException ex) {
			throw new JspTagException("IO Error:" + ex.getMessage());
		}
		return 0;
	}

	
}
