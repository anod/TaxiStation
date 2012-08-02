/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author alex
 */
public class ResponseMessage implements JSONMessage {
	private static final String KEY_RESPONSE_STATUS = "status";
	
	public static final String STATUS_OK = "ok";
	public static final String STATUS_ERROR = "error";

	private static final String KEY_ERRORS = "errors";
	
	private String mStatus;
	private String mAction;
	private List<String> mErrors = new ArrayList<>();
	
	public ResponseMessage() {
	}

	/**
	 * Original request action
	 * @param action 
	 */
	public void setAction(String action) {
		mAction = action;
	}
	
	/**
	 * Status of response
	 * @param status 
	 */
	public void setStatus(String status) {
		mStatus = status;
	}
	
	/**
	 * Add error message to response
	 * @param error 
	 */
	public void addError(String error) {
		mErrors.add(error);
	}

	/**
	 * Set list of errors
	 * @param errorList
	 */
	public void setErrors(List<String> errorList) {
		mErrors = errorList;
	}

	/**
	 * Check for good status
	 * @return 
	 */
	public boolean isStatusOk() {
		return mStatus!=null & mStatus.equals(STATUS_OK);
	}
	
	public String getStatus() {
		return mStatus;
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put(KEY_RESPONSE_STATUS, mStatus);
		json.put(RequestMessage.KEY_ACTION, mAction);
		if (mErrors.size() > 0) {
			json.put(KEY_ERRORS, mErrors);
		}
		return json;
	}

	@Override
	public void parse(JSONObject json) {
		mStatus = (String)json.get(KEY_RESPONSE_STATUS);
		if (json.containsKey(KEY_ERRORS)) {
			mErrors = (List<String>) json.get(KEY_ERRORS);
		}
	}
	

}
