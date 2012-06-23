package com.roylaurie.subcomm.client.exception;


@SuppressWarnings("serial")
public final class SubcommDisconnectedException extends SubcommIOException{
    public SubcommDisconnectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubcommDisconnectedException(String message) {
        super(message);
    }
}
