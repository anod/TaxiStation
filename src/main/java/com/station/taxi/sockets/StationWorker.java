package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.model.Cab;
import com.station.taxi.model.Station;
import com.station.taxi.validator.CabValidator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.springframework.validation.MapBindingResult;

/**
 *
 * @author alex
 */
public class StationWorker implements Runnable {
	private final Station mStation;
	private final SocketStationContext mContext;
	private final Worker mWorker;
		
	public StationWorker(Worker worker, Station station, SocketStationContext context) {
		mWorker = worker;
		mStation = station;
		mContext = context;
	}
	
	@Override
	public void run() {

		if (!mWorker.init()) {
			return;
		}
		
		boolean running = true;
		while (running) {
			if (!mWorker.isSocketConnected()) {
				running = false;
				break;
			}
			try {
				JSONObject request = (JSONObject)mWorker.readRequest();
				String action = (request!=null) ? (String) request.get(Message.KEY_ACTION) : "";

				JSONObject response = new JSONObject();
				response.put(Message.KEY_ACTION, action);

				switch (action) {
					case Message.ACTION_ADDCAB:
						addCab(request,response);
						break;
					case Message.ACTION_EXIT:
						response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_OK);
						running = false;
						break;
					default:
						response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_ERROR);
						response.put(Message.KEY_ERRORS,Arrays.asList( "Unknown data received: " + request ) );
						break;
				}
				mWorker.sendResponse(response);
			} catch (Exception ex) {
				LoggerWrapper.logException(StationWorker.class.getName(), ex);
				JSONObject response = new JSONObject();
				response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_ERROR);
				response.put(Message.KEY_ERRORS, Arrays.asList("Server error") );
				mWorker.sendResponse(response);
				continue;
			}
		}
	}
	
	/**
	 * Read data validate, add new cab, fill response
	 * @param data
	 * @param response 
	 */
	private void addCab(JSONObject data, JSONObject response) {
		long num = (long)data.get(Message.KEY_CABNUM);
		String whileWaiting = (String)data.get(Message.KEY_CABWHILEWAITING);
		
		Map<String, String> map = new HashMap<>();
		MapBindingResult errors = new MapBindingResult(map, String.class.getName());

		CabValidator.getNumberStringValidator().validate(String.valueOf(num), errors);
		CabValidator.getWhileWaitingValidator().validate(whileWaiting, errors);

		if (errors.hasErrors()) {
			response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_ERROR);
			String[] errs = (String[]) errors.getGlobalErrors().toArray();
			response.put(Message.KEY_ERRORS, Arrays.asList(errs));
			return;
		}
		Cab cab = mContext.createCab((int)num, whileWaiting);
		mStation.addCab(cab);
		response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_OK);
	}
	
}
