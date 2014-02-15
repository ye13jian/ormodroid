package com.ormodroid.app.schema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "event.db";
    
    private static final String eventCreateTableSQL = "CREATE TABLE event (_id INTEGER PRIMARY KEY, title TEXT)";
    private static final String authorCreateTableSQL = "CREATE TABLE author (_id INTEGER PRIMARY KEY, name TEXT, dob TEXT, num_of_books INTEGER)";
    private static final String bookCreateTableSQL = "CREATE TABLE book (_id INTEGER PRIMARY KEY, title TEXT, author_id INTEGER)";
    
    private static final String eventDropTableSQL = "DROP TABLE IF EXISTS event";
    private static final String authorDropTableSQL = "DROP TABLE IF EXISTS author";
    private static final String bookDropTableSQL = "DROP TABLE IF EXISTS book";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(eventCreateTableSQL);
		db.execSQL(authorCreateTableSQL);
		db.execSQL(bookCreateTableSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL(eventDropTableSQL);
		db.execSQL(authorDropTableSQL);
		db.execSQL(bookDropTableSQL);
        onCreate(db);
	}
	
}
