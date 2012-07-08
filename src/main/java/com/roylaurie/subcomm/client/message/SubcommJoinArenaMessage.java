package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommJoinArenaMessage extends SubcommMessage {
	private static final String NETCHAT_PREFIX = "GO";
	private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*)$"
    );  
	
	public static SubcommCommandMessage parseNetChatMessage(String netchatMessage) {
        Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            throw new IllegalArgumentException("Unknown message format.");
        
        String arena = matcher.group(1);
        if (arena == null || arena.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommCommandMessage(arena);
    }
	
    private final String mArena;
    
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
