package com.zeuxislo.cnbeta.entry;

import java.io.Serializable;

public class PageEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String id;
	public String title;
	public String date;
	public String readCount;
	public String pushCount;
	public String categoryId;
	public String categoryImage; 
	public String categoryName;
	public String commentCount;

	public PageEntry(String id, String title, String date, String readCount, String pushCount, String categoryId, String categoryImage, String categoryName, String commentCount) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.readCount = readCount;
		this.pushCount = pushCount;
		this.categoryId = categoryId;
		this.categoryImage = categoryImage;
		this.categoryName = categoryName;
		this.commentCount = commentCount;
	}
}
