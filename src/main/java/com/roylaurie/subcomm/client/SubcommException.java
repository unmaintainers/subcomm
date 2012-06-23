package com.roylaurie.subcomm.client;

@SuppressWarnings("serial")
public abstract class SubcommException extends Exception {
    public SubcommException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SubcommException(String message) {
        super(message);
    }
    
    public SubcommException(Throwable cause) {
        super(cause);
    }    
}
