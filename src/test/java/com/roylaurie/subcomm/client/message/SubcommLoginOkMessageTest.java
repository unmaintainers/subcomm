package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommLoginOkMessageTest {
    @Test
    public void testParse() {
        SubcommMessage expected = new SubcommLoginOkMessage();
        SubcommMessage actual = SubcommLoginOkMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
