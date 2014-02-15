package com.ormodroid.core.transaction;

import android.database.sqlite.SQLiteDatabase;

import com.ormodroid.core.exception.TransactionException;

public class TransactionTemplate {
	private SQLiteDatabase mDb;

	public TransactionTemplate(SQLiteDatabase mDb) {
		this.mDb = mDb;
	}

	public <T> T execute(TransactionCallback<T> action) throws TransactionException {
		T result = null;

		mDb.beginTransaction();
		try {
			result = action.doInTransaction();
			mDb.setTransactionSuccessful();

		} catch (RuntimeException ex) {
			throw ex;
			
		} catch (Error err) {
			throw err;
			
		} catch (Exception ex) {
			throw new TransactionException(ex);
			
		} finally {
			mDb.endTransaction();
			
		}

		return result;
	}

}
