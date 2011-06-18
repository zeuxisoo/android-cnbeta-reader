package com.zeuxislo.cnbeta;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.zeuxislo.cnbeta.entry.PageEntry;
import com.zeuxislo.cnbeta.listener.CommentRefreshClickListener;
import com.zeuxislo.cnbeta.task.CommentTask;

public class Comment extends Activity {

	private PageEntry pageEntry;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        this.setContentView(R.layout.comment);
        
        Bundle bundle = this.getIntent().getExtras();
        this.pageEntry = (PageEntry) bundle.getSerializable("pageEntry");
        
        this.bindHeader();
        this.bindControlEvent();
		this.bindAdapterView();
    }
    
    private void bindHeader() {
    	TextView comment_title = (TextView) this.findViewById(R.id.comment_title);
        comment_title.setText(this.pageEntry.title);    	
    }
    
    private void bindControlEvent() {
    	TextView commentRefresh = (TextView) this.findViewById(R.id.comment_refresh);
    	
    	commentRefresh.setOnClickListener(new CommentRefreshClickListener(this));
    }
    
    public void bindAdapterView() {
    	CommentTask commentTask = new CommentTask(this, this.pageEntry.id);
    	commentTask.execute();
    }
	
}
