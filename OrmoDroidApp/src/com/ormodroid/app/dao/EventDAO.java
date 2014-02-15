package com.ormodroid.app.dao;

import android.database.sqlite.SQLiteDatabase;

import com.ormodroid.app.model.Event;
import com.ormodroid.core.dao.impl.GenericDAOSupport;

public class EventDAO extends GenericDAOSupport<Event> {

	public EventDAO(SQLiteDatabase mDb) {
		super(Event.class, mDb);
	}

}
