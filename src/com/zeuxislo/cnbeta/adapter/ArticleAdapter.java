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
import android.widget.TextView;

import com.zeuxislo.cnbeta.R;
import com.zeuxislo.cnbeta.entry.PageEntry;
import com.zeuxislo.cnbeta.task.IconTask;


public class ArticleAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<PageEntry> entryList;
	
	public ArticleAdapter(Context context, ArrayList<PageEntry> entryList) {		
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
			convertView = this.layoutInflater.inflate(R.layout.main_article_list, null);
			viewTag = new ViewTag();
						
			viewTag.icon = (ImageView) convertView.findViewById(R.id.main_article_list_icon);
			viewTag.title = (TextView) convertView.findViewById(R.id.main_article_list_title);
			viewTag.info = (TextView) convertView.findViewById(R.id.main_article_list_info);
			viewTag.comment = (TextView) convertView.findViewById(R.id.main_article_list_comment);
			
			convertView.setTag(viewTag);
		}else{
			viewTag = (ViewTag) convertView.getTag();
		}
		
		PageEntry pageEntry = (PageEntry) this.entryList.get(position);
		
		viewTag.title.setText(pageEntry.title);
		viewTag.info.setText("發佈於 " + pageEntry.date + " | " + pageEntry.readCount + " 次閱讀");
		viewTag.comment.setText("已有 " + pageEntry.commentCount + " 個意見 | " + pageEntry.pushCount + " 次推薦");
	
		StringBuilder stringBuilder = new StringBuilder("/sdcard/CnBetaReader/icon/");
		stringBuilder.append(pageEntry.categoryImage);
		
		File file = new File(stringBuilder.toString());
		if (file.exists() == true) {
			Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
			viewTag.icon.setImageBitmap(bitmap);
		}else{
			viewTag.icon.setImageResource(R.drawable.no_article_icon);
			viewTag.icon.setTag(pageEntry.categoryImage);
			
			IconTask iconTask = new IconTask();
			iconTask.execute(viewTag.icon);
		}

		return convertView;
	}

	class ViewTag {
		public ImageView icon;
		public TextView title;
		public TextView info;
		public TextView comment;
	}
}
