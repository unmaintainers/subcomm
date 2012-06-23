package com.roylaurie.subcomm.client.exception;

import com.roylaurie.subcomm.client.SubcommException;

@SuppressWarnings("serial")
public class SubcommIOException extends SubcommException {
    public SubcommIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubcommIOException(String message) {
        super(message);
    }
    
    public SubcommIOException(Throwable cause) {
        super(cause);
    }    
}
