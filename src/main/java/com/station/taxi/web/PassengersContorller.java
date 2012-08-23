package com.station.taxi.web;

import com.station.taxi.sockets.Client;
import com.station.taxi.sockets.SocketStationContext;
import com.station.taxi.sockets.StationServer;
import com.station.taxi.sockets.message.AbstractResponse;
import com.station.taxi.sockets.message.ListPassengersResponse;
import com.station.taxi.sockets.message.MessageFactory;
import com.station.taxi.sockets.message.Request;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author srgrn
 */
@Controller
@RequestMapping(value = "/passengers")
public class PassengersContorller {

	private static final String HOST = "localhost";
	private static Client mClient = null;
	private static final String TPL_PASSENGERS = "passengers";

	@RequestMapping(value = "/")
	public String passengers(Model model) {
		if (mClient == null) {
			SocketStationContext stationContext = SocketStationContext.readFromXml();
			mClient = stationContext.createClient(HOST, StationServer.PORT);
		}
		if (mClient.connect()) {
			ListPassengersResponse response = (ListPassengersResponse) getList(MessageFactory.ACTION_LIST_WAITING_PASSENGERS);
			if (response.isStatusOk()) {
				model.addAttribute("PassengersInLine", response.getPassengers());
			}
			mClient.close();
		} else {
			model.addAttribute("ERROR", "Error cannot connect to socket");
		}
		return TPL_PASSENGERS;
	}

	private AbstractResponse getList(String action) //unusfull code duplication for the moment
	{
		Request msg = new Request(action);
		JSONObject json = (JSONObject) mClient.sendAndReceive(msg.toJSON());
		AbstractResponse response = MessageFactory.parseResponse(json);
		return response;
	}
}
