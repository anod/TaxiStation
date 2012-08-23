package com.station.taxi.web;

import com.station.taxi.sockets.*;
import com.station.taxi.sockets.message.*;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author srgrn
 */
@Controller
@RequestMapping("/taxi")
public class CabsController {

	private static final String HOST = "localhost";
	private static Client mClient = null;
	private static final String TPL_CABS = "cabs";

	@RequestMapping(value = "/")
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
			ListWaitingCabsResponse response2 = (ListWaitingCabsResponse) getList(MessageFactory.ACTION_LIST_WAITING_CABS);
			if (response2.isStatusOk()) {
				model.addAttribute("WaitingCabs", response2.mCabsStatus());
			}
			mClient.close();
		} else {
			model.addAttribute("ERROR", "Error cannot connect to socket");
		}
		return TPL_CABS;
	}

	private AbstractResponse getList(String action) {
		Request msg = new Request(action);
		JSONObject json = (JSONObject) mClient.sendAndReceive(msg.toJSON());
		AbstractResponse response = MessageFactory.parseResponse(json);
		return response;
	}
}
