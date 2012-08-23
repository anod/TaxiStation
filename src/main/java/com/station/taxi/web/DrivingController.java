package com.station.taxi.web;

import com.station.taxi.sockets.Client;
import com.station.taxi.sockets.SocketStationContext;
import com.station.taxi.sockets.StationServer;
import com.station.taxi.sockets.message.AbstractResponse;
import com.station.taxi.sockets.message.ListDrivingCabsResponse;
import com.station.taxi.sockets.message.ListWaitingCabsResponse;
import com.station.taxi.sockets.message.MessageFactory;
import com.station.taxi.sockets.message.Request;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author alex
 */
@Controller
public class DrivingController {
	
	private static final String HOST = "localhost";
	private static Client mClient = null;
	private static final String TPL_DRIVING = "driving";

	@RequestMapping(value = "/admin/driving")
	public String taxi(Model model) {
		if (mClient == null) {
			SocketStationContext stationContext = SocketStationContext.readFromXml();
			mClient = stationContext.createClient(HOST, StationServer.PORT);
		}
		if (mClient.connect()) {
			ListDrivingCabsResponse response = (ListDrivingCabsResponse) getList(MessageFactory.ACTION_LIST_DRIVING);
			if (response.isStatusOk()) {
				model.addAttribute("DrivingCabs", response.getCabs());
			}
			mClient.close();
		} else {
			model.addAttribute("ERROR", "Error cannot connect to socket");
		}
		return TPL_DRIVING;
	}
	
	private AbstractResponse getList(String action) {
		Request msg = new Request(action);
		JSONObject json = (JSONObject) mClient.sendAndReceive(msg.toJSON());
		AbstractResponse response = MessageFactory.parseResponse(json);
		return response;
	}
}
