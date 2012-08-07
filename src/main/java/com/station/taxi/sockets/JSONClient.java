package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JSON Implementation of client
 * @author alex
 */
public class JSONClient implements Client {
	private JSONSocket mJSONSocket;

	private final String mHost;
	private final Integer mPort;
	
	public JSONClient(String host, Integer port) {
		mHost = host;
		mPort = port;
	}
	
	@Override
	public boolean connect() {
		try {
			mJSONSocket = new JSONSocket(new Socket(mHost, mPort));
			mJSONSocket.init();
		} catch (UnknownHostException ex) {
			LoggerWrapper.logException(CLIStationClient.class.getName(), ex);
			return false;		
		} catch (IOException ex) {
			LoggerWrapper.logException(CLIStationClient.class.getName(), ex);
			return false;
		}
		return true;
	}

	@Override
	public void close() {
		if (mJSONSocket == null) {
			return;
		}
		try {
			mJSONSocket.close();
		} catch (IOException ex) {
			LoggerWrapper.logException(CLIStationClient.class.getName(), ex);
		}
		mJSONSocket = null;
	}


	@Override
	public void sendRequest(Object request) {
		mJSONSocket.sendMessage(request);
	}
	
	@Override
	public Object receiveResponse() {
		try {
			return mJSONSocket.receiveMessage();
		} catch (IOException ex) {
			Logger.getLogger(JSONClient.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	@Override
	public Object sendAndReceive(Object request) {
		sendRequest(request);
		return receiveResponse();
	}
	
}
