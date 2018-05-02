/**
 * 
 */
package com.xa3ti.blackcat.base.converter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.Dict;
import com.xa3ti.blackcat.base.annotation.TableJoin;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.service.impl.QueryBaseService;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.util.ContextUtil;
import com.xa3ti.blackcat.base.util.JsonUtil;
import com.xa3ti.blackcat.business.DataDict.CacheDepend;
import com.xa3ti.blackcat.business.DataDict.CacheDict;

/**
 * @author nijie
 * @param <T>
 *
 */
public class MyPageObjectConverter<T>
		extends
		org.springframework.http.converter.AbstractHttpMessageConverter<XaResult> {
	public static Logger  log = Logger.getLogger(MyPageObjectConverter.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.http.converter.AbstractHttpMessageConverter#readInternal
	 * (java.lang.Class, org.springframework.http.HttpInputMessage)
	 */
	@Override
	protected XaResult readInternal(Class<? extends XaResult> arg0,
			HttpInputMessage arg1) throws IOException,
			HttpMessageNotReadableException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.http.converter.AbstractHttpMessageConverter#supports
	 * (java.lang.Class)
	 */
	@Override
	protected boolean supports(Class<?> arg0) {
		return XaResult.class.isAssignableFrom(arg0);
		/*
		if (XaResult.class.isAssignableFrom(arg0)) {
			try{
				Object po = invokeMethodOnObject(arg0, "getObject", null);
				if (po != null && po instanceof Page) {
					return true;
				}
			}catch(Exception e){
				
			}
		}*/

		//return false;
	}

/*
	@Override
	protected boolean canWrite(MediaType mediaType) {
		if (mediaType == null || MediaType.ALL.equals(mediaType)) {
			return true;
		}
		for (MediaType supportedMediaType : getSupportedMediaTypes()) {
			if (supportedMediaType.isCompatibleWith(mediaType)) {
				return true;
			}
		}
		return false;
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.http.converter.AbstractHttpMessageConverter#writeInternal
	 * (java.lang.Object, org.springframework.http.HttpOutputMessage)
	 */
	@Override
	protected void writeInternal(XaResult arg0, HttpOutputMessage arg1)
			throws IOException, HttpMessageNotWritableException {
        try{
        QueryBaseService queryBaseService = (QueryBaseService) ContextUtil
					.getContext().getBean("QueryBaseService");
		Object po = AnnotationUtil.invokeMethodOnObject(arg0, "getObject", null);
		if (po != null && (po instanceof Page || po instanceof List)) {
			List<T> list =null;
           if(po instanceof Page ){
			Page<T> p = ((Page) (po));
			list = p.getContent();
           }else if( po instanceof List){
        	   list=(List)po;
           }
           HashMap<String,Object> oriValues=new HashMap<String,Object>();
           if(list!=null&&list.size()>0){
			for (T t : list) {
				
				Boolean isJoined=AnnotationUtil.isAnnotationPresentForEntity(t.getClass().getName(), TableJoin.class);
				
				if(!isJoined){
				Field[] fa = t.getClass().getDeclaredFields();
				for (Field f : fa) {
					String dependValue=null;
					if(f.isAnnotationPresent(Depend.class)){
						Depend depend = (Depend) f.getAnnotation(Depend.class);
						String key = depend.name();
						String showPropName = depend.showname();
						String joinField = depend.joinfield();

						showPropName = showPropName.replace('.', '/');
						String[] str = showPropName.split("/");
						String entityName = str[0];
						String propName =AnnotationUtil.convertToPropName(str[1]);
						
						Object o =null;
						if(!StringUtils.isBlank(joinField)){
							if(oriValues.get(joinField)!=null){
							  o=oriValues.get(joinField);
							  
							}else{
							  o = AnnotationUtil.invokeMethodOnObject(t, "get"
									+ joinField.substring(0, 1).toUpperCase()
									+ joinField.substring(1), null);
							  
							  String value=String.valueOf(o);
							  oriValues.put(f.getName(), value);
							 
							}
						}else{
						     o = AnnotationUtil.invokeMethodOnObject(t, "get"
								+ f.getName().substring(0, 1).toUpperCase()
								+ f.getName().substring(1), null);
						     String value=String.valueOf(o);
						     oriValues.put(f.getName(), value);
						}
						
						String value=String.valueOf(o);
						
						
						
						String[] vs=value.split(",");
						String _value="";
						if(vs!=null&vs.length>0){
							for(String v:vs){
								String _v=CacheDepend.getByKeyKey(entityName,v,propName,false);
								if(_v!=null)
								  _value+= (_v+",");
							}
						}
						if(_value.length()>1){
						 _value=_value.substring(0,_value.length()-1);
						}
						
						dependValue=_value;
						log.debug("setDepend f.getName()->"+f.getName()+" set value->"+_value);
						if(!StringUtils.isBlank(_value)){
							String[] params = new String[1];
							params[0] = _value;
							AnnotationUtil.invokeMethodOnObject(t,
									"set"
											+ f.getName().substring(0, 1)
													.toUpperCase()
											+ f.getName().substring(1), params);
						}
					}
				
			
				
					if (f.isAnnotationPresent(Dict.class)) {
						Dict dict = (Dict) f.getAnnotation(Dict.class);
						String key = dict.name();
						
						Object o =null;
						if(f.isAnnotationPresent(Depend.class)){
							 o = dependValue;
						}else{
							 o = AnnotationUtil.invokeMethodOnObject(t, "get"
									+ f.getName().substring(0, 1).toUpperCase()
									+ f.getName().substring(1), null);
						}
						
						

						String value = CacheDict.getDictValueByKeyCode(key,
								String.valueOf(o));
						log.debug("setDict f.getName()->"+f.getName()+" set value->"+value);
						if(value!=null){
							String[] params = new String[1];
							params[0] = value;
							
							
							AnnotationUtil.invokeMethodOnObject(t,
									"set"
											+ f.getName().substring(0, 1)
													.toUpperCase()
											+ f.getName().substring(1), params);
						}

					}
					
					

//					if(f.isAnnotationPresent(Query.class)){//这个方法 有待细化 hql-->sql 目前支持hql和sql写法一样的情况
//						Query query = (Query) f.getAnnotation(Depend.class);
//						String hql=query.value();
//						String paraField=query.paramField();
//						String[] fields=paraField.split(",");
//						String[] v=new String[fields.length];
//						int i=0;
//						for(String s:fields){
//							Object o = AnnotationUtil.invokeMethodOnObject(t, "get"
//									+ s.substring(0, 1).toUpperCase()
//									+ s.substring(1), null);
//							
//							v[i]=String.valueOf(o);
//							i++;
//						}
//						String sql=hql;
//						for(int j=1;j<=v.length;j++){
//						    sql=sql.replace("$"+j, "'"+v[j]+"'");
//						}
//						log.debug("@Query sql===>"+sql);
//						String value="";
//						List<Object[]> valueList=queryBaseService.query(sql);
//						if(valueList!=null){
//							for(Object[] oo:valueList){
//								value=String.valueOf(oo[0]);
//								break;
//							}
//						}
//						
//						if(!StringUtils.isBlank(value)){
//							String[] params = new String[1];
//							params[0] = value;
//							
//							
//							AnnotationUtil.invokeMethodOnObject(t,
//									"set"
//											+ f.getName().substring(0, 1)
//													.toUpperCase()
//											+ f.getName().substring(1), params);
//						}
//						
//					}
//					
				}
           }

		}

		
	}
		
		}
		
        }catch(Exception e){
        	e.printStackTrace();
        }
        
       
        arg1.getBody().write(JsonUtil.toJson(arg0).getBytes());
	    //arg1.getBody().write(JSON.toJSONString(arg0).getBytes());
	}
	
}
