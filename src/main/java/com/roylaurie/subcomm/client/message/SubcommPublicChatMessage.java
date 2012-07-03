package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommPublicChatMessage extends SubcommMessage {
    private final String mMessage;
    
    public SubcommPublicChatMessage(String message) {
        super(SubcommMessageType.SEND_PUB);
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mMessage);
    }
}
