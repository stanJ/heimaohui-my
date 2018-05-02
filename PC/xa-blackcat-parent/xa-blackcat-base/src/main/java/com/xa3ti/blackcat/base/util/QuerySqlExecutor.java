/**
 * 
 */
package com.xa3ti.blackcat.base.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.JoinField;
import com.xa3ti.blackcat.base.service.impl.QueryBaseService;
import com.xa3ti.blackcat.business.DataDict.CacheDepend;





/**
 * @author nijie
 *
 */
public class QuerySqlExecutor {
	
	static Connection conn = null;
	static String url = Settings.getInstance().getDbString("jdbc.url");
	static String username = Settings.getInstance().getDbString("jdbc.username");
	static String password = Settings.getInstance().getDbString("jdbc.password");
	
	
	private boolean avoidSqlInje=false;
	
	public QuerySqlExecutor(Boolean avoidSqlInje){
		this.avoidSqlInje=avoidSqlInje;
	}
	
	public QuerySqlExecutor(){
		
	}
	
	private static void getConnection() throws Exception{
		try{
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url, username, password);
		}catch(Exception e){
			throw e;
		}	
	}
	
	
	private static void closeConnection() throws Exception{
		try{
			if(conn!=null)
				conn.close();
		}catch(Exception e){
			throw e;
		}	
	}
	
	private static String[] parse(String str) {
		String str2 = str.replace('.', '/');
		String[] stra = str2.split("/");
		return stra;
	}
	
	
	private static final Logger log = Logger.getLogger(QuerySqlExecutor.class);
	
	
	public  <T> Page<T> find(Collection<SearchFilter> filters,Class<T> clazz,Pageable pageable,boolean ignoreMultipleValue){
		return find(filters,clazz, pageable,null,null,false,ignoreMultipleValue);
	}
	
	
	public  <T> Page<T> find(Collection<SearchFilter> filters,Class<T> clazz,Pageable pageable){
		return find(filters,clazz, pageable,null,null,false,false);
	}
	
	public  <T> Page<T> find(Collection<SearchFilter> filters,Class<T> clazz,Pageable pageable,String jsonFilter){
		return find(filters,clazz, pageable,null,jsonFilter,false,false);
	}
	
	public  <T> Page<T> find(Collection<SearchFilter> filters,Class<T> clazz,Pageable pageable,String jsonFilter,boolean ignoreMultipleValue){
		return find(filters,clazz, pageable,null,jsonFilter,false,ignoreMultipleValue);
	}
	
	
	public  <T> Page<T> find(Collection<SearchFilter> filters,Class<T> clazz,Pageable pageable,String sqlCondition,String jsonFilter,Boolean usecache,Boolean ignoreMutilpleValue){
		try {
		QueryBaseService queryBaseService = (QueryBaseService) ContextUtil
				.getContext().getBean("QueryBaseService");
		
		int pageSize=10;
		int offset=0;
		if(pageable!=null){
			 pageSize=pageable.getPageSize();
			 offset=pageable.getOffset();
		}
		QueryBuilder qb=new QueryBuilder();
		
		String alias="AAA";
		qb.setAvoidSQLInj(avoidSqlInje);
		qb.clearParamList();
		
		String sql=qb.buildQuery(filters, clazz, pageable,sqlCondition,jsonFilter,alias);
		
		List<QueryParamater> l=qb.getParamList();
		List<Object> paramList=new ArrayList<Object>();
		log.info("PARAMATERS======================>");
		int a=1;
		for(QueryParamater qp:l){
			log.info(a+"--->"+qp.getValue());
			paramList.add(qp.getValue());
			a++;
		}
		log.info("PARAMATERS======================>");
		
		String orderSql=qb.getOrderSql();
		HashMap<String,Class> originalPropertyMap=qb.getOriginalPropertyMap();
		List<String> originalPropertyList=qb.getOriginalPropertyList();
	    //String wrappedSql="select AAA.* from ("+sql+") AAA limit "+pageSize+" offset  "+offset;
	    String wrappedSql="select "+alias+".* from ("+sql+" "+orderSql+" "+qb.getPageableSql()+") "+alias;
	    log.info(wrappedSql);
	    /////////
	    String cSql=sql.substring(0,sql.indexOf(","));
	    cSql+=" "+sql.substring(sql.indexOf(" from "));
	    /////////
		String countSql="select count("+alias+".id) from ("+cSql+") "+alias;
		log.info(countSql);
		
		Integer count=0;
		if(qb.isAvoidSQLInj()){		
			List cList=queryBaseService.query(countSql,paramList);
			for(int i=0;i<cList.size();i++){
				count=((java.math.BigInteger)cList.get(i)).intValue();
				break;
			}
		}else
		  count =DBHelper.count(countSql);
		
		//用JDBC 查总数 
//		log.info("before getConnection");
//		getConnection();
//		log.info("after getConnection");
//		PreparedStatement ps=conn.prepareStatement(countSql);
//		ResultSet rs=ps.executeQuery();
//		Integer count=0;
//		while(rs.next()){
//			count=Integer.valueOf(rs.getInt(1));
//			break;
//		}
//		try{
//			if(rs!=null) rs.close();
//			if(ps!=null) ps.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		log.info("before closeConnection");
//		closeConnection();
//		log.info("after closeConnection");
		
//		List cList=queryBaseService.query(countSql);
//		Integer count=0;
//		for(int i=0;i<cList.size();i++){
//			count=((java.math.BigInteger)cList.get(i)).intValue();
//			break;
//		}
		
		log.info("before queryBaseService.query");
		List<Object[]> list=null;
		if(qb.isAvoidSQLInj()){
			list=queryBaseService.query(wrappedSql,paramList);
		}else{
		  list=queryBaseService.query(wrappedSql);
		}
		log.info("after queryBaseService.query");
		List<Object> rList=new ArrayList<Object>();
		HashMap<String, String> map = (HashMap<String, String>) AnnotationUtil
				.getAllPropertysFromEntityName(clazz.getName());
		for(Object[] o:list){
			
			Object entity = clazz.newInstance();
			Object id = o[0];
			
			try{
			    AnnotationUtil.invokeMethodOnObject(entity, "setTid",id);
			}catch(Exception e){
				try{
					AnnotationUtil.invokeMethodOnObject(entity, "setTid",Long.valueOf(String.valueOf(id)));
				}catch(Exception ee){
					AnnotationUtil.invokeMethodOnObject(entity, "setTid",String.valueOf(id));
				}
			}
			
			HashMap<String,Object> multipeValueMap=new HashMap<String,Object> ();
			List<String> multipeValueList=new ArrayList<String> ();
			
            int index = 1;
			for (String propName : map.keySet()) {
				//System.out.println("ooo["+index+"]====>"+o[index]);
				Field f=null;
				try{
				    f=clazz.getDeclaredField(propName);
				}catch(Exception e){
					if(clazz.getSuperclass()!=null)
				        f=clazz.getSuperclass().getDeclaredField(propName);
				}
				
				if(f.getType().equals(List.class)||f.getType().equals(ArrayList.class))
					continue;
				
				boolean multiplevalue=false;
				String entityName="";
				String pName ="";
				boolean isDepend=false;
				boolean cascade=false;
				if(f!=null&&f.isAnnotationPresent(Depend.class)){
					Depend depend=f.getAnnotation(Depend.class);
				    multiplevalue=depend.multiplevalue();
				    String showPropName=depend.showname();
				    
				    showPropName = showPropName.replace('.', '/');
					String[] str = showPropName.split("/");
				    entityName = str[0];
				    pName=str[1];
					AnnotationUtil.convertToPropName(str[1]);
				    
					isDepend=true;
					
					cascade=depend.cascade();
				    
				}
		
				if(isDepend&&multiplevalue&&!ignoreMutilpleValue){ 
					////////////////////
					if (f.isAnnotationPresent(JoinField.class)) {
						
						try{
						   Field _dependF=AnnotationUtil.getFiledFromPropertyName(AnnotationUtil
									.resolveClassFullNameByEntityName(entityName), pName);
						  
						   if(_dependF.isAnnotationPresent(Depend.class)&&cascade){
							   Depend d = (Depend) _dependF.getAnnotation(Depend.class);
							    
						       String _depend_name=d.name();
						       String _depend_showname=d.showname();
						       String[] _str = parse(_depend_name);
							   String _depend_entityName =_str[0];
							   String _depend_idName = _str[1];
							   String[] __str = parse(_depend_showname);
							   String _depend_showName = __str[1];
							   
							   entityName=_depend_entityName;
							   pName=_depend_showName;
						   }
						   
						   
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					////////////////////
					if(o[index]!=null){
						String value=String.valueOf(o[index]);
						String[] vs=value.split(",");
						String _value="";
						if(vs!=null&vs.length>0){
							for(String v:vs){
								String _v=CacheDepend.getByKeyKey(entityName,v,pName,usecache?true:false);
								if(_v!=null)
								_value+= (_v+",");
							}
						}
						if(_value!=null&&_value.length()>1){
						 _value=_value.substring(0,_value.length()-1);
						}
						if(!StringUtils.isEmpty(_value))
							AnnotationUtil.invokeMethodOnObject(entity, "set"
									+ propName.substring(0, 1).toUpperCase()
									+ propName.substring(1), _value);
						
						multipeValueMap.put(propName, value);
						multipeValueList.add(propName);
					}
				}else{
					if(o[index]!=null){
						if(o[index] instanceof java.sql.Timestamp){
							
							try{
								AnnotationUtil.invokeMethodOnObject(entity, "set"
										+ propName.substring(0, 1).toUpperCase()
										+ propName.substring(1), o[index]);
							}catch(Exception e){
								Date d=new Date(((java.sql.Timestamp)o[index]).getTime()); 
								AnnotationUtil.invokeMethodOnObject(entity, "set"
										+ propName.substring(0, 1).toUpperCase()
										+ propName.substring(1), d);
							}
						}
						else{
							AnnotationUtil.invokeMethodOnObject(entity, "set"
									+ propName.substring(0, 1).toUpperCase()
									+ propName.substring(1), o[index]);
						}
				}
				}
				index++;
			}
			
			////////////////////////////
			
//			Iterator it=originalPropertyMap.keySet().iterator();
//			
//			while(it.hasNext()){
//				String propName=(String)it.next();
//				Class clz=originalPropertyMap.get(propName);
//				classUtil.addProperty("converted_"+propName, java.lang.String.class);
//			}
			ClassUtil classUtil=new ClassUtil();
			
			///map 版本换成list版本 保持顺序
			for(int i=0;i<originalPropertyList.size();i++){
				String propName=(String)originalPropertyList.get(i);
				Class clz=originalPropertyMap.get(propName);
				//classUtil.addProperty("converted_"+propName, java.lang.String.class);
				classUtil.addProperty("converted_"+propName, clz);
			}
			
			
			for(int i=0;i<multipeValueList.size();i++){
				String propName=(String)multipeValueList.get(i);
				classUtil.addProperty("original_"+propName, java.lang.String.class);
			}
			
			log.info("before classUtil.dynamicClass");
			Object dyEntity=classUtil.dynamicClass(entity);
			log.info("after classUtil.dynamicClass");
			/*
			 Method[] methods = dyEntity.getClass().getDeclaredMethods();  
		        for (Method curMethod : methods) {  
		            System.out.println(curMethod.getName());  
		        } 
		     */   
//			Iterator it2=originalPropertyMap.keySet().iterator();
//			while(it2.hasNext()){
//				System.out.println("ooo["+index+"]====>"+o[index]);
//				String propName=(String)it2.next();
//				propName="converted_"+propName;
//				if(o[index]!=null){
//					System.out.println("=======>set"
//							+ propName.substring(0, 1).toUpperCase()
//							+ propName.substring(1)+"-->"+o[index]);
//					AnnotationUtil.invokeMethodOnObject(dyEntity, "set"
//							+ propName.substring(0, 1).toUpperCase()
//							+ propName.substring(1), o[index]);
//				}
//				index++;
//			}
			
			
			for(int i=0;i<originalPropertyList.size();i++){
				String propName=(String)originalPropertyList.get(i);
				propName="converted_"+propName;
				if(o[index]!=null){
					if(o[index] instanceof java.sql.Timestamp){
						
						try{
							AnnotationUtil.invokeMethodOnObject(dyEntity, "set"
									+ propName.substring(0, 1).toUpperCase()
									+ propName.substring(1), o[index]);
						}catch(Exception e){
							Date d=new Date(((java.sql.Timestamp)o[index]).getTime()); 
							AnnotationUtil.invokeMethodOnObject(dyEntity, "set"
									+ propName.substring(0, 1).toUpperCase()
									+ propName.substring(1), d);
						}
					}else{
					
						AnnotationUtil.invokeMethodOnObject(dyEntity, "set"
								+ propName.substring(0, 1).toUpperCase()
								+ propName.substring(1), o[index]);
					}
				}
				index++;
				
			}
			
			for(int i=0;i<multipeValueList.size();i++){
				String propName=(String)multipeValueList.get(i);
				Object v=multipeValueMap.get(propName);
				propName="original_"+propName;
				if(v!=null){
					AnnotationUtil.invokeMethodOnObject(dyEntity, "set"
							+ propName.substring(0, 1).toUpperCase()
							+ propName.substring(1), v);
				}
			}
			
			
			rList.add(dyEntity);
			//////////////////////////
			//rList.add(entity);
			
		}
		
		PageImpl<T> pl=new PageImpl(rList,pageable,count);
		
		return pl;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
