package com.zeuxislo.cnbeta.task;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.zeuxislo.cnbeta.Article;
import com.zeuxislo.cnbeta.R;
import com.zeuxislo.cnbeta.adapter.ArticleContentAdapter;
import com.zeuxislo.cnbeta.entry.ArticleContentEntry;
import com.zeuxislo.cnbeta.entry.PageEntry;
import com.zeuxislo.cnbeta.helper.WebHelper;
import com.zeuxislo.cnbeta.parser.ArticleContentParser;

public class ArticleTask extends AsyncTask<String, String, ArticleContentAdapter> {

	private Context context;
	private PageEntry pageEntry;
	private ProgressDialog dialog;
	private String baseUrlString = "http://m.cnbeta.com/marticle.php?sid=";
	
	public ArticleTask(Context context, PageEntry pageEntry) {
		this.context = context;
		this.pageEntry = pageEntry;
	}
	
	protected void onPreExecute() {
		ProgressDialog progressDialog = ProgressDialog.show(context, "", "下載數據中...請稍後...", true, true);
		this.dialog = progressDialog;
	}
	
	@Override
	protected ArticleContentAdapter doInBackground(String... text) {
		StringBuilder url = new StringBuilder(this.baseUrlString);
		url.append(this.pageEntry.id).append("&randnum=").append(System.currentTimeMillis());
		
		String html = "";
		ArrayList<ArticleContentEntry> articleContentList = new ArrayList<ArticleContentEntry>();
		
		try {
			html = WebHelper.fetch(url.toString(), "utf-8", "http://m.cnbeta.com/");
			
			this.publishProgress("分析資料中...請稍後...");
			
			articleContentList = ArticleContentParser.execute(this, html);
		}catch(Exception e) {
			e.printStackTrace();
		}

		return new ArticleContentAdapter(this.context, articleContentList);
	}

	protected void onPostExecute(ArticleContentAdapter articleContentAdapter) {
		Article article = (Article) this.context;
		
		ListView articleContentListView = (ListView) article.findViewById(R.id.article_content_list);
		articleContentListView.setAdapter(articleContentAdapter);
		
		this.dialog.dismiss();
	}
	
	public void updateProgreeMessage(String message) {
		this.publishProgress(message);
	}
	
}
