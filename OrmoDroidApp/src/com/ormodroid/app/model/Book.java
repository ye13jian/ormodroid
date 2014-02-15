package com.ormodroid.app.model;

import com.ormodroid.core.annotation.SQLiteColumn;
import com.ormodroid.core.annotation.SQLitePrimaryKey;
import com.ormodroid.core.annotation.SQLiteTable;

@SQLiteTable(name = "book")
public class Book {

	@SQLitePrimaryKey
	@SQLiteColumn(name = "_id")
	private Long id;

	@SQLiteColumn(name = "title")
	private String title;

	@SQLiteColumn(name = "author_id")
	private Long authorId;

	public Book() {
	}

	public Book(String title, Long authorId) {
		super();
		this.title = title;
		this.authorId = authorId;
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

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

}
