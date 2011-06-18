package com.zeuxislo.cnbeta.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zeuxislo.cnbeta.R;
import com.zeuxislo.cnbeta.entry.CommentEntry;

public class CommentAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<CommentEntry> entryList;
	
	public CommentAdapter(Context context, ArrayList<CommentEntry> entryList) {
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
			convertView = this.layoutInflater.inflate(R.layout.comment_content_list, null);
			viewTag = new ViewTag();
						
			viewTag.description = (TextView) convertView.findViewById(R.id.comment_content_description);
			viewTag.body = (TextView) convertView.findViewById(R.id.comment_content_body);
			viewTag.marker = (TextView) convertView.findViewById(R.id.comment_content_marker);
			
			convertView.setTag(viewTag);
		}else{
			viewTag = (ViewTag) convertView.getTag();
		}
		
		CommentEntry commentEntry = this.entryList.get(position);
		
		viewTag.description.setText("第" + commentEntry.liveId + "樓 " + commentEntry.name + " 發表於 " + commentEntry.date);
		viewTag.body.setText(commentEntry.message);
		viewTag.marker.setText("支持(" + commentEntry.supportNumber + ") 反對(" + commentEntry.unSupportNumber + ")");

		return convertView;
	}
	
	class ViewTag {
		public TextView description;
		public TextView body;
		public TextView marker;
	}

}
