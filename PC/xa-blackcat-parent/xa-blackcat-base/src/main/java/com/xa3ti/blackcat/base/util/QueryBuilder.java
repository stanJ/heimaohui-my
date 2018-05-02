/**
 * 
 */
package com.xa3ti.blackcat.base.util;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import jodd.util.StringUtil;

import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.xa3ti.blackcat.base.annotation.Depend;
import com.xa3ti.blackcat.base.annotation.Dict;
import com.xa3ti.blackcat.base.annotation.IsJoinedTableField;
import com.xa3ti.blackcat.base.annotation.JoinField;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.base.util.expression.RuleResolver;




/**
 * @author nijie
 *
 */
public class QueryBuilder {
	private static final Logger log = Logger.getLogger(QueryBuilder.class);

	HashMap<String, Class> originalPropertyMap = new HashMap<String, Class>();

	List<String> originalPropertyList = new ArrayList<String>();
	
	private String orderSql="";
	
	private String pageableSql="";
	
	
	
	private boolean avoidSQLInj = false;

	private List<QueryParamater> paramList = new ArrayList<QueryParamater>();
	private List<QueryParamater> paramList2 = new ArrayList<QueryParamater>();



	public void clearParamList() {
		paramList.clear();
	}

	public boolean isAvoidSQLInj() {
		return avoidSQLInj;
	}

	public void setAvoidSQLInj(boolean avoidSQLInj) {
		this.avoidSQLInj = avoidSQLInj;
	}
	
	public List<QueryParamater> getParamList() {
		return paramList;
	}

	public void setParamList(List<QueryParamater> paramList) {
		this.paramList = paramList;
	}
	
	public String getPageableSql() {
		return pageableSql;
	}

	public void setPageableSql(String pageableSql) {
		this.pageableSql = pageableSql;
	}

	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public HashMap<String, Class> getOriginalPropertyMap() {
		return originalPropertyMap;
	}

	public List<String> getOriginalPropertyList() {
		return originalPropertyList;
	}

	public String buildQuery(Collection<SearchFilter> filters,
			final Class clazz, Pageable pageable) {

		return buildQuery(filters, clazz, pageable, null,null,null);
	}
	
	public String buildQuery(Collection<SearchFilter> filters,
			final Class clazz, Pageable pageable,String jsonFilter) {

		return buildQuery(filters, clazz, pageable, null,jsonFilter,null);
	}
	
