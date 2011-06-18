package com.zeuxislo.cnbeta.entry;

public class CommentEntry {

	public String liveId;
	public String name;
	public String date;
	public String message;
	public String unknow;
	public String supportNumber;
	public String unSupportNumber;
	
	public CommentEntry(String liveId, String name, String date, String message, String unknow, String supportNumber, String unSupportNumber) {
		this.liveId = liveId;
		this.name = name;
		this.date = date;
		this.message = message;
		this.unknow = unknow;
		this.supportNumber = supportNumber;
		this.unSupportNumber = unSupportNumber;
	}
}
