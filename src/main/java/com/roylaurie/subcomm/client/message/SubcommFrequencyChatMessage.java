package com.roylaurie.subcomm.client.message;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommFrequencyChatMessage extends SubcommMessage {
    private final String mFrequency;
    private final String mMessage;
    
    public SubcommFrequencyChatMessage(String frequency, String message) {
        super(SubcommMessageType.SEND_FREQ);
        mFrequency = frequency;
        mMessage = message;
    }

    public String getFrequency() {
        return mFrequency;
    }    
    
    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mFrequency, mMessage);
    }
}
