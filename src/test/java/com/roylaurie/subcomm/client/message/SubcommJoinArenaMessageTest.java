package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommJoinArenaMessageTest {
    @Test
    public void testParse() {
        String arena = DataGenerator.generate();
        SubcommMessage expected = new SubcommJoinArenaMessage(arena);
        SubcommMessage actual = SubcommJoinArenaMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
