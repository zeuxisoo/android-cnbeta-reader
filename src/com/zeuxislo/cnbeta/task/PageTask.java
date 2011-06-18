package com.zeuxislo.cnbeta.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import com.zeuxislo.cnbeta.CnbetaReader;
import com.zeuxislo.cnbeta.R;
import com.zeuxislo.cnbeta.adapter.ArticleAdapter;
import com.zeuxislo.cnbeta.entry.PageEntry;
import com.zeuxislo.cnbeta.helper.WebHelper;
import com.zeuxislo.cnbeta.listener.ArticleAdapterItemClickListener;
import com.zeuxislo.cnbeta.parser.PageParser;

public class PageTask extends AsyncTask<String, String, ArticleAdapter> {
	
	private ProgressDialog dialog;
	private Context context;
	private Integer pageNumber = 0;
	private String baseUrl = "http://www.cnbeta.com/pagedata0.php?pageID=";
	private ArrayList<PageEntry> entryList;
	
	public PageTask(Context context, Integer pageNumber) {
		this.context = context;
		this.pageNumber = pageNumber;
	}
	
	protected void onProgressUpdate(String... message) {
		this.dialog.setMessage(message[0]);
	}

	protected void onPreExecute() {
		ProgressDialog progressDialog = ProgressDialog.show(context, "", "下載數據中...請稍後...", true, true);
		this.dialog = progressDialog;
	}

	@Override
	protected ArticleAdapter doInBackground(String... text) {		
		StringBuilder url = new StringBuilder(this.baseUrl);
		url.append(this.pageNumber).append("&randnum=").append(System.currentTimeMillis());
				
		String html = "";
		this.entryList = new ArrayList<PageEntry>();
		
		try {
			html = WebHelper.fetch(url.toString(), "gb2312", "http://www.cnbeta.com/");
			
			this.publishProgress("分析資料中...請稍後...");
			
			this.entryList = PageParser.execute(this, html);
		} catch (Exception e) {
		}

		return new ArticleAdapter(this.context, this.entryList);
	}
	
	protected void onPostExecute(ArticleAdapter articleAdapter) {		
		CnbetaReader cnbetaReader = (CnbetaReader) this.context;
		cnbetaReader.setTitle(cnbetaReader.appName + " - 第 " + (this.pageNumber) + " 頁");
		
		ListView articleList = (ListView) cnbetaReader.findViewById(R.id.main_article);
		articleList.setAdapter(articleAdapter);
		articleList.setOnItemClickListener(new ArticleAdapterItemClickListener(cnbetaReader, this.entryList));
		
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[MM-dd HH:mm]更新");
			
			TextView mainRefersh = (TextView) cnbetaReader.findViewById(R.id.main_refresh);
			mainRefersh.setText(simpleDateFormat.format(new Date(System.currentTimeMillis())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.dialog.dismiss();
	}
	
	public void updateProgreeMessage(String message) {
		this.publishProgress(message);
	}
	
}
