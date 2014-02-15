package com.ormodroid.core.transaction;

public abstract class TransactionCallbackWithoutResult implements
		TransactionCallback<Object> {

	@Override
	public final Object doInTransaction() {
		doInTransactionWithoutResult();
		return null;
	}

	protected abstract void doInTransactionWithoutResult();

}