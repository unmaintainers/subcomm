package com.roylaurie.subcomm.client.netchat;
/**
 * All of the standard command prefixes, including the trailing colon.
 * @author Roy Laurie <roy.laurie@gmail.com>
 */
public enum MessageType {
	LOGIN ("LOGIN:"),
	GO ("GO:"),
	NOOP ("NOOP"),
	LOGINOK ("LOGINOK:"),
	LOGINBAD ("LOGINBAD:"),
	CHANGEFREQ ("CHANGEFREQ:"),
	SEND_CMD ("SEND:CMD:"),
	SEND_PRIVCMD ("SEND:PRIVCMD:"),
	SEND_CHAT ("SEND:CHAT:"),
	SEND_FREQ ("SEND:FREQ:"),
	SEND_MOD ("SEND:MOD:"),
	SEND_PRIV ("SEND:PRIV:"),
	SEND_PUB ("SEND:PUB:"),
	SEND_SQUAD ("SEND:SQUAD:")
	;
	
	private final String mCommand;
	
	private MessageType(String command) {
		mCommand = command;
	}
	
	/**
	 * @return String Returns the command prefix including trailing colon.
	 */
	@Override
	public String toString() {
		return mCommand;
	}
}
