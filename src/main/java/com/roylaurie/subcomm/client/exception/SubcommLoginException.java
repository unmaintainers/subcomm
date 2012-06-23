package com.roylaurie.subcomm.client.exception;

import com.roylaurie.subcomm.client.SubcommException;

@SuppressWarnings("serial")
public class SubcommLoginException extends SubcommException {
    public SubcommLoginException(String message, Throwable cause) {
        super(message);
    }

    public SubcommLoginException(String message) {
        super(message);
    }
}
