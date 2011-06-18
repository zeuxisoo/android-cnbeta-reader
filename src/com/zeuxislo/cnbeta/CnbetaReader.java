package com.zeuxislo.cnbeta;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zeuxislo.cnbeta.helper.DialogHelper;
import com.zeuxislo.cnbeta.helper.UIHelper;
import com.zeuxislo.cnbeta.helper.UtilityHelper;
import com.zeuxislo.cnbeta.listener.MainHomeClickListener;
import com.zeuxislo.cnbeta.listener.MainNextClickListener;
import com.zeuxislo.cnbeta.listener.MainPrevClickListener;
import com.zeuxislo.cnbeta.listener.MainRefreshClickListener;
import com.zeuxislo.cnbeta.task.PageTask;


public class CnbetaReader extends Activity {
	
	public int pageNumber = 1;
	public String appName = "CnBeta Reader";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        this.setContentView(R.layout.main);

        this.bindControlEvent();
        
		if (UtilityHelper.isConnected(this) == false) {
        	DialogHelper.setWaitTimeForDialog(3000, DialogHelper.popupFreezeMessage(this, "沒有網絡!請先連線"));
        }else{
			if (UtilityHelper.isSDCardEnabled() == true) {
				UtilityHelper.isFolderExists("/sdcard/CnBetaReader/");
				UtilityHelper.isFolderExists("/sdcard/CnBetaReader/icon/");
				UtilityHelper.isFolderExists("/sdcard/CnBetaReader/thumb/");
			}
			
			this.bindAdapterView();
        }
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	return UIHelper.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem menuItem) {
    	super.onOptionsItemSelected(menuItem);
    	return UIHelper.onOptionsItemSelected(this, menuItem);
    }
    
    private void bindControlEvent() {
    	ViewGroup viewGroup = (ViewGroup) this.findViewById(R.id.ui_main);
    	UIHelper.loadNavigatorControl(viewGroup);

    	TextView mainHome = (TextView) this.findViewById(R.id.main_home);
    	TextView mainPrev = (TextView) this.findViewById(R.id.main_prev);
    	TextView mainNext = (TextView) this.findViewById(R.id.main_next);
    	TextView mainRefresh = (TextView) this.findViewById(R.id.main_refresh);
		
    	mainHome.setOnClickListener(new MainHomeClickListener(this));
    	mainPrev.setOnClickListener(new MainPrevClickListener(this));
    	mainNext.setOnClickListener(new MainNextClickListener(this));
    	mainRefresh.setOnClickListener(new MainRefreshClickListener(this));
    }
    
    public void bindAdapterView() {
    	PageTask pageTask = new PageTask(this, this.pageNumber);
    	pageTask.execute(); 	
    }
}