package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommChannelChatMessageTest {
    @Test
    public void testParse() {
        String channel = DataGenerator.generate();
        String message = DataGenerator.generate();
        SubcommMessage expected = new SubcommChannelChatMessage(channel, message);
        SubcommMessage actual = SubcommChannelChatMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
