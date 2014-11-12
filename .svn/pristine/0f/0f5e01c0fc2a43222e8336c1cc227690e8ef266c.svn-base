package com.sb.framework.http;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sb.framework.SB;
import com.sb.framework.SB.CommonCallback;
import com.sb.framework.utils.SBLog;


public class HttpHelper {
	
	
	public static final String HTTP_ERROR_INTO_PREFIX = "---http-fail---:";
	
	/**
	 * 给get请求用的，把params拼到URL里
	 * @param p_url
	 * @param params
	 * @return
	 */
	public static String makeURL(String p_url, Map<String, String> params) {
		StringBuilder url = new StringBuilder(p_url);
		if(url.indexOf("?")<0)
			url.append('?');

		for(String name : params.keySet()){
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
			//不做URLEncoder处理
			//url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
		}

		return url.toString().replace("?&", "?");
	}
	
	/**
	 * 给post请求用的，把params拼到post参数里
	 * @param params
	 * @return
	 */
	public static StringBuilder makeParamForPost(Map<String, String> params)
	{
		String boundary = "*****";
		StringBuilder sb = new StringBuilder();   
        for (Entry<String, String> entry : params.entrySet()) 
        {
        	//构建表单字段内容    
            sb.append("--");    
            sb.append(boundary);    
            sb.append("\r\n");    
            sb.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");    
            sb.append(entry.getValue());    
            sb.append("\r\n");    
        }    

        sb.append("--");    
        sb.append(boundary);    
        sb.append("\r\n");   
        return sb;
	}
	
	/** 
     * 组装参数,这里用Map,一键一值比较通用,可以当做共用方法 
     * 
     * 
     * @param params 
     * @return 
     */  
//    public static org.apache.commons.httpclient.NameValuePair[] buildNameValuePairs(Map<String, String> params) {  
//        Object[] keys = params.keySet().toArray();  
//        org.apache.commons.httpclient.NameValuePair[] pairs = new org.apache.commons.httpclient.NameValuePair[keys.length];  
//  
//        for (int i = 0; i < keys.length; i++) {  
//            String key = (String) keys[i];  
//            pairs[i] = new org.apache.commons.httpclient.NameValuePair(key, params.get(key));  
//        }  
//  
//        return pairs;  
//    }  
    
    /** 
     * 组装参数,这里用Map,一键一值比较通用,可以当做共用方法 
     * 
     * 
     * @param params 
     * @return 
     */  
    public static List<org.apache.http.NameValuePair> buildNameValuePairs2(Map<String, String> params) {  
        Object[] keys = params.keySet().toArray();  
        List<org.apache.http.NameValuePair> pairs = new ArrayList<org.apache.http.NameValuePair>();  
  
        for (int i = 0; i < keys.length; i++) {  
            String key = (String) keys[i];  
            org.apache.http.NameValuePair p = new org.apache.http.message.BasicNameValuePair(key, params.get(key));  
            pairs.add(p);
        }  
  
        return pairs;  
    }  
    
    public static String translateResponseStatusCode(int statusCode){
		
		return "未知状态码：" + statusCode;
	}
    
    
    public static String processInputStreamLikeString(InputStream in){
    	String result = "";
    	InputStreamReader inReader=new InputStreamReader(in);
		BufferedReader buffer=new BufferedReader(inReader);
		String strLine=null;
		try {
			while((strLine=buffer.readLine())!=null)
			{
				result+=strLine;
			}
			inReader.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}finally{
			try {
				inReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
    }
    
    /**
     * 返回流保存的路径
     * @param in
     * @return
     */
    public static String processInputStreamLikeFile(InputStream in, String path){
    	
    	// 1K的数据缓冲 
    	byte[] bs = new byte[1024]; 
    	// 读取到的数据长度 
    	int len; 
    	// 输出的文件流 
    	OutputStream os;
		try {
			os = new FileOutputStream(path);
			// 开始读取 
	    	while ((len = in.read(bs)) != -1) { 
	    		os.write(bs, 0, len); 
	    	}
	    	
	    	os.close(); 
	    	in.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	return path;
    }
    
    //取得url域名
  	public static String getDomainName(String url) {
  		Pattern p = Pattern.compile("^http://[^/]+");
          Matcher matcher = p.matcher(url);
          if(matcher.find()){
          	return  matcher.group();
          }
  		return "";
  	}
    
    public static String translateHttpCode(int httpCode){
    	
    	return "";
    }
	
    public static void printMap(final Map<String, String> map){
    	SB.collection.walk(map.keySet(), new CommonCallback() {
			
			@Override
			public Object process(Object... params) {
				String key = (String) params[0];
				String value = map.get(key).toString();
				SBLog.debug(key + "==>" + value);
				return null;
			}
		});
    }
}
