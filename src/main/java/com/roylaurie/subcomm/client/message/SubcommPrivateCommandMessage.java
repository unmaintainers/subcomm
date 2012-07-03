package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommPrivateCommandMessage extends SubcommMessage {
    private final String mUsername;
    private final String mCommand;
    
    public SubcommPrivateCommandMessage(String username, String command) {
        super(SubcommMessageType.SEND_PRIVCMD);
        mUsername = username;
        mCommand = command;
    }
    
    public String getUsername() {
        return mUsername;
    }
    
    public String getCommand() {
        return mCommand;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mCommand);
    }
}
