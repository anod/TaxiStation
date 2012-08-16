package com.station.taxi.sockets.message;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 * Represent request of StationClient
 * @author alex
 */
public class Request implements JSONMessage {
	public static final String KEY_CABNUM = "num";
	public static final String KEY_CABWHILEWAITING = "whileWaiting";

	public static final String KEY_PASSENGERNAME = "name";
	public static final String KEY_PASSENGERDESTINATION = "dest";

	private String mAction = "";
	private Map<String,Object> mData = new HashMap<>();
	
	public Request() { }
	
	/**
	 * 
	 * @param action 
	 */
	public Request(String action) {
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
		json.put(MessageFactory.KEY_ACTION, mAction);
		json.putAll(mData);
		return json;
	}

	@Override
	public void parse(JSONObject json) {
		for (Object key: json.keySet()) {
			if (key.equals(MessageFactory.KEY_ACTION)) {
				mAction = (String) json.get(key);
			} else {
				mData.put((String)key, json.get(key));
			}
		}
	}
	
}
