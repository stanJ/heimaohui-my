/**
 * 
 */
package com.xa3ti.blackcat.base.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import com.xa3ti.blackcat.business.DataDict.CacheDict;
import com.xa3ti.blackcat.business.DataDict.Dict;
import com.xa3ti.blackcat.business.DataDict.DictMeta;

/**
 * @author nijie
 *
 */
public class DictListTag extends BaseTag implements Tag {

	
	private String onchange;
	
	
	public String getOnchange() {
		return onchange;
	}


	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
		    HttpServletRequest request = (HttpServletRequest)super.getPageContext().getRequest();  
            //输出contextPath  
		    String urlWithContextPath=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(); 
		    
		    String idPrefix="";
		    if(!StringUtils.isBlank(super.getIdprefix())){
		    	idPrefix=super.getIdprefix();
		    }
		    
		    String dataRule="";
		    if(!StringUtils.isBlank(super.getDatarule())){
		    	dataRule=" data-rule=\""+super.getDatarule()+"\" ";
		    }
		    
		    String[] ids=null;
		    String[] names=null;
		    String[] titles=null;
		    int idLen=0;
		    int nameLen=0;
		    int titleLen=0;
		    if(!StringUtils.isBlank(super.getId())){
		    	try{
		    		ids=super.getId().split(",");
		    		idLen=ids.length;
		    	}catch(Exception e){
		    		
		    	}
		    }
		    
		    if(!StringUtils.isBlank(super.getName())){
		    	try{
		    		names=super.getName().split(",");
		    		nameLen=names.length;
		    	}catch(Exception e){
		    		
		    	}
		    }
		    
		    if(!StringUtils.isBlank(super.getTitle())){
		    	try{
		    		titles=super.getTitle().split(",");
		    		titleLen=titles.length;
		    	}catch(Exception e){
		    		
		    	}
		    }
		    String[] titleTags=null;
            if(!StringUtils.isBlank(super.getTitletag())){
            	titleTags=super.getTitletag().split("(\\s)+");
            }	
		    
		    
			StringBuffer strBuffer = new StringBuffer();
			List<Dict> dictList=CacheDict.getDictByKey(super.getKey());
			DictMeta dictMeta=CacheDict.getDictMetaByKey(super.getKey());
			DictMeta pDictMeta=dictMeta.getParent();
			String pKey=null;
			DictMeta theFinalParent=null;
			/////////////
			if(pDictMeta==null){
				theFinalParent=dictMeta;
			    //pKey=dictMeta.getKey();
				//pDictMeta=dictMeta.getParent();
			}else{
				///////
				while(pDictMeta!=null){
					theFinalParent=pDictMeta;
				    pKey=pDictMeta.getKey();
					pDictMeta=pDictMeta.getParent();
				}
				///////////////
			}
			////////预算所有SELECT 控件的ID//////////////////////////////
			Queue<String> selControllerIds=new LinkedList<String>();
			Queue<String> selControllerKeys=new LinkedList<String>();
			DictMeta curDict=theFinalParent;
			boolean needBreak=false;
			int _i=0;
			while(curDict!=null){
				selControllerKeys.add(curDict.getKey());
				if(curDict.getKey().equals(super.getKey()))
					needBreak=true;
				
				if(!StringUtils.isBlank(idPrefix+ids[_i]))
					selControllerIds.add(idPrefix+ids[_i]);
				else
					selControllerIds.add("_select_"+curDict.getKey());
				curDict=curDict.getChild();
				_i++;
				if(needBreak)
					break;
			}
			
			String[] clearFunctioNames=new String[selControllerIds.size()];
			String[] selIds = new String[selControllerIds.size()];
			String[] selKeys = new String[selControllerKeys.size()];
			selControllerIds.toArray(selIds);
			selControllerKeys.toArray(selKeys);
			strBuffer.append("<script type='text/javascript'>\n");
			for(int ii=0;ii<selControllerIds.size();ii++){
				clearFunctioNames[ii]="clearSelfAndChilds_"+selIds[ii];
			    strBuffer.append(" function "+clearFunctioNames[ii]+"(){\n");
				for(int jj=ii;jj<selControllerIds.size();jj++){
				  strBuffer.append("   $(\"#"+selIds[jj]+"\").val(\"\");\n");
				}
			    strBuffer.append("}\n");
			}
			
