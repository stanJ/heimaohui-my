/**
 * 
 */
package com.xa3ti.blackcat.base.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang.StringUtils;

import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.business.DataDict.CacheDepend;
/**
 * @author nijie
 *
 */
public class DependTextTag extends BaseTag implements Tag {
	private String readonly;

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	
	
    private String show;
	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}
	
	
	private String joinvalue;
	
	
	
	

	public String getJoinvalue() {
		return joinvalue;
	}

	public void setJoinvalue(String joinvalue) {
		this.joinvalue = joinvalue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) super
					.getPageContext().getRequest();
			// 输出contextPath
			String urlWithContextPath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			String idPrefix = "";
			if (!StringUtils.isBlank(super.getIdprefix())) {
				idPrefix = super.getIdprefix();
			}
            String id=idPrefix+super.getId();
            
			String dataRule = "";
			if (!StringUtils.isBlank(super.getDatarule())) {
				dataRule = " data-rule=\"" + super.getDatarule() + "\" ";
			}
			
			StringBuffer strBuffer = new StringBuffer();
			

			String tableFiledName = super.getKey();
			String showTableFiledName = this.getShow();

			tableFiledName = tableFiledName.replace('.', '/');
			String[] str = tableFiledName.split("/");
			String entityName = str[0];
			String codePropertyName = AnnotationUtil.convertToPropName(str[1]);

			
			String[] showPropTableNames=showTableFiledName.split(",");
			String[] showPropNames=new String[showPropTableNames.length];
			for(int i=0;i<showPropTableNames.length;i++){
				showPropTableNames[i] = showPropTableNames[i].replace('.', '/');
				String[] str2 = showPropTableNames[i].split("/");
				String showPropertyName = AnnotationUtil.convertToPropName(str2[1]);
				showPropNames[i]=showPropertyName;
			}

				strBuffer.append("<script type='text/javascript'>\n");
				strBuffer
						.append("!window.jQuery && document.write('<script type=\"text/javascript\" src=\"../js/jquery-1.11.0.min.js\"><\\/script>');\n");
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
			int index=0;
			String key=entityName;
			if(StringUtils.isBlank(readonly)){
				readonly="false";
			}
			for(int j=0;j<showPropNames.length;j++){
			        String mappedValue=CacheDepend.getByKeyKey(key, joinvalue, showPropNames[j],false);
					strBuffer.append("<input type='text' id='"+id+"_"+index+"' name='"+super.getName()+"' value='"+mappedValue+"' readonly="+readonly+" />\n");
					index++;
				
			}
			
            ///////////////////////////////////////////////
			if (!StringUtils.isBlank(super.getOuttag())) {
				strBuffer.append("</" + super.getOuttag() + ">");
			}
			super.getPageContext().getOut().write(strBuffer.toString());
			
		} catch (IOException ex) {
			throw new JspTagException("IO Error:" + ex.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				super.getPageContext().getOut().write(e.getMessage());
			} catch (IOException e1) {
				throw new JspTagException("IO Error:" + e1.getMessage());
			}
		} 

		return this.EVAL_PAGE;
	}
	

}
