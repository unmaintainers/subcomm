package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommUnsupportedMessageTest {
    @Test
    public void testParse() {
        String netchatMessage = DataGenerator.generate();
        SubcommMessage expected = new SubcommUnsupportedMessage(netchatMessage);
        SubcommMessage actual = SubcommUnsupportedMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
