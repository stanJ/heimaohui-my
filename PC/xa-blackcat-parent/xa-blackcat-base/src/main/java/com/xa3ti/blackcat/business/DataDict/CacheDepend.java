/**
 * 
 */
package com.xa3ti.blackcat.business.DataDict;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xa3ti.blackcat.base.service.impl.QueryBaseService;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.util.ContextUtil;



/**
 * @author nijie
 *
 */
@Component("cacheDepend")
@Scope("singleton")
public class CacheDepend {
	private static final Logger log=Logger.getLogger(CacheDepend.class);
	public static Map<String, Map<String, Object>> dependMap = new ConcurrentHashMap<String, Map<String, Object>>();

	public static void put(String key, Map<String, Object> map) {
		dependMap.put(key, map);
	}

	public static Map<String,Object> getByKey(String key,boolean loadByCache) {
		String classFullName=AnnotationUtil.resolveClassFullNameByEntityName(key);
		
		Map<String, Object> m = dependMap.get(classFullName);
		if (m != null&&loadByCache) {
			return m;
		}else{
			fetchAndSaveDependDataToCache(classFullName);
			Map<String, Object> mm = dependMap.get(classFullName);
			dependMap.clear();
			return mm;
		}
	}
	public static String getByKeyKey(String key, String id,String propName,boolean loadByCache) {
		String classFullName=AnnotationUtil.resolveClassFullNameByEntityName(key);
		Map<String, Object> m = dependMap.get(classFullName);
		if (m != null&&loadByCache) {
			Object entity=m.get(id);
			if(entity!=null){
				String value=null;
				try{
			    value=String.valueOf(AnnotationUtil.invokeMethodOnObject(entity, "get"
					+ propName.substring(0, 1).toUpperCase()
					+ propName.substring(1), null));
				}catch(Exception e){
					
				}
				return value;
			}
		}else{
			fetchAndSaveDependDataToCache(classFullName);
			Map<String, Object> mm = dependMap.get(classFullName);
			if (mm != null) {
				Object entity=mm.get(id);
				dependMap.clear();
				if(entity!=null){
					
					String value=null;
					try{
					value=String.valueOf(AnnotationUtil.invokeMethodOnObject(entity, "get"
								+ propName.substring(0, 1).toUpperCase()
								+ propName.substring(1), null));
					}catch(Exception e){
						
					}
					return value;
				
				
				
				}
			}
		}
		return null;
	}
	
	
	

	private static void fetchAndSaveDependDataToCache(String entityName) {
		try {
			String tableName = AnnotationUtil
					.getTableNameFromEntityName(entityName);
//			HashMap<String, String> map = (HashMap<String, String>) AnnotationUtil
//					.getAllPropertysFromEntityName(entityName);
			
			HashMap<String, String> map = (HashMap<String, String>) AnnotationUtil
					.getAllValidPropertysFromEntityName(entityName);
			
			if (tableName != null) {
				QueryBaseService queryBaseService = (QueryBaseService) ContextUtil
						.getContext().getBean("QueryBaseService");
				Iterator it = map.keySet().iterator();
				String sql = "select id,";
				
				if(entityName.contains("XaCmsUser")){//对这个实体 做修正
					sql="select user_id,";
				}
				
				boolean enter = false;
				while (it.hasNext()) {
					String propName = (String) it.next();
					String colName = (String) map.get(propName);
					sql += colName + " " + propName + ",";
					enter = true;
				}
				if (enter)
					sql = sql.substring(0, sql.length() - 1);

				sql += " from " + tableName
						+ " where ((status ='1' or status='2') and (status!=5) )";
				List<Object[]> list = queryBaseService.query(sql);
				if (list != null && list.size() > 0) {
					Map<String, Object> m = new HashMap<String, Object>();
					for (Object[] o : list) {
						Class entityClazz = Class.forName(entityName);
						Object entity = entityClazz.newInstance();
						String id = String.valueOf(o[0]);

						int index = 1;
						for (String propName : map.keySet()) {
							log.debug("invoke object "+entity.getClass().getName()+" method->set"
									+ propName.substring(0, 1).toUpperCase()
									+ propName.substring(1));
							if(o[index]!=null){
								try{
								AnnotationUtil.invokeMethodOnObject(entity, "set"
										+ propName.substring(0, 1).toUpperCase()
										+ propName.substring(1), o[index]);
								}catch(Exception e){
									
								}
							}
							index++;
						}

						m.put(id, entity);

					}
					put(entityName, m);
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
