package com.station.taxi.sockets;

/**
 * Socket server interfaceß
 * @author alex
 */
public interface Server {

	/**
	 * Start server to listen to incoming connection on predefined port
	 * @return true if its has been connected
	 */
	boolean start();

	/**
	 * Accept connections and start worker thread
	 */
	void accept();

	/**
	 * Stop server
	 */
	void stop();

	
}
