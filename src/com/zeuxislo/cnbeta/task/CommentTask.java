package com.zeuxislo.cnbeta.task;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.zeuxislo.cnbeta.Comment;
import com.zeuxislo.cnbeta.R;
import com.zeuxislo.cnbeta.adapter.CommentAdapter;
import com.zeuxislo.cnbeta.entry.CommentEntry;
import com.zeuxislo.cnbeta.helper.WebHelper;
import com.zeuxislo.cnbeta.parser.CommentParser;

public class CommentTask extends AsyncTask<String, String, CommentAdapter> {

	private Context context;
	private String articleId;
	private ProgressDialog dialog;
	private String baseUrlString = "http://www.cnbeta.com/comment/normal/";
	
	public CommentTask(Context context, String articleId) {
		this.context = context;
		this.articleId = articleId;
	}
	
	protected void onPreExecute() {
		ProgressDialog progressDialog = ProgressDialog.show(context, "", "下載數據中...請稍後...", true, true);
		this.dialog = progressDialog;
	}	
	
	@Override
	protected CommentAdapter doInBackground(String... arg0) {
		StringBuilder url = new StringBuilder(this.baseUrlString);
		url.append(articleId).append(".html").append("?_=").append(System.currentTimeMillis());;
		
		String html = "";
		ArrayList<CommentEntry> commentList = new ArrayList<CommentEntry>();
		
		try {
			html = WebHelper.fetch(url.toString(), "utf-8", "http://m.cnbeta.com/");
			
			this.publishProgress("分析資料中...請稍後...");
			
			commentList = CommentParser.execute(this, html);
		}catch(Exception e) {
			e.printStackTrace();
		}

		return new CommentAdapter(this.context, commentList);
	}

	protected void onPostExecute(CommentAdapter commentAdapter) {
		Comment comment = (Comment) this.context;
		
		ListView commentListView = (ListView) comment.findViewById(R.id.comment_list);
		commentListView.setAdapter(commentAdapter);
		
		this.dialog.dismiss();
	}
	
	public void updateProgreeMessage(String message) {
		this.publishProgress(message);
	}	
	
}
