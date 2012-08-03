package com.station.taxi.sockets.message;

import org.json.simple.JSONObject;

/**
 *
 * @author alex
 */
public class MessageFactory {
	
	public static final String KEY_ACTION = "action";

	public static final String ACTION_ADDCAB = "addcab";
	public static final String ACTION_LIST_WAITING_CABS = "list_waiting_cabs";
	public static final String ACTION_LIST_WAITING_PASSENGERS = "list_waiting_passengers";
	public static final String ACTION_LIST_DRIVING = "list_driving";
	public static final String ACTION_EXIT = "exit";

	/**
	 * Create StationSocket response message
	 * @param action
	 * @return 
	 */
	public static AbstractResponse createResponse(String action) {
		AbstractResponse response = null;
		switch(action) {
			case ACTION_LIST_WAITING_PASSENGERS:
				response = new ListPassengersResponse();
				break;
			case ACTION_LIST_DRIVING:
				response = new ListDrivingCabsResponse();
				break;
			case ACTION_LIST_WAITING_CABS:
				response = new ListWaitingCabsResponse();
				break;
			case ACTION_ADDCAB:
			case ACTION_EXIT:
				response = new SimpleResponse();
				break;
			default:
				throw new IllegalArgumentException("Unknown action:" + action);
			
		}
		response.setAction(action);
		return response;
		
	}
	
	/**
	 * Parse StationSocket response message
	 * @param json
	 * @return 
	 */
	public static AbstractResponse parseResponse(JSONObject json) {
		String action = (String) json.get(KEY_ACTION);
		AbstractResponse response = createResponse(action);
		response.parse(json);
		return response;
	}
	
	/**
	 * Create StationClient request
	 * @param action
	 * @return 
	 */
	public static Request parseRequest(JSONObject json) {
		Request request = new Request();
		request.parse(json);
		return request;
	}
}
