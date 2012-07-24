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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alex
 */
public class StationClient implements Client{
	private Socket mSocket;
	
	private final SocketStationContext mStationContext;

	private StationClient(SocketStationContext context) {
		mStationContext = context;
	}
	
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
	
	private static final String CONFIG_PATH = "SocketsXMLConfig.xml";

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_PATH, StationClient.class);
		final SocketStationContext context = new SocketStationContext(applicationContext);
		final Client client = context.createClient();
		boolean isConnected = client.connect();
		if (isConnected) {
			client.readInput();
			client.close();
		}
		
	}

	@Override
	public void readInput() {
		//throw new UnsupportedOperationException("Not supported yet.");
	}
}
