package com.station.taxi.sockets;

import org.json.simple.JSONObject;

/**
 * Server thread worker interface
 * @author alex
 */
public interface Worker  {
	/**
	 * Initialize
	 * @return 
	 */
	public boolean init();
	/**
	 * Check if client still connected
	 * @return 
	 */
	public boolean isSocketConnected();
	/**
	 * Read request message
	 * @return 
	 */
	public Object readRequest();
	/**
	 * Send response message
	 * @param response 
	 */
	public void sendResponse(JSONObject response);
	/**
	 * Close connection to clientß
	 */
	public void close();
}
