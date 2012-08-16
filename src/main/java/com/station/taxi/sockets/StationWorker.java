package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.model.Cab;
import com.station.taxi.model.Passenger;
import com.station.taxi.model.Station;
import com.station.taxi.sockets.message.AbstractResponse;
import com.station.taxi.sockets.message.ListDrivingCabsResponse;
import com.station.taxi.sockets.message.ListPassengersResponse;
import com.station.taxi.sockets.message.ListWaitingCabsResponse;
import com.station.taxi.sockets.message.MessageFactory;
import com.station.taxi.sockets.message.Request;
import com.station.taxi.sockets.message.SimpleResponse;
import com.station.taxi.validator.CabValidator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.springframework.validation.MapBindingResult;

/**
 * Server worker wrapper
 * @author alex
 */
public class StationWorker implements Runnable {
	private final Station mStation;
	private final SocketStationContext mContext;
	private final Worker mWorker;
	
	/**
	 * 
	 * @param worker
	 * @param station
	 * @param context 
	 */
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
			String action = "";
			AbstractResponse response;
			try {
				JSONObject json = (JSONObject)mWorker.readRequest();
				Request request = new Request();
				if (json != null) {
					request.parse(json);
					action = request.getAction();
					response = MessageFactory.createResponse(action);
				} else {
					response = new SimpleResponse();
				}
				switch (action) {
					case MessageFactory.ACTION_ADDCAB:
						addCab(request,(SimpleResponse)response);
						break;
					case MessageFactory.ACTION_LIST_DRIVING:
						listDriving((ListDrivingCabsResponse)response);
						break;
					case MessageFactory.ACTION_LIST_WAITING_CABS:
						listWaitingCabs((ListWaitingCabsResponse)response);
						break;
					case MessageFactory.ACTION_LIST_WAITING_PASSENGERS:
						listWaitingPassengers((ListPassengersResponse)response);
						break;
					case MessageFactory.ACTION_EXIT:
						response.setStatus(AbstractResponse.STATUS_OK);
						running = false;
						break;
					default:
						response.setStatus(AbstractResponse.STATUS_ERROR);
						response.addError("Unknown data received: " + request);
						break;
				}
				mWorker.sendResponse(response.toJSON());
			} catch (Exception ex) {
				running = false;
				LoggerWrapper.logException(StationWorker.class.getName(), ex);
				response = new SimpleResponse();
				response.setStatus(AbstractResponse.STATUS_ERROR);
				response.addError("Server error");
				mWorker.sendResponse(response.toJSON());
			}
		}
		mWorker.close();
	}
	
	/**
	 * Read data validate, add new cab, fill response
	 * @param data
	 * @param response 
	 */
	private void addCab(Request request, SimpleResponse response) {
		long num = (long)request.getData(Request.KEY_CABNUM);
		String whileWaiting = (String)request.getData(Request.KEY_CABWHILEWAITING);
		
		Map<String, String> map = new HashMap<>();
		MapBindingResult errors = new MapBindingResult(map, String.class.getName());

		CabValidator.getNumberStringValidator().validate(String.valueOf(num), errors);
		CabValidator.getWhileWaitingValidator().validate(whileWaiting, errors);

		if (errors.hasErrors()) {
			response.setStatus(AbstractResponse.STATUS_ERROR);
			String[] errs = (String[]) errors.getGlobalErrors().toArray();
			response.setErrors(Arrays.asList(errs));
			return;
		}
		Cab cab = mContext.createCab((int)num, whileWaiting);
		mStation.addCab(cab);
		response.setStatus(AbstractResponse.STATUS_OK);
	}

	/**
	 * Add currently driving cabs to response
	 * @param response 
	 */
	private void listDriving(ListDrivingCabsResponse response) {
		List<Cab> cabs = mStation.getCabs();
		for(Cab cab : cabs) {
			if (cab.isDriving()) {
	 			response.addCab(cab);
			}
		}
		response.setStatus(AbstractResponse.STATUS_OK);
	}

	/**
	 * Add waiting passengers to response
	 * @param response 
	 */
	private void listWaitingPassengers(ListPassengersResponse response) {
		List<Passenger> passengers = mStation.getPassengers();
		for(Passenger p: passengers) {
			response.addPassenger(p.getPassangerName(), p.getDestination());
		}
		response.setStatus(AbstractResponse.STATUS_OK);
	}

	/**
	 * Add waiting cabs to response
	 * @param response 
	 */
	private void listWaitingCabs(ListWaitingCabsResponse response) {
		List<Cab> cabs = mStation.getCabs();
		for(Cab cab : cabs) {
			if (!cab.isDriving()) {
				response.addCab(cab);
			}
		}
		response.setStatus(AbstractResponse.STATUS_OK);
	}
	
}
