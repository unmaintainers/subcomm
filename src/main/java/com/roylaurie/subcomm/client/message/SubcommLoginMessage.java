package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommLoginMessage extends SubcommMessage {
	private static final String NETCHAT_PREFIX = "LOGIN";
	private static final String VERSION_ONE = "1";
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":\\d+:(.*?):(.*)$"
    );
    
    private final String mUsername;
    private final String mPassword;
    
   /**
    * Creates a message object from a single raw netchat message.
    * @param String netchatMessage
    * @return SubcommLoginMessage NULL if the expected pattern doesn't match.
    * @throws IllegalArgumentException If the parameter values are unsupported
    */
    public static SubcommLoginMessage parseNetchatMessage(String netchatMessage) {
		Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            return null;
        
        String username = matcher.group(1);
        if (username == null || username.length() == 0)
            throw new IllegalArgumentException("Username not specified.");
        
        String password = matcher.group(2);
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Password not specified.");
        
        return new SubcommLoginMessage(username, password);
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
