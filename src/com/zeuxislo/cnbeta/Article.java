package com.zeuxislo.cnbeta;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.zeuxislo.cnbeta.entry.PageEntry;
import com.zeuxislo.cnbeta.helper.UIHelper;
import com.zeuxislo.cnbeta.listener.ArticleCommentClickListener;
import com.zeuxislo.cnbeta.listener.ArticleRefreshClickListener;
import com.zeuxislo.cnbeta.task.ArticleTask;

public class Article extends Activity {

	private PageEntry pageEntry;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        this.setContentView(R.layout.article);
        
        this.pageEntry = (PageEntry) this.getIntent().getSerializableExtra("pageEntry");
        
        this.bindControlEvent();
		this.bindAdapterView();
    }
	
    private void bindControlEvent() {
    	UIHelper.loadArticleHeaderControl(this, this.pageEntry);
    	
    	TextView articleComment = (TextView) this.findViewById(R.id.article_comment);
    	TextView articleBookmark= (TextView) this.findViewById(R.id.article_bookmark);
    	TextView articleRefresh = (TextView) this.findViewById(R.id.article_refresh);
    	
    	articleComment.setOnClickListener(new ArticleCommentClickListener(this, this.pageEntry));
    	articleBookmark.setOnClickListener(null);
    	articleRefresh.setOnClickListener(new ArticleRefreshClickListener(this));
    }
    
    public void bindAdapterView() {
    	ArticleTask articleTask = new ArticleTask(this, this.pageEntry);
        articleTask.execute();    	
    }
}
