package com.station.taxi.sockets.message;

import org.json.simple.JSONObject;

/**
 * General response
 * @author alex
 */
public class SimpleResponse extends AbstractResponse {

	@Override
	protected void parseType(JSONObject json) {
		//Empty
	}

	@Override
	protected void toJSONType(JSONObject json) {
		//Empty
	}
	
}
