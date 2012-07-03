package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommModeratorsChatMessage extends SubcommMessage {
    private final String mMessage;
    
    public SubcommModeratorsChatMessage(String message) {
        super(SubcommMessageType.SEND_MOD);
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
