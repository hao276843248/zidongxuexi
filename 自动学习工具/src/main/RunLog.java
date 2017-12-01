package main;


import java.util.concurrent.atomic.AtomicLong;

import javax.swing.plaf.SliderUI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class RunLog implements Runnable{


	public String time=null;
	public String cookie=null;
	public String logID=null;
	public String aspsession=null;
	public String jinud=null;
	private long leagth=0;
	
	public RunLog( String time,String cookie,String logID,String aspsession,String jinud) {
		this.time=time;
		this.cookie=cookie;
		this.logID=logID;
		this.aspsession=aspsession;
		this.jinud=jinud;
	}
	@Override
	public void run() {
		System.out.println("开始记录日志！"+"进度为："+jinud);
		if(!"100 %".equals(jinud)){
			// TODO Auto-generated method stub
			String url="http://ysdx.sdcen.cn/study/action-directory-CellLeaveLog?logId="+logID+"&videoEndTime=100&stopSconds=0";
			if(time!=null){
				String[] split = time.split(":");
				leagth+=Long.parseLong(split[0]) *60*60+Long.parseLong(split[1])*60+60;
			}
			String[] split = jinud.split("%");
			int a=Integer.parseInt(split[0].trim());
			leagth=leagth*100;
			leagth=leagth-(leagth*a/100);
			System.out.println("记录日志时长为："+leagth);
			long old = System.currentTimeMillis();
			for (int i = 0; i < 10; i++) {
				try {
					long newt = System.currentTimeMillis();
					if(6*60*1000<newt-old){
						aspsession = main.getASPsessionID();
					}
					String htmlPage = util.getHTMLPage(url, cookie+";"+aspsession);
					System.out.println("日志记录URL:"+url);
					System.out.println("日志记录次数："+i+"----"+htmlPage);
					if(htmlPage.indexOf("padding")>0){
						i++;
					}
					Thread.sleep(leagth);
				} catch (InterruptedException e) {
					i++;
					e.printStackTrace();
				} catch (Exception e) {
					i++;
					e.printStackTrace();
				}
			}
		}
	}
}
