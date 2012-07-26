/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

/**
 *
 * @author alex
 */
public interface Server {

	/**
	 * Start server to listen to incoming connection on predefined port
	 * @return 
	 */
	void start();

	/**
	 * Accept connections and start worker thread
	 */
	void accept();

	/**
	 * Stop server
	 */
	void stop();

	
}
