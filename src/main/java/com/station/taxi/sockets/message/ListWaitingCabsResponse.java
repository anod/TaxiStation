package com.station.taxi.sockets.message;

import com.station.taxi.model.Cab;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 * Response message for list of waiting cabs
 * @author alex
 */
public class ListWaitingCabsResponse extends AbstractResponse {
	private static final String KEY_WAITACTIONS = "waitActions";
	private static final String KEY_STATUS = "statuses";

	public static final String STATUS_BREAK = "onBreak";
	public static final String STATUS_WAITING = "waiting";
	
	private Map<Integer,String> mCabsWaitActions = new HashMap<>();
	private Map<Integer,String> mCabsStatus = new HashMap<>();

	/**
	 * @return the mCabsWaitActions
	 */
	public Map<Integer,String> getCabsWaitActions() {
		return mCabsWaitActions;
	}

	/**
	 * @return the mCabsStatus
	 */
	public Map<Integer,String> mCabsStatus() {
		return mCabsStatus;
	}

	/**
	 * Add a cab to response
	 * @param cab 
	 */
	public void addCab(Cab cab) {
		int number = cab.getNumber();
		String status = cab.isOnBreak() ? STATUS_BREAK : STATUS_WAITING;
		mCabsWaitActions.put(number, cab.getWhileWaiting());
		mCabsStatus.put(number, status);
	}

	@Override
	protected void parseType(JSONObject json) {
		mCabsWaitActions = (Map<Integer, String>) json.get(KEY_WAITACTIONS);
		mCabsStatus = (Map<Integer, String>) json.get(KEY_STATUS);
	}

	@Override
	protected void toJSONType(JSONObject json) {
		json.put(KEY_WAITACTIONS, mCabsWaitActions);
		json.put(KEY_STATUS, mCabsStatus);
	}
	
}
