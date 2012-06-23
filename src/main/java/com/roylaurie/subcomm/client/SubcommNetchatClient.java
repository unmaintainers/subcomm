package com.roylaurie.subcomm.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.roylaurie.subcomm.client.Command;
import com.roylaurie.subcomm.client.exception.SubcommIOException;
import com.roylaurie.subcomm.client.exception.SubcommLoginException;
import com.roylaurie.subcomm.client.exception.SubcommLoginTimeoutException;
import com.roylaurie.subcomm.client.exception.SubcommTimeoutException;
import com.roylaurie.subcomm.client.netchat.NetChatConnection;

/**
 * Connects to (blocking) a SubSpace server and allows sending / receiving of messages (non-blocking).
 * @author Roy Laurie <roy.laurie@gmail.com>
 */
public final class SubcommNetchatClient implements SubcommClient {
	private static final String PROTOCOL_TYPE = "1";
	private static final int LOGIN_SLEEP = 350;
	private static final char COLON = ':';
	private static final char SPACE = ' ';
	private static final String SUBCOMM_CLIENT = "SubcommClient";
	private static final char SEMICOLON = ';';
	private static final String ZERO = "0";
	
	private NetChatConnection mConnection;
    private final String mHost;
    private final int mPort;
    private final String mUsername;
    private final String mPassword;
    
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
     * @param String host
     * @param int port
     * @param String username
     * @param String password
     */
    public SubcommNetchatClient(String host, int port, String username, String password) {
        mHost = host;
        mPort = port;
        mUsername = username;
        mPassword = password;
    }

	/**
	 * Connects to the server (blocking), starts a connection thread, and logs in (blocking).
	 * @throws IOException
	 * @throws SubcommLoginException 
	 * @throws SubcommLoginTimeoutException 
	 * @throws SubcommTimeoutException 
	 */
	public void connect() throws SubcommIOException, SubcommLoginException, SubcommTimeoutException {
		if (connected())
			return;

		mConnection = new NetChatConnection(getHost(), getPort());
		try {
            mConnection.connect();
        } catch (UnknownHostException e) {
            throw new SubcommIOException(e);
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }

		Thread thread = new Thread(mConnection);
		String name = new StringBuffer(SUBCOMM_CLIENT).append(SPACE).append(mHost).append(COLON).append(mPort).toString();
		thread.setName(name);
		thread.start();
		
		login(getUsername(), getPassword());
	}

	/**
	 * @return boolean TRUE if connected, FALSE otherwise
	 */
	public boolean connected() {
		return (mConnection != null && mConnection.connected());
	}

	/**
	 * Disconnects quietly, if connected.
	 */
	public void disconnect() {
		if (!connected())
			return;

		mConnection.disconnect();
	}
	
	/**
	 * Retrieves the next received message (non-blocking).
	 * @return String The message or NULL if nothing received
	 * @throws IOException If not connected.
	 */
	public SubcommMessage nextReceivedMessage() throws SubcommIOException {
		String message = mConnection.nextReceivedMessage();
		return SubcommMessageParser.parse(message);
	}
	
	   /**
     * Retrieves the next received message (non-blocking).
     * @return String[] Empty if nothing received
     * @throws IOException If not connected.
     */
    public List<SubcommMessage> nextReceivedMessages() throws SubcommIOException {
        return mConnection.nextReceivedMessages();
    }

