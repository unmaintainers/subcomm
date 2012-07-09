package com.roylaurie.subcomm.client.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.test.DataGenerator;

public final class SubcommSquadChatMessageTest {
    @Test
    public void testParse() {
        String squad = DataGenerator.generate();
        String message = DataGenerator.generate();
        SubcommMessage expected = new SubcommSquadChatMessage(squad, message);
        SubcommMessage actual = SubcommSquadChatMessage.parseNetchatMessage(expected.getNetchatMessage());
        assertEquals(expected, actual);
    }
}
