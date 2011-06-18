package com.zeuxislo.cnbeta.entry;

import android.graphics.Bitmap;
import android.widget.LinearLayout;

public class ArticleImageEntry {
	public Bitmap bitmap;
	public Integer position;
	public String url;
	public String imageThumbName;
	public LinearLayout view;
	
	public ArticleImageEntry(Integer position, String url, String imageThumbName, LinearLayout view) {
		this.position = position;
		this.url = url;
		this.imageThumbName = imageThumbName;
		this.view = view;
	}
}
