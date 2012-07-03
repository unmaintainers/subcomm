package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommJoinArenaMessage extends SubcommMessage {
    private final String mArena;
    
    public SubcommJoinArenaMessage(String arena) {
        super(SubcommMessageType.GO);
        mArena = arena;
    }

    public String getArena() {
        return mArena;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mArena);
    }
}
