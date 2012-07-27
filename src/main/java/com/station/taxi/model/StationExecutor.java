package com.station.taxi.model;

import java.util.concurrent.Executor;

/**
 * Start station thread
 * @author alex
 */
public class StationExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		Station station = (Station)command;
		for(Runnable target: station.getInitThreads()) {
			new Thread(target).start();
		}
		
		new Thread(station).start();
	}
	
}
