/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.dao;

/**
 * @author nijie
 *
 */

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.springframework.beans.BeanUtils;

import com.xa3ti.blackcat.base.util.AnnotationUtil;
/**
* @author nijie
*
*/
public class PaginationPlugin extends PluginAdapter {  
  @Override  
  public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,  
          IntrospectedTable introspectedTable) {  
      // add field, getter, setter for limit clause  
	  topLevelClass.addImportedType("com.xa3ti.blackcat.base.model.BaseExampleModel");

	  topLevelClass.addSuperInterface(new FullyQualifiedJavaType("com.xa3ti.blackcat.base.model.BaseExampleModel"));
      //addLimit(topLevelClass, introspectedTable, "limitStart");  
      //addLimit(topLevelClass, introspectedTable, "limitEnd");  
	  topLevelClass.addImportedType("com.xa3ti.blackcat.base.util.Pageable");
      addLimit(topLevelClass, introspectedTable, "pageable");  
      
      return super.modelExampleClassGenerated(topLevelClass,  
              introspectedTable);  
  }  
  
  
  @Override  
  public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {  
	  
	  topLevelClass.addImportedType("com.xa3ti.blackcat.base.model.BaseModel");
	  topLevelClass.addImportedType("com.xa3ti.blackcat.base.util.AnnotationUtil");
	  topLevelClass.addSuperInterface(new FullyQualifiedJavaType("com.xa3ti.blackcat.base.model.BaseModel"));
	  
	  
	  Method method = new Method();  
	  method.setVisibility(JavaVisibility.PUBLIC);
	  method.setReturnType(PrimitiveTypeWrapper.getObjectInstance());
	  method.addParameter(new Parameter(PrimitiveTypeWrapper.getStringInstance(),"fullyClassPathName"));
	  method.setName("convertToEntity");  
	   
	  method.addBodyLine("Object destObj = null;\n"+
		"	try {\n"+
		"		destObj = Class.forName(fullyClassPathName).newInstance();\n"+
		"		org.springframework.beans.BeanUtils.copyProperties(this, destObj);\n"+
		"       AnnotationUtil.invokeMethodOnObject(destObj, \"setTid\", this.getId());\n"+
		"	} catch (InstantiationException e) {\n"+
		"		e.printStackTrace();\n"+
		"	} catch (IllegalAccessException e) {\n"+
		"		e.printStackTrace();\n"+
		"	} catch (ClassNotFoundException e) {\n"+
		"		e.printStackTrace();\n"+
		"	}\n"+
		"   catch (Exception e) {\n"+
		"	    e.printStackTrace();\n"+
		"   }\n"+
		"	return destObj;\n"+
		"");
	  
	  topLevelClass.addMethod(method);  
	  
	  return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
  }
  
  
  
  
//  @Override  
//  public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(  
//          XmlElement element, IntrospectedTable introspectedTable) {  
////    XmlElement isParameterPresenteElemen = (XmlElement) element  
////            .getElements().get(element.getElements().size() - 1);  
//      XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$  
//      isNotNullElement.addAttribute(new Attribute("test", "limitStart != null and limitStart>=0")); //$NON-NLS-1$ //$NON-NLS-2$  
////    isNotNullElement.addAttribute(new Attribute("compareValue", "0")); //$NON-NLS-1$ //$NON-NLS-2$  
//      isNotNullElement.addElement(new TextElement(  
//              "limit #{limitStart} , #{limitEnd}"));  
////    isParameterPresenteElemen.addElement(isNotNullElement);  
//      element.addElement(isNotNullElement);  
//      return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,  
//              introspectedTable);  
//  }  
  private void addLimit(TopLevelClass topLevelClass,  
          IntrospectedTable introspectedTable, String name) {  
      CommentGenerator commentGenerator = context.getCommentGenerator();  
      Field field = new Field();  
      field.setVisibility(JavaVisibility.PROTECTED);  
    field.setType(new FullyQualifiedJavaType("com.xa3ti.blackcat.base.util.Pageable"));  
//    field.setType(PrimitiveTypeWrapper.getIntegerInstance());  
      field.setName(name);  
//    field.setInitializationString("-1");  
      commentGenerator.addFieldComment(field, introspectedTable);  
      topLevelClass.addField(field);  
      char c = name.charAt(0);  
      String camel = Character.toUpperCase(c) + name.substring(1);  
      Method method = new Method();  
      method.setVisibility(JavaVisibility.PUBLIC);  
      method.setName("set" + camel);  
      //method.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(), name));  
      
      method.addParameter(new Parameter(new FullyQualifiedJavaType("com.xa3ti.blackcat.base.util.Pageable"), name));  
      
      method.addBodyLine("this." + name + "=" + name + ";");  
      commentGenerator.addGeneralMethodComment(method, introspectedTable);  
      topLevelClass.addMethod(method);  
      method = new Method();  
      method.setVisibility(JavaVisibility.PUBLIC);  
      //method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());  
      
      method.setReturnType(new FullyQualifiedJavaType("com.xa3ti.blackcat.base.util.Pageable"));  
      
      method.setName("get" + camel);  
      method.addBodyLine("return " + name + ";");  
      commentGenerator.addGeneralMethodComment(method, introspectedTable);  
      topLevelClass.addMethod(method);  
  }  
  /** 
   * This plugin is always valid - no properties are required 
   */  
  public boolean validate(List<String> warnings) {  
      return true;  
  }  
  
  
  
  

}
