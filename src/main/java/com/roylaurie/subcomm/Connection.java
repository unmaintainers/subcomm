package com.roylaurie.subcomm;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs an blocking old-IO loop for a single TCP socket connection.
 * @author Roy Laurie <roy.laurie@gmail.com>
 */
public final class Connection implements Runnable {
	public static final int SOCKET_TIMEOUT = 10000;
	private static final char COLON = ':';
	private static final int MAX_CONSECUTIVE_READS = 10;
	private static final int MAX_CONSECUTIVE_WRITES = 10;
	private static final int KEEPALIVE_INTERVAL = 180000;
	
	private final String mHost;
	private final int mPort;
	private final List<String> mInput = new ArrayList<String>();
	private final List<String> mOutputList = new ArrayList<String>();
	private long mKeepAliveTime;
	private Socket mSocket;
	private PrintWriter mWriter;
	private BufferedReader mReader;
	
	public Connection(String host, int port) {
		mHost = host;
		mPort = port;
	}
	
	public void connect() throws UnknownHostException, IOException {
		mSocket = new Socket(mHost, mPort);
		mSocket.setKeepAlive(true);
		mSocket.setSoTimeout(SOCKET_TIMEOUT);
		mWriter = new PrintWriter(mSocket.getOutputStream(), true);
		mReader = new BufferedReader( new InputStreamReader(mSocket.getInputStream()));
		mKeepAliveTime = System.currentTimeMillis() + KEEPALIVE_INTERVAL;
	}
	
	public synchronized boolean connected() {
		return ( mSocket != null && mSocket.isConnected() );
	}

	public String nextReceivedMessage() throws IOException {
        if (!connected())
            throw new IOException("Not connected");
        
        synchronized (mInput) {
            if (mInput.isEmpty())
                return null;

            return mInput.remove(0);
        }	    
	}
	
	public String[] nextReceivedMessages() throws IOException {
		if (!connected())
			throw new IOException("Not connected");
		
		synchronized (mInput) {
			if (mInput.isEmpty())
				return new String[0];

			String[] result = mInput.toArray(new String[0]);
			mInput.clear();
			return result;
		}
	}

	@Override
	public void run() {	
		try {
			while(connected()) {
				read();
				keepalive(); // before write, so that the noop is sent immediately
				write();			
			}
		} catch (Exception e) {
			// do nothing
		} finally {
			disconnect();
		}		
	}
	
	private void write() {
		List<String> outputs;
		synchronized(mOutputList) {
			if (mOutputList.isEmpty())
				return;
			
			int size = mOutputList.size();
			int payloadSize = ( size <= MAX_CONSECUTIVE_WRITES ? size : MAX_CONSECUTIVE_WRITES );
			List<String> payload = mOutputList.subList(0, payloadSize);
			outputs = new ArrayList<String>(payload);
			payload.clear();
		}
		
		for (String output : outputs) {
			mWriter.println(output);
		}
	}
	
	private void keepalive() throws IOException {
		if (System.currentTimeMillis() > mKeepAliveTime) {
			send(Command.NOOP);
			mKeepAliveTime = System.currentTimeMillis() + KEEPALIVE_INTERVAL;
		}
	}
	
	private void read() throws IOException {
		if (!mReader.ready())
			return;
		
		for (int i = 0; i < MAX_CONSECUTIVE_READS; ++i) {
			if (!mReader.ready())
				return;
			
			String line = mReader.readLine();
			if (line == null)
				return;
			
			synchronized(mInput) {
				mInput.add(line);
			}
		}
	}

	public synchronized void disconnect() {
		try {
			if (mSocket != null)
				mSocket.close();
		} catch (Exception e) { /* do nothing */ }
		
		mSocket = null;
	}
	
	public void send(String message) throws IOException {
		if (!connected())
			throw new IOException("Not connected.");
		
		synchronized(mOutputList) {
			mOutputList.add(message);
		}
	}
	
	public void send(Command command, String ... parameters) throws IOException {
		StringBuffer buffer = new StringBuffer(command.toString());
		for (int i = 0, n = parameters.length; i < n; ++i) {
		    String str = parameters[i].trim().replaceAll("/[\\r\\n]/", "");
			buffer.append(str);
			if ((i+1) < n)
				buffer.append(COLON);
		}
		
		send(buffer.toString());
	}
}
