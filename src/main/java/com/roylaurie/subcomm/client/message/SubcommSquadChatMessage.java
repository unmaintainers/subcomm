package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommSquadChatMessage extends SubcommMessage {
    private static final String NETCHAT_PREFIX = "SEND:SQUAD";
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*?):(.*)$"
    );
    
    private final String mSquad;
    private final String mMessage;
    
    /**
     * Creates a message object from a single raw netchat message.
     * @param String netchatMessage
     * @return SubcommSquadChatMessage NULL if the expected pattern doesn't match.
     * @throws IllegalArgumentException If the parameter values are unsupported
     */
    public static SubcommSquadChatMessage parseNetchatMessage(String netchatMessage) {
        Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            return null;

        String squad = matcher.group(1);
        if (squad == null || squad.length() == 0)
            throw new IllegalArgumentException("Squad not specified.");        
        
        String message = matcher.group(2);
        if (message == null || message.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommSquadChatMessage(squad, message);
    }    
    
    public SubcommSquadChatMessage(String squad, String message) {
        super(NETCHAT_PREFIX);
        mSquad = squad;
        mMessage = message;
    }

    public String getSquad() {
        return mSquad;
    }    
    
    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mSquad, mMessage);
    }
}
