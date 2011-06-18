package com.zeuxislo.cnbeta.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.zeuxislo.cnbeta.Article;

public class ArticleRefreshClickListener implements OnClickListener {

	private Context context;
	
	public ArticleRefreshClickListener(Context context) {
		this.context = context;
	}	
	
	@Override
	public void onClick(View view) {
		Article article = (Article) this.context;
		article.bindAdapterView();
	}

}
