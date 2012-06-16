package com.roylaurie.subcomm;

import java.io.IOException;

/**
 * Connects to (blocking) a SubSpace server and allows sending / receiving of messages (non-blocking).
 * @author Roy Laurie <roy.laurie@gmail.com>
 */
public final class SubcommClient {
	private static final String PROTOCOL_TYPE = "1";
	private static final int LOGIN_SLEEP = 350;
	private static final char COLON = ':';
	private static final char SPACE = ' ';
	private static final String SUBCOMM_CLIENT = "SubcommClient";
	private static final char SEMICOLON = ';';
	private static final String ZERO = "0";
	
	private final String mHost;
	private final int mPort;
	private final String mUsername;
	private final String mPassword;
	private Connection mConnection;

	public String getHost() {
        return mHost;
    }

    public int getPort() {
        return mPort;
    }

    public String getUsername() {
        return mUsername;
    }

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

	/**
	 * Connects to the server (blocking), starts a connection thread, and logs in (blocking).
	 * @throws IOException
	 */
	public void connect() throws IOException {
		if (connected())
			return;

		mConnection = new Connection(mHost, mPort);
		mConnection.connect();

		Thread thread = new Thread(mConnection);
		String name = new StringBuffer(SUBCOMM_CLIENT).append(SPACE).append(mHost).append(COLON).append(mPort).toString();
		thread.setName(name);
		thread.start();
		
		login(mUsername, mPassword);
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
	public String nextReceivedMessage() throws IOException {
		return mConnection.nextReceivedMessage();
	}
	
	   /**
     * Retrieves the next received message (non-blocking).
     * @return String[] Empty if nothing received
     * @throws IOException If not connected.
     */
    public String[] nextReceivedMessages() throws IOException {
        return mConnection.nextReceivedMessages();
    }

	/**
	 * Logs in (blocking) to the server.
	 * @param String username
	 * @param String password
	 * @throws IOException
	 */
	private void login(String username, String password) throws IOException {
		mConnection.send(Command.LOGIN, PROTOCOL_TYPE, username, password);
		String input = mConnection.nextReceivedMessage();
		while (input == null || !input.startsWith(Command.LOGINOK.toString())) {
			try {
				Thread.sleep(LOGIN_SLEEP);
			} catch (InterruptedException e) { /* do nothing */ }
			
			input = mConnection.nextReceivedMessage();
		}
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
	public void joinArena(String arena) throws IOException {
		mConnection.send(Command.GO, arena);
	}
	
	/**
     * Joins the default '0' arena.
     * @throws IOException If not connected
     */
    public void joinDefaultArena() throws IOException {
        mConnection.send(Command.GO, ZERO);
    }
	
	/**
	 * Joins a channel.
	 * @param String channel
	 * @param String line
	 * @throws IOException If not connected
	 */
	public void messageChat(String channel, String message) throws IOException {
		mConnection.send(Command.SEND_CHAT, new StringBuffer(channel).append(SEMICOLON).append(message).toString());
	}
	
	/**
	 * Sends a message to a frequency.
	 * @param String frequency
	 * @param String message
	 * @throws IOException If not connected
	 */
	public void messageFrequency(String frequency, String message) throws IOException {
		mConnection.send(Command.SEND_FREQ, frequency, message);
	}

	/**
	 * Sends a message to all moderators.
	 * @param String message
	 * @throws IOException If not connected
	 */
	public void messageModerators(String message) throws IOException {
		mConnection.send(Command.SEND_MOD, message);
	}
	
	/**
	 * Sends a message to a single user.
	 * @param String username
	 * @param String message
	 * @throws IOException If not connected
	 */
	public void messagePrivate(String username, String message) throws IOException {
		mConnection.send(Command.SEND_PRIV, username, message);
	}
	
	/**
	 * Sends a message to the public.
	 * @param String message
	 * @throws IOException
	 */
	public void messagePublic(String message) throws IOException {
		mConnection.send(Command.SEND_PUB, message);
	}	
	
	/**
	 * Sends a emssage to a squad.
	 * @param String squad
	 * @param String message
	 * @throws IOException
	 */
	public void messageSquad(String squad, String message) throws IOException {
		mConnection.send(Command.SEND_SQUAD, squad, message);
	}
}
