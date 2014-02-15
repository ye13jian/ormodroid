package com.ormodroid.app.dao;

import android.database.sqlite.SQLiteDatabase;

import com.ormodroid.app.model.Book;
import com.ormodroid.core.dao.impl.GenericDAOSupport;

public class BookDAO extends GenericDAOSupport<Book> {

	public BookDAO(SQLiteDatabase mDb) {
		super(Book.class, mDb);
	}

}
