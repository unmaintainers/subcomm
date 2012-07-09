package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommPrivateChatMessage extends SubcommMessage {
    private static final String NETCHAT_PREFIX = "SEND:PRIV";
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*?):(.*)$"
    );
    
    private final String mUsername;
    private final String mMessage;
    
    /**
     * Creates a message object from a single raw netchat message.
     * @param String netchatMessage
     * @return SubcommPrivateChatMessage NULL if the expected pattern doesn't match.
     * @throws IllegalArgumentException If the parameter values are unsupported
     */
    public static SubcommPrivateChatMessage parseNetchatMessage(String netchatMessage) {
        Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            return null;
        
        String username = matcher.group(1);
        if (username == null || username.length() == 0)
            throw new IllegalArgumentException("Username not specified.");
        
        String message = matcher.group(2);
        if (message == null || message.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommPrivateChatMessage(username, message);
    } 
    
    public SubcommPrivateChatMessage(String username, String message) {
        super(NETCHAT_PREFIX);
        mUsername = username;
        mMessage = message;
    }

    public String getUsername() {
        return mUsername;
    }    
    
    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mUsername, mMessage);
    }
}
