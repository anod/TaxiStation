package com.station.taxi.logger;

import java.util.List;

import com.station.taxi.Cab;
import com.station.taxi.Passenger;
import com.station.taxi.events.CabEventListener;


public class CabLogger extends CabEventListener {

	public CabLogger(Cab cab) {
		LoggerWrapper.addCabLogger(cab);
	}
	@Override
	public void update(int type, Cab cab) {
		switch(type) {
		case START:
			logCab(cab, "Cab is ready and running...");
		break;
		case INTERRUPT:
			logCab(cab, "Cab interupt requested...");		
		break;
		case DRIVE_DESTINATION:
			logDriveDestination(cab);
		break;
		case ARRIVED_DESTINATION:
			logArrivedDestination(cab);		
		break;
		case GOTO_BREAK:
			logCab(cab,"Goto break for "+cab.getBreakTime()+" seconds to "+cab.getWhileWaiting());
		break;
		case FINISH_BREAK:
			logCab(cab,"Finished break");
		break;
		}
		
	}
	/**
	 * Write a cab action
	 * @param cab
	 * @param message
	 */
	private static void logCab(Cab cab, String message) {
		LoggerWrapper.log(String.format(CabFilter.PATTERN, cab.getNumber()) + " " + message);
	}

	/**
	 * @param cab
	 */
	private void logArrivedDestination(Cab cab) {
		List<Passenger> passengers = cab.getPassegners();
		String destination = passengers.get(0).getDestination();		
		int size = passengers.size();
		logCab(cab,"Arrived to desitnation '"+destination+"' with "+size+" passengers");
	}

	/**
	 * @param cab
	 */
	private void logDriveDestination(Cab cab) {
		// Create string of passenger names
		List<Passenger> passengers = cab.getPassegners();
		String destination = passengers.get(0).getDestination();		
		StringBuffer sb = new StringBuffer(passengers.get(0).getPassangerName());
		int size = passengers.size();
		for(int i=1; i<size;i++) {
			sb.append(",");
			sb.append(passengers.get(i).getPassangerName());
		}

		logCab(cab,"Start driving to '"+destination+"' with "+size+" passengers ("+sb.toString()+").");
	}


}