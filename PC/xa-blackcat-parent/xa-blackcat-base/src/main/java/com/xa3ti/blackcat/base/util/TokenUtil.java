/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;


/**
 * @author nijie
 *
 */
public class TokenUtil {

    
   private static Logger logger = Logger.getLogger(TokenUtil.class);

   
   public static String generateToken(String userId){
	
	   String value = System.currentTimeMillis()+new Random().nextInt()+""+userId;  
       //获取数据指纹，指纹是唯一的  
       try {  
           MessageDigest md = MessageDigest.getInstance("md5");  
           byte[] b = md.digest(value.getBytes());//产生数据的指纹  
           //Base64编码  
           BASE64Encoder be = new BASE64Encoder();  
           be.encode(b);  
           return be.encode(b);//制定一个编码  
       } catch (NoSuchAlgorithmException e) {  
           e.printStackTrace();  
       }   
       
       return null;
   }
    
  

   public static void main(String[] args) {
	   
	   
   }


}
