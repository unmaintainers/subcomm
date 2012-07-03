package com.roylaurie.subcomm.client.netchat;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.roylaurie.subcomm.client.SubcommClient;
import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.SubcommMessageType;
import com.roylaurie.subcomm.client.exception.SubcommIOException;
import com.roylaurie.subcomm.client.exception.SubcommLoginException;
import com.roylaurie.subcomm.client.exception.SubcommLoginTimeoutException;
import com.roylaurie.subcomm.client.exception.SubcommTimeoutException;

/**
 * Connects to (blocking) a SubSpace server and allows sending / receiving of messages (non-blocking).
 * @author Roy Laurie <roy.laurie@gmail.com>
 */
public final class SubcommNetchatClient extends SubcommClient {
	private static final String PROTOCOL_TYPE = "1";
	private static final int LOGIN_SLEEP = 350;
	private static final char COLON = ':';
	private static final char SPACE = ' ';
	private static final String SUBCOMM_CLIENT = "SubcommClient";
	
	private NetChatConnection mConnection;


    /**
     * @param String host
     * @param int port
     * @param String username
     * @param String password
     */
    public SubcommNetchatClient(String host, int port, String username, String password) {
        super(host, port, username, password);
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
		String name = new StringBuffer(SUBCOMM_CLIENT).append(SPACE).append(getHost()).append(COLON).append(getPort()).toString();
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
		String message;
        try {
            message = mConnection.nextReceivedMessage();
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
        
		if (message == null)
		    return null;
		
		return SubcommNetchatMessageParser.parse(message);
	}
	
    /**
     * Retrieves the next received message (non-blocking).
     * @return String[] Empty if nothing received
     * @throws IOException If not connected.
     */
    public List<SubcommMessage> nextReceivedMessages() throws SubcommIOException {
        String[] received;
        try {
            received = mConnection.nextReceivedMessages();
        } catch (IOException e) {
            throw new SubcommIOException(e);
        }
        
        if (received == null)
            return null;
        
        
        List<SubcommMessage> messages = new ArrayList<SubcommMessage>();
        for (String raw : received) {
            messages.add(SubcommNetchatMessageParser.parse(raw));
        }
        
        return messages;
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
            mConnection.send(SubcommMessageType.LOGIN, PROTOCOL_TYPE, username, password);
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
		    } else if (input.startsWith(SubcommMessageType.LOGINOK.getNetchatPrefix())) {
		        return;
		    } else if (input.startsWith(SubcommMessageType.LOGINBAD.getNetchatPrefix())) {
		        disconnect();
		        String reason = input.substring(SubcommMessageType.LOGINBAD.getNetchatPrefix().length());
		        throw new SubcommLoginException("Unable to login. Reason: " + reason);
		    }
		}
		
		disconnect();
		throw new SubcommTimeoutException("Login timed out.");
	}
	
    @Override
    public void send(SubcommMessage message) throws SubcommIOException {
       try {
           mConnection.send(message.getNetchatMessage());
       } catch (IOException e) {
           throw new SubcommIOException(e);
       }
    }
}
