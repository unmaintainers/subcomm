package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommPrivateChatMessage extends SubcommMessage {
    private final String mUsername;
    private final String mMessage;
    
    public SubcommPrivateChatMessage(String username, String message) {
        super(SubcommMessageType.SEND_PRIV);
        mUsername = username;
        mMessage = message;
    }

    public String getUsername() {
        return mUsername;
    }    
    
    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mUsername, mMessage);
    }
}
