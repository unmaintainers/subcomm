package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommPrivateCommandMessage extends SubcommMessage {
    private static final String NETCHAT_PREFIX = "SEND:PRIVCMD";
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*?):(.*)$"
    );
    
    private final String mUsername;
    private final String mCommand;
    
    /**
     * Creates a message object from a single raw netchat message.
     * @param String netchatMessage
     * @return SubcommPrivateCommandMessage NULL if the expected pattern doesn't match.
     * @throws IllegalArgumentException If the parameter values are unsupported
     */
    public static SubcommPrivateCommandMessage parseNetchatMessage(String netchatMessage) {
        Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            return null;
        
        String username = matcher.group(1);
        if (username == null || username.length() == 0)
            throw new IllegalArgumentException("Username not specified.");
        
        String command = matcher.group(2);
        if (command == null || command.length() == 0)
            throw new IllegalArgumentException("Command not specified.");
        
        return new SubcommPrivateCommandMessage(username, command);
    }     
    
    public SubcommPrivateCommandMessage(String username, String command) {
        super(NETCHAT_PREFIX);
        mUsername = username;
        mCommand = command;
    }
    
    public String getUsername() {
        return mUsername;
    }
    
    public String getCommand() {
        return mCommand;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mUsername, mCommand);
    }
}
