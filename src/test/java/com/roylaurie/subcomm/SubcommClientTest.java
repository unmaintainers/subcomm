package com.roylaurie.subcomm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.junit.Test;

import com.roylaurie.subcomm.client.Command;
import com.roylaurie.subcomm.client.SubcommNetchatClient;
import com.roylaurie.subcomm.test.TestServer;

/**
 * Performs tests against the SubcommClient class.
 * @author Roy Laurie <roy.laurie@gmail.com>
 */
public final class SubcommClientTest {
	private static final class ConnectResult {
		public final SubcommNetchatClient client;
		public final TestServer server;
		
		public ConnectResult(SubcommNetchatClient client, TestServer server) {
			this.client = client;
			this.server = server;
		}
		
		public void close() {
			client.disconnect();
			server.close();
		}
	}
	
	private static final long CONNECT_THREAD_TIMEOUT = 10000;
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 5555;
	private static final char COLON = ':';
	private static final String TEST_CONNECT = "TestConnect";
	private static final String ONE = "1";
	private static final char SEMICOLON = ';';
	private static final int NUM_RECEIVES = 20;
	private static final long WAIT_FOR_INPUT_TIMEOUT = 5000;
	private final SecureRandom mRandom = new SecureRandom();
	
	/**
	 * Tests connect setup and teardown as well as health reporting.
	 * @throws IOException
	 */
	@Test
	public void testConnection() throws IOException {
		ConnectResult result = connect();
		try {
			boolean wasConnected = result.client.connected();
			result.close();
			assertTrue(wasConnected);
			assertFalse(result.client.connected());
		} finally {
			result.close();
		}
	}
	
	/**
	 * Tests sending all of the standard commands.
	 * @throws IOException
	 */
	@Test
	public void testSend() throws IOException {
		ConnectResult result = connect();
		SubcommNetchatClient client = result.client;
		TestServer server = result.server;
		String expected;
		
		try {
			String frequency = makeData();
			client.changeFrequency(frequency);
			expected = makeCommand(Command.CHANGEFREQ, frequency);
			assertEquals(expected, server.receiveLine());
			
			String command = makeData();
			client.command(command);
			expected = makeCommand(Command.SEND_CMD, command);
			assertEquals(expected, server.receiveLine());
			
			String username = makeData();
			command = makeData();
			client.commandPrivate(username, command);
			expected = makeCommand(Command.SEND_PRIVCMD, username, command);
			assertEquals(expected, server.receiveLine());
			
			String arena = makeData();
			client.joinArena(arena);
			expected = makeCommand(Command.GO, arena);
			assertEquals(expected, server.receiveLine());
			
			String channel = makeData();
			String message = makeData();
			client.messageChat(channel, message);
			expected = makeCommand(Command.SEND_CHAT, new StringBuffer(channel).append(SEMICOLON).append(message).toString());
			assertEquals(expected, server.receiveLine());
			
			frequency = makeData();
			message = makeData();
			client.messageFrequency(frequency, message);
			expected = makeCommand(Command.SEND_FREQ, frequency, message);
			assertEquals(expected, server.receiveLine());
			
			message = makeData();
			client.messageModerators(message);
			expected = makeCommand(Command.SEND_MOD, message);
			assertEquals(expected, server.receiveLine());
			
			username = makeData();
			message = makeData();
			client.messagePrivate(username, message);
			expected = makeCommand(Command.SEND_PRIV, username, message);
			assertEquals(expected, server.receiveLine());
			
			message = makeData();
			client.messagePublic(message);
			expected = makeCommand(Command.SEND_PUB, message);
			assertEquals(expected, server.receiveLine());
			
			String squad = makeData();
			message = makeData();
			client.messageSquad(squad, message);
			expected = makeCommand(Command.SEND_SQUAD, squad, message);
			assertEquals(expected, server.receiveLine());
		} finally {
			result.close();
		}
	}
	
	/**
	 * Tests receiving commands.
	 * @throws IOException
	 */
	@Test
	public void testReceive() throws IOException {
		ConnectResult result = connect();
		SubcommNetchatClient client = result.client;
		TestServer server = result.server;
		
		try {
			for (int i = 0; i < NUM_RECEIVES; ++i) {
				String expected = makeData();
				server.send(expected);
				assertEquals(expected, waitForInput(client));
			}
			
		} finally {
			result.close();
		}
	}
	
	private String waitForInput(SubcommNetchatClient client) throws IOException {
		long timeoutTime = System.currentTimeMillis() + WAIT_FOR_INPUT_TIMEOUT;
		String input = client.nextReceivedMessage();
		while (input == null && System.currentTimeMillis() < timeoutTime) {
			input = client.nextReceivedMessage();
		}
		
		if (input == null)
			throw new IOException("No IO received");
		
		return input;
	}
	
	/**
	 * Starts a TestServer and SubcommClient and connects the two. Tests the login() phase using dummy data.
	 * @return ConnectResult With two interconnected connected clients
	 * @throws IOException Will close() connections before bubbling exceptions up
	 */
	private ConnectResult connect() throws IOException {
		TestServer server = new TestServer(HOST, PORT);
		try {
			server.bind();
			
			final String username = makeData();
			final String password = makeData();
			final SubcommNetchatClient client = new SubcommNetchatClient(HOST, PORT, username, password);
			Thread connectThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						client.connect();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					
					synchronized(this) {
						this.notifyAll();
					}
				}
			});
			
			connectThread.setName(TEST_CONNECT);
			connectThread.start();
			server.accept();
			String expected = makeCommand(Command.LOGIN, ONE, username, password);
			assertEquals(expected, server.receiveLine());
			server.send(makeCommand(Command.LOGINOK));
			
			try {
				synchronized(connectThread) {
					connectThread.wait(CONNECT_THREAD_TIMEOUT);
				}
			} catch (InterruptedException e) { /* do nothing */ }
			
			return new ConnectResult(client, server); 
		} catch (RuntimeException e) {
			server.close();
			throw e;
		} catch (IOException e) {
			server.close();
			throw e;
		}
	}
	
	/**
	 * Makes random 32-character dummy data.
	 * @return String
	 */
	private String makeData() {
		return new BigInteger(130, mRandom).toString(32);
	}
	
	/**
	 * Makes a command by joining each parameter together with a colon.
	 * @param String command
	 * @param String parameters
	 * @return String The joined result
	 */
	public String makeCommand(Command command, String ... parameters) {
		StringBuffer buffer = new StringBuffer(command.toString());
		for (int i = 0, n = parameters.length; i < n; ++i) {
			buffer.append(parameters[i]);
			if ((i+1) < n)
				buffer.append(COLON);
		}
		
		return buffer.toString();
	}
}
