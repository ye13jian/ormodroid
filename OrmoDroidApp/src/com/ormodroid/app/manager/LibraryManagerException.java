package com.ormodroid.app.manager;

public class LibraryManagerException extends Exception {

	private static final long serialVersionUID = -3750769917247498654L;

	public LibraryManagerException() {
		super();
	}

	public LibraryManagerException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public LibraryManagerException(String detailMessage) {
		super(detailMessage);
	}

	public LibraryManagerException(Throwable throwable) {
		super(throwable);
	}

}
