package com.ormodroid.core.exception;

public class TransactionException extends Exception {

	private static final long serialVersionUID = 3461709423521512536L;

	public TransactionException() {
		super();
	}

	public TransactionException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public TransactionException(String detailMessage) {
		super(detailMessage);
	}

	public TransactionException(Throwable throwable) {
		super(throwable);
	}

}
