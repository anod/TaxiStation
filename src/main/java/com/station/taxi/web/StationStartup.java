/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.web;

import com.sun.appserv.server.LifecycleEvent;
import com.sun.appserv.server.LifecycleEventContext;
import com.sun.appserv.server.LifecycleListener;
import com.sun.appserv.server.ServerLifecycleException;

/**
 *
 * @author alex
 */
public class StationStartup implements LifecycleListener {

	@Override
	public void handleEvent(LifecycleEvent event) throws ServerLifecycleException {
		LifecycleEventContext context = event.getLifecycleEventContext();
	    context.log("got event" + event.getEventType() + " event data: " 
		  + event.getData());
	}
	
}
