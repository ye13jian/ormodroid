package com.ormodroid.core.exception;

public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = -3750769917247498654L;

	public ObjectNotFoundException() {
		super();
	}

	public ObjectNotFoundException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ObjectNotFoundException(String detailMessage) {
		super(detailMessage);
	}

	public ObjectNotFoundException(Throwable throwable) {
		super(throwable);
	}

}
