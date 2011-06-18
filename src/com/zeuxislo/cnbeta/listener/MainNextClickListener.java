package com.zeuxislo.cnbeta.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.zeuxislo.cnbeta.CnbetaReader;

public class MainNextClickListener implements OnClickListener {

	private Context context;
	
	public MainNextClickListener(Context context) {
		this.context = context;
	}
	
	@Override
	public void onClick(View view) {
		CnbetaReader cnbetaReader = (CnbetaReader)this.context;
		cnbetaReader.pageNumber += 1;
		cnbetaReader.bindAdapterView();
	}

}
