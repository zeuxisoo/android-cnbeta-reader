package com.zeuxislo.cnbeta.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.zeuxislo.cnbeta.CnbetaReader;

public class MainHomeClickListener implements OnClickListener {

	private Context context;
	
	public MainHomeClickListener(Context context) {
		this.context = context;
	}
	
	@Override
	public void onClick(View view) {
		CnbetaReader cnbetaReader = (CnbetaReader)this.context;
		int pageNumber = cnbetaReader.pageNumber;
		
		if (pageNumber == 1) {
			Toast.makeText(this.context, "已經是首頁了", Toast.LENGTH_LONG).show();
		}else{
			cnbetaReader.pageNumber = 1;
			cnbetaReader.bindAdapterView();
		}
	}

}
