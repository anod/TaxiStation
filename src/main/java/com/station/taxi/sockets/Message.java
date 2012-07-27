package com.station.taxi.sockets;

/**
 *
 * @author alex
 */
public class Message {
	
	public static final String KEY_ACTION = "action";

	public static final String ACTION_ADDCAB = "addcab";
	public static final String ACTION_EXIT = "exit";

	public static final String KEY_ERRORS = "errors";
	
	public static final String KEY_CABNUM = "num";
	public static final String KEY_CABWHILEWAITING = "whileWaiting";
	
	public static final String KEY_RESPONSE_STATUS = "status";
	
	public static final String STATUS_OK = "ok";
	public static final String STATUS_ERROR = "error";
	
}
