package net.nba.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WebPageReader {
	
	public static StringBuffer readWebPage(String urlStr){
		 StringBuffer result = new StringBuffer();
	        try {
	            URL url = new URL(urlStr);
	            URLConnection conn = url.openConnection();
	            conn.connect();
	            InputStream in = conn.getInputStream();
	            //包装流时要加入网站的编码(此处为gbk)，防止中文乱码问题
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"gbk"));
	            String line = null;
	            while ((line = reader.readLine()) != null){
	                //必须在一行中 否则用正则表达式取值时会出错
	                result.append(new String(line.getBytes(),"utf-8"));
	            }
	            reader.close();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return result;
	}

}
