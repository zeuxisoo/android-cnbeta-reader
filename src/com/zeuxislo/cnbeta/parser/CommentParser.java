package com.zeuxislo.cnbeta.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import com.zeuxislo.cnbeta.entry.CommentEntry;
import com.zeuxislo.cnbeta.task.CommentTask;

public class CommentParser {

	public static ArrayList<CommentEntry> execute(CommentTask commentTask, String html) {
		ArrayList<CommentEntry> entryList = new ArrayList<CommentEntry>();
		
		html = html.replace("<dd class=\"re_detail\">", "分");
		String pattern = "第(\\d+)[^<]+</strong>([^发]+)发表于([^<]+)[^分]+分([^<]+)[^支]+支持</a>\\(<span id=\"support([h\\d]+)\">(\\d+)[^反]+反对</a>[^>]+>(\\d+)";
		
		Matcher matcher = Pattern.compile(pattern).matcher(html);
		while(matcher.find()) {
			String liveId = matcher.group(1).trim();
			String name = StringEscapeUtils.unescapeHtml(matcher.group(2).trim().replace("\r", "").replace("\n", ""));
			String date = matcher.group(3).trim();
			String message = StringEscapeUtils.unescapeHtml(matcher.group(4).trim().replace("\r", ""));
			String unknow = matcher.group(5).trim();
			String supportNumber = matcher.group(6).trim();
			String unSupportNumber = matcher.group(7).trim();
			
			entryList.add(new CommentEntry(liveId, name, date, message, unknow, supportNumber, unSupportNumber));
		}
		
		return entryList;
	}
	
}
