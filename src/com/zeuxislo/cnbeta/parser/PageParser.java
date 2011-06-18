package com.zeuxislo.cnbeta.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zeuxislo.cnbeta.entry.PageEntry;
import com.zeuxislo.cnbeta.task.PageTask;

public class PageParser {

	public static ArrayList<PageEntry> execute(PageTask pageTask, String html) {		
		/*
		Document document = Jsoup.parse(html);
		Elements newsLists = document.select("div.newslist");
		
		int total = newsLists.toArray().length;
		
		Log.d("PageParser", "Total news list:" + total);
		
		pageTask.updateProgreeMessage("分析資料中...請稍後...0/" + total);
		
		int i=1;
		
		for(Element news : newsLists) {			
			pageTask.updateProgreeMessage("分析資料中...請稍後..." + (i++) + "/" + total);
			
			String topic = news.select(".topic a").text();
			String icon = news.select(".desc img").attr("src");			
			String info = news.select(".author span").text();
			
			entryList.add(new PageEntry(topic, info, icon));
		}
		*/

		ArrayList<PageEntry> entryList = new ArrayList<PageEntry>();
		
		html = html.replace("img.cnbeta.com/topics", "☆").replace("<strong>详细内容</strong>", "☆").replace("<dd class=\"desc\"><a href=\"/topics/", "☆");
				
		Matcher matcher_article = Pattern.compile("(\\d+).htm\"[^>]+><strong>([^<]+)</strong>[^发]+发布于([\\d-: ]+)\\| (\\d+) 次阅读 \\| (\\d+)次推荐</span>[^☆]+☆(\\d+).htm[^☆]+☆/([^\"]+)\" alt=\"([^\"]+)[^☆]+☆</a> \\|  已有(\\d+) 个意见").matcher(html);

		int total_article = 0;
		while(matcher_article.find()) {
			total_article++;
		}
		matcher_article.reset();
		
		pageTask.updateProgreeMessage("分析資料中...請稍後...0/" + total_article);
		
		
		int i = 0;
		while(matcher_article.find()) {
			pageTask.updateProgreeMessage("分析資料中...請稍後..." + (i++) + "/" + total_article);
			
			String id = matcher_article.group(1).trim();
			String title = matcher_article.group(2).trim();
			String date = matcher_article.group(3).trim();
			String readCount = matcher_article.group(4).trim();
			String pushCount = matcher_article.group(5).trim();
			String categoryId = matcher_article.group(6).trim();
			String categoryImage = matcher_article.group(7).trim();
			String categoryName = matcher_article.group(8).trim();
			String commentCount = matcher_article.group(9).trim();
			
			entryList.add(new PageEntry(id, title, date, readCount, pushCount, categoryId, categoryImage, categoryName, commentCount));
		}
		
		return entryList;
	}
	
}
