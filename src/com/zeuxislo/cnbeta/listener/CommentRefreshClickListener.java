package com.zeuxislo.cnbeta.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.zeuxislo.cnbeta.Comment;

public class CommentRefreshClickListener implements OnClickListener {
	
	private Context context;
	
	public CommentRefreshClickListener(Context context) {
		this.context = context;
	}	
	
	@Override
	public void onClick(View view) {
		Comment comment = (Comment) this.context;
		comment.bindAdapterView();
	}
	
}
