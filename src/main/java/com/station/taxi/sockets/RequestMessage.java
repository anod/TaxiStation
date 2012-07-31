/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author alex
 */
public class RequestMessage implements JSONMessage {
	
	public static final String KEY_ACTION = "action";

	public static final String ACTION_ADDCAB = "addcab";
	public static final String ACTION_EXIT = "exit";

	public static final String KEY_CABNUM = "num";
	public static final String KEY_CABWHILEWAITING = "whileWaiting";
	

	private String mAction;
	private Map<String,Object> mData = new HashMap<>();
	
	/**
	 * 
	 * @param action 
	 */
	public RequestMessage(String action) {
		mAction = action;
	}
	
	/**
	 * 
	 * @param key
	 * @param value 
	 */
	public void put(String key, Object value) {
		mData.put(key, value);
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put(KEY_ACTION, mAction);
		json.putAll(mData);
		return json;
	}
	
}
