/**
 * 
 */
package com.xa3ti.blackcat.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xa3ti.blackcat.base.entity.XaCmsDict;
import com.xa3ti.blackcat.base.entity.XaCmsDictMeta;
import com.xa3ti.blackcat.base.entity.XaCmsRole;
import com.xa3ti.blackcat.base.exception.BusinessException;

/**
 * @author nijie
 *
 */
public interface XaCmsDictService {
	
	/**
	 * 根据条件查询字典
	 * @param filterParams
	 * @param pageable
	 * @return
	 * @throws BusinessException
	 */
	Iterable<XaCmsDict> findXaCmsDictByConditon(Map<String, Object> filterParams,Pageable pageable) throws BusinessException;
	
	/**
	 *查询所有的字典key
	 * @param status
	 * @return
	 */
	List<String>  findAllKeys(int status);
	
	
	/**
	 * 根据条件查询字典元数据
	 * @param filterParams
	 * @param pageable
	 * @return
	 * @throws BusinessException
	 */
	Iterable<XaCmsDictMeta> findXaCmsDictMetaByConditon(Map<String, Object> filterParams,Pageable pageable) throws BusinessException;
	

}
