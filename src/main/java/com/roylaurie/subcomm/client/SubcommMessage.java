package com.roylaurie.subcomm.client;

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

public abstract class SubcommMessage {
    private static final char COLON = ':';
    private static final Pattern NETCHAT_TRIM_PATTERN = Pattern.compile("/[\\r\\n]/");
    private static final String EMPTY_STR = "";
    
    private final String mNetchatPrefix;   
    
    protected SubcommMessage(String netchatPrefix) {
        mNetchatPrefix = netchatPrefix;
    }
    
    public final String getNetchatPrefix() {
        return mNetchatPrefix;
    }
    
    protected final String createNetchatMessage(String ... parameters) {
        StringBuffer buffer = new StringBuffer(mNetchatPrefix).append(COLON);
        for (int i = 0, n = parameters.length; i < n; ++i) {
            String str = NETCHAT_TRIM_PATTERN.matcher(parameters[i].trim()).replaceAll(EMPTY_STR);
            buffer.append(str);
            if ((i+1) < n)
                buffer.append(COLON);
        }
        
        return buffer.toString();
    }    
    
    @Override
    public final String toString() {
        return getNetchatMessage();
    }
    
    @Override
    public final int hashCode() {
        return getNetchatMessage().hashCode();
    }
    
    @Override
    public final boolean equals(Object ro) {
        if (ro == null || !getClass().equals(ro.getClass()))
            return false;
        
        return getNetchatMessage().equals(((SubcommMessage)ro).getNetchatMessage());
    }
    
    public ByteBuffer getUdpPacket() {
        throw new UnsupportedOperationException();
    }
    
    public abstract String getNetchatMessage();
}
