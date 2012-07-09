package com.roylaurie.subcomm.client.netchat;

import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.message.SubcommChangeFrequencyMessage;
import com.roylaurie.subcomm.client.message.SubcommChannelChatMessage;
import com.roylaurie.subcomm.client.message.SubcommCommandMessage;
import com.roylaurie.subcomm.client.message.SubcommFrequencyChatMessage;
import com.roylaurie.subcomm.client.message.SubcommJoinArenaMessage;
import com.roylaurie.subcomm.client.message.SubcommLoginMessage;
import com.roylaurie.subcomm.client.message.SubcommLoginOkMessage;
import com.roylaurie.subcomm.client.message.SubcommModeratorsChatMessage;
import com.roylaurie.subcomm.client.message.SubcommNoOpMessage;
import com.roylaurie.subcomm.client.message.SubcommPrivateChatMessage;
import com.roylaurie.subcomm.client.message.SubcommPrivateCommandMessage;
import com.roylaurie.subcomm.client.message.SubcommPublicChatMessage;
import com.roylaurie.subcomm.client.message.SubcommSquadChatMessage;
import com.roylaurie.subcomm.client.message.SubcommUnsupportedMessage;

public final class SubcommNetchatMessageParser {
    public static SubcommMessage parse(String netchatMessage) {
        if (netchatMessage == null || netchatMessage.length() == 0)
            throw new IllegalArgumentException("Invalid message");
        
        SubcommMessage message = null;
        
        message = SubcommChangeFrequencyMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommChannelChatMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommCommandMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommFrequencyChatMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommJoinArenaMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommLoginMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommLoginOkMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommModeratorsChatMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommNoOpMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;        
        
        message = SubcommPrivateChatMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommPrivateCommandMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommPublicChatMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;
        
        message = SubcommSquadChatMessage.parseNetchatMessage(netchatMessage);
        if (message != null)
            return message;

        return new SubcommUnsupportedMessage(netchatMessage);
    }
}
