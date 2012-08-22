/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author srgrn
 */
@Controller
@RequestMapping(value="/passenger")
public class passenger {
    private static final String HOST = "localhost";
    private static int PORT = StationServer.PORT;
    private static SocketStationContext mStationContext = SocketStationContext.readFromXml();
    private static Client mClient = null;

    
    @RequestMapping(value="/")
    public String passenger(Model model) {
	if(mClient == null)
        {
            mClient = mStationContext.createClient(HOST, StationServer.PORT);
        }
        if(mClient.connect())
        {
        ListPassengersResponse response = (ListPassengersResponse)getList(MessageFactory.ACTION_LIST_WAITING_PASSENGERS);
        if(response.isStatusOk())
        {model.addAttribute("PassengersInLine", response.getPassengers());}
        mClient.close();
        }
        else
        {
            model.addAttribute("ERROR", "Error cannot connect to socket");
        }
        return "passengerView";
	}
    @RequestMapping(value="/{var}")
    public String SpecificPassenger(@PathVariable String var,Model model) {
            model.addAttribute("name", var);
            return "passengerdetails";
	}
    private AbstractResponse getList(String action) //unusfull code duplication for the moment
        {
            Request msg = new Request(action);
            JSONObject json = (JSONObject)mClient.sendAndReceive(msg.toJSON());
            AbstractResponse response = MessageFactory.parseResponse(json);
            return response;
        }
}
