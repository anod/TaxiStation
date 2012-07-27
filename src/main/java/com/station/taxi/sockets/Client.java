package com.station.taxi.sockets;

import java.io.IOException;

/**
 * Socket client interface
 * @author alex
 */
public interface Client {
	
	/**
	 * Connect to the server
	 * @return 
	 */
	public boolean connect();
	
	/**
	 * Close connection with server
	 */
	public void close();
	
	/**
	 * Send request to server
	 * @param request 
	 */
	public void sendRequest(Object request);
	
	/**
	 * Receive and parse response from server 
	 * @return
	 * @throws IOException 
	 */
	public Object receiveResponse();
}
