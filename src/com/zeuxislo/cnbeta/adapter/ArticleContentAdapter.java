package com.zeuxislo.cnbeta.adapter;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zeuxislo.cnbeta.R;
import com.zeuxislo.cnbeta.entry.ArticleContentEntry;
import com.zeuxislo.cnbeta.entry.ArticleImageEntry;
import com.zeuxislo.cnbeta.task.ArticleImageTask;

public class ArticleContentAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private ArrayList<ArticleContentEntry> entryList;
	
	public ArticleContentAdapter(Context context, ArrayList<ArticleContentEntry> entryList) {
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.entryList = entryList;
	}
	
	@Override
	public int getCount() {
		return this.entryList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.entryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewTag viewTag;

		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.article_content_list, null);
			viewTag = new ViewTag();
			
			viewTag.contentBody = (TextView) convertView.findViewById(R.id.article_content_list_content_body);
			viewTag.clickView = (TextView) convertView.findViewById(R.id.article_content_list_click_view);
			viewTag.imageLayout = (RelativeLayout) convertView.findViewById(R.id.article_content_list_image_layout);
			viewTag.imageView = (ImageView) convertView.findViewById(R.id.article_content_list_image_view);
			viewTag.infoView = (TextView) convertView.findViewById(R.id.article_content_list_info_view);
			viewTag.downloadedImage = (ImageView) convertView.findViewById(R.id.article_content_list_downloaded_image);
			viewTag.loading = (RelativeLayout) convertView.findViewById(R.id.article_content_list_loading);
			
			convertView.setTag(viewTag);
		}else{
			viewTag = (ViewTag) convertView.getTag();
		}
		
		ArticleContentEntry articleContentEntry = this.entryList.get(position);
		
		if (articleContentEntry.type == 0) {
			viewTag.contentBody.setVisibility(View.VISIBLE);
			viewTag.imageLayout.setVisibility(View.GONE);
			viewTag.downloadedImage.setVisibility(View.GONE);
			viewTag.imageView.setVisibility(View.GONE);
			viewTag.loading.setVisibility(View.GONE);
			
			viewTag.contentBody.setText(articleContentEntry.content);
		}else{
			viewTag.contentBody.setVisibility(View.GONE);
			viewTag.imageLayout.setVisibility(View.GONE);
			viewTag.downloadedImage.setVisibility(View.GONE);
			viewTag.imageView.setVisibility(View.GONE);
			viewTag.loading.setVisibility(View.VISIBLE);
			
			StringBuilder stringBuilder = new StringBuilder("/sdcard/CnBetaReader/thumb/");
			stringBuilder.append(articleContentEntry.imageThumbName);
			
			File file = new File(stringBuilder.toString());
			if (file.exists() == true) {				
				Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
				viewTag.downloadedImage.setImageBitmap(bitmap);
				viewTag.downloadedImage.setVisibility(View.VISIBLE);
			}else{
				ArticleImageTask articleImageTask = new ArticleImageTask(this.context);
				articleImageTask.execute(new ArticleImageEntry(
					Integer.valueOf(position), 
					articleContentEntry.content,
					articleContentEntry.imageThumbName,
					(LinearLayout)convertView
				));
			}
		}

		return convertView;
	}

	class ViewTag {
		public TextView clickView;
		public TextView contentBody;
		public RelativeLayout imageLayout;
		public ImageView downloadedImage;
		public ImageView imageView;
		public TextView infoView;
		public RelativeLayout loading;
	}
}