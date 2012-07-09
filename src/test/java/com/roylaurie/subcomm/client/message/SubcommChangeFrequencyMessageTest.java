package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommChangeFrequencyMessageTest {
    @Test
    public void testParse() {
        String frequency = DataGenerator.generate();
        SubcommMessage expected = new SubcommChangeFrequencyMessage(frequency);
        SubcommMessage actual = SubcommChangeFrequencyMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
