/**
 * 
 */
package com.xa3ti.blackcat.base.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.business.DataDict.CacheDict;
import com.xa3ti.blackcat.business.DataDict.Dict;
import com.xa3ti.blackcat.business.DataDict.DictMeta;
import com.xa3ti.blackcat.business.service.ConstantService;

/**
 * @author nijie
 *
 */
@Controller
@RequestMapping("dict")
public class XaCmsDictController extends BaseController {

	@Autowired
	CacheDict cacheDict;
	
	@Autowired
	ConstantService constantService;
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="{key}",method=RequestMethod.GET)
	public List<Dict> getDictByKey(@PathVariable String key) throws BusinessException{
		return cacheDict.getDictByKey(key);
	}
	
	/**
	 * 
	 * @param code
	 * @param key
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="code/{code}",method=RequestMethod.GET)
	public List<Dict> getSubDictByCode(@PathVariable String code,
			@RequestParam String key) throws BusinessException{
		return cacheDict.getSubDictByCode(key,code);
	}
	
	/**
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="dictmeta",method=RequestMethod.GET)
	public Collection<DictMeta> getDictMeta() throws BusinessException{
		return cacheDict.getAllDictMeta().values();
	}
	
	
	/**
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="constant",method=RequestMethod.GET)
	public XaResult<HashMap> getConstant()throws BusinessException {
		return constantService.getConstant();
	}
}
