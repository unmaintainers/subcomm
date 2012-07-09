package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommFrequencyChatMessageTest {
    @Test
    public void testParse() {
        String frequency = DataGenerator.generate();
        String message = DataGenerator.generate();
        SubcommMessage expected = new SubcommFrequencyChatMessage(frequency, message);
        SubcommMessage actual = SubcommFrequencyChatMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
