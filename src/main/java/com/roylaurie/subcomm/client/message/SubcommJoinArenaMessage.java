package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommJoinArenaMessage extends SubcommMessage {
	private static final String NETCHAT_PREFIX = "GO";
	private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*)$"
    );  
	
    private final String mArena;
    
    /**
     * Creates a message object from a single raw netchat message.
     * @param String netchatMessage
     * @return SubcommJoinArenaMessage NULL if the expected pattern doesn't match.
     * @throws IllegalArgumentException If the parameter values are unsupported
     */
    public static SubcommJoinArenaMessage parseNetchatMessage(String netchatMessage) {
        Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            return null;
        
        String arena = matcher.group(1);
        if (arena == null || arena.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommJoinArenaMessage(arena);
    }    
    
    public SubcommJoinArenaMessage(String arena) {
        super(NETCHAT_PREFIX);
        mArena = arena;
    }

    public String getArena() {
        return mArena;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mArena);
    }
}
