package com.roylaurie.subcomm.client.exception;

@SuppressWarnings("serial")
public class SubcommLoginTimeoutException extends SubcommLoginException {
    public SubcommLoginTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
    public SubcommLoginTimeoutException(String message) {
        super(message);
    }
}
