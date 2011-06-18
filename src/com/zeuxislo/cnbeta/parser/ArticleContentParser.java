package com.zeuxislo.cnbeta.parser;

import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;

import com.zeuxislo.cnbeta.entry.ArticleContentEntry;
import com.zeuxislo.cnbeta.helper.UtilityHelper;
import com.zeuxislo.cnbeta.task.ArticleTask;

public class ArticleContentParser {
	
	public static ArrayList<ArticleContentEntry> execute(ArticleTask articleTask, String html) {
		ArrayList<ArticleContentEntry> entryList = new ArrayList<ArticleContentEntry>();
		
		if (html.equals("") == true) {
			articleTask.updateProgreeMessage("下載新聞內容失敗...請刷新...");
		}else{
			int i = html.indexOf("新闻主题");
			String one = html.substring(i);
			
			int j = one.indexOf("<br/>") + 10;
			String two = one.substring(j);
			
			int k = two.indexOf("hcomment.php?sid") - 14;
			
			String[] matched_strings = StringEscapeUtils.unescapeHtml(two.substring(0, k).replaceAll("[ ]+", " ")).replace(" <", "<").replace("> ", ">").replace("\t", "　　").replace("\r\n", "").replace("\n", "").replace("<br />", "\n").replace("<br/>", "\n").replaceAll("<img[^>]*?src\\s*=\\s*(?:\"([^\"]*)\"|'([^']*)')[^>]+>", "[分]$1[分]").replaceAll("<p[^>]*>(.+?)</p>", "\n$1\n").replaceAll("<li[^>]*>(.+?)</li>", "\n$1").replaceAll("<.+?>", "").replaceAll("     ", "    ").replaceAll("　 ", "　").replace(" 　", "　").replace("\n[分]", "[分]").replace("[分]\n", "[分]").split("\\[分\\]");
			
			for(int n = 0; n < matched_strings.length; n++) {
				ArticleContentEntry articleContentEntry = new ArticleContentEntry();
				
				if (matched_strings[n].trim().startsWith("pic/") == false && matched_strings[n].trim().endsWith(".gif") == false) {					
					articleContentEntry.content = matched_strings[n];
					articleContentEntry.type = 0;
					
					if (matched_strings[n].toLowerCase().startsWith("http") == true && ( matched_strings[n].toLowerCase().endsWith(".jpg") || matched_strings[n].toLowerCase().endsWith(".png") || matched_strings[n].toLowerCase().endsWith(".gif") ) ) {
						articleContentEntry.type = 1;
						articleContentEntry.imageFilename = UtilityHelper.getFilename(articleContentEntry.content);
						articleContentEntry.imageThumbName = UtilityHelper.md5(articleContentEntry.content) + UtilityHelper.getFileExtension(articleContentEntry.content);
					}
					
					entryList.add(articleContentEntry);
				}
			}
		}
		
		return entryList;
	}
	
}
