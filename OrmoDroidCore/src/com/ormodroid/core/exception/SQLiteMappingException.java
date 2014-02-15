package com.ormodroid.core.exception;

public class SQLiteMappingException extends RuntimeException {
	private static final long serialVersionUID = -6799604460820738802L;

	public SQLiteMappingException() {
        super();
    }

    public SQLiteMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLiteMappingException(String message) {
        super(message);
    }

    public SQLiteMappingException(Throwable cause) {
        super(cause);
    }

}
