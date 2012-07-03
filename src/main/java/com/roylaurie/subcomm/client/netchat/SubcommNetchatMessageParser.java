package com.roylaurie.subcomm.client.netchat;

import java.util.regex.Pattern;

import com.roylaurie.subcomm.client.SubcommException;
import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;
import com.roylaurie.subcomm.client.message.SubcommChangeFrequencyMessage;
import com.roylaurie.subcomm.client.message.SubcommChannelChatMessage;
import com.roylaurie.subcomm.client.message.SubcommCommandMessage;
import com.roylaurie.subcomm.client.message.SubcommFrequencyChatMessage;
import com.roylaurie.subcomm.client.message.SubcommJoinArenaMessage;
import com.roylaurie.subcomm.client.message.SubcommModeratorsChatMessage;
import com.roylaurie.subcomm.client.message.SubcommPrivateChatMessage;
import com.roylaurie.subcomm.client.message.SubcommPublicChatMessage;
import com.roylaurie.subcomm.client.message.SubcommSquadChatMessage;

public final class SubcommNetchatMessageParser {
    public static SubcommMessage parse(String netchatMessage) {
        if (netchatMessage == null || netchatMessage.length() == 0)
            throw new IllegalArgumentException("Invalid message");
        
        try {
            return SubcommChangeFrequencyMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        try {
            return SubcommChannelChatMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        try {
            return SubcommCommandMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        try {
            return SubcommFrequencyChatMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        try {
            return SubcommJoinArenaMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        try {
            return SubcommModeratorsChatMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        try {
            return SubcommPrivateChatMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        try {
            return SubcommPublicChatMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        try {
            return SubcommSquadChatMessage.parseNetChatMessage(netchatMessage);
        } catch (SubcommException e) { /* do nothing */ }
        
        return null;
    }
}
