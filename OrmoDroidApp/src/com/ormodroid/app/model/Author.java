package com.ormodroid.app.model;

import java.util.Date;

import com.ormodroid.core.annotation.SQLiteColumn;
import com.ormodroid.core.annotation.SQLitePrimaryKey;
import com.ormodroid.core.annotation.SQLiteTable;

@SQLiteTable(name = "author")
public class Author {

	@SQLitePrimaryKey
	@SQLiteColumn(name = "_id")
	private Long id;

	@SQLiteColumn(name = "name")
	private String name;

	@SQLiteColumn(name = "dob")
	private Date dob;

	@SQLiteColumn(name = "num_of_books")
	private Integer numOfBooks;

	public Author() {
	}

	public Author(String name, Date dob) {
		this.name = name;
		this.dob = dob;
		this.numOfBooks = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getNumOfBooks() {
		return numOfBooks;
	}

	public void setNumOfBooks(Integer numOfBooks) {
		this.numOfBooks = numOfBooks;
	}

	public void incrementNumOfBooks() {
		this.numOfBooks++;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", dob=" + dob
				+ ", numOfBooks=" + numOfBooks + "]";
	}

}
