/**
 * 
 */
package com.xa3ti.blackcat.base.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

/**
 * @author nijie
 *
 */
public class JsonUtil {
	//null的JSON序列  
//    public class NullSerializer extends JsonSerializer<Object> {
//
//		/* (non-Javadoc)
//		 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
//		 */
//		@Override
//		public void serialize(Object value, JsonGenerator jgen,
//				SerializerProvider provider) throws IOException,
//				JsonProcessingException {
//			
//		            jgen.writeString("");  
//		     
//			
//		}  
//       
//    }  
//    
//	
//	public class MyDateFormat extends DateFormat{
//		
//		
//		/* (non-Javadoc)
//		 * @see java.text.DateFormat#format(java.util.Date, java.lang.StringBuffer, java.text.FieldPosition)
//		 */
//		@Override
//		public StringBuffer format(Date date, StringBuffer toAppendTo,
//				FieldPosition fieldPosition) {
//			StringBuffer strBuf=new StringBuffer();
//			if(date!=null){
//				if(date instanceof java.sql.Time){
//					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//					strBuf.append(dateFormat.format(date));
//					return  strBuf;
//				}else if(date instanceof java.util.Date){
//					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					strBuf.append(dateFormat.format(date));
//					return  strBuf;
//				}
//			}
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			strBuf.append(dateFormat.format(date));
//			return  strBuf;
//		}
//
//		/* (non-Javadoc)
//		 * @see java.text.DateFormat#parse(java.lang.String, java.text.ParsePosition)
//		 */
//		@Override
//		public Date parse(String source, ParsePosition pos) {
//			// TODO Auto-generated method stub
//			return (Date)super.parseObject(source, pos);
//		}
//	}
//	
//	
//	
//	private static final String DEFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";  
//    private static final ObjectMapper mapper;  
//  
//    static {  
//        //SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);  
//        
//        mapper = new ObjectMapper();  
//        mapper.setDateFormat(new JsonUtil().new MyDateFormat());  
//        mapper.getSerializerProvider().setNullValueSerializer(new JsonUtil().new NullSerializer());
//        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
//        
//        
//    }  
	private static  SerializerFeature[] features = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect};
	    
    private static SerializeConfig mapping = new SerializeConfig(); 
    private static ValueFilter filter = new ValueFilter() {
	    @Override
	    public Object process(Object obj, String s, Object v) {
	    if(v==null)
	        return "";
	    else if(v instanceof Time){
	    	 Date date = (Date) v;
	         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	         String text = format.format(date);
	         return text.substring(11);
	    }
	    return v;
	    }
	};
	
    static { 
    	mapping.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); 
        mapping.put(Time.class, new SimpleDateFormatSerializer("HH:mm:ss")); 
    } 
      
    public static String toJson(Object obj) {  
        try {  
//        	mapping.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
//            mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); 
//            mapping.put(Time.class, new SimpleDateFormatSerializer("HH:mm:ss")); 
//        	System.out.println("*************************************************************");
//        	System.out.println("*************************************************************");
//        	System.out.println("*************************************************************");
//        	System.out.println("*************************************************************");
//        	System.out.println("*************************************************************");
//        	System.out.println("*************************************************************");
//        	System.out.println("*************************************************************");
//        	System.out.println("*************************************************************");
//        	System.out.println("*************************************************************");
        	//System.out.println("*************************************************************");
        	//String json=JSON.toJSONString(obj, mapping,features);
        	String json=JSON.toJSONString(obj, filter,features);
        	
        	//System.out.println(json);
        	//System.out.println("*************************************************************");
        	return json;
            //return mapper.writeValueAsString(obj);  
        } catch (Exception e) {  
        	e.printStackTrace();
            throw new RuntimeException("转换json字符失败!");  
        }  
    }  
      
//    public <T> T toObject(String json,Class<T> clazz) {  
//        try {  
//            return mapper.readValue(json, clazz);  
//        } catch (IOException e) {  
//        	e.printStackTrace();
//            throw new RuntimeException("将json字符转换为对象时失败!");  
//        }  
//    }  

    public class StringSerializer implements ObjectSerializer{

		/* (non-Javadoc)
		 * @see com.alibaba.fastjson.serializer.ObjectSerializer#write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type)
		 */
		@Override
		public void write(JSONSerializer serializer, Object object,
				Object fieldName, Type fieldType) throws IOException {
			if (object == null) {
	    		serializer.getWriter().write("");
	    		return;
	    	}
			
			//serializer.write(object);
		}
    	
    }
    
    public class A{
    	Date a;
    	String b;
		public Date getA() {
			return a;
		}
		public void setA(Date a) {
			this.a = a;
		}
		public String getB() {
			return b;
		}
		public void setB(String b) {
			this.b = b;
		}
    	
    	
    	
    }
    
    public static void main(String[] args){
       SerializerFeature[] features = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect};
    	HashMap obj=new HashMap();
    	
    	obj.put("a", "a");
    	obj.put("b", new ArrayList());
    	obj.put("c", null);
    	obj.put("d", new Date());
    	obj.put("e", new Time(new Date().getTime()));
    	obj.put("f", new Timestamp(new Date().getTime()) );
    	
    	
    	try {
//    	Class clazz=Class.forName("com.xa3ti.aiya.base.util.A");
//    	Object entity = clazz.newInstance();
//    	ClassUtil classUtil=new ClassUtil();
//    	Object dyEntity=null;
//    	
//    		dyEntity=classUtil.dynamicClass(entity);
		
    	
    	ValueFilter filter = new ValueFilter() {
    	    @Override
    	    public Object process(Object obj, String s, Object v) {
    	    if(v==null)
    	        return "";
    	    else if(v instanceof Time){
    	    	 Date date = (Date) v;
    	         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	         String text = format.format(date);
    	         return text.substring(11);
    	    }
    	    return v;
    	    }
    	};
    	
    	 //mapping.put(String.class, new JsonUtil().new StringSerializer());
    	 
    	String json=JSON.toJSONString(obj, filter,features);
    	System.out.println(json);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
