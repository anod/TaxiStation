package com.station.taxi.sockets;

/**
 * Entry point to run Station Client
 * @author alex
 */
public class StationClientMain {

		
	/**
	 * Start StationClient 
	 * @param args 
	 */
	public static void main(String[] args) {
		final SocketStationContext context = SocketStationContext.readFromXml();
		final StationClient client = new StationClient(context);
		client.run();
		
	}
}
