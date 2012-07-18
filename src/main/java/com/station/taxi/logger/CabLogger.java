package com.station.taxi.logger;

import com.station.taxi.ICab;
import com.station.taxi.IPassenger;
import com.station.taxi.events.CabEventListener;
import java.util.List;


public class CabLogger extends CabEventListener {

	@Override
	public void update(int type, ICab cab) {
		switch(type) {
			case START:
				LoggerWrapper.addCabLogger(cab);
				logCab(cab, "Cab is ready and running...");
			break;
			case INTERRUPT:
				logCab(cab, "Cab interupt requested...");		
			break;
			case WAITING:
				logCab(cab, "Waiting for passengers...");		
			break;		
			case DRIVE_DESTINATION:
				logDriveDestination(cab);
			break;
			case ARRIVED_DESTINATION:
				logArrivedDestination(cab);		
			break;
			case GOTO_BREAK:
				logCab(cab,"Goto break for "+(cab.getBreakTime()/1000)+" seconds to "+cab.getWhileWaiting());
			break;
		}
		
	}
	/**
	 * Write a cab action
	 * @param cab
	 * @param message
	 */
	private static void logCab(ICab cab, String message) {
		LoggerWrapper.log(String.format(CabFilter.PATTERN, cab.getNumber()) + " " + message);
	}

	/**
	 * @param cab
	 */
	private void logArrivedDestination(ICab cab) {
		List<IPassenger> passengers = cab.getPassegners();
		String destination = cab.getDestination();
		int size = passengers.size();
		logCab(cab,"Arrived to desitnation '"+destination+"' with "+size+" passengers");
	}

	/**
	 * @param cab
	 */
	private void logDriveDestination(ICab cab) {
		// Create string of passenger names
		List<IPassenger> passengers = cab.getPassegners();
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
