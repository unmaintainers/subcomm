package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;

public final class SubcommNoOpMessageTest {
    @Test
    public void testParse() {
        SubcommMessage expected = new SubcommNoOpMessage();
        SubcommMessage actual = SubcommNoOpMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
