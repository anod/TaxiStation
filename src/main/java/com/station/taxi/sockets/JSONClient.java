/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * JSON Implementation of client
 * @author alex
 */
public class JSONClient implements Client {
	private Socket mSocket;
	
	private BufferedReader mFromNetInputStream;
	private PrintStream mToNetOutputStream;
	private final String mHost;
	private final Integer mPort;
	
	public JSONClient(String host, Integer port) {
		mHost = host;
		mPort = port;
	}
	
	@Override
	public boolean connect() {
		try {
			mSocket = new Socket(mHost, mPort);
			mFromNetInputStream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			mToNetOutputStream = new PrintStream(mSocket.getOutputStream());
		} catch (UnknownHostException ex) {
			LoggerWrapper.logException(StationClient.class.getName(), ex);
			return false;		
		} catch (IOException ex) {
			LoggerWrapper.logException(StationClient.class.getName(), ex);
			return false;
		}
		return true;
	}

	@Override
	public void close() {
		if (mSocket == null) {
			return;
		}
		try {
			mSocket.close();
		} catch (IOException ex) {
			LoggerWrapper.logException(StationClient.class.getName(), ex);
		}
		mSocket = null;
	}


	@Override
	public void sendRequest(Object request) {
		mToNetOutputStream.println(((JSONObject)request).toString());		
	}
	
	@Override
	public Object receiveResponse() {
		String msg = null;
		try {
			msg = mFromNetInputStream.readLine();
		} catch (IOException ex) {
			Logger.getLogger(JSONClient.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
		JSONObject response = (JSONObject)JSONValue.parse(msg);
		return response;
	}
	
}
