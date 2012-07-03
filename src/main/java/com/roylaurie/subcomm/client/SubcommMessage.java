package com.roylaurie.subcomm.client;

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

public abstract class SubcommMessage {
    private static final char COLON = ':';
    private static final Pattern NETCHAT_TRIM_PATTERN = Pattern.compile("/[\\r\\n]/");
    private static final String EMPTY_STR = "";
    
    private final SubcommMessageType mType;   
    
    protected SubcommMessage(SubcommMessageType type) {
        mType = type;
    }
    
    public final SubcommMessageType getType() {
        return mType;
    }
    
    protected final String createNetchatMessage(String ... parameters) {
        StringBuffer buffer = new StringBuffer(getType().getNetchatPrefix());
        for (int i = 0, n = parameters.length; i < n; ++i) {
            String str = NETCHAT_TRIM_PATTERN.matcher(parameters[i].trim()).replaceAll(EMPTY_STR);
            buffer.append(str);
            if ((i+1) < n)
                buffer.append(COLON);
        }
        
        return buffer.toString();
    }    
    
    public ByteBuffer getUdpPacket() {
        throw new UnsupportedOperationException();
    }
    
    public abstract String getNetchatMessage();
}
