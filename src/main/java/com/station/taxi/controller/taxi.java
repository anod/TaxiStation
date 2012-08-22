/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.controller;

import com.station.taxi.sockets.*;
import com.station.taxi.sockets.message.*;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author srgrn
 */
@Controller
@RequestMapping("/taxi")
public class taxi {
    private static final String HOST = "localhost";
    private static int PORT = StationServer.PORT;
    private static SocketStationContext mStationContext = SocketStationContext.readFromXml();
    private static Client mClient = null;
    @RequestMapping(value="/")
    public String taxi(Model model) {
        if(mClient == null)
        {
            mClient = mStationContext.createClient(HOST, StationServer.PORT);
        }
        if(mClient.connect())
        {
        ListDrivingCabsResponse response = (ListDrivingCabsResponse)getList(MessageFactory.ACTION_LIST_DRIVING);
        if(response.isStatusOk())
        {model.addAttribute("DrivingCabs", response.getCabs());}
        ListWaitingCabsResponse response2 = (ListWaitingCabsResponse)getList(MessageFactory.ACTION_LIST_WAITING_CABS);
        if(response2.isStatusOk())
        {model.addAttribute("WaitingCabs", response2.mCabsStatus());}
        mClient.close();
        }
        else
        {
            model.addAttribute("ERROR", "Error cannot connect to socket");
        }
        return "taxiView";
	}
    @RequestMapping(value="/{cabno}")
    public String SingleCab(@PathVariable int cabno,Model model) {
        // currently do nothing since i am not sure it is needed and i should have finished it by now.
        return "singleCab";
    }

    private AbstractResponse getList(String action)
        {
            Request msg = new Request(action);
            JSONObject json = (JSONObject)mClient.sendAndReceive(msg.toJSON());
            AbstractResponse response = MessageFactory.parseResponse(json);
            return response;
        }

}