	/**
	 * Logs in (blocking) to the server.
	 * @param String username
	 * @param String password
	 * @throws IOException
	 * @throws SubcommLoginTimeoutException 
	 * @throws SubcommTimeoutException 
	 */
	private void login(String username, String password)
	                throws SubcommIOException, SubcommLoginException, SubcommTimeoutException {
		try {
            mConnection.send(Command.LOGIN, PROTOCOL_TYPE, username, password);
        } catch (IOException e) {
           throw new SubcommIOException(e);
        }
		long timeoutTime = System.currentTimeMillis() + 30000; // 30seconds
		
		String input;
        try {
            input = mConnection.nextReceivedMessage();
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
        
		while (System.currentTimeMillis() < timeoutTime) {
		    if (input == null) {
		        try {
		            Thread.sleep(LOGIN_SLEEP);
	            } catch (InterruptedException e) { /* do nothing */ }
		            
		        try {
		            input = mConnection.nextReceivedMessage();
		        } catch (IOException e) {
		            throw new SubcommIOException(e);
		        }
	            continue;
		    } else if (input.startsWith(Command.LOGINOK.toString())) {
		        return;
		    } else if (input.startsWith(Command.LOGINBAD.toString())) {
		        disconnect();
		        String reason = input.substring(Command.LOGINBAD.toString().length());
		        throw new SubcommLoginException("Unable to login. Reason: " + reason);
		    }
		}
		
		disconnect();
		throw new SubcommTimeoutException("Login timed out.");
	}		

	/**
	 * Changes to a different frequency.
	 * @param String frequency
	 * @throws IOException
	 */
	public void changeFrequency(String frequency) throws IOException {
		mConnection.send(Command.CHANGEFREQ, frequency);
	}

	/**
	 * Sends a SubSpace command.
	 * @param String command
	 * @throws IOException If not connected
	 */
	public void command(String command) throws IOException {
		mConnection.send(Command.SEND_CMD, command);
	}

	/**
	 * Sends a private SubSpace command.
	 * @param String username
	 * @param String command
	 * @throws IOException If not connected
	 */
	public void commandPrivate(String username, String command) throws IOException {
		mConnection.send(Command.SEND_PRIVCMD, username, command);
	}	
	
	/**
	 * Joins an arena.
	 * @param String arena
	 * @throws IOException If not connected
	 */
	public void joinArena(String arena) throws SubcommIOException {
		try {
            mConnection.send(Command.GO, arena);
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
	}
	
	/**
     * Joins the default '0' arena.
     * @throws IOException If not connected
     */
    public void joinDefaultArena() throws SubcommIOException {
        try {
            mConnection.send(Command.GO, ZERO);
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
    }
	
	/**
	 * Joins a channel.
	 * @param String channel
	 * @param String line
	 * @throws IOException If not connected
	 */
	public void messageChat(String channel, String message) throws SubcommIOException {
		try {
            mConnection.send(Command.SEND_CHAT, new StringBuffer(channel).append(SEMICOLON).append(message).toString());
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
	}
	
	/**
	 * Sends a message to a frequency.
	 * @param String frequency
	 * @param String message
	 * @throws IOException If not connected
	 */
	public void messageFrequency(String frequency, String message) throws SubcommIOException {
		try {
            mConnection.send(Command.SEND_FREQ, frequency, message);
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
	}

	/**
	 * Sends a message to all moderators.
	 * @param String message
	 * @throws IOException If not connected
	 */
	public void messageModerators(String message) throws SubcommIOException {
		try {
            mConnection.send(Command.SEND_MOD, message);
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
	}
	
	/**
	 * Sends a message to a single user.
	 * @param String username
	 * @param String message
	 * @throws IOException If not connected
	 */
	public void messagePrivate(String username, String message) throws SubcommIOException {
		try {
            mConnection.send(Command.SEND_PRIV, username, message);
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
	}
	
	/**
	 * Sends a message to the public.
	 * @param String message
	 * @throws IOException
	 */
	public void messagePublic(String message) throws SubcommIOException {
		try {
            mConnection.send(Command.SEND_PUB, message);
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
	}	
	
	/**
	 * Sends a emssage to a squad.
	 * @param String squad
	 * @param String message
	 * @throws IOException
	 */
	public void messageSquad(String squad, String message) throws SubcommIOException {
		try {
            mConnection.send(Command.SEND_SQUAD, squad, message);
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
	}
}
