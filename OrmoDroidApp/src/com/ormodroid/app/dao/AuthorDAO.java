package com.ormodroid.app.dao;

import android.database.sqlite.SQLiteDatabase;

import com.ormodroid.app.model.Author;
import com.ormodroid.core.dao.impl.GenericDAOSupport;

public class AuthorDAO extends GenericDAOSupport<Author> {

	public AuthorDAO(SQLiteDatabase mDb) {
		super(Author.class, mDb);
	}

}
