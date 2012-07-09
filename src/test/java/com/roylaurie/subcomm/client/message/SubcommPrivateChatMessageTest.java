package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommPrivateChatMessageTest {
    @Test
    public void testParse() {
        String username = DataGenerator.generate();
        String message = DataGenerator.generate();
        SubcommMessage expected = new SubcommPrivateChatMessage(username, message);
        SubcommMessage actual = SubcommPrivateChatMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
