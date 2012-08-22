package com.station.taxi.sockets.message;

import com.station.taxi.model.Cab;
import com.station.taxi.model.Passenger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 * Response with list of driving cabs
 * @author alex
 */
public class ListDrivingCabsResponse extends AbstractResponse{
	private static final String KEY_CABS = "cabs";
	private static final String KEY_STATUS = "statuses";

	public static final String STATUS_BREAK = "onBreak";
	public static final String STATUS_WAITING = "waiting";
	private static final String KEY_DESTINATION = "destination";
	private static final String KEY_PASSENGERS = "passengers";
	
	private Map<Integer,Map<String,Object>> mCabs = new HashMap<>();

	/**
	 * Add a cab to response
	 * @param cab 
	 */
	public void addCab(Cab cab) {
		int number = cab.getNumber();
		String destination = cab.getDestination();
		List<Passenger> passengers = cab.getPassegners();
		List<String> names = new ArrayList<>(passengers.size());

		for(Passenger p: passengers) {
			names.add(p.getPassangerName());
		}
		
		Map<String,Object> data = new HashMap<>();
		data.put(KEY_DESTINATION, destination);
		data.put(KEY_PASSENGERS, names);
		
		mCabs.put(number, data);
	}

	@Override
	protected void parseType(JSONObject json) {
		Map<Integer, Map<String, Object>> cabs = (Map<Integer,Map<String,Object>>) json.get(KEY_CABS);
		Map<String, Object> data;
		for(Object key: cabs.keySet()) {
			int number = Integer.valueOf((String)key);
			data = cabs.get(key);
			mCabs.put(number, data);
		}
	}

	@Override
	protected void toJSONType(JSONObject json) {
		json.put(KEY_CABS, mCabs);
	}
        public Map<Integer,Map<String,Object>> getCabs() {
		return mCabs;
	}
}
