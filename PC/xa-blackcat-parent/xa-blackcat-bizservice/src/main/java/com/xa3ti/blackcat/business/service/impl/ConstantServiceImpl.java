/**
 * 
 */
package com.xa3ti.blackcat.business.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.business.DataDict.CacheDict;
import com.xa3ti.blackcat.business.DataDict.Dict;
import com.xa3ti.blackcat.business.service.ConstantService;
import com.xa3ti.blackcat.business.util.BizConstant;


/**
 * @author nijie
 *
 */
@Service("ConstantService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ConstantServiceImpl implements ConstantService {
	@Autowired
	CacheDict cacheDict;
	
	
	
	
	/* (non-Javadoc)
	 * @see com.xa3ti.aiya.business.service.ConstantService#getConstant()
	 */
	@Override
	public XaResult<HashMap> getConstant() throws BusinessException {
		XaResult<HashMap> xa=new  XaResult<HashMap>();
		 BizConstant c=new BizConstant();
		
		 
		 
		 HashMap map=new HashMap();
		 for(String key:c.usedDict){
			 List<Dict> dictList=cacheDict.getDictByKey(key);
			 HashMap dictMap=new HashMap();
			 for(Dict dict:dictList){
				 dictMap.put("code_"+dict.getCode(), dict);
			 }
			 
			 
			 map.put(key, dictMap);
		 }
		 
		 HashMap finalMap=new HashMap();
		 finalMap.put("constant", c.getAllConstants());
		 finalMap.put("dict", map);
		 finalMap.put("picServerHost", Settings.getInstance().getString("pic.server.host"));
		 finalMap.put("ueeditorPicPath", Settings.getInstance().getString("storage.ueeditor.path"));
		 
		 //finalMap.put("menuMacroList", h5ResourceTemplateService.listMenuMacro());
		 
		 
		 
		 xa.setObject(finalMap);
		
		 return xa;   
	}

}
