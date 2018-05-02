package com.xa3ti.blackcat.business.DataDict;

import java.util.List;

public class UserTypeData extends AbstractDataDict {
	public static final int USER_ADMIN = 1;
	public static final int USER_USER = 2;
	@Override
	protected List<Integer> getValueList() {
		valueList.add(USER_ADMIN);
		valueList.add(USER_USER);
		return super.getValueList();
	}
	
	@Override
	public String getNameByValue(Integer index) {
		if(index == null) return null;
		switch(index){
		case USER_ADMIN:
			return "管理员";
		case USER_USER:
			return "普通用户";
		default:
			return "";
		}
	}
	
	
	
}
 