/**
 * 
 */
package com.xa3ti.blackcat.business.DataDict;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import jodd.util.PropertiesUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.thirdparty.easemob.comm.PropertiesUtils;
import com.xa3ti.blackcat.base.entity.XaCmsDict;
import com.xa3ti.blackcat.base.entity.XaCmsDictMeta;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.service.XaCmsDictService;
import com.xa3ti.blackcat.base.service.impl.XaCmsRoleServiceImpl;
import com.xa3ti.blackcat.base.util.SearchFilter;

/**
 * @author nijie
 *
 */
@Component("cacheDict")
@Scope("singleton")
public class CacheDict {
	private static final Logger log=Logger.getLogger(CacheDict.class);
	public static final int DICT_STATUS_VALID=1;
	
	@Autowired
	XaCmsDictService xaCmsDictService;
	

	
	/**
     *      所有的数据字典类型  
     */
    public static List<String> allTypes = new ArrayList<String>();
    /**
     *  所有类型，对应的数据字典项
     */
    public static Map<String, List<Dict>> dictList = new HashMap<String,List<Dict>>();
            
    /**
     * 类型与字典项  由List 转成Map（key,value）
     */
    public static Map<String, Map<String,String>> dictMap = new HashMap<String,Map<String,String>>();
    
    public static Map<String, DictMeta> dictMetaMap = new HashMap<String,DictMeta>();
    
    
    /**
     * 内部缓存类
     */
    public static Map<String, List<Dict>> cacheDictList = new HashMap<String,List<Dict>>();
    
    private static Boolean lazy=false;
    
    static{
    	try{
    	    //lazy=new Boolean(PropertiesUtils.getProperties("dict.properties").getProperty("lazy"));
    	  }catch(Exception e){
    		
    	}
    }
   
    
    /**
     * 加载所有字典数据
     */
    @PostConstruct
    public void loadAllDict(){
    	if(!lazy){
	    	
	    	try {
	    		allTypes=xaCmsDictService.findAllKeys(DICT_STATUS_VALID);
	    		
	    		
	    		for(String key:allTypes){
	    			try{
			    			Map m=new HashMap();
			    			m.put("EQ"+SearchFilter.SPLIT_CHAR+"key", key);
			    			Iterable<XaCmsDictMeta> metaIterable=xaCmsDictService.findXaCmsDictMetaByConditon(m, null);
			    			Iterator metaIt=metaIterable.iterator();
			    			while(metaIt.hasNext()){
			    				XaCmsDictMeta xaDictMeta=(XaCmsDictMeta)metaIt.next();
			    				DictMeta dictMeta=new DictMeta();
			    				BeanUtils.copyProperties(dictMeta, xaDictMeta);
								
			    				if(!StringUtils.isBlank(xaDictMeta.getParentKey())){
			    					DictMeta pDictMeta=dictMetaMap.get(xaDictMeta.getParentKey());
			    					if(pDictMeta==null){
				    					Map mm=new HashMap();
				    	    			mm.put("EQ"+SearchFilter.SPLIT_CHAR+"key", xaDictMeta.getParentKey());
				    	    			Iterable<XaCmsDictMeta> pIterable=xaCmsDictService.findXaCmsDictMetaByConditon(mm, null);
				    	    			Iterator pIt=pIterable.iterator();
				    	    			while(pIt.hasNext()){
				    	    				XaCmsDictMeta xaDictParentMeta=(XaCmsDictMeta)pIt.next();
				    	    				pDictMeta=new DictMeta();
				    	    				BeanUtils.copyProperties(pDictMeta, xaDictParentMeta);
				    	    				dictMeta.setParent(pDictMeta);
				    	    				pDictMeta.setChild(dictMeta);
				    	    				break;
				    	    			}
			    					}else{
			    						dictMeta.setParent(pDictMeta);
			    	    				pDictMeta.setChild(dictMeta);
			    					}
			    	    			
			    				}
			    				dictMetaMap.put(key, dictMeta);
			    				break;
			    			}
			    			
	    			} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			
	    			Map map=new HashMap();
	    			map.put("EQ"+SearchFilter.SPLIT_CHAR+"key", key);
	    			Iterable<XaCmsDict> iterable=xaCmsDictService.findXaCmsDictByConditon(map, null);
					Iterator it=iterable.iterator();
					List<Dict> list=new ArrayList<Dict>();
					while(it.hasNext()){
						XaCmsDict xaDict=(XaCmsDict)it.next();
						
						/*String thisDictCode=xaDict.getCode();
						int isLeaf=xaDict.getIsLeaf();
						if(isLeaf!=1){
							Map map2=new HashMap();
			    			map.put("EQ_parentCode", thisDictCode);
			    			Iterable<XaCmsDict> iterable2=xaCmsDictService.findXaCmsDictByConditon(map2, null);
			    			Iterator it2=iterable2.iterator();
			    			while(it2.hasNext()){
			    				XaCmsDict xaDict2=(XaCmsDict)it2.next();
								String thisDictCode2=xaDict2.getCode();
			    			
			    			}
						}
						*/
		    			
						Dict dict=new Dict();
						
					    try {
							BeanUtils.copyProperties(dict, xaDict);
							
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						
						list.add(dict);
					}
					dictList.put(key, list);
					
					HashMap<String,String> dMap=new HashMap<String,String>();
					for(Dict dict:list){
						dMap.put(dict.getCode(), dict.getValue());
					}
					dictMap.put(key, dMap);
					
					
	    		}
	    		
			} catch (BusinessException e) {
				log.error(e.getMessage());
			}
    	}
    }
    
    
    @PreDestroy
    public void unloadAllDict(){
    	allTypes.clear();
    	dictList.clear();
    	dictMap.clear();
    }
    
    
    public static List<String> getAllTypes(){
    	return allTypes;
    }
    
    public static Map<String,DictMeta> getAllDictMeta(){
    	return dictMetaMap;
    }
    
    public static DictMeta getDictMetaByKey(String key){
    	return dictMetaMap.get(key);
    }
    
    public static List<Dict> getDictByKey(String key){
    	return dictList.get(key);
    }
    
    public static String getDictValueByKeyCode(String key,String code){
    	HashMap<String,String> dMap= (HashMap<String,String> )dictMap.get(key);
    	if(dMap!=null){
    		return dMap.get(code);
    	}
    	return null;
    }
    
    
    public List<Dict> getSubDictByCode(String key,String code){
    	List<Dict> tmpList=cacheDictList.get(key+"_"+code);
    	if(tmpList!=null)
    		return tmpList;
    	else{
    	    Map map=new HashMap();
			map.put("EQ_parentCode", code);
			Iterable<XaCmsDict> iterable = null;
			try {
				iterable = xaCmsDictService.findXaCmsDictByConditon(map, null);
				Iterator it=iterable.iterator();
				List<Dict> list=new ArrayList<Dict>();
				while(it.hasNext()){
					XaCmsDict xaDict=(XaCmsDict)it.next();
					Dict dict=new Dict();
					
				    try {
						BeanUtils.copyProperties(dict, xaDict);
						
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					list.add(dict);
				}
				cacheDictList.put(key+"_"+code, list);
		    	return list;
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
    	}
    }
    
}
