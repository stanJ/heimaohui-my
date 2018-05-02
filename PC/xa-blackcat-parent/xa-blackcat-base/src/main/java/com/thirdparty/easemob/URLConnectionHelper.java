package com.thirdparty.easemob;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class URLConnectionHelper {
//	 private static final String SERVLET_POST = "POST" ;
//     private static final String SERVLET_GET = "GET" ;
//     private static final String SERVLET_DELETE = "DELETE" ;
     //private static final String SERVLET_PUT = "PUT" ;
         
//	 public static String sendGet(String url, Map<String, String> httpHeads, String params) {
//	        String result = "";
//	        BufferedReader in = null;
//	        
//	        try {
//	            String urlName = url + "?" + params;
//	            URL realUrl = new URL(urlName);
//	            // 打开和URL之间的连接
//	            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
//	            for (Map.Entry<String, String> obj : httpHeads.entrySet()) {
//	            	conn.setRequestProperty(obj.getKey(), obj.getValue());
//				}
//
//	            conn.setRequestMethod(SERVLET_GET);
//	            
//	            // 设置通用的请求属性
//	            conn.setRequestProperty("accept", "*/*");
//	            conn.setRequestProperty("connection", "Keep-Alive");
//	            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//	 
//	            
//	            conn.connect();
//	            // 获取所有响应头字段
//	 
//	            Map<String, List<String>> map = conn.getHeaderFields();
//	            // 遍历所有的响应头字段
//	            for (String key : map.keySet()) {
//	                System.out.println(key + "--->" + map.get(key));
//	            }
//	 
//	            // 定义BufferedReader输入流来读取URL的响应
//	            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	            String line;
//	            while ((line = in.readLine()) != null) {
//	                result += "\n" + line;
//	            }
//	        } catch (Exception e) {
//	            System.out.println("发送GET请求出现异常！" + e);
//	            e.printStackTrace();
//	        }
//	        
//	        // 使用finally块来关闭输入流
//	        finally {
//	            try {
//	                if (in != null) {
//	                    in.close();
//	                }
//	            } catch (IOException ex) {
//	                ex.printStackTrace();
//	            }
//	        }
//	        return result;
//	   }
	 
	 public static String sendGet(String url,Map<String,String> httpHeads,String params){
			
	        HttpClient httpClient = null;  
	        HttpGet httpGet = null;  
	        String result = null;  
	        try{  
	            httpClient = new SSLClient();  
	            httpGet = new HttpGet(url); 
	                        
	            for (Map.Entry<String, String> obj : httpHeads.entrySet()) {
	            	httpGet.setHeader(obj.getKey(), obj.getValue());
				}
	            
	            //设置参数  
	                       
//	            StringEntity reqEntity = new StringEntity(params);
//	            reqEntity.setContentType("application/json");
//	            httpGet.setEntity(reqEntity);
	            HttpResponse response = httpClient.execute(httpGet);  
	            if(response != null){  
	                HttpEntity resEntity = response.getEntity();  
	                if(resEntity != null){  
	                    result = EntityUtils.toString(resEntity,"utf-8");  
	                }  
	            }  
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }  
	        return result;  
	    }  
	 
	 
	 /*	 	 
	public static String sendPost(String url, Map<String, String> httpHeads,
			String params) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);

			HttpsURLConnection conn = (HttpsURLConnection) realUrl
					.openConnection();
			conn.setRequestMethod(SERVLET_POST);
			//conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

			for (Map.Entry<String, String> obj : httpHeads.entrySet()) {
				conn.setRequestProperty(obj.getKey(), obj.getValue());
			}
			
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new PrintWriter(conn.getOutputStream());
			out.print(params);
			out.flush();

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
			
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	 */
	public static String sendPost(String url,Map<String,String> httpHeads,String params){
				
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url); 
                        
            for (Map.Entry<String, String> obj : httpHeads.entrySet()) {
            	httpPost.setHeader(obj.getKey(), obj.getValue());
			}
            
            //设置参数  
                       
            StringEntity reqEntity = new StringEntity(params, HTTP.UTF_8);
            reqEntity.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"utf-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
	
	public static String sendDelete(String url,Map<String,String> httpHeads){
				
        HttpClient httpClient = null;  
        HttpDelete httpDelete = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpDelete = new HttpDelete(url); 
                        
            for (Map.Entry<String, String> obj : httpHeads.entrySet()) {
            	httpDelete.setHeader(obj.getKey(), obj.getValue());
			}
            
            //设置参数  
                 
            HttpResponse response = httpClient.execute(httpDelete);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"utf-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    } 
	
	public static String sendPut(String url,Map<String,String> httpHeads,String params){
		
        HttpClient httpClient = null;  
        HttpPut httpPut = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPut = new HttpPut(url); 
                        
            for (Map.Entry<String, String> obj : httpHeads.entrySet()) {
            	httpPut.setHeader(obj.getKey(), obj.getValue());
			}
            
            //设置参数  
                       
            StringEntity reqEntity = new StringEntity(params, HTTP.UTF_8);
            reqEntity.setContentType("application/json;charset=UTF-8");
            httpPut.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPut);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"utf-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }
	/*
	public static String sendDelete(String url, Map<String, String> httpHeads) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";

		try {

			URL realUrl = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) realUrl
					.openConnection();

			for (Map.Entry<String, String> obj : httpHeads.entrySet()) {
				conn.setRequestProperty(obj.getKey(), obj.getValue());
			}

			conn.setDoOutput(true);
			conn.setRequestMethod(SERVLET_DELETE);
			conn.setDoInput(true);

			out = new PrintWriter(conn.getOutputStream());
			out.flush();

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
			
		} catch (Exception e) {
			System.out.println("发送DELETE请求出现异常！" + e);
			e.printStackTrace();
			
		}finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;

	}*/
}
