/**
 * 
 */
package com.xa3ti.blackcat.base.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xa3ti.blackcat.base.entity.XaCmsDict;
import com.xa3ti.blackcat.base.entity.XaCmsDictMeta;
import com.xa3ti.blackcat.base.entity.XaCmsResource;
import com.xa3ti.blackcat.base.entity.XaCmsRole;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.repository.XaCmsDictMetaRepository;
import com.xa3ti.blackcat.base.repository.XaCmsDictRepository;
import com.xa3ti.blackcat.base.repository.XaCmsRoleRepository;
import com.xa3ti.blackcat.base.service.XaCmsDictService;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.business.DataDict.CacheDict;

/**
 * @author nijie
 *
 */
@Service("xaCmsDictService")
public class XaCmsDictServiceImpl implements XaCmsDictService {

	private static final Logger log=Logger.getLogger(XaCmsRoleServiceImpl.class);
	@Autowired
	private XaCmsDictRepository xaCmsDictRepository;
	
	@Autowired
	private XaCmsDictMetaRepository xaCmsDictMetaRepository;
	
	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.base.service.XaCmsDictService#findXaCmsDictByConditon(java.util.Map, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Iterable<XaCmsDict> findXaCmsDictByConditon(
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		filters.put("status", new SearchFilter("status", Operator.EQ, CacheDict.DICT_STATUS_VALID));
		Iterable<XaCmsDict> iterable = xaCmsDictRepository.findAll(DynamicSpecifications.bySearchFilter(filters.values(), XaCmsDict.class));
		return iterable;
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.base.service.XaCmsDictService#findAllKeys(int)
	 */
	@Override
	public List<String> findAllKeys(int status) {
		return xaCmsDictRepository.findAllKeys(status);
	}

	/* (non-Javadoc)
	 * @see com.xa3ti.blackcat.base.service.XaCmsDictService#findXaCmsDictMetaByConditon(java.util.Map, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Iterable<XaCmsDictMeta> findXaCmsDictMetaByConditon(
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		Iterable<XaCmsDictMeta> iterable = xaCmsDictMetaRepository.findAll(DynamicSpecifications.bySearchFilter(filters.values(), XaCmsDictMeta.class));
		return iterable;
	}

}
