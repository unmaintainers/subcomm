package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.netchat.MessageType;

public class SubcommChatMessage extends SubcommMessage {
    private final String mMessage;
    
    protected SubcommChatMessage(String message) {
        super(MessageType.SEND_CHAT);
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
