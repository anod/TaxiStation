package com.station.taxi;

import java.util.concurrent.Executor;

/**
 * Start station thread
 * @author alex
 */
public class StationExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		IStation station = (IStation)command;
		for(Runnable target: station.getInitThreads()) {
			new Thread(target).start();
		}
		
		new Thread(station).start();
	}
	
}
