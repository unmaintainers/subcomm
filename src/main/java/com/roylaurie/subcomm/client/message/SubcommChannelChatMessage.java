package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommChannelChatMessage extends SubcommMessage {
	private static final String NETCHAT_PREFIX = "SEND:CHAT";
    private static final char SEMICOLON = ';';
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*?):(.*)$"
    );
    
    private final String mChannel;
    private final String mMessage;
    
    public static SubcommChannelChatMessage parseNetChatMessage(String netchatMessage) {
        Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            throw new IllegalArgumentException("Unknown message format.");
        
        String channel = matcher.group(1);
        if (channel == null || channel.length() == 0)
            throw new IllegalArgumentException("Channel not specified.");
        
        String message = matcher.group(2);
        if (message == null || message.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommChannelChatMessage(channel, message);
    }
    
    public SubcommChannelChatMessage(String channel, String message) {
        super(NETCHAT_PREFIX);
        mChannel = channel;
        mMessage = message;
    }

    public String getChannel() {
        return mChannel;
    }    
    
    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(new StringBuffer(mChannel).append(SEMICOLON).append(mMessage).toString());
    }
}
