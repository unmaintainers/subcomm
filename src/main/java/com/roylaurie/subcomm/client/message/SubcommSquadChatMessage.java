package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommSquadChatMessage extends SubcommMessage {
    private final String mSquad;
    private final String mMessage;
    
    public SubcommSquadChatMessage(String squad, String message) {
        super(SubcommMessageType.SEND_SQUAD);
        mSquad = squad;
        mMessage = message;
    }

    public String getSquad() {
        return mSquad;
    }    
    
    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mSquad, mMessage);
    }
}
