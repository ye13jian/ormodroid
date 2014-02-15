package com.ormodroid.app.model;

import com.ormodroid.core.annotation.SQLiteColumn;
import com.ormodroid.core.annotation.SQLitePrimaryKey;
import com.ormodroid.core.annotation.SQLiteTable;

@SQLiteTable(name = "event")
public class Event {
	
	@SQLitePrimaryKey
	@SQLiteColumn(name = "_id")
	private Long id;

	@SQLiteColumn(name = "title")
	private String title;

	public Event() {
	}
	
	public Event(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + "]";
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
