package com.roylaurie.subcomm.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommChangeFrequencyMessage extends SubcommMessage {
	private static final String NETCHAT_PREFIX = "CHANGEFREQ";
    private static final Pattern NETCHAT_PATTERN = Pattern.compile(
        "^" + NETCHAT_PREFIX + ":(.*)$"
    );  
    
    private final String mFrequency;
    
    /**
     * Creates a message object from a single raw netchat message.
     * @param String netchatMessage
     * @return SubcommChangeFrequencyMessage NULL if the expected pattern doesn't match.
     * @throws IllegalArgumentException If the parameter values are unsupported
     */
    public static SubcommChangeFrequencyMessage parseNetchatMessage(String netchatMessage) {
       Matcher matcher = NETCHAT_PATTERN.matcher(netchatMessage);
       if (!matcher.find())
           return null;
       
       String frequency = matcher.group(1);
       if (frequency == null || frequency.length() == 0)
           throw new IllegalArgumentException("Frequency not specified.");
       
       return new SubcommChangeFrequencyMessage(frequency);
    }  
    
    public SubcommChangeFrequencyMessage(String frequency) {
        super(NETCHAT_PREFIX);
        mFrequency = frequency;
    }

    public String getFrequency() {
        return mFrequency;
    }

    @Override
    public String getNetchatMessage() {
        return createNetchatMessage(mFrequency);
    }
}
