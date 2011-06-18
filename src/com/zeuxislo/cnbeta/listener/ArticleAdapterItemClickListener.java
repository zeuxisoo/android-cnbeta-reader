package com.zeuxislo.cnbeta.listener;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zeuxislo.cnbeta.Article;
import com.zeuxislo.cnbeta.entry.PageEntry;

public class ArticleAdapterItemClickListener implements OnItemClickListener {

	private Context context;
	private ArrayList<PageEntry> entryList;
	
	public ArticleAdapterItemClickListener(Context context, ArrayList<PageEntry> entryList) {
		this.context = context;
		this.entryList = entryList;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PageEntry pageEntry = this.entryList.get(position);
		
		Bundle bundle = new Bundle();
		bundle.putSerializable("pageEntry", pageEntry);
		
		Intent intent = new Intent();
		intent.setClass(this.context, Article.class);
		intent.putExtras(bundle);
		this.context.startActivity(intent);
	}

}
