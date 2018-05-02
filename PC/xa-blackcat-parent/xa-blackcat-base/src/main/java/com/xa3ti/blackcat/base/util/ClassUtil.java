/**
 * 
 */
package com.xa3ti.blackcat.base.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;



/**
 * @author nijie
 *
 */
public class ClassUtil {
	HashMap typeMap = new HashMap();
	
	public void addProperty(String key,Class clazz){
		typeMap.put(key, clazz);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object dynamicClass(Object object) throws Exception {
		HashMap returnMap = new HashMap();
		
		// 读取配置文件
		/*
		Properties prop = new Properties();
		String sourcepackage = object.getClass().getName();
		String classname = sourcepackage.substring(sourcepackage
				.lastIndexOf(".") + 1);
		InputStream in = ClassUtil.class.getResourceAsStream(filepath
				+ classname + ".properties");
		prop.load(in);

		Set<String> keylist = prop.stringPropertyNames();
		*/

		Class type = object.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(object, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					if(java.lang.String.class.equals(descriptor.getPropertyType()))
					  returnMap.put(propertyName, null);
					else if(java.lang.Long.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, new Long("0"));
					else if(java.lang.Integer.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, new Integer("0"));
					else if(java.lang.Double.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, new Double("0"));
					else if(java.math.BigDecimal.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, new java.math.BigDecimal("0"));
					else if(java.util.Date.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, null);
				}
				typeMap.put(propertyName, descriptor.getPropertyType());
			}
		}
		// 加载配置文件中的属性
		/*
		Iterator<String> iterator = keylist.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			returnMap.put(key, prop.getProperty(key));
			typeMap.put(key, Class.forName("java.lang.String"));
		}
		*/
		
		// map转换成实体对象
		DynamicBean bean = new DynamicBean(typeMap);
		// 赋值
		Set keys = typeMap.keySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			//System.out.println("key->"+key+",value->"+returnMap.get(key));
			bean.setValue(key, returnMap.get(key));
		}
		Object obj = bean.getObject();
		return obj;
	}
	
	
	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
		public static Map<String, Object> transBean2Map(Object obj) {

			if (obj == null) {
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo
						.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					String key = property.getName();

					// 过滤class属性
					if (!key.equals("class")) {
						// 得到property对应的getter方法
						Method getter = property.getReadMethod();
						Object value = getter.invoke(obj);

						if (value instanceof ArrayList) {
							List<Object> convertedList = new ArrayList<Object>();
							ArrayList<Object> list = (ArrayList<Object>) value;
							for (Object o : list) {
								convertedList.add(transBean2Map(o));
							}

							map.put(key, convertedList);
						} else
							map.put(key, value);
					}

				}
			} catch (Exception e) {
				System.out.println("transBean2Map Error " + e);
			}

			return map;

		}

		// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
		public static Map<String, Object> transBean2MapCascade(Object obj) {

			if (obj == null) {
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo
						.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					String key = property.getName();

					// 过滤class属性
					if (!key.equals("class")) {
						// 得到property对应的getter方法
						Method getter = property.getReadMethod();
						Object value = getter.invoke(obj);
						if(value==null)
							map.put(key, null);
						
						else if (value instanceof ArrayList) {
							List<Object> convertedList = new ArrayList<Object>();
							ArrayList<Object> list = (ArrayList<Object>) value;
							for (Object o : list) {
								convertedList.add(transBean2MapCascade(o));
							}

							map.put(key, convertedList);
						} else if (value.getClass().isArray()) {
							Object[] arr = (Object[]) value;
							Map[] ma = new Map[arr.length];
							for (int i = 0; i < arr.length; i++) {
								ma[i] = transBean2MapCascade(arr[i]);
							}

							map.put(key, ma);
						} else {
							if (value instanceof java.lang.String
						       ||value instanceof java.util.Date
						       ||value instanceof java.sql.Date	
						       ||value instanceof java.math.BigDecimal	
							   ||value.getClass().isPrimitive())
								map.put(key, value);
							else
								map.put(key, transBean2MapCascade(value));

						}
					}

				}
			} catch (Exception e) {
				System.out.println("transBean2Map Error " + e);
			}

			return map;

		}


	public static void main(String[] args) throws Exception {
		ClassUtil classUtil=new ClassUtil();
		/*
		classUtil.addProperty("original_sex", java.lang.String.class);
	     Object o=classUtil.dynamicClass(new Doctor());
	     Class clazz = o.getClass();  
	     
	     System.out.println(clazz.getName()); 
	     System.out.println(clazz.getSuperclass().getName());
	     
	        Method[] methods = clazz.getDeclaredMethods();  
	        for (Method curMethod : methods) {  
	            System.out.println(curMethod.getName());  
	        }  
	        
	        Field[] fields = clazz.getDeclaredFields();
	        for (Field filed : fields) {  
	            System.out.println(filed.getName());  
	        }  
	        
	        AnnotationUtil.invokeMethodOnObject(o, "setOriginal_sex", "1");
	        */
	        
	     int abc=1;
	     abc++;
	}
}
