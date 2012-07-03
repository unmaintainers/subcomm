package com.roylaurie.subcomm.client;
/**
 * All of the standard command prefixes, including the trailing colon.
 * @author Roy Laurie <roy.laurie@gmail.com>
 */
public enum SubcommMessageType {
	LOGIN ("LOGIN"),
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
	
	private final String mNetchatPrefix;
	
	private SubcommMessageType(String command) {
		mNetchatPrefix = command;
	}
	
	
	public final String getNetchatPrefix() {
	    return mNetchatPrefix;
	}
	
	/**
	 * @return String Returns the command prefix including trailing colon.
	 */
	@Override
	public String toString() {
		return mNetchatPrefix;
	}
}
