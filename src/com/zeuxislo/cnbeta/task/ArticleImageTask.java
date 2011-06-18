package com.zeuxislo.cnbeta.task;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zeuxislo.cnbeta.Article;
import com.zeuxislo.cnbeta.R;
import com.zeuxislo.cnbeta.entry.ArticleImageEntry;
import com.zeuxislo.cnbeta.helper.UtilityHelper;
import com.zeuxislo.cnbeta.helper.WebHelper;

public class ArticleImageTask extends AsyncTask<ArticleImageEntry, Integer, ArticleImageEntry> {

	private Context context;
	
	public ArticleImageTask(Context context) {
		this.context = context;
	}
	
	protected void onPreExecute() {
		
	}
	
	@Override
	protected ArticleImageEntry doInBackground(ArticleImageEntry... params) {
		ArticleImageEntry articleImageEntry = params[0];
		
		try {
			articleImageEntry.bitmap = WebHelper.fetchImage(articleImageEntry.url);

			if (articleImageEntry.bitmap != null) {
				if (UtilityHelper.isSDCardEnabled() == true) {
					DisplayMetrics displayMetrics = new DisplayMetrics();
					((Article) this.context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

					Integer displayWidth = Integer.valueOf(displayMetrics.widthPixels);

					if (articleImageEntry.bitmap.getWidth() > displayWidth.intValue() - 8) {
						int width = displayWidth.intValue();
						int height = displayWidth.intValue() * articleImageEntry.bitmap.getHeight() / articleImageEntry.bitmap.getWidth();

						articleImageEntry.bitmap = UtilityHelper.extractMiniThumb(articleImageEntry.bitmap, width, height);
					}

					FileOutputStream fileOutPutStream = new FileOutputStream(new File("/sdcard/CnBetaReader/thumb/" + articleImageEntry.imageThumbName));
					
					Bitmap.CompressFormat compressFormat;
					
					if (articleImageEntry.imageThumbName.toLowerCase().endsWith(".jpg") == true) {
					 	compressFormat= Bitmap.CompressFormat.JPEG;
					}else{
						compressFormat = Bitmap.CompressFormat.PNG;
					}
					
					articleImageEntry.bitmap.compress(compressFormat, 100, fileOutPutStream);
				}
			}
			
			return articleImageEntry;
		}catch(Exception e) {
			return null;
		}
	}
	
	protected void onPostExecute(ArticleImageEntry articleImageEntry) {
		ImageView downloadImage = (ImageView) articleImageEntry.view.findViewById(R.id.article_content_list_downloaded_image);
		RelativeLayout loading = (RelativeLayout) articleImageEntry.view.findViewById(R.id.article_content_list_loading);
		
		downloadImage.setImageBitmap(articleImageEntry.bitmap);
		
		downloadImage.setVisibility(View.VISIBLE);
		loading.setVisibility(View.GONE);
	}

}
