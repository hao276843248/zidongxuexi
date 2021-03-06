package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;

public class main {

//	http://ysdx.sdcen.cn/study/action-directory-CellLeaveLog?logId=rucgadqopzfdmit4pc06xa&videoEndTime=0&stopSconds=0
//	http://ysdx.sdcen.cn/study/action-directory-CellLeaveLog?logId=r10gadqorilh4enug2p4xg&videoEndTime=0&stopSconds=0
	public static String cookie="";
	public static Map<String, String> url=new HashMap<String, String>();
	public static String longinUrl="http://ysdx.sdcen.cn/common/Login/login?";
	public static String schoolId="648c1c25-7bb9-48c3-9362-ebc998f1fd83";
	public static String ASPSessionKmID="";
//	schoolId=648c1c25-7bb9-48c3-9362-ebc998f1fd83&username=20170200303001032&password=181417
	 public static void main(String[] args) throws Exception {
//		 String username="20170200303001032";
//		 String password="181417";
		 String username="2017023300504";
		 String password="068039";
		 login(username, password);
		 System.out.println(System.currentTimeMillis());
	 }
	  
	    //登录
	    private static  void login(String username,String password) throws Exception {
//	    	登录获取Cookie
	    	getCookie(username, password);
//	    	访问主页面
	    	String mainUrl="http://ysdx.sdcen.cn/stumanager/LearningSpace.aspx";
	    	String ref="http://ysdx.sdcen.cn/portal/home.aspx";
	    	String htmlPage = util.getHTMLPage(mainUrl, cookie);
	    	List<String> kmIdS = getKmIdS(htmlPage);
	    	ASPSessionKmID=kmIdS.get(0);
	    	System.out.println("科目开始");
	    	System.out.println("科目数量为:"+kmIdS.size());
//	    	for (String string : kmIdS) {
	    		String getdataId = getdataId(kmIdS.get(3));
		    	String asPsessionID = getASPsessionID();
		    	List<String> jindu = getJindu(kmIdS.get(3));
		    	Catch c=new Catch(getdataId, kmIdS.get(3), cookie,asPsessionID,jindu);
		    	Thread t=new Thread(c);
		    	t.start();
//			}
	    }
	    
	    public static String  getCookie(String username, String password) throws IOException{
	    	String getresponse = util.getresponse(longinUrl+"schoolId="+schoolId+"&username="+username+"&password="+password);
//	    	List<Cookie> cookies =((AbstractHttpClient) util.m_httpClient).getCookieStore().getCookies();
	    	String[] split = getresponse.split(";");
	    	cookie=split[0];
	    	return split[0];
	    }

	    //获取sessionID
	    public static String getASPsessionID() throws Exception{
	    	String aspSessionUrl="http://ysdx.sdcen.cn/study/navigation.aspx?courseOpenId="+ASPSessionKmID;
	    	String aspSession = util.getResponseCook(aspSessionUrl, cookie);
	    	String[] split1 = aspSession.split(",");
	    	String string2=null;
			for (String string : split1) {
				if(string.indexOf("Set-Cookie")>0){
					string2 = string.split(";")[0].split(":")[1].trim();
					System.out.println(string2);
				}
			}
			return string2;
	    }
	    
	    //获取科目代码
    	public static List<String> getKmIdS(String htmlPage) {
//    		解析
	    	Document doc = Jsoup.parse(htmlPage);
	    	Elements elementsByClass = doc.getElementsByClass("sh-coursename");
	    	List<String> kmids=new ArrayList<String >();
	    	for (Element element : elementsByClass) {
	    		String attr = element.children().attr("href");
	    		String[] kmID = attr.split("=");
	    		kmids.add(kmID[1]);
			}
    		return kmids;
	    }
	    
    	//获取第一节视频ID
	    public static String getdataId(String kmid) throws Exception{
	    	String dataId=null;
	    	String url="http://ysdx.sdcen.cn/study/learnCatalog.aspx?courseOpenId="+kmid;
	    	String htmlPage = util.getHTMLPage(url, cookie);
	    	Document doc = Jsoup.parse(htmlPage);
	    	Elements elementsByClass = doc.getElementsByClass("topic");
	    	for (Element element : elementsByClass) {
	    		dataId = element.attr("data-topicid");
			}
	    	return dataId;
	    }
	  //获取所有节视频的进度
	    public static List<String> getJindu(String kmid) throws Exception{
	    	String dataId=null;
	    	String url="http://ysdx.sdcen.cn/study/learnCatalog.aspx?courseOpenId="+kmid;
	    	String htmlPage = util.getHTMLPage(url, cookie);
	    	Document doc = Jsoup.parse(htmlPage);
			Elements elementsByClass = doc.getElementsByClass("am-progress-bar am-progress-bar-warning");
			List<String> jinduList=new ArrayList<String>();
			for (Element element : elementsByClass) {
				String text = element.text();
				jinduList.add(text);
			}
	    	return jinduList;
	    }
	    
	    
}
