package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommFrequencyChatMessage extends SubcommMessage {
	private static final String NETCHAT_PREFIX = "SEND:FREQ";
	private static final Pattern NETCHAT_PATTERN = Pattern.compile(
		"^" + NETCHAT_PREFIX + ":(.*?):(.*)$"
    );
	
    private final String mFrequency;
    private final String mMessage;
    
    /**
     * Creates a message object from a single raw netchat message.
     * @param String netchatMessage
     * @return SubcommFrequencyChatMessage NULL if the expected pattern doesn't match.
     * @throws IllegalArgumentException If the parameter values are unsupported
     */
	public static SubcommFrequencyChatMessage parseNetchatMessage(String netchatMessage) {
		Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
        if (!matcher.find())
            return null;
        
        String frequency = matcher.group(1);
        if (frequency == null || frequency.length() == 0)
            throw new IllegalArgumentException("Frequency not specified.");
        
        String message = matcher.group(2);
        if (message == null || message.length() == 0)
            throw new IllegalArgumentException("Message not specified.");
        
        return new SubcommFrequencyChatMessage(frequency, message);
	}    
    
    public SubcommFrequencyChatMessage(String frequency, String message) {
        super(NETCHAT_PREFIX);
        mFrequency = frequency;
        mMessage = message;
    }

    public String getFrequency() {
        return mFrequency;
    }    
    
    public String getMessage() {
        return mMessage;
    }

    @Override
    public String getNetchatMessage() {
       return createNetchatMessage(mFrequency, mMessage);
    }
}
