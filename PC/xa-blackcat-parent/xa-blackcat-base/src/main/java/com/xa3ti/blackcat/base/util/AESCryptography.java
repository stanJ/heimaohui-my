/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.util;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author nijie
 *
 */
public class AESCryptography {
	
	private static String TRANSFORM = "AES/CBC/PKCS5Padding";
    private static String ALGORITHM = "AES";
    private SecretKeySpec secretKeySpec;
    
    private static String base64EncodedKey="7OLxM6hcEyxChDFOhCbbQw";
    private byte[] initialVector  = { 0x0a, 0x0d, 0x05, 0x03, 0x04, 0x0b, 0x0e, 0x0d,0x0a, 0x01, 0x02, 0x03, 0x07, 0x0b, 0x0c, 0x0d };
       
    public AESCryptography(String base64EncodedKey) {
        secretKeySpec = new SecretKeySpec(Base64.decodeBase64(base64EncodedKey), "AES");
    }
    
    public AESCryptography() {
        secretKeySpec = new SecretKeySpec(Base64.decodeBase64(base64EncodedKey), "AES");
    }
    
    public byte[] encrypt(byte[] inputBytes) throws Exception {
        try {   
            Cipher cipher = Cipher.getInstance(AESCryptography.TRANSFORM);
            IvParameterSpec ips = new IvParameterSpec(initialVector);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ips);
            return cipher.doFinal(inputBytes);
        } catch (Exception e) { 
            throw e;
        }
    }

    public byte[] decrypt(byte[] inputBytes) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(AESCryptography.TRANSFORM);
            IvParameterSpec ips = new IvParameterSpec(initialVector);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ips);
            return cipher.doFinal(inputBytes);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static byte[] generateBase64Key() {
        try {
            KeyGenerator  keyGenerator  = KeyGenerator.getInstance(AESCryptography.ALGORITHM);
            SecretKey     key           = keyGenerator.generateKey();
            return Base64.encodeBase64(key.getEncoded(),false,true);
        } catch (NoSuchAlgorithmException e) {
             return null;
        }
    }
    
    public static String byte2String(byte[] buff) {
		StringBuffer sbuf = new StringBuffer();
		for (int i = 0; i < buff.length; i++) {
			int tmp = buff[i] & 0XFF;
			String str = Integer.toHexString(tmp);
			if (str.length() == 1) {
				sbuf.append("0" + str);
			} else {
				sbuf.append(str);
			}

		}
		return sbuf.toString();
	}

	public static byte[] String2byte(String str) {
		byte[] result = new byte[str.length() / 2];
		int index = 0;
		for (int i = 0; i < str.length(); i += 2) {
			result[index++] = (byte) Integer.parseInt(str.substring(i, i + 2),
					16);
		}
		return result;
	}
    
    public static void main(String[] unused) throws Exception {
        String secretKey = new String(AESCryptography.generateBase64Key());
        System.out.println(secretKey);
        AESCryptography crypto = new AESCryptography(secretKey);
        String md5=MD5Util.getMD5String("123456");
        byte[] dataBytes =
        		md5.getBytes("utf-8");

        byte[] encBytes = crypto.encrypt(dataBytes);
        System.out.println(encBytes);
        String ss=byte2String(encBytes);
        System.out.println(ss);
        byte[] dncBytes = String2byte(ss);
        byte[] decBytes = crypto.decrypt(dncBytes);
        System.out.println(new String(decBytes));
    }

}
