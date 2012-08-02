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
	public static final String ACTION_LIST_WAITING_CABS = "list_waiting_cabs";
	public static final String ACTION_LIST_WAITING_PASSENGERS = "list_waiting_passengers";
	public static final String ACTION_LIST_DRIVING = "list_driving";
	public static final String ACTION_EXIT = "exit";

	public static final String KEY_CABNUM = "num";
	public static final String KEY_CABWHILEWAITING = "whileWaiting";
	

	private String mAction = "";
	private Map<String,Object> mData = new HashMap<>();
	
	public RequestMessage() {
		
	}
	/**
	 * 
	 * @param action 
	 */
	public RequestMessage(String action) {
		mAction = action;
	}
	/**
	 * Request action
	 * @return 
	 */
	public String getAction() {
		return mAction;
	}
	
	/**
	 * Data value by key
	 * @param key
	 * @return 
	 */
	public Object getData(String key) {
		return mData.get(key);
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
		if (mAction == null) {
			throw new IllegalStateException("Action is not defined");
		}
		JSONObject json = new JSONObject();
		json.put(KEY_ACTION, mAction);
		json.putAll(mData);
		return json;
	}

	@Override
	public void parse(JSONObject json) {
		for (Object key: json.keySet()) {
			if (key.equals(KEY_ACTION)) {
				mAction = (String) json.get(key);
			} else {
				mData.put((String)key, json.get(key));
			}
		}
	}
	
}
