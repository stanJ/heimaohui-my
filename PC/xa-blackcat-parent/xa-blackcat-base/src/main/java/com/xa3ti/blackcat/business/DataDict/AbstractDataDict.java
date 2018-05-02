package com.xa3ti.blackcat.business.DataDict;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataDict {
	protected List<Integer> valueList = new ArrayList<Integer>();
	
	public List<DataDict> getDataList(){
		List<DataDict> data = new ArrayList<DataDict>();
		for(Integer index : this.getValueList()){
			DataDict dd = new DataDict();
			dd.setName(this.getNameByValue(index));
			dd.setValue(index);
			data.add(dd);
		}
		return data;
	}

	protected List<Integer> getValueList(){
		return valueList;
	}
	
	public String getNameByValue(Integer index){
		return "";
	}
}
