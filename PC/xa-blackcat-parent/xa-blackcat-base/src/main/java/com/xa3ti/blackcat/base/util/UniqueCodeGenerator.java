/**
 * 
 */
package com.xa3ti.blackcat.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.codec.Base64;

import com.google.common.primitives.Longs;

/**
 * @author nijie
 *
 */
public class UniqueCodeGenerator {
    public static final String PREFIX="G6H6J2";
    public static final String SUFFIX="JTFQ";
	/**
	 * 基本思想：64位编码  前56位为数据位 后8位为校验位，数据位%2的8次方 作为校验位
	 * 算法：采用RC4密码算法（私钥“8976501f8451f03c5c4067b47882f2e5”） 把56位数据位加密，然后该56位数据位的值 mod 2的8次方 作为后8位补上
	 * 然后用base64，base24 两次编码 将64位的数据 隐射到 字符串 
	 * decode 算法 可逆
	 * @param base
	 * @return
	 */
	public static String encode(long base) {
		byte[] a = Longs.toByteArray(base);
		//System.out.println(a.length);
		byte[] b = new byte[7];
		for (int i = 0; i < a.length; i++) {
			if (i > 0)
				b[i - 1] = a[i];

			//System.out.printf("%02X", a[i]);
		}

		//System.out.println();
		//for (int i = 0; i < b.length; i++) {
		//	System.out.printf("%02X", b[i]);
		//}

		String str = new String(b);

		try {
			//System.out.println("\nencode===");
			//for (int i = 0; i < b.length; i++) {
			//	System.out.printf("%02X", b[i]);
			//}
			//System.out.println("\nafter encode===");
			byte[] byte56 = RC4.encrypt(b);
			//for (int i = 0; i < byte56.length; i++) {
			//	System.out.printf("%02X", byte56[i]);
			//}

			long value = 0;
			for (int i = 0; i < byte56.length; i++) {
				value += ((long) byte56[i] & 0xffL) << (8 * i);
			}

			long bb = 256;

			long d = value % 256;

			byte[] c = Longs.toByteArray(d);
			//System.out.println();
			//for (int i = 0; i < c.length; i++) {
			//	System.out.printf("%02X", c[i]);
			//}

			byte mod = c[7];

			byte[] f = new byte[8];

			for (int i = 0; i < b.length; i++) {
				f[i] = b[i];
			}
			f[7] = mod;
			//System.out.println();
			//for (int i = 0; i < f.length; i++) {
			//	System.out.printf("%02X", f[i]);
			//}
			//System.out.println();
			String finalStr = new String(Base64.encode(f));
			String ff=Base24.Encode(finalStr.getBytes());
			//System.out.println("finalStr--->" + finalStr);
			return ff;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static long decode(String code) {
        String decode24=Base24.Decode(code);
		byte[] a = Base64.decode(decode24.getBytes());
		//System.out.println();
		//for (int i = 0; i < a.length; i++) {
		//	System.out.printf("%02X", a[i]);
		//}
		//System.out.println();
		byte[] b = new byte[7];
		for (int i = 0; i < 7; i++) {
			b[i] = a[i];
		}
		//System.out.println("\ndecode====");
		//for (int i = 0; i < b.length; i++) {
		//	System.out.printf("%02X", b[i]);
		//}
		byte[] deByte = RC4.decrypt(b, 0, b.length);
		//System.out.println("\nafter decode====");
		//for (int i = 0; i < deByte.length; i++) {
		//	System.out.printf("%02X", deByte[i]);
		//}
		//System.out.println();

		long value = 0;
		for (int i = 0; i < deByte.length; i++) {
			value = (value << 8) + (deByte[i] & 0xff);
		}

		return value;
	}

	
	public static List<Long> getRand(int num,long startNumber){
		HashMap<Long,String> map=new HashMap<Long,String>();
		//Long l=992015122612493036l;
		//System.out.println(l.toString());
		//String s=DateUtil.formatDate(new Date(), "yyyyMmddHHmmss");
		
		
		
		int j=0;
		while(j<num){
			String s=StringUtils.leftPad(String.valueOf(startNumber+j), 10,"0");
			int r1=0;
			int r2=(int)(Math.random()*1000);
			
			String ss=r1+s+r2;
			StringBuffer sb=new StringBuffer();
			List<String> list=new ArrayList<String>();
			for(int i=0;i<ss.length();i++){
				list.add(String.valueOf(ss.charAt(i)));
			}
			//Collections.shuffle(list);
			for(String oneStr:list){
				sb.append(oneStr);
			}
			Long key=Long.parseLong(sb.toString());
			if(map.get(key)==null){
			 map.put(key, "1");
			 j++;
			}
			
		}
		List<Long> li=new ArrayList<Long>();
		Iterator it=map.keySet().iterator();
		while(it.hasNext()){
			Long ll=(Long)it.next();
			li.add(ll);
		}
		
		
		return li;
	}
	
	public static HashMap<String,Long> generate(long start,int num) {
		List<Long> list=getRand(num,start);
		HashMap map=new HashMap();
		for(Long l:list){
	       // System.out.println(l);
			String str = encode(l);
		   //System.out.println(str);
			long a = decode(str);
			//System.out.println(a);
			if(map.get(str)==null)
			  map.put(str, a);
			else
			  System.out.println("Duplicate");
		}
		
		return map;
	}
	
	
	public static String generateOne(long start) {
		List<Long> list=getRand(1,start);
		HashMap map=new HashMap();
		for(Long l:list){
			//System.out.println(l);
			String str = encode(l);
			return str;
		}
		return null;
	}
	
	
	private  static Long f(Long l){
		Long ll=l*(78910111213l)+5000l;
				return ll;
	}
	
	private static Long df(Long l){
		Long ll=(l-5000)/78910111213l;
		return ll;
	}
	
	public static String Encode(Long l){
		return encode(f(l));
	}
	
	public static Long Decode(String s){
		return df(decode(s));
	}
	
	public static void main(String[] args){
//		for(Long i=1l;i<100000;i++){
//			String s=encode(f(i));
//			
//			
//			System.out.println(s);
//			
//			Long l=decode(s);
//			
//			System.out.println(df(l));
//		}
		
		Long l=decode("asdfqwef3qqfwe");
		
	}

}
