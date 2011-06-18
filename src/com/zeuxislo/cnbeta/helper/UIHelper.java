package com.zeuxislo.cnbeta.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zeuxislo.cnbeta.Article;
import com.zeuxislo.cnbeta.R;
import com.zeuxislo.cnbeta.entry.PageEntry;

public class UIHelper {
	
	protected static final int MENU_SETTINGS = Menu.FIRST;
	protected static final int MENU_ABOUT = Menu.FIRST + 1;
	 
	public static void loadNavigatorControl(ViewGroup viewGroup) {
		LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_navigation, null);
		viewGroup.addView(linearLayout, 0);
	}
	
	public static void loadArticleHeaderControl(Context context, PageEntry pageEntry) {
		Article article = (Article) context;
		
		LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(article).inflate(R.layout.article_header, null);
		
		ListView article_content_list = (ListView) article.findViewById(R.id.article_content_list);
		article_content_list.addHeaderView(linearLayout);
		
		TextView article_header_title = (TextView) linearLayout.findViewById(R.id.article_header_title);
		TextView article_header_description = (TextView) linearLayout.findViewById(R.id.article_header_description);
		
		article_header_title.setText(pageEntry.title);
		article_header_description.setText(pageEntry.date + " | " + pageEntry.readCount + " 次閱讀");
		
		if (pageEntry.date != null && pageEntry.readCount != null) {
			article_header_description.setVisibility(View.VISIBLE);
		}
	}
	
	public static boolean onCreateOptionsMenu(Menu menu) {
		MenuItem settings = menu.add(0, MENU_SETTINGS, 0, "設    置");
		MenuItem about = menu.add(0, MENU_ABOUT, 1, "關    於");

		settings.setIcon(android.R.drawable.ic_menu_preferences);
		about.setIcon(android.R.drawable.ic_menu_info_details);

		return true;		
	}
	
	public static boolean onOptionsItemSelected(Context context, MenuItem menuItem) {
    	switch(menuItem.getItemId()) {
    		case MENU_ABOUT:
    			Toast.makeText(context, UtilityHelper.getAbout(context), Toast.LENGTH_LONG).show();
    			break;
    	}
    	
    	return true;
	}
	
}
