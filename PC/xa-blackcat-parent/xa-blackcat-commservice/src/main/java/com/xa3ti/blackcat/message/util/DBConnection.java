/**
 * 
 */
package com.xa3ti.blackcat.message.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.xa3ti.blackcat.base.util.Settings;



/**
 * @author nijie
 *
 */
public class DBConnection {
	
	static public Connection conn = null;
	static String url = Settings.getInstance().getDbString("jdbc.url");
	static String username = Settings.getInstance().getDbString("jdbc.username");
	static String password = Settings.getInstance().getDbString("jdbc.password");
	
	public static void getConnection() throws Exception{
		try{
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url, username, password);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}	
	}
	
	
	public static void closeConnection() throws Exception{
		try{
			if(conn!=null)
				conn.close();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}	
	}

}
