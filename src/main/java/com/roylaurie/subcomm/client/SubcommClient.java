package com.roylaurie.subcomm.client;

import java.io.IOException;
import java.util.List;


public interface SubcommClient {
    public String getHost();
    public int getPort();
    public String getUsername();

    /**
     * Connects to the server (blocking), starts a connection thread, and logs in (blocking).
     * @throws SubcommException
     */
    public void connect() throws SubcommException;

    /**
     * @return boolean TRUE if connected, FALSE otherwise
     */
    public boolean connected();

    /**
     * Disconnects quietly, if connected.
     */
    public void disconnect();
    
    /**
     * Retrieves the next received message (non-blocking).
     * @return SubcommMessage The message or NULL if nothing received
     * @throws IOException If not connected.
     */
    public SubcommMessage nextReceivedMessage() throws SubcommException;
    
       /**
     * Retrieves the next received message (non-blocking).
     * @return String[] Empty if nothing received
     * @throws IOException If not connected.
     */
    public List<SubcommMessage> nextReceivedMessages() throws SubcommException;

    /**
     * Changes to a different frequency.
     * @param String frequency
     * @throws IOException
     */
    public void changeFrequency(String frequency) throws IOException;

    /**
     * Sends a SubSpace command.
     * @param String command
     * @throws IOException If not connected
     */
    public void command(String command) throws IOException;

    /**
     * Sends a private SubSpace command.
     * @param String username
     * @param String command
     * @throws IOException If not connected
     */
    public void commandPrivate(String username, String command) throws IOException;
    
    /**
     * Joins an arena.
     * @param String arena
     * @throws IOException If not connected
     */
    public void joinArena(String arena) throws SubcommException;
    
    /**
     * Joins the default '0' arena.
     * @throws IOException If not connected
     */
    public void joinDefaultArena() throws SubcommException;
    
    /**
     * Joins a channel.
     * @param String channel
     * @param String line
     * @throws IOException If not connected
     */
    public void messageChat(String channel, String message) throws SubcommException;
    
    /**
     * Sends a message to a frequency.
     * @param String frequency
     * @param String message
     * @throws IOException If not connected
     */
    public void messageFrequency(String frequency, String message) throws SubcommException;

    /**
     * Sends a message to all moderators.
     * @param String message
     * @throws IOException If not connected
     */
    public void messageModerators(String message) throws SubcommException;
    
    /**
     * Sends a message to a single user.
     * @param String username
     * @param String message
     * @throws IOException If not connected
     */
    public void messagePrivate(String username, String message) throws SubcommException;
    
    /**
     * Sends a message to the public.
     * @param String message
     * @throws IOException
     */
    public void messagePublic(String message) throws SubcommException;
    
    /**
     * Sends a emssage to a squad.
     * @param String squad
     * @param String message
     * @throws IOException
     */
    public void messageSquad(String squad, String message) throws SubcommException;
}
