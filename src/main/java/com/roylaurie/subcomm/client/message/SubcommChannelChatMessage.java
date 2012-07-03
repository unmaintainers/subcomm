package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;

public final class SubcommChannelChatMessage extends SubcommMessage {
    private static final char SEMICOLON = ';';
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + SubcommMessageType.SEND_CHAT.getNetchatPrefix() + ":(.*)$"
    );
    
    private final String mChannel;
    private final String mMessage;
    
    public static SubcommMessage parseNetChatMessage(String netchatMessage) {
        Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            throw new IllegalArgumentException("Unknown message format.");
        
        String channel = matcher.group(1);
        if (channel == null || channel.length() == 0)
            throw new IllegalArgumentException("Channel not specified.");
        
        String message = matcher.group(1);
        if (message == null || message.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommChannelChatMessage(channel, message);
    }
    
    public SubcommChannelChatMessage(String channel, String message) {
        super(SubcommMessageType.SEND_CHAT);
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
