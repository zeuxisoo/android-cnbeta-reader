package com.zeuxislo.cnbeta.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

public class DialogHelper {
	public static AlertDialog popupFreezeMessage(Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("");
		builder.setMessage(message);
		
		AlertDialog dialog = builder.create();
		dialog.show();
		
		return dialog;
	}
	
	public static void setWaitTimeForDialog(int waitTime, final Dialog dialog){
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		}, waitTime);
	}
}
