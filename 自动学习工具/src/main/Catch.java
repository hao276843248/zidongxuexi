package main;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Catch implements Runnable{
//	http://ysdx.sdcen.cn/study/directory.aspx?CourseOpenId=ecccb477-1ecc-4dcb-a351-9e2f4e53bc0d&topicId=630327801rdagayunyj9geteqwfvt3g
	public String dataid;
	public String kmid;
	public String cookie;
	public String aspsession;
	public List<String> jindu;
	
	public Catch(String dataid, String kmid, String cookie, String aspsession,List<String> jindu) {
		super();
		this.dataid = dataid;
		this.kmid = kmid;
		this.cookie = cookie;
		this.aspsession = aspsession;
		this.jindu=jindu;
	}
	
	@Override
	public void run() {
		String url="http://ysdx.sdcen.cn/study/directory.aspx?CourseOpenId="+kmid+"&topicId="+dataid;
		String htmlPage=null;
		try {
			htmlPage = util.getHTMLPage(url, cookie+";"+aspsession);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(url);
		Document doc = Jsoup.parse(htmlPage);
		Elements elementsByClass = doc.getElementsByClass("cell_info");
		List<String> callIds=new ArrayList<String>();
		for (Element element : elementsByClass) {
			String callID = element.prependText(htmlPage).attr("data-id");
			callIds.add(callID);
		}
		System.out.println("getcall获取视频信息"+"视频数量"+callIds.size());
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url2="http://ysdx.sdcen.cn/study/action-directory-getCell?cellId=";
		//getcall获取视频信息；
		for (int i = 0; i < callIds.size(); i++) {
			String asPsessionID=null;
			try {
				asPsessionID = main.getASPsessionID();
				runLog(url2+callIds.get(i), cookie,asPsessionID,jindu.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void runLog(String url,String cookie,String aspsession,String jindu ) throws Exception{
		System.out.println("访问科目页面开始:"+url);
		String htmlPage2=util.getHTMLPage(url,cookie+";"+aspsession);
		String[] logIDandStatus = getLogIDandStatus(htmlPage2);
		System.out.println(logIDandStatus);
		String time = util.getHTMLPage(logIDandStatus[1],"");
		String times=null;
		String[] split = time.split(",");
		for (String string : split) {
			if(string.indexOf("duration")>0){
				String[] string2 = string.split("\"");
				for (int i = 0; i < string2.length; i++) {
					times=string2[3];
				}
			}
		}
		RunLog runLog=new RunLog(times,cookie,logIDandStatus[0],aspsession,jindu);
		Thread t=new Thread(runLog);
		t.start();
	}
	
	public static String[] getLogIDandStatus(String htmlPage){
		String[] requsest=new String[2];
		String[] split = htmlPage.split(",");
		System.out.println(htmlPage);
		for (String string : split) {
			if(string.indexOf("logId")>0){
				String[] string2 = string.split("\"");
				for (int i = 0; i < string2.length; i++) {
					requsest[0]=string2[3];
				}
			}else{
				if(string.indexOf("status")>0){
					String[] string2 = string.split("\"");
					requsest[1]=string2[5].substring(0, string2[5].length()-1);
				}
			}
		}
		return requsest;
	}

	
	
	
}
