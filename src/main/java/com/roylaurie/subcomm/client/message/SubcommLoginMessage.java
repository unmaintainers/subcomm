package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommLoginMessage extends SubcommMessage {
	private static final String NETCHAT_PREFIX = "LOGIN";
	private static final String VERSION_ONE = "1";
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*)$"
    );
    
    private final String mUsername;
    private final String mPassword;
    
    public static SubcommMessage parseNetChatMessage(String netchatMessage) {
		Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            throw new IllegalArgumentException("Unknown message format.");
        
        String frequency = matcher.group(1);
        if (frequency == null || frequency.length() == 0)
            throw new IllegalArgumentException("Frequency not specified.");
        
        String message = matcher.group(2);
        if (message == null || message.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommChannelChatMessage(frequency, message);
	}       
    
    public SubcommLoginMessage(String username, String password) {
        super(NETCHAT_PREFIX);
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }
    
    public String getPassword() {
    	return mPassword;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(VERSION_ONE, mUsername, mPassword);
    }
}
