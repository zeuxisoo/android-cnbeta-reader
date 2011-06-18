package com.zeuxislo.cnbeta.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zeuxislo.cnbeta.Comment;
import com.zeuxislo.cnbeta.entry.PageEntry;

public class ArticleCommentClickListener implements OnClickListener {

	private Context context;
	private PageEntry pageEntry;
	
	public ArticleCommentClickListener(Context context, PageEntry pageEntry) {
		this.context = context;
		this.pageEntry = pageEntry;
	}
	
	@Override
	public void onClick(View view) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("pageEntry", pageEntry);
		
		Intent intent = new Intent(this.context, Comment.class);
		intent.putExtras(bundle);
		this.context.startActivity(intent);
	}

}
