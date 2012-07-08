package com.roylaurie.subcomm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.junit.Test;

import com.roylaurie.subcomm.client.SubcommException;
import com.roylaurie.subcomm.client.SubcommMessage;
import com.roylaurie.subcomm.client.exception.SubcommIOException;
import com.roylaurie.subcomm.client.message.SubcommChangeFrequencyMessage;
import com.roylaurie.subcomm.client.message.SubcommChannelChatMessage;
import com.roylaurie.subcomm.client.message.SubcommCommandMessage;
import com.roylaurie.subcomm.client.message.SubcommFrequencyChatMessage;
import com.roylaurie.subcomm.client.message.SubcommJoinArenaMessage;
import com.roylaurie.subcomm.client.message.SubcommLoginMessage;
import com.roylaurie.subcomm.client.message.SubcommLoginOkMessage;
import com.roylaurie.subcomm.client.message.SubcommModeratorsChatMessage;
import com.roylaurie.subcomm.client.message.SubcommPrivateChatMessage;
import com.roylaurie.subcomm.client.message.SubcommPrivateCommandMessage;
import com.roylaurie.subcomm.client.message.SubcommPublicChatMessage;
import com.roylaurie.subcomm.client.message.SubcommSquadChatMessage;
import com.roylaurie.subcomm.client.netchat.SubcommNetchatClient;
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
	private static final String TEST_CONNECT = "TestConnect";
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
	 * @throws SubcommException 
	 */
	@Test
	public void testSend() throws IOException, SubcommException {
		ConnectResult result = connect();
		SubcommNetchatClient client = result.client;
		TestServer server = result.server;
		SubcommMessage expected;
		
		try {
			String frequency = makeData();
			client.changeFrequency(frequency);
			expected = new SubcommChangeFrequencyMessage(frequency);
			assertEquals(expected, server.nextMesssageReceived());
			
			String command = makeData();
			client.command(command);
			expected = new SubcommCommandMessage(command);
			assertEquals(expected, server.nextMesssageReceived());
			
			String username = makeData();
			command = makeData();
			client.commandPrivate(username, command);
			expected = new SubcommPrivateCommandMessage(username, command);
			assertEquals(expected, server.nextMesssageReceived());
			
			String arena = makeData();
			client.joinArena(arena);
			expected = new SubcommJoinArenaMessage(arena);
			assertEquals(expected, server.nextMesssageReceived());
			
			String channel = makeData();
			String message = makeData();
			client.chatChannel(channel, message);
			expected = new SubcommChannelChatMessage(channel, message);
			assertEquals(expected, server.nextMesssageReceived());
			
			frequency = makeData();
			message = makeData();
			client.chatFrequency(frequency, message);
			expected = new SubcommFrequencyChatMessage(frequency, message);
			assertEquals(expected, server.nextMesssageReceived());
			
			message = makeData();
			client.chatModerators(message);
			expected = new SubcommModeratorsChatMessage(message);
			assertEquals(expected, server.nextMesssageReceived());
			
			username = makeData();
			message = makeData();
			client.chatPrivate(username, message);
			expected = new SubcommPrivateChatMessage(username, message);
			assertEquals(expected, server.nextMesssageReceived());
			
			message = makeData();
			client.chatPublic(message);
			expected = new SubcommPublicChatMessage(message);
			assertEquals(expected, server.nextMesssageReceived());
			
			String squad = makeData();
			message = makeData();
			client.chatSquad(squad, message);
			expected = new SubcommSquadChatMessage(squad, message);
			assertEquals(expected, server.nextMesssageReceived());
		} finally {
			result.close();
		}
	}
	
	/**
	 * Tests receiving commands.
	 * @throws IOException
	 * @throws SubcommIOException 
	 */
	@Test
	public void testReceive() throws IOException, SubcommIOException {
		ConnectResult result = connect();
		SubcommNetchatClient client = result.client;
		TestServer server = result.server;
		
		try {
			for (int i = 0; i < NUM_RECEIVES; ++i) {
	            String username = makeData();
	            String message = makeData();
			    SubcommMessage expected = new SubcommPrivateChatMessage(username, message);
				server.send(expected);
				assertEquals(expected, waitForInput(client));
			}
			
		} finally {
			result.close();
		}
	}
	
	private SubcommMessage waitForInput(SubcommNetchatClient client) throws SubcommIOException {
		long timeoutTime = System.currentTimeMillis() + WAIT_FOR_INPUT_TIMEOUT;
		SubcommMessage message = client.nextReceivedMessage();
		while (message == null && System.currentTimeMillis() < timeoutTime) {
			message = client.nextReceivedMessage();
		}
		
		if (message == null)
			throw new SubcommIOException("No IO received");
		
		return message;
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
			SubcommMessage expected = new SubcommLoginMessage(username, password);
			assertEquals(expected, server.nextMesssageReceived());
			server.send(new SubcommLoginOkMessage());
			
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
}
