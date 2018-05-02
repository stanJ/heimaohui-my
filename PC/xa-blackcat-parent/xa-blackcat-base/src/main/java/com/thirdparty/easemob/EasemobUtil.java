package com.thirdparty.easemob;

import java.util.UUID;

public class EasemobUtil {
	public static String getEasMobAccount(String userID)
	{
		return EncryptionHelp.MD5(UUID.randomUUID().toString()).toUpperCase();
		//return EncryptionHelp.MD5("B5E" + userID + "test").toUpperCase();
	}
	
	public static String getEasMobPassword(String userID)
	{
		return EncryptionHelp.MD5(userID).toUpperCase();
		//return password;
	}

	public static void main(String[] strs){
		System.out.println(getEasMobPassword("724"));
		//EasMobProcess.registEasMobSimpleUser("008DB43F50CAF376AB53C84FF91351AF", "8248A99E81E752CB9B41DA3FC43FBE7F");
	}
}
