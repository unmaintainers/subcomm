package com.roylaurie.subcomm.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Performs basic server IO. All methods are blocking.
 * @author Roy Laurie <roy.laurie@gmail.com>
 */
public final class TestServer {
	private final String mHost;
	private final int mPort;
	private ServerSocket mListenSocket;
	private Socket mClientSocket;
	private BufferedReader mReader;
	private PrintWriter mWriter;
	
	/**
	 * @param String host The hostname to bind to
	 * @param String port The port to bind to
	 */
	public TestServer(String host, int port) {
		mHost = host;
		mPort = port;
	}

	/**
	 * Starts the server and makes it ready to accept connections.
	 * @throws IOException
	 */
	public void bind() throws IOException {
		mListenSocket = new ServerSocket();
		InetSocketAddress address = new InetSocketAddress(mHost, mPort);
		mListenSocket.bind(address);
	}
	
	/**
	 * Accepts the next incoming connection.
	 * @throws IOException
	 */
	public void accept() throws IOException {
		mClientSocket = mListenSocket.accept();
		mReader = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
		mWriter = new PrintWriter(mClientSocket.getOutputStream(), true);
	}
	
	/**
	 * Receives the next line received.
	 * @return
	 * @throws IOException
	 */
	public String receiveLine() throws IOException {
		return mReader.readLine();
	}
	
	/**
	 * Sends a line. Appends a CRLF.
	 * @param message
	 * @throws IOException
	 */
	public void send(String message) throws IOException {
		mWriter.println(message);
	}
	
	/**
	 * Closes listen and client socket, quietly.
	 */
	public void close() {
		try {
			if (mClientSocket != null)
				mClientSocket.close();
		} catch (IOException e) { /* do nothing */ }
		try {
			if (mListenSocket != null)
				mListenSocket.close();
		} catch (IOException e) { /* do nothing */ }
	}
}
