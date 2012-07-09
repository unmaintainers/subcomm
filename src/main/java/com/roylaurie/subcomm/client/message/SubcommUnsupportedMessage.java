package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommUnsupportedMessage extends SubcommMessage {
    private static final String EMPTY_STR = "";
    
    private final String mNetchatMessage;
    
   /**
    * Creates a message object from a single raw netchat message.
    * @param String netchatMessage
    * @return SubcommLoginOkMessage NULL if the expected pattern doesn't match.
    * @throws IllegalArgumentException If the parameter values are unsupported
    */
    public static SubcommUnsupportedMessage parseNetchatMessage(String netchatMessage) {
        return new SubcommUnsupportedMessage(netchatMessage);
    }  	
	
    public SubcommUnsupportedMessage(String netchatMessage) {
        super(EMPTY_STR);
        mNetchatMessage = netchatMessage;
    }

    @Override
    public String getNetchatMessage() {
       return mNetchatMessage;
    }
}
