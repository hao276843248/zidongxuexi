package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test {

	
	public static void main(String[] args) throws Exception {
		long leagth=20;
		int a=90;
		leagth=leagth*100;
		leagth=leagth-(leagth*a/100);
		System.out.println(leagth);
	}
	
	
	public void aaa(String htmlPage2) throws Exception{
		String[] split = htmlPage2.split(",");
		for (String string : split) {
			System.out.println(string);
			if(string.indexOf("duration")>0){
				String[] string2 = string.split("\"");
				for (int i = 0; i < string2.length; i++) {
					System.out.println(string2[3]+"-"+i);
				}
			}else{
				if(string.indexOf("status")>0){
					String[] string2 = string.split("\"");
					String status=string2[0].substring(0, string2[0].length()-1);
				}
			}
		}
		
		
		String username="2017023300504";
		String password="068039";		
		String aa="http://ysdx.sdcen.cn/study/learnCatalog.aspx?courseOpenId=ecccb477-1ecc-4dcb-a351-9e2f4e53bc0d";
		String cookie=main.getCookie(username, password);
		String htmlPage = util.getHTMLPage(aa, cookie);
		System.out.println(htmlPage);	
		Document doc = Jsoup.parse(htmlPage);
		Elements elementsByClass = doc.getElementsByClass("am-progress-bar am-progress-bar-warning");
		List<String> jinduList=new ArrayList<String>();
		for (Element element : elementsByClass) {
			String text = element.text();
			jinduList.add(text);
		}
	}
}
