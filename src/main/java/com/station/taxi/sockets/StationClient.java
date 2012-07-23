/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class StationClient {
	private Socket mSocket;
	
	public boolean connect() {
		try {
			mSocket = new Socket("localhost", StationServer.PORT);
		} catch (UnknownHostException ex) {
			LoggerWrapper.logException(StationClient.class.getName(), ex);
			return false;		
		} catch (IOException ex) {
			LoggerWrapper.logException(StationClient.class.getName(), ex);
			return false;
		}
		return true;
	}
	
	
	
	public void close() {
		if (mSocket == null) {
			return;
		}
		try {
			mSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(StationClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
