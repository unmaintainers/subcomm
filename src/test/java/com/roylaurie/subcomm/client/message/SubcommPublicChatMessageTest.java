package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommPublicChatMessageTest {
    @Test
    public void testParse() {
        String message = DataGenerator.generate();
        SubcommMessage expected = new SubcommPublicChatMessage(message);
        SubcommMessage actual = SubcommPublicChatMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
