package com.xa3ti.blackcat.business.DataDict;

import java.util.List;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserTypeData type=new UserTypeData();
		List<DataDict> list=type.getDataList();
		for(DataDict dict:list){
			System.out.println("name:"+dict.getName());
			System.out.println("value:"+dict.getValue());
		}
	}

}
