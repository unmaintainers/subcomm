package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommLoginMessageTest {
    @Test
    public void testParse() {
        String username = DataGenerator.generate();
        String password = DataGenerator.generate();
        SubcommMessage expected = new SubcommLoginMessage(username, password);
        SubcommMessage actual = SubcommLoginMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
