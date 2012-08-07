package com.station.taxi.sockets;

/**
 * Entry point to run Station Client
 * @author alex
 */
public class StationClientMain {

		
	/**
	 * Start CLIStationClient 
	 * @param args 
	 */
	public static void main(String[] args) {
		final SocketStationContext context = SocketStationContext.readFromXml();
		final CLIStationClient client = new CLIStationClient(context);
		client.run();
		
	}
}
