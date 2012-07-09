package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommPublicChatMessage extends SubcommMessage {
    private static final String NETCHAT_PREFIX = "SEND:PUB";
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*)$"
    );
    
    private final String mMessage;
    
    /**
     * Creates a message object from a single raw netchat message.
     * @param String netchatMessage
     * @return SubcommPublicChatMessage NULL if the expected pattern doesn't match.
     * @throws IllegalArgumentException If the parameter values are unsupported
     */
    public static SubcommPublicChatMessage parseNetchatMessage(String netchatMessage) {
        Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            return null;

        String message = matcher.group(1);
        if (message == null || message.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommPublicChatMessage(message);
    }    
    
    public SubcommPublicChatMessage(String message) {
        super(NETCHAT_PREFIX);
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mMessage);
    }
}
