package com.station.taxi.sockets;

import org.json.simple.JSONObject;

/**
 *
 * @author alex
 */
public interface JSONMessage extends Message {

	public JSONObject toJSON(); 
}
