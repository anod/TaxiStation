package com.station.taxi.sockets;

import org.json.simple.JSONObject;

/**
 *
 * @author alex
 */
public interface Worker  {

	public boolean init();

	public boolean isSocketConnected();

	public Object readRequest();
	
	public void sendResponse(JSONObject response);
	
}
