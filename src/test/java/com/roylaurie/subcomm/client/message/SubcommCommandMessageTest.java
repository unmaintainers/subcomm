package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommCommandMessageTest {
    @Test
    public void testParse() {
        String command = DataGenerator.generate();
        SubcommMessage expected = new SubcommCommandMessage(command);
        SubcommMessage actual = SubcommCommandMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
