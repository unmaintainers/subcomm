package com.roylaurie.subcomm.client.exception;

import com.roylaurie.subcomm.client.SubcommException;


@SuppressWarnings("serial")
public final class SubcommTimeoutException extends SubcommException {
    public SubcommTimeoutException(String message) {
        super(message);
    }

    public SubcommTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
