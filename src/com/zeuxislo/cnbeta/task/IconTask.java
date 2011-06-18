package com.zeuxislo.cnbeta.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.zeuxislo.cnbeta.helper.UtilityHelper;
import com.zeuxislo.cnbeta.helper.WebHelper;

public class IconTask extends AsyncTask<ImageView, Integer, Bitmap> {

	private String imageName;
	private ImageView imageView;
	
	@Override
	protected Bitmap doInBackground(ImageView... params) {
		ImageView imageView = params[0];
		
		this.imageName = imageView.getTag().toString();
		
		StringBuilder url = new StringBuilder("http://img.cnbeta.com/topics/");
		url.append(Uri.encode(this.imageName));
		
		Bitmap bitmap;
		
		try {
			bitmap = WebHelper.fetchImage(url.toString(), "http://img.cnbeta.com/");
			
			if (bitmap != null) {				
				if (UtilityHelper.isSDCardEnabled() == true) {
					FileOutputStream fileOutPutStream = new FileOutputStream(new File("/sdcard/CnBetaReader/icon/" + this.imageName));
				
					Bitmap.CompressFormat compressFormat;
					if (this.imageName.toLowerCase().endsWith(".jpg") == true) {
					 	compressFormat= Bitmap.CompressFormat.JPEG;
					}else{
						compressFormat = Bitmap.CompressFormat.PNG;
					}
					
					bitmap.compress(compressFormat, 100, fileOutPutStream);
				}
				
				this.imageView = imageView;
			}
			
			return bitmap;
		} catch (IOException e) {
			return null;
		}
	}
	
	protected void onPostExecute(Bitmap bitmap) {
		if (bitmap != null) {
			this.imageView.setImageBitmap(bitmap);
		}
	}

}