	public static Date parseDate(String str) {
		Date convertDate = null;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			convertDate=format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			try{
				SimpleDateFormat _format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				_format.setLenient(false);
				convertDate=_format.parse(str);
			}catch (ParseException ee) {
				try{
					SimpleDateFormat __format = new SimpleDateFormat("yyyy-MM-dd");
					__format.setLenient(false);
					convertDate=__format.parse(str);
				}catch (ParseException eee) {
					
				}
			}
			
			
		}
		return convertDate;
	}

	private  String generateBasedOnProp(final Class clazz,
			String relation, String propName, Operator operator, Object value,
			HashMap<String, Integer> joinTableMap, HashMap propMap) {
		String sqlJoinTable = "";
		String sqlJoinTableAvoidInj = "";
		// String filedName = filter.fieldName;
		// SearchFilter.Operator operator = filter.operator;
		// Object value = filter.value;

		// ///////////
		// propName = fieldName;
		String filedName = (String) propMap.get(propName);
		// ///////////////

		Field f = null;
		try {
			f = clazz.getDeclaredField(propName);
		} catch (Exception e) {
			try {
				f = clazz.getSuperclass().getDeclaredField(propName);
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		String valueCondi = null;
		
		Object valueAvoidInj = null;
		
		
		if(value==null){
			if ("is null".equals(parseOperator(operator)))
				valueCondi = "";
		}
		else if (value instanceof String) {
			value = ((String) value).replace("\"", "");
			if ("find_in_set".equals(parseOperator(operator))){
				valueCondi = (String) value;
			
				if (avoidSQLInj) {
					valueAvoidInj = (String) value;
					// paramList.add(new
					// QueryParamater(java.lang.String.class,(String)value));
				}
			}
			else if ("like".equals(parseOperator(operator))){
				valueCondi = "'%" + value + "%'";
				
				if (avoidSQLInj) {
					valueAvoidInj = "%" + value + "%";
					// paramList.add(new
					// QueryParamater(java.lang.String.class,"'%" + value +
					// "%'"));
				}
				
			}
			else if ("in".equals(parseOperator(operator))){
				valueCondi = "(" + value + ")";
				
				if (avoidSQLInj) {
					valueAvoidInj = "(" + value + ")";
					// paramList.add(new
					// QueryParamater(java.lang.String.class,"(" + value +
					// ")"));
				}
			}
			else{
				valueCondi = "'" + value + "'";
				
				if (avoidSQLInj) {
					Date dValue=parseDate((String)value);
					if(dValue!=null){
						valueAvoidInj=dValue;
					}else{
					  valueAvoidInj = "" + value + "";
					}
					// paramList.add(new
					// QueryParamater(java.lang.String.class,"'" + value +
					// "'"));
				}
				
			}

		} else if (value instanceof Integer || value instanceof Double
				|| value instanceof Long || value instanceof Number) {
			valueCondi = "" + Double.parseDouble(String.valueOf(value)) + "";
			
			if (avoidSQLInj) {
				valueAvoidInj = Double.parseDouble(String.valueOf(value));
				// paramList.add(new
				// QueryParamater(java.lang.Double.class,Double.parseDouble(String.valueOf(value))));
			}
			
		}else if(value instanceof java.util.Date){
			valueCondi = "'" + DateUtil.DateToString((java.util.Date)value, "yyyy-MM-dd HH:mm:ss") + "'";
			
			if (avoidSQLInj) {
				valueAvoidInj = (java.util.Date) value;

				// paramList.add(new
				// QueryParamater(java.util.Date.class,com.xa3ti.aiya.base.util.DateUtil.DateToString(
				// (java.util.Date) value, "yyyy-MM-dd HH:mm:ss")));
			}
		}
		boolean isDictField = false;
		if (f != null && f.isAnnotationPresent(Dict.class) && !f.isAnnotationPresent(Depend.class)) {
			Dict dict = (Dict) f.getAnnotation(Dict.class);
			String name = dict.name();

			String tableIndexKey = name + "_" + propName;

			Integer curIndex = joinTableMap.get(tableIndexKey);

			String sqlJoinTableA = relation+"  t0."+filedName
					+ parseOperator(operator) + valueCondi +" ";

			if (avoidSQLInj) {
				String sqlJoinTableB = relation + "  t0." + filedName
						+ parseOperator(operator) + "?" + " ";

				paramList.add(new QueryParamater(valueAvoidInj));

				sqlJoinTableAvoidInj += sqlJoinTableB;

			}

			
			sqlJoinTable += sqlJoinTableA;

			// Integer oIndex=occupyMap.get(propName);
			// if(oIndex!=null)
			// sqlJoinTable =
			// sqlJoinTable.replace("$"+oIndex,sqlJoinTableA);

			isDictField = true;
		}
		if (f != null && f.isAnnotationPresent(Depend.class)) {
			Depend depend = (Depend) f.getAnnotation(Depend.class);
			String name = depend.name();
			String showname = depend.showname();

			String[] str = parse(name);
			String entityName = str[0];
			String idName = str[1];

			String idFieldName = AnnotationUtil.convertToFieldName(idName);

			String[] str1 = parse(showname);
			String showName = str1[1];
			String showFieldName = AnnotationUtil.convertToFieldName(showName);

			String dependTalbeName = AnnotationUtil
					.getTableNameFromEntityName(AnnotationUtil
							.resolveClassFullNameByEntityName(entityName));
			
			

			//String tableIndexKey = entityName + "_" + showName;
			String tableIndexKey = entityName + "_" + propName;//nijie modify this to gurantee unique
			Integer curIndex = joinTableMap.get(tableIndexKey);

			boolean multiplevalue = depend.multiplevalue();
			String sqlJoinTableA = "";
			String sqlJoinTableB = "";
			if (multiplevalue) {
				if ("find_in_set".equals(parseOperator(operator))) {
					String[] v = String.valueOf(valueCondi).split(",");
					List<Predicate> ps = new ArrayList<Predicate>();
					if (v != null && v.length > 0) {
						sqlJoinTableA += relation + "  (";
						
						if (avoidSQLInj) {
							sqlJoinTableB += relation + "  (";
						}
						
						for (int i = 0; i < v.length; i++) {
							sqlJoinTableA += " find_in_set('" + v[i] + "',t0."
									+ filedName + ") or";
							
							if (avoidSQLInj) {
								sqlJoinTableB += " find_in_set('" + v[i] + "',t0."
										+ filedName + ") or";

								//paramList.add(new QueryParamater(
								//		java.lang.String.class, v[i]));

							}
						}
						sqlJoinTableA = sqlJoinTableA.substring(0,
								sqlJoinTableA.length() - 2);
						
						if (avoidSQLInj) {
							sqlJoinTableB = sqlJoinTableB.substring(0,
									sqlJoinTableB.length() - 2);
						}
						
						sqlJoinTableA += ")";
						
						if (avoidSQLInj) {
							sqlJoinTableB += ")";
						}
					}
				} else {
					sqlJoinTableA = relation + "  t0." + filedName + " "
							+ parseOperator(operator) + " " + valueCondi + " ";
					
					
					if (avoidSQLInj) {
						if(Operator.ISNULL.equals(operator)){
							sqlJoinTableB = relation + "  t0." + filedName + " "
									+ parseOperator(operator) + " ";
						}else{
							sqlJoinTableB = relation + "  t0." + filedName + " "
									+ parseOperator(operator) + " " + "?" + " ";
	
							paramList.add(new QueryParamater(valueAvoidInj));
						}
					}
				}
			} else {
				///////////////////////////////////////
				if(f.isAnnotationPresent(IsJoinedTableField.class)){
					IsJoinedTableField joinedTableField = (IsJoinedTableField) f.getAnnotation(IsJoinedTableField.class);
					String prop=joinedTableField.field();
					String field = AnnotationUtil.convertToFieldName(prop);
				   sqlJoinTableA = "#"+relation + "  (t" + curIndex + "."
						+ field + " " + parseOperator(operator) + " "
						+ valueCondi;
				   
				   
				   if (avoidSQLInj) {
						if(Operator.ISNULL.equals(operator)){
							sqlJoinTableB = "#" + relation + "  (t" + curIndex
									+ "." + field + " " + parseOperator(operator)
									+ " ";
						}else{
							sqlJoinTableB = "#" + relation + "  (t" + curIndex
									+ "." + field + " " + parseOperator(operator)
									+ " " + "?";
							paramList2.add(new QueryParamater(valueAvoidInj)); //放到头上
						}
					}
				   
				}
				///////////////////////////////////////
				// 这里做两个条件 分别是对应关联表的显示字段查询，或者本表的值做查询
				else{
					sqlJoinTableA = relation + "  ( ifnull(t" + curIndex + "."
							+ showFieldName + ",'') " + parseOperator(operator)
							+ " " + valueCondi + " ";
				  
				  
				  if (avoidSQLInj) {
						if(Operator.ISNULL.equals(operator)){
							sqlJoinTableB = relation + "  ( ifnull(t" + curIndex
									+ "." + showFieldName + ",'') "
									+ parseOperator(operator) +  " ";
						}else{
							sqlJoinTableB = relation + "  ( ifnull(t" + curIndex
									+ "." + showFieldName + ",'') "
									+ parseOperator(operator) + " " + "?" + " ";
							paramList.add(new QueryParamater(valueAvoidInj));
						}
					}
				  
				}
				
				
				if (!f.isAnnotationPresent(JoinField.class)) {
//					sqlJoinTableA += " or t0." + filedName + " "
//							+ parseOperator(operator) + " " + valueCondi;
					
					if (operator == operator.NE) {
						sqlJoinTableA += " and t0." + filedName + " "
								+ parseOperator(operator) + " " + valueCondi;

						if (avoidSQLInj) {
							if(Operator.ISNULL.equals(operator)){
								sqlJoinTableB += " and t0." + filedName + " "
										+ parseOperator(operator) + " ";
							}else{
								sqlJoinTableB += " and t0." + filedName + " "
										+ parseOperator(operator) + " " + "?";
	
								paramList.add(new QueryParamater(valueAvoidInj));
							}
						}

					} else {
						sqlJoinTableA += " or t0." + filedName + " "
								+ parseOperator(operator) + " " + valueCondi;

						if (avoidSQLInj) {
							if(Operator.ISNULL.equals(operator)){
								sqlJoinTableB += " or t0." + filedName + " "
										+ parseOperator(operator) + " ";
							}else{
								sqlJoinTableB += " or t0." + filedName + " "
										+ parseOperator(operator) + " " + "?";
								paramList.add(new QueryParamater(valueAvoidInj));
							}

						}

					}
					
				}
				sqlJoinTableA += " )";
				
				if (avoidSQLInj) {
					sqlJoinTableB += " )";
				}
				
				if(f.isAnnotationPresent(IsJoinedTableField.class)){
					sqlJoinTableA += " #";
					
					if (avoidSQLInj) {
						sqlJoinTableB += " #";
					}
				}
			}

			sqlJoinTable += sqlJoinTableA;

			
			if (avoidSQLInj) {
				sqlJoinTableAvoidInj += sqlJoinTableB;
			}
			// Integer oIndex=occupyMap.get(propName);
			// if(oIndex!=null)
			// sqlJoinTable = sqlJoinTable.replace("$"+oIndex, sqlJoinTableA);
		} else {
			if (!isDictField) {
			String sqlJoinTableA = relation + "  t0." + filedName + " "
					+ parseOperator(operator) + " " + valueCondi + " ";
			
			if (avoidSQLInj) {
				String sqlJoinTableB ="";
				if(Operator.ISNULL.equals(operator)){
				    sqlJoinTableB = relation + "  t0." + filedName + " "
							+ parseOperator(operator) + " ";
				}else{
				    sqlJoinTableB = relation + "  t0." + filedName + " "
							+ parseOperator(operator) + " " + "?" + " ";
					paramList.add(new QueryParamater(valueAvoidInj));
				}

				sqlJoinTableAvoidInj += sqlJoinTableB;
			}

			sqlJoinTable += sqlJoinTableA;
			}
		}
		
		if (avoidSQLInj) {
			return sqlJoinTableAvoidInj;
		}

		return sqlJoinTable;
	}

	public String buildQuery(Collection<SearchFilter> filters,
			final Class clazz, Pageable pageable, String sqlCondition,String jsonFilter,String alias) {
		String sql = "";
		try {

			String sqlMain = "select ";
			String tableName = AnnotationUtil.getTableNameFromEntityName(clazz
					.getName());
			String sqlMainFromTable = " from " + tableName + " t0 ";

			HashMap<String, String> map = (HashMap<String, String>) AnnotationUtil
					.getAllPropertysFromEntityName(clazz.getName());
			
			HashMap<String,Boolean> convertedConvertedMap=new HashMap<String,Boolean>();

			HashMap<String, Integer> joinTableMap = new HashMap<String, Integer>();
			Iterator it = map.keySet().iterator();
			String selField = " t0.id id,";
			String sqlJoinTable = "";
			Integer index = 1;
			Integer occupyIndex = 0;
			HashMap<String, Integer> occupyMap = new HashMap<String, Integer>();

			HashMap<String, Boolean> joinedTableMap = new HashMap<String, Boolean>();
			String convertedSelectSql = "";
			while (it.hasNext()) {
				String propName = (String) it.next();
				String colName = (String) map.get(propName);
				Field f = null;
				try {
					f = clazz.getDeclaredField(propName);
				} catch (Exception e) {
					if (clazz.getSuperclass() != null)
						f = clazz.getSuperclass().getDeclaredField(propName);
				}
				
				if(f.getType().equals(List.class)||f.getType().equals(ArrayList.class))
					continue;
				
				if (f != null
						&& (f.isAnnotationPresent(Dict.class) || f
								.isAnnotationPresent(Depend.class))) {
					if (f.isAnnotationPresent(Dict.class)
							&& !f.isAnnotationPresent(Depend.class)) {
						Dict dict = (Dict) f.getAnnotation(Dict.class);
						String name = dict.name();
						Integer curIndex = index;
						String tableIndexKey = name + "_" + propName;
						if (joinTableMap.get(tableIndexKey) == null) {
							joinTableMap.put(tableIndexKey, index);
							selField += "t0." + colName + " " + propName + ",";

							convertedSelectSql += "td" + curIndex + ".value "
									+ "converted_" + propName + ",";

							index++;
						} else {
							curIndex = joinTableMap.get(tableIndexKey);
							// curIndex++;
							// joinTableMap.put(tableIndexKey, curIndex);
							selField += "t0." + colName + " " + propName + ",";

							convertedSelectSql += "td" + curIndex + ".value "
									+ "converted_" + propName + ",";
						}

						sqlJoinTable += " left join tb_ms_cms_dict td"
								+ curIndex + "  on " + " td" + curIndex
								+ ".key='" + name + "' and td" + curIndex
								+ ".code=t0." + colName + " $" + occupyIndex
								+ " ";
						occupyMap.put(propName, occupyIndex++);

						originalPropertyMap.put(propName, f.getType());
						originalPropertyList.add(propName);
					}
					if (f.isAnnotationPresent(Depend.class)) {
						Depend depend = (Depend) f.getAnnotation(Depend.class);
						String name = depend.name();
						String showname = depend.showname();

						String[] str = parse(name);
						String entityName = str[0];
						String idName = str[1];

						String idFieldName = AnnotationUtil
								.convertToFieldName(idName);

						String[] str1 = parse(showname);
						String showName = str1[1];

						// ///////////////////////////
						String fullClassName = AnnotationUtil
								.resolveClassFullNameByEntityName(entityName);
						Field showField = null;
						Class cls = null;
						try {
						    cls = Class.forName(fullClassName);

							showField = cls.getDeclaredField(showName);
						} catch (Exception e) {
							showField = cls.getSuperclass().getDeclaredField(showName);
							
						}
						if (showField == null)
							showField = f;
						// ///////////////////////////
						String showFieldName = AnnotationUtil
								.convertToFieldName(showName);
						String dependTalbeName = AnnotationUtil
								.getTableNameFromEntityName(AnnotationUtil
										.resolveClassFullNameByEntityName(entityName));
						boolean multiplevalue = depend.multiplevalue();

						Integer curIndex = index;
						//String tableIndexKey = entityName + "_" + showName;
						String tableIndexKey = entityName + "_" + propName;//modify this to gurantee unique

						boolean isJoinField=f.isAnnotationPresent(JoinField.class);
						
						if (multiplevalue&&!isJoinField) {

							selField += "t0." + colName + " " + propName + ",";
						} else {
							if (joinTableMap.get(tableIndexKey) == null) {
								joinTableMap.put(tableIndexKey, curIndex);
//
//								if (f.isAnnotationPresent(JoinField.class)) {
//									JoinField joinField = (JoinField) f
//											.getAnnotation(JoinField.class);
//									String selffield = AnnotationUtil
//											.convertToFieldName(joinField
//													.selffield());
//									if (!f.isAnnotationPresent(Dict.class)) {
//										selField += "t" + curIndex + "."
//												+ showFieldName + " " + ""
//												+ propName + ",";
//									} else if (f
//											.isAnnotationPresent(Dict.class)) {
//										selField += "td" + curIndex + ".value"
//												+ " " + "" + propName + ",";
//									}
//								} else {
//									selField += "t0." + colName + " " + ""
//											+ propName + ",";
//								}
//
//								if (!f.isAnnotationPresent(Dict.class))
//									convertedSelectSql += "t" + curIndex + "."
//											+ showFieldName + " "
//											+ "converted_" + propName + ",";
//								else
//									convertedSelectSql += "td" + curIndex
//											+ ".value" + " " + "converted_"
//											+ propName + ",";

								index++;
							} else {
								curIndex = joinTableMap.get(tableIndexKey);
								
							}
							if (f.isAnnotationPresent(JoinField.class)) {
								////////////////////
								try{
								   Field _dependF=AnnotationUtil.getFiledFromPropertyName(AnnotationUtil
											.resolveClassFullNameByEntityName(entityName), showName);
								  boolean cascade=depend.cascade();
										   if(_dependF.isAnnotationPresent(Depend.class)&&cascade){
											   Depend d = (Depend) _dependF.getAnnotation(Depend.class);
										    
										       String _depend_name=d.name();
										       String _depend_showname=d.showname();
										       String[] _str = parse(_depend_name);
											   String _depend_entityName =_str[0];
											   String _depend_idName = _str[1];
											   
											   String[] __str = parse(_depend_showname);
											   String _depend_showName = __str[1];
											
											   String _depend_showFieldName = AnnotationUtil
														.convertToFieldName(_depend_showName);
											   
											   String _tableIndexKey = showFieldName+"_"+_depend_entityName + "_" + _depend_showName;//modify this to gurantee unique
											   if (joinTableMap.get(_tableIndexKey) == null) {
												   curIndex = index;
												   joinTableMap.put(_tableIndexKey, curIndex);
												   index++;
												} else {
													curIndex = joinTableMap.get(_tableIndexKey);
												}
											   Boolean used=convertedConvertedMap.get(_depend_entityName+"_"+_depend_idName);
											   if(used!=null&&used){
											    convertedSelectSql += "t" + curIndex + "."
														+ _depend_showFieldName + " " + ""
														+ "converted_"+showFieldName+"_"+_depend_entityName+"_"+_depend_idName + ",";
											    
											    originalPropertyMap.put(showFieldName+"_"+_depend_entityName+"_"+_depend_idName,
										        		AnnotationUtil.getFiledFromPropertyName(AnnotationUtil
																.resolveClassFullNameByEntityName(_depend_entityName),_depend_showName).getType());
												originalPropertyList.add(showFieldName+"_"+_depend_entityName+"_"+_depend_idName);
												
											   }else{
												   convertedSelectSql += "t" + curIndex + "."
															+ _depend_showFieldName + " " + ""
															+ "converted_"+_depend_entityName+"_"+_depend_idName + ",";
												   
												   originalPropertyMap.put(_depend_entityName+"_"+_depend_idName,
											        		AnnotationUtil.getFiledFromPropertyName(AnnotationUtil
																	.resolveClassFullNameByEntityName(_depend_entityName),_depend_showName).getType());
												   originalPropertyList.add(_depend_entityName+"_"+_depend_idName);
												   convertedConvertedMap.put(_depend_entityName+"_"+_depend_idName, true);	
											   }
											
												curIndex = joinTableMap.get(tableIndexKey);
									   
								   }
								}catch(Exception e){
									
									e.printStackTrace();
								}
								
								//////////////////////
									JoinField joinField = (JoinField) f
											.getAnnotation(JoinField.class);
									String selffield = AnnotationUtil
											.convertToFieldName(joinField
													.selffield());
									
									
									
									if (!f.isAnnotationPresent(Dict.class)) {
										selField += "t" + curIndex + "."
												+ showFieldName + " " + ""
												+ propName + ",";
									} else if (f
											.isAnnotationPresent(Dict.class)) {
										//selField += "td" + curIndex + ".value"
										//		+ " " + "" + propName + ",";
										
										selField += "td" + curIndex + ".code"
												+ " " + "" + propName + ",";
									}
								} else {
									selField += "t0." + colName + " " + ""
											+ propName + ",";
								}

								if (!f.isAnnotationPresent(Dict.class))
									convertedSelectSql += "t" + curIndex + "."
											+ showFieldName + " "
											+ "converted_" + propName + ",";
								else
									convertedSelectSql += "td" + curIndex
											+ ".value" + " " + "converted_"
											+ propName + ",";

							}
						

						if (!(multiplevalue)||isJoinField) {
							curIndex = joinTableMap.get(tableIndexKey);

							if (f.isAnnotationPresent(JoinField.class)) {
								JoinField joinField = (JoinField) f
										.getAnnotation(JoinField.class);
								String selffield = AnnotationUtil
										.convertToFieldName(joinField
												.selffield());
								String joinFieldName = AnnotationUtil
										.convertToFieldName(joinField
												.joinfield());
								
								String condi=joinField.condition();
								
								if (!f.isAnnotationPresent(Dict.class)) {
									
										
										
									sqlJoinTable += " left join "
											+ dependTalbeName + " t" + curIndex
											+ "  on " + " t" + curIndex + "."
											+ joinFieldName + "=t0."
											+ selffield + " $" + occupyIndex
											+ " ";
									
									sqlJoinTable +=" and t" + curIndex + ".status=1  ";
									
									if(!StringUtil.isBlank(condi)){
										sqlJoinTable +=" and t" + curIndex + "."+condi.trim()+" ";
									}
									
									
							/////////
									if(f.isAnnotationPresent(IsJoinedTableField.class)){
									IsJoinedTableField joinedTableField = (IsJoinedTableField) f.getAnnotation(IsJoinedTableField.class);

									String prop=joinedTableField.field();
									String condition=joinedTableField.condition();
									String field = AnnotationUtil.convertToFieldName(prop);
									  sqlJoinTable +=  " and  t" + curIndex + ".status=1 ";
									  if(!StringUtils.isBlank(condition)){
										  sqlJoinTable +=  " and t" + curIndex + "."+condition+" ";
									  }
									  sqlJoinTable+=" @@";
									}
							       /////////
									
	                                       ////////////////////////////////////
											try{
												 Field _dependF=AnnotationUtil.getFiledFromPropertyName(AnnotationUtil
															.resolveClassFullNameByEntityName(entityName), showName);
												 boolean cascade=depend.cascade();
														   if(_dependF.isAnnotationPresent(Depend.class)&&cascade){
															   Depend d = (Depend) _dependF.getAnnotation(Depend.class);
														       String _depend_name=d.name();
														       String _depend_showname=d.showname();
														       String[] _str = parse(_depend_name);
															   String _depend_entityName =_str[0];
															   String _depend_idName = _str[1];
															   
															   String[] __str = parse(_depend_showname);
															   String _depend_showName = __str[1];
															
															   String _depend_depend_talbeName= AnnotationUtil
																		.getTableNameFromEntityName(AnnotationUtil
																				.resolveClassFullNameByEntityName(_depend_entityName));
															   
															   String _depend_showFieldName = AnnotationUtil
																		.convertToFieldName(_depend_showName);
															   
															   String _depend_idFieldName = AnnotationUtil
																		.convertToFieldName(_depend_idName);
															   
															   String _tableIndexKey = _depend_entityName + "_" + _depend_showName;//modify this to gurantee unique
															   if (joinTableMap.get(_tableIndexKey) == null) {
																   curIndex = index;
																   joinTableMap.put(_tableIndexKey, curIndex);
																   index++;
																} else {
																	curIndex = joinTableMap.get(_tableIndexKey);
																}  
															   sqlJoinTable +="left join "
																		+ _depend_depend_talbeName + " t" + curIndex
																		+ "  on " + " t" + curIndex + "."
																		+ _depend_idFieldName;
															   
															   
															
															   curIndex = joinTableMap.get(tableIndexKey);
																	
																
															   sqlJoinTable += "=t"+curIndex+"."+ showFieldName + " ";
															 
												   }
											}catch(Exception e){
												e.printStackTrace();
											}
											////////////////////////////////////
											
								} else if (f.isAnnotationPresent(Dict.class)) {
									Dict dict = (Dict) f
											.getAnnotation(Dict.class);
									String dictname = dict.name();
									
									sqlJoinTable += " left join "
											+ dependTalbeName + " t" + curIndex
											+ "  on " + " t" + curIndex + "."
											+ joinFieldName + "=t0."
											+ selffield + " ";
									
									

									sqlJoinTable += " left join tb_ms_cms_dict td"
											+ curIndex
											+ "  on "
											+ " td"
											+ curIndex
											+ ".key='"
											+ dictname
											+ "' and td"
											+ curIndex
											+ ".code=t"
											+ curIndex + "." + showFieldName;

								}
							} else {
								sqlJoinTable += " left join " + dependTalbeName
										+ " t" + curIndex + "  on " + " t"
										+ curIndex + "." + idFieldName + "=t0."
										+ colName + " $" + occupyIndex + " ";
							}

							occupyMap.put(propName, occupyIndex++);

						} else {
							sqlJoinTable += "$" + occupyIndex;
							occupyMap.put(propName, occupyIndex++);
						}

						if (!multiplevalue) {
							originalPropertyMap.put(propName,
									showField.getType());
							originalPropertyList.add(propName);
						}
						// ///////
						// curIndex++;
						// joinTableMap.put(entityName, curIndex);
						// ////////
					}

					// selField += "t0." + colName + " t0_" + propName + ",";

				} else {
					selField += "t0." + colName + " " + propName + ",";
				}

			}

			// ///////////补充字段，原来的code数据
			// Iterator ittt=originalPropertyMap.keySet().iterator();
			// while(ittt.hasNext()){
			// String propName=(String)ittt.next();
			// String fieldName=AnnotationUtil.convertToFieldName(propName);
			// selField += "t0." + fieldName + " " + "original_"+propName + ",";
			// }
			selField += convertedSelectSql;
			// ///////////////////////////

			selField = selField.substring(0, selField.length() - 1);

//			sqlJoinTable += " where 1=1 ";
//			if (filters.size() > 0) {
//				for (SearchFilter filter : filters) {
//					String filedName = filter.fieldName;
//					SearchFilter.Operator operator = filter.operator;
//					Object value = filter.value;
//
//					String propName = AnnotationUtil
//							.convertToPropName(filedName);
//
//					// ///////////
//					propName = filter.fieldName;
//					filedName = (String) map.get(propName);
//					// ///////////////
//
//					Field f = null;
//					try {
//						f = clazz.getDeclaredField(propName);
//					} catch (Exception e) {
//						f = clazz.getSuperclass().getDeclaredField(propName);
//					}
//
//					String valueCondi = null;
//					if (value instanceof String) {
//						if ("find_in_set".equals(parseOperator(operator)))
//							valueCondi = (String) value;
//						else if ("like".equals(parseOperator(operator)))
//							valueCondi = "'%" + value + "%'";
//						else
//							valueCondi = "'" + value + "'";
//
//					} else if (value instanceof Integer
//							|| value instanceof Double || value instanceof Long
//							|| value instanceof Number) {
//						valueCondi = ""
//								+ Double.parseDouble(String.valueOf(value))
//								+ "";
//					}
//
//					if (f != null && f.isAnnotationPresent(Dict.class)) {
//
//						Dict dict = (Dict) f.getAnnotation(Dict.class);
//						String name = dict.name();
//						String tableIndexKey = name + "_" + propName;
//
//						Integer curIndex = joinTableMap.get(tableIndexKey);
//
//						String sqlJoinTableA = " and t0." + filedName
//								+ parseOperator(operator) + valueCondi + " ";
//
//						sqlJoinTable += sqlJoinTableA;
//						// Integer oIndex=occupyMap.get(propName);
//						// if(oIndex!=null)
//						// sqlJoinTable =
//						// sqlJoinTable.replace("$"+oIndex,sqlJoinTableA);
//					}
//					if (f != null && f.isAnnotationPresent(Depend.class)) {
//						Depend depend = (Depend) f.getAnnotation(Depend.class);
//						String name = depend.name();
//						String showname = depend.showname();
//
//						String[] str = parse(name);
//						String entityName = str[0];
//						String idName = str[1];
//
//						String idFieldName = AnnotationUtil
//								.convertToFieldName(idName);
//
//						String[] str1 = parse(showname);
//						String showName = str1[1];
//						String showFieldName = AnnotationUtil
//								.convertToFieldName(showName);
//
//						String dependTalbeName = AnnotationUtil
//								.getTableNameFromEntityName(AnnotationUtil
//										.resolveClassFullNameByEntityName(entityName));
//
//						String tableIndexKey = entityName + "_" + showName;
//						Integer curIndex = joinTableMap.get(tableIndexKey);
//
//						boolean multiplevalue = depend.multiplevalue();
//						String sqlJoinTableA = "";
//						if (multiplevalue) {
//							if ("find_in_set".equals(parseOperator(operator))) {
//								String[] v = String.valueOf(valueCondi).split(
//										",");
//								List<Predicate> ps = new ArrayList<Predicate>();
//								if (v != null && v.length > 0) {
//									sqlJoinTableA += " and (";
//									for (int i = 0; i < v.length; i++) {
//										sqlJoinTableA += " find_in_set('"
//												+ v[i] + "',t0." + filedName
//												+ ") or";
//									}
//									sqlJoinTableA = sqlJoinTableA.substring(0,
//											sqlJoinTableA.length() - 2);
//									sqlJoinTableA += ")";
//								}
//							} else {
//								sqlJoinTableA = " and t0." + filedName + " "
//										+ parseOperator(operator) + " "
//										+ valueCondi + " ";
//							}
//						} else {
//							// 这里做两个条件 分别是对应关联表的显示字段查询，或者本表的值做查询
//							sqlJoinTableA = " and (t" + curIndex + "."
//									+ showFieldName + " "
//									+ parseOperator(operator) + " "
//									+ valueCondi + " ";
//
//							if (!f.isAnnotationPresent(JoinField.class)) {
//								sqlJoinTableA += " or t0." + filedName + " "
//										+ parseOperator(operator) + " "
//										+ valueCondi;
//							}
//							sqlJoinTableA += " )";
//						}
//
//						sqlJoinTable += sqlJoinTableA;
//
//						// Integer oIndex=occupyMap.get(propName);
//						// if(oIndex!=null)
//						// sqlJoinTable = sqlJoinTable.replace("$"+oIndex,
//						// sqlJoinTableA);
//					} else {
//
//						String sqlJoinTableA = " and t0." + filedName + " "
//								+ parseOperator(operator) + " " + valueCondi
//								+ " ";
//
//						sqlJoinTable += sqlJoinTableA;
//					}
//				}
//			}

			// /////////支持 AND OR 混合查询//////////////////////
			sqlJoinTable += " where 1=1 ";
			if (!StringUtils.isBlank(jsonFilter)
					&& !jsonFilter.contains("search_")
					&& !jsonFilter.contains("null")) {
				ParseTree tree = RuleResolver.parseRule(jsonFilter);

				String whereSql = generateWhere(clazz, tree, 0, joinTableMap,map);

				sqlJoinTable += whereSql;

			} else {
				// 原来的 WHERE begin/////////////////////////////
				if (filters.size() > 0) {
					for (SearchFilter filter : filters) {
						String filedName = filter.fieldName;
						SearchFilter.Operator operator = filter.operator;
						Object value = filter.value;
						sqlJoinTable += generateBasedOnProp(clazz, "and",
								filedName, operator, value, joinTableMap, map);
					}
				}
			}// /WHERE end/////////////////////////////

			if (!StringUtils.isBlank(sqlCondition)) {
				sqlJoinTable += " " + sqlCondition;
			}

			String orderSql = "";
			Sort sort =null;
			if(pageable!=null)
			   sort = pageable.getSort();
			if(sort!=null){
				Iterator<Order> itt = sort.iterator();
				while (itt.hasNext()) {
					Order order = (Order) itt.next();
					Direction d = order.getDirection();
					String p = order.getProperty();
					String fieldName = AnnotationUtil.convertToFieldName(p);
	//				String fieldName = p;
	//				if (d.ASC.equals(d)) {
	//					orderSql += alias +"."+ fieldName + ",";
	//				} else if (d.DESC.equals(d)) {
	//					orderSql += alias +"."+fieldName + " desc,";
	//				}
					
					if (d.ASC.equals(d)) {
						orderSql +=  "t0."+ fieldName + ",";
					} else if (d.DESC.equals(d)) {
						orderSql +=  "t0."+fieldName + " desc,";
					}
					
					
				}
	
				if (!StringUtils.isBlank(orderSql)) {
					orderSql = orderSql.substring(0, orderSql.length() - 1);
					orderSql = " order by " + orderSql;
				}
			}
			
			int pageSize=10;
			int offset=0;
			if(pageable!=null){
				 pageSize=pageable.getPageSize();
				 offset=pageable.getOffset();
			}
			sqlJoinTable = sqlJoinTable.replaceAll("\\$[0-9]*", " ");

			sql = sqlMain + selField + sqlMainFromTable + sqlJoinTable;
			//////////////////////
			if(sql.contains("@@")&&sql.contains("#")){
			  int pos=sql.indexOf("#");
			  String replace=sql.substring(pos+1,sql.lastIndexOf("#"));
			  /////////////////////////修正//////////////////////////////////
			  if(!replace.contains("and")&&!replace.contains("and")&&!replace.contains("and")&&!replace.contains("and")){
				  String tmpSql=sql.substring(0, pos);
				  String tmpSql2=sql.substring(pos+1);
				  
				  List<Integer> l=new ArrayList<Integer>();
				  Integer p1=tmpSql.lastIndexOf("and");
				  Integer p2=tmpSql.lastIndexOf("or");
				  Integer p3=tmpSql.lastIndexOf("AND");
				  Integer p4=tmpSql.lastIndexOf("OR");
				  l.add(p1);l.add(p2);l.add(p3);l.add(p4);
				  Collections.sort(l, new Comparator(){

					@Override
					public int compare(Object o1, Object o2) {
						Integer i1=(Integer)o1;
						Integer i2=(Integer)o2;
						return i2-i1;
					}
					  
				  });
				  
				 Integer p=l.get(0);
				  
				 sql=tmpSql.substring(0,p)+"#"+tmpSql.substring(p)+tmpSql2;
				 int pos2=sql.indexOf("#");
				 replace=sql.substring(pos2+1,sql.lastIndexOf("#"));
			  }
			  /////////////////////////修正//////////////////////////////////
			  
			  sql=sql.replace(replace, "");
			  sql=sql.replace("##", "");
			  sql=sql.replace("@@", replace);
			}else if(sql.contains("@@")&&!sql.contains("#")){
				sql=sql.replace("@@", "");
			}
			///////////////////////
			
					//+ orderSql;
			if(pageSize>0)
			  setPageableSql(" limit "+pageSize+" offset  "+offset);
			else if(pageSize==1 && offset==0) //0,1代表查全部
			  setPageableSql(" ");
			setOrderSql(orderSql);

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		paramList.addAll(0, paramList2);

		return sql;
	}

	public String buildQuery(Map<String, Object> filterParams,
			final Class clazz, Pageable pageable) {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);

		return buildQuery(filters.values(), clazz, pageable);
	}

	public String buildQuery(String jsonFilter, final Class clazz,
			Pageable pageable) {
		Map<String, Object> filterParams = WebUitl.getParametersStartingWith(
				jsonFilter, "search_");

		return buildQuery(filterParams, clazz, pageable);
	}

	private static String[] parse(String str) {
		String str2 = str.replace('.', '/');
		String[] stra = str2.split("/");
		return stra;
	}

	
	private  String generateWhere(final Class clazz,ParseTree tree, int depth,HashMap<String, Integer>  joinTableMap,HashMap map) {
		String text=tree.getText();
		
		for(int i=0;i<depth;i++){
		  System.out.print(" ");
		}
		System.out.print(depth+"->"+text);
		System.out.println();
		
		StringBuffer strBuf=new StringBuffer();
		if(depth==0)
		  strBuf.append(" and (");
		int n = tree.getChildCount();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				ParseTree child = (ParseTree) tree.getChild(i);
				ParseTree prefetchChild = (ParseTree) tree.getChild(i+1);
				
				
				String firstChildText=child.getText();
				String relation="and";
				if("(".equals(firstChildText)){
					String str=generateWhere(clazz,prefetchChild,depth+1,joinTableMap,map);
					strBuf.append(" ("+str+") ");
					break;
				}else if("or".equals(firstChildText)||"OR".equals(firstChildText)||"and".equals(firstChildText)||"AND".equals(firstChildText)){
					relation=firstChildText;
					strBuf.append(" "+firstChildText+" ");
					String subStr=generateWhere(clazz,prefetchChild, depth+1,joinTableMap,map); 
					strBuf.append(" "+subStr+" ");
					break;
				}else if(!text.contains("(")&&text.equals(firstChildText)){
					
					String field=child.getChild(0).getText();
					String op=child.getChild(1).getText();
					String v=child.getChild(2).getText();
					
//					strBuf.append(" "+field+" ");
//					strBuf.append(" "+opp+" ");
//					strBuf.append(" "+vv+" ");
					
					
					strBuf.append(generateBasedOnProp(clazz,"",field,encodeOperator(op),v,joinTableMap,map));
					
					
					break;
				}else{
					String str=generateWhere(clazz,child,depth+1,joinTableMap,map);
					strBuf.append(str);
				}
			}
			if(depth==0)
				  strBuf.append(" )");
			
			
			return strBuf.toString();
		} else {
			Object v = tree.getText();
			return String.valueOf(v);
		}
	}
	
	private static SearchFilter.Operator encodeOperator(String str) {
		switch (str) {
		case "=":
		case "==":
			return SearchFilter.Operator.EQ;
		case "!=":
			return SearchFilter.Operator.NE;
		case "in":
		case "IN":
			return SearchFilter.Operator.IN;
		case "is null":
		case "is NULL":
			return SearchFilter.Operator.ISNULL;
		case "like":
		case "LIKE":
			return SearchFilter.Operator.LIKE;
		//case "LIKE":
		//	return SearchFilter.Operator.LIKEIGNORE;
		case ">":
			return SearchFilter.Operator.GT;
		case "<":
			return SearchFilter.Operator.LT;
		case ">=":
			return SearchFilter.Operator.GTE;
		case "<=":
			return SearchFilter.Operator.LTE;
		case  "find_in_set":
		case  "FIND_IN_SET":
			return SearchFilter.Operator.FINDINSET;

		}
		return null;
	}
	
	private static String parseOperator(SearchFilter.Operator operator) {
		switch (operator) {
		case EQ:
			return "=";
		case NE:
			return "!=";
		case IN:
			return "in";
		case ISNULL:
			return "is null";
		case LIKE:
			return "like";
		case LIKEIGNORE:
			return "like";
		case GT:
			return ">";
		case LT:
			return "<";
		case GTE:
			return ">=";
		case LTE:
			return "<=";
		case FINDINSET:
			return "find_in_set";

		}
		return null;
	}

	
	public class testClass{
		
		private java.util.Date d;
		
		private String s;
		
		
		
		
	}
	public static void main(String[] args) {
		
		
		try {
			Class clazz=Class.forName("com.xa3ti.aiya.base.util.QueryBuilder$testClass");
			
			Field[] fs=clazz.getDeclaredFields();
			for(Field f:fs){
				System.out.println(f.getType());
				System.out.println(f.getDeclaringClass());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
}
