/**
 * 
 */
package com.xa3ti.blackcat.base.tag;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.service.impl.QueryBaseService;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.util.ContextUtil;
import com.xa3ti.blackcat.business.DataDict.CacheDepend;
import com.xa3ti.blackcat.business.DataDict.CacheDict;
import com.xa3ti.blackcat.business.DataDict.Dict;
import com.xa3ti.blackcat.business.DataDict.DictMeta;

/**
 * @author nijie
 *
 */
public class DependListTag extends BaseTag implements Tag {
	
	private String show;

	

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
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
			String codePropertyName =AnnotationUtil.convertToPropName(str[1]);

			showTableFiledName = showTableFiledName.replace('.', '/');
			String[] str2 = showTableFiledName.split("/");
			String showPropertyName = AnnotationUtil.convertToPropName(str2[1]);

		
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

			strBuffer.append("<select name='"
					+ super.getName()
					+ "' id='"
					+ id
					+ "' "
					+ dataRule
					+ (super.getClazz() != null ? " class='" + super.getClazz()
							+ "'" : "") + "/>");
			strBuffer.append("\n<option value=''>请选择</option>");
			///////////////////////////////////////////
			String key=entityName;
			HashMap<String,Object> map=(HashMap<String,Object>) CacheDepend.getByKey(key,false);
			if (map != null) {
				Iterator it=map.keySet().iterator();
				while (it.hasNext()) {
					
					String code =(String)it.next();
					Object entity=map.get(code);
					String value =(String)AnnotationUtil.invokeMethodOnObject(entity,
							"get"+showPropertyName.substring(0, 1).toUpperCase()+showPropertyName.substring(1), null);
					
					//Integer status =(Integer)AnnotationUtil.invokeMethodOnObject(entity,
					//		"getStatus", null);
					
					//if(!status.equals(XaConstant.Status.binded)){
					   strBuffer.append("\n<option value='" + code + "'>" + value
							+ "</option>");
					//}
					
				}
			}
			strBuffer.append("</select>");
			/////////////////////////////////////////
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
