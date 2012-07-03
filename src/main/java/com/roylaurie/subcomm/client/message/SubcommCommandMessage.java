package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommCommandMessage extends SubcommMessage {
    private final String mCommand;
    
    public SubcommCommandMessage(String command) {
        super(SubcommMessageType.SEND_CMD);
        mCommand = command;
    }

    public String getCommand() {
        return mCommand;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mCommand);
    }
}
