package com.ormodroid.core.transaction;

import com.ormodroid.core.exception.TransactionException;

public interface TransactionCallback<T> {
	
	T doInTransaction() throws TransactionException;

}