			//strBuffer.append(" function setDict(v){\n");
			//strBuffer.append("	setDict_"+selKeys[selKeys.length-1]+"(v);\n");
			//strBuffer.append(" }\n");

			strBuffer.append("function setDict_"+selKeys[selKeys.length-1]+"(v){\n");
			strBuffer.append("var val=v.split(\",\");\n");
			for(int pp=0;pp<selKeys.length-1;pp++){
				strBuffer.append("sel"+selKeys[pp]+"();\n");
				strBuffer.append("$(\"#"+selIds[pp+1]+"\").val(val["+(pp+1)+"]);\n");
			}
			strBuffer.append("}\n");
			
			strBuffer.append("</script>\n");
            /////////////////////////////////////////////////
			
			int index=0;
			if(pKey!=null){//存在父字典
                List<Dict> pDictList=CacheDict.getDictByKey(pKey);
        		
                String name=names!=null&&index<nameLen&&!StringUtils.isBlank(names[index])?names[index]:"_select_"+pKey;
                String id=ids!=null&&index<idLen&&!StringUtils.isBlank(ids[index])?idPrefix+ids[index]:"_select_"+pKey;
                String title=titles!=null&&index<titleLen&&!StringUtils.isBlank(titles[index])?titles[index]:"";
                
                String childId=ids!=null&&index+1<idLen&&!StringUtils.isBlank(ids[index+1])?idPrefix+ids[index+1]:"_select_"+theFinalParent.getChild().getKey();
                
                strBuffer.append("<script type='text/javascript'>\n");
                strBuffer.append("!window.jQuery && document.write('<script type=\"text/javascript\" src=\"../js/jquery-1.11.0.min.js\"><\\/script>');\n");
                strBuffer.append("</script>\n");
                
               
                
                strBuffer.append("\n<script>\n");
                strBuffer.append("function sel"+pKey+"(){\n");
                strBuffer.append("var code=$(\"#"+id+"\").val();\n");
                strBuffer.append("var url=\""+urlWithContextPath+"/dict/code/\"+code+\"?key="+theFinalParent.getKey()+"\";\n");
                //strBuffer.append("alert(url);        \n");  
                strBuffer.append("$.ajax({\n");
                strBuffer.append("   url:url,\n");
                strBuffer.append("   dataType:'json',\n");
                strBuffer.append("   type:'GET',\n");
                strBuffer.append("   async:false,\n");
                strBuffer.append("   data:{\n");
                strBuffer.append("        },\n");
                strBuffer.append("   success:function(data){\n");
                strBuffer.append("         var html=\"<option value=''>请选择</option>\";\n");
                strBuffer.append("         for(var i=0;i<data.length;i++){\n");
                strBuffer.append("           var code=data[i].code;\n");
                strBuffer.append("           var value=data[i].value;\n");
                strBuffer.append("           html+=\"<option value='\"+code+\"'>\"+value+\"</option>\";\n");
                strBuffer.append("         }\n");
                strBuffer.append("         $(\"#"+childId+"\").html(html);\n");
                //strBuffer.append("         sel"+theFinalParent.getChild().getKey()+"();\n");
                strBuffer.append("       },\n");
                strBuffer.append("       fail:function(jqxhr, textStatus, error){\n");
                strBuffer.append("         alert(error);\n");
                strBuffer.append("       },\n");
                strBuffer.append("       error: function(XMLHttpRequest, textStatus, errorThrown) { \n");
                strBuffer.append("         //alert(XMLHttpRequest.status);    //http响应状态  \n");
                strBuffer.append("         //alert(XMLHttpRequest.readyState);//5个状态 \n");
                strBuffer.append("         //alert(textStatus);               //六个值  \n");
                strBuffer.append("        "+clearFunctioNames[index]+"();\n" );
                strBuffer.append("       }\n");
                
                 strBuffer.append(" });\n");
                strBuffer.append("}\n");
                //strBuffer.append("sel"+pKey+"();\n");
                strBuffer.append("</script>\n\n");
                
               
                
                if(titleTags!=null&&titleTags.length>0){
                	for(int i=0;i<titleTags.length;i++){
	                	strBuffer.append("<"+titleTags[i]);
	                	if((i==titleTags.length-1)&&!StringUtils.isBlank(super.getTitletagclass())){
	                		strBuffer.append(" class=\""+super.getTitletagclass()+"\"");
	                	}
	                	strBuffer.append(">");
                	}
                	strBuffer.append(title+":");
                	for(int i=titleTags.length-1;i>=0;i--){
                	  strBuffer.append("</"+titleTags[i]+">");
                	}
                }else{
                	strBuffer.append(title+":");
                }
                
                
                if(!StringUtils.isBlank(super.getOuttag())){
                	strBuffer.append("<"+super.getOuttag());
                	if(!StringUtils.isBlank(super.getOuttagstyle())){
                		strBuffer.append(" style=\""+super.getOuttagstyle()+"\"");
                	}
                	strBuffer.append(">");
                 }
                
                strBuffer.append("<select name='"+name+"' id='"+id+"' onchange='sel"+pKey+"()' "+dataRule+(super.getClazz()!=null?" class='"+super.getClazz()+"'":"")+">");
                strBuffer.append("\n<option value=''>请选择</option>");
                for(Dict dict:pDictList){
					strBuffer.append("\n<option value='"+dict.getCode()+"'>"+dict.getValue()+"</option>");
				}
				strBuffer.append("</select>");
				if(!StringUtils.isBlank(super.getOuttag())){
					strBuffer.append("</"+super.getOuttag()+">");
				}
				index++;
				if(theFinalParent!=null){
					DictMeta child=theFinalParent.getChild();
					while(child!=null){
						
						String _name=names!=null&&index<nameLen&&!StringUtils.isBlank(names[index])?names[index]:"_select_"+child.getKey();
			            String _id=ids!=null&&index<idLen&&!StringUtils.isBlank(ids[index])?idPrefix+ids[index]:"_select_"+child.getKey();
			            String _title=titles!=null&&index<titleLen&&!StringUtils.isBlank(titles[index])?titles[index]:"";
			            
			            if(titleTags!=null&&titleTags.length>0){
		                	for(int i=0;i<titleTags.length;i++){
			                	strBuffer.append("<"+titleTags[i]);
			                	if((i==titleTags.length-1)&&!StringUtils.isBlank(super.getTitletagclass())){
			                		strBuffer.append(" class=\""+super.getTitletagclass()+"\"");
			                	}
			                	strBuffer.append(">");
		                	}
		                	strBuffer.append(_title+":");
		                	for(int i=titleTags.length-1;i>=0;i--){
		                	  strBuffer.append("</"+titleTags[i]+">");
		                	}
			            }else{
		                	strBuffer.append(_title+":");
		                }
			            
			            
			            if(!StringUtils.isBlank(super.getOuttag())){
		                	strBuffer.append("<"+super.getOuttag());
		                	if(!StringUtils.isBlank(super.getOuttagstyle())){
		                		strBuffer.append(" style=\""+super.getOuttagstyle()+"\"");
		                	}	
		                	strBuffer.append(">");
		                }
			            
						
		                strBuffer.append("\n<select name='"+_name+"' id='"+_id+"' onchange='sel"+child.getKey()+"()' "+dataRule+(super.getClazz()!=null?" class='"+super.getClazz()+"'":"")+">");
		                strBuffer.append("\n<option value=''>请选择</option>");
		                strBuffer.append("</select>");
						if(!StringUtils.isBlank(super.getOuttag())){
							strBuffer.append("</"+super.getOuttag()+">");
						}
						
						if(super.getKey().equals(child.getKey())){
							    strBuffer.append("\n<script>\n");
				                strBuffer.append("function sel"+child.getKey()+"(){\n");
				                strBuffer.append(onchange+";");
				                strBuffer.append("}\n");
				                strBuffer.append("</script>\n\n");
								break;
								
								
						}
						
						if(child.getChild()!=null){
							String _childId=ids!=null&&index+1<idLen&&!StringUtils.isBlank(ids[index+1])?idPrefix+ids[index+1]:"_select_"+child.getChild().getKey();
				               
							 strBuffer.append("\n<script>\n");
				                strBuffer.append("function sel"+child.getKey()+"(){\n");
				                strBuffer.append("var code=$(\"#"+_id+"\").val();\n");
				                strBuffer.append("var url=\""+urlWithContextPath+"/dict/code/\"+code+\"?key="+child.getKey()+"\";\n");
				                //strBuffer.append("alert(url);        \n"); 
				                strBuffer.append("$.ajax({\n");
				                strBuffer.append("   url:url,\n");
				                strBuffer.append("   dataType:'json',\n");
				                strBuffer.append("   type:'GET',\n");
				                strBuffer.append("   async:false,\n");
				                strBuffer.append("   data:{\n");
				                strBuffer.append("        },\n");
				                strBuffer.append("   success:function(data){\n");
				                strBuffer.append("         var html=\"<option value=''>请选择</option>\";\n");
				                strBuffer.append("         for(var i=0;i<data.length;i++){\n");
				                strBuffer.append("           var code=data[i].code;\n");
				                strBuffer.append("           var value=data[i].value;\n");
				                strBuffer.append("           html+=\"<option value='\"+code+\"'>\"+value+\"</option>\";\n");
				                strBuffer.append("         }\n");
				                strBuffer.append("         $(\"#"+_childId+"\").html(html);\n");
				                strBuffer.append("       },\n");
				                strBuffer.append("       fail:function(jqxhr, textStatus, error){\n");
				                strBuffer.append("         alert(error);\n");
				                strBuffer.append("       },\n");
				                strBuffer.append("       error: function(XMLHttpRequest, textStatus, errorThrown) { \n");
				                strBuffer.append("         //alert(XMLHttpRequest.status);    //http响应状态  \n");
				                strBuffer.append("         //alert(XMLHttpRequest.readyState);//5个状态 \n");
				                strBuffer.append("         //alert(textStatus);               //六个值  \n");
				                strBuffer.append("        "+clearFunctioNames[index]+"();\n" );
				                strBuffer.append("       }\n");
				                strBuffer.append(" });\n");
				                strBuffer.append("}\n");
				                strBuffer.append("</script>\n\n");
						}
				         
						child=child.getChild();
						index++;
					}
				}
				
			}else{//不存在父字典
				String name=super.getName()!=null&&!StringUtils.isBlank(super.getName())?super.getName():"_select_"+super.getKey();
	            String id=super.getId()!=null&&!StringUtils.isBlank(super.getId())?idPrefix+super.getId():"_select_"+super.getKey();
	            String title=super.getTitle()!=null&&!StringUtils.isBlank(super.getTitle())?super.getTitle():"";
	            
	         
	            if(titleTags!=null&&titleTags.length>0){
                	for(int i=0;i<titleTags.length;i++){
	                	strBuffer.append("<"+titleTags[i]);
	                	if((i==titleTags.length-1)&&!StringUtils.isBlank(super.getTitletagclass())){
	                		strBuffer.append(" class=\""+super.getTitletagclass()+"\"");
	                	}
	                	strBuffer.append(">");
                	}
                	strBuffer.append(title+":");
                	for(int i=titleTags.length-1;i>=0;i--){
                	  strBuffer.append("</"+titleTags[i]+">");
                	}
                }else{
                	strBuffer.append(title+":");
                }
	            
	            if(!StringUtils.isBlank(super.getOuttag())){
                	strBuffer.append("<"+super.getOuttag());
                	if(!StringUtils.isBlank(super.getOuttagstyle())){
                		strBuffer.append(" style=\""+super.getOuttagstyle()+"\"");
                	}
                	strBuffer.append(">");
                 }
                
                strBuffer.append("<select name='"+name+"' id='"+id+"' onchange='"+onchange+"' >");
                strBuffer.append("\n<option value=''>请选择</option>");
				for(Dict dict:dictList){
					strBuffer.append("\n<option value='"+dict.getCode()+"'>"+dict.getValue()+"</option>");
				}
				strBuffer.append("</select>");
				
				if(!StringUtils.isBlank(super.getOuttag())){
					strBuffer.append("</"+super.getOuttag()+">");
				}
			}
			
			
			
			super.getPageContext().getOut().write(strBuffer.toString());
		} catch (IOException ex) {
			throw new JspTagException("IO Error:" + ex.getMessage());
		}

		return this.EVAL_PAGE;
	}


}
