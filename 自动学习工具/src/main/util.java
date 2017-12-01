package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class util {

	
	/**
	 * 程序中访问http数据接口
	 */
	public static String getURLContent(String urlStr) {
		/** 网络的url地址 */
		URL url = null;
		/** http连接 */
		HttpURLConnection httpConn = null;
		/**//** 输入流 */
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(urlStr);
			in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception ex) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		String result = sb.toString();
		System.out.println(result);
		return result;
	}
	
	 // 抓取页面
    public static String getHTMLPage(String url,String cookie) throws Exception {
    	HttpClient m_httpClient = new DefaultHttpClient();
        String result = "";

        HttpGet httpget = new HttpGet(url);
//        httpget.setHeader("Referer",Referer);
        httpget.setHeader("Cookie",cookie);
        HttpResponse response = m_httpClient.execute(httpget);
       
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "UTF-8");
        httpget.releaseConnection();
        response.toString();
        return result;
    }
    
    
    public static String getresponse(String url) throws IOException{
    	 URL urls = new URL(url);
         URLConnection conn = urls.openConnection();
         String setcookie=null;
         Map headers = conn.getHeaderFields();
         Set<String> keys = headers.keySet();
         for( String key : keys ){
             String val = conn.getHeaderField(key);
             if("Set-Cookie".equals(key)){
            	 setcookie=val;
             }
//             System.out.println(key+"    "+val);
         }
		return setcookie;
     }
    
    public static String getResponseCook(String url,String cookie) throws Exception{
    	HttpClient m_httpClient = new DefaultHttpClient();
        String result = "";

        HttpGet httpget = new HttpGet(url);
//        httpget.setHeader("Referer",Referer);
        httpget.setHeader("Cookie",cookie);
        HttpResponse response = m_httpClient.execute(httpget);
       
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "UTF-8");
        httpget.releaseConnection();
        return response.toString();
    }
}
