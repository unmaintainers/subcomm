package com.roylaurie.subcomm.client;

import java.util.List;

import com.roylaurie.subcomm.client.message.SubcommChangeFrequencyMessage;
import com.roylaurie.subcomm.client.message.SubcommChannelChatMessage;
import com.roylaurie.subcomm.client.message.SubcommCommandMessage;
import com.roylaurie.subcomm.client.message.SubcommFrequencyChatMessage;
import com.roylaurie.subcomm.client.message.SubcommJoinArenaMessage;
import com.roylaurie.subcomm.client.message.SubcommModeratorsChatMessage;
import com.roylaurie.subcomm.client.message.SubcommPrivateChatMessage;
import com.roylaurie.subcomm.client.message.SubcommPrivateCommandMessage;
import com.roylaurie.subcomm.client.message.SubcommPublicChatMessage;
import com.roylaurie.subcomm.client.message.SubcommSquadChatMessage;


public abstract class SubcommClient {
    private static final String ZERO = null;
    private final String mHost;
    private final int mPort;
    private final String mUsername;
    private final String mPassword;
    
    /**
     * @param String host
     * @param int port
     * @param String username
     * @param String password
     */
    public SubcommClient(String host, int port, String username, String password) {
        mHost = host;
        mPort = port;
        mUsername = username;
        mPassword = password;
    }    
    
    public String getHost() {
        return mHost;
    }

    public int getPort() {
        return mPort;
    }

    public String getUsername() {
        return mUsername;
    }
    
    protected String getPassword() {
        return mPassword;
    }

    /**
     * Connects to the server (blocking), starts a connection thread, and logs in (blocking).
     * @throws SubcommException
     */
    public abstract void connect() throws SubcommException;

    /**
     * @return boolean TRUE if connected, FALSE otherwise
     */
    public abstract boolean connected();

    /**
     * Disconnects quietly, if connected.
     */
    public abstract void disconnect();
    
    /**
     * Retrieves the next received message (non-blocking).
     * @return SubcommMessage The message or NULL if nothing received
     * @throws SubcommException
     */
    public abstract SubcommMessage nextReceivedMessage() throws SubcommException;
    
    /**
     * Retrieves the next received message (non-blocking).
     * @return String[] Empty if nothing received
     * @throws SubcommException
     */
    public abstract List<SubcommMessage> nextReceivedMessages() throws SubcommException;

    public abstract void send(SubcommMessage message) throws SubcommException;
    
    /**
     * Changes to a different frequency.
     * @param String frequency
     * @throws SubcommException
     */
    public void changeFrequency(String frequency) throws SubcommException {
       send(new SubcommChangeFrequencyMessage(frequency));
    }

    /**
     * Sends a SubSpace command.
     * @param String command
     * @throws SubcommException
     */
    public void command(String command) throws SubcommException {
        send(new SubcommCommandMessage(command));
    }

    /**
     * Sends a private SubSpace command.
     * @param String username
     * @param String command
     * @throws SubcommException
     */
    public void commandPrivate(String username, String command) throws SubcommException {
       send(new SubcommPrivateCommandMessage(username, command));
    }   
    
    /**
     * Joins an arena.
     * @param String arena
     * @throws SubcommException
     */
    public void joinArena(String arena) throws SubcommException {
        send(new SubcommJoinArenaMessage(arena));
    }
    
    /**
     * Joins the default '0' arena.
     * @throws SubcommException
     */
    public void joinDefaultArena() throws SubcommException {
        joinArena(ZERO);
    }
    
    /**
     * Sends a message to the public.
     * @param String message
     * @throws SubcommException
     */
    public void chatPublic(String message) throws SubcommException {
       send(new SubcommPublicChatMessage(message));
    }      
    
    /**
     * Sends a message to a channel.
     * @param String channel
     * @param String message
     * @throws SubcommException
     */
    public void chatChannel(String channel, String message) throws SubcommException {
        send(new SubcommChannelChatMessage(channel, message));
    }
    
    /**
     * Sends a message to a frequency.
     * @param String frequency
     * @param String message
     * @throws SubcommException
     */
    public void chatFrequency(String frequency, String message) throws SubcommException {
        send(new SubcommFrequencyChatMessage(frequency, message));
    }

    /**
     * Sends a message to all moderators.
     * @param String message
     * @throws SubcommException
     */
    public void chatModerators(String message) throws SubcommException {
        send(new SubcommModeratorsChatMessage(message));
    }
    
    /**
     * Sends a message to a single user.
     * @param String username
     * @param String message
     * @throws SubcommException
     */
    public void chatPrivate(String username, String message) throws SubcommException {
       send(new SubcommPrivateChatMessage(username, message));
    } 
    
    /**
     * Sends a message to a squad.
     * @param String squad
     * @param String message
     * @throws SubcommException
     */
    public void chatSquad(String squad, String message) throws SubcommException {
        send(new SubcommSquadChatMessage(squad, message));
    }
}
