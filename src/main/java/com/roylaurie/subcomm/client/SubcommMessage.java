package com.roylaurie.subcomm.client;

import com.roylaurie.subcomm.client.netchat.MessageType;

public abstract class SubcommMessage {
    private final MessageType mType;
    
    protected SubcommMessage(MessageType type) {
        mType = type;
    }
    
    public final MessageType getType() {
        return mType;
    }
}
