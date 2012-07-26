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
import java.util.Date;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author alex
 */
public class ServerWorker implements Runnable {
	private final Socket mSocket;
	
	public ServerWorker(Socket socket) {
		mSocket = socket;
	}
	
	@Override
	public void run() {
		System.out.println(new Date() + " ---> Client connected: " + mSocket.getInetAddress() + ":" + mSocket.getPort());
		try {
//			DataInputStream inputStream = new DataInputStream(mSocket.getInputStream());
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			PrintStream outputStream = new PrintStream(mSocket.getOutputStream());
			OUTER:
			while (true) {
				String msg = inputStream.readLine();
				JSONObject data = (JSONObject)JSONValue.parse(msg);
				String action = (String) data.get(StationServer.KEY_ACTION);
				switch (action) {
					case StationServer.ACTION_ADDCAB:
						
						break;
					case StationServer.ACTION_EXIT:
						break OUTER;
					default:
						System.out.print("Unknown data received: " + msg);
						break;
				}
			}
			
		} catch (IOException ex) {
			LoggerWrapper.logException(ServerWorker.class.getName(), ex);
		}
	}
	
}
