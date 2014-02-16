package com.rise.bean;

/**
 * Created by kai on 2/15/14.<br/>
 * Function :
 */
public class NotesItemOrder {
	public static final int TYPE_MONTH = 1;
	public static final int TYPE_ITEM = 2;

	private String month;
	private NotesItem item;
	private int type;
	private String year;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public NotesItem getItem() {
		return item;
	}

	public void setItem(NotesItem item) {
		this.item = item;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
