package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommLoginOkMessage extends SubcommMessage {
	private static final String NETCHAT_PREFIX = "LOGINOK";
	
    public SubcommLoginOkMessage() {
        super(SubcommMessageType.LOGINOK);
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage();
    }
}
