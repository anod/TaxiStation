package com.station.taxi.sockets;

import org.json.simple.JSONObject;

/**
 *
 * @author alex
 */
public interface JSONMessage extends Message {

	/**
	 * Convert message to JSON object
	 */
	public JSONObject toJSON(); 
	
	/**
	 * Parse json message
	 */
	public void parse(JSONObject json);
}
