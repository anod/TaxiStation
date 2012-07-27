/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.model.Cab;
import com.station.taxi.model.Station;
import com.station.taxi.validator.CabValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.validation.MapBindingResult;

/**
 *
 * @author alex
 */
public class ServerWorker implements Runnable {
	private final Socket mSocket;
	private final Station mStation;
	private final SocketStationContext mContext;
	
	private BufferedReader mInputStream;
	private PrintStream mOutputStream;
		
	public ServerWorker(Socket socket, Station station, SocketStationContext context) {
		mSocket = socket;
		mStation = station;
		mContext = context;
	}
	
	@Override
	public void run() {
		String tag = ServerWorker.class.getSimpleName() + mSocket.getInetAddress() + ":" + mSocket.getPort();
		LoggerWrapper.log(tag, "Connected");

		if (!initStreams()) {
			return;
		}
		
		boolean running = true;
		while (running) {
			if (!mSocket.isConnected()) {
				running = false;
				break;
			}
			try {
				String msg = mInputStream.readLine();
				JSONObject data = (JSONObject)JSONValue.parse(msg);
				String action = (String) data.get(Message.KEY_ACTION);

				JSONObject response = new JSONObject();
				response.put(Message.KEY_ACTION, action);

				switch (action) {
					case Message.ACTION_ADDCAB:
						addCab(data,response);
						break;
					case Message.ACTION_EXIT:
						response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_OK);
						running = false;
						break;
					default:
						response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_ERROR);
						response.put(Message.KEY_ERRORS,Arrays.asList( "Unknown data received: " + msg ) );
						break;
				}
				LoggerWrapper.log(tag, response.toString());
				mOutputStream.println(response.toString());
			} catch (Exception ex) {
				LoggerWrapper.logException(ServerWorker.class.getName(), ex);
				JSONObject response = new JSONObject();
				response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_ERROR);
				response.put(Message.KEY_ERRORS, Arrays.asList("Server error") );
				mOutputStream.println(response.toString());
				continue;
			}
		}
	}

	
	
	
	private boolean initStreams() {
		try {
			mInputStream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			mOutputStream = new PrintStream(mSocket.getOutputStream());
		} catch (IOException ex) {
			LoggerWrapper.logException(ServerWorker.class.getName(), ex);
			try {
				mSocket.close();
			} catch (IOException ex1) {
				LoggerWrapper.logException(ServerWorker.class.getName(), ex1);
			}
			return false;
		}
		return true;
	}
	
	private void addCab(JSONObject data,JSONObject response) {
		long num = (long)data.get(Message.KEY_CABNUM);
		String whileWaiting = (String)data.get(Message.KEY_CABWHILEWAITING);
		
		Map<String, String> map = new HashMap<>();
		MapBindingResult errors = new MapBindingResult(map, String.class.getName());

		CabValidator.getNumberStringValidator().validate(String.valueOf(num), errors);
		CabValidator.getWhileWaitingValidator().validate(whileWaiting, errors);

		if (errors.hasErrors()) {
			response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_ERROR);
			String[] errs = (String[]) errors.getGlobalErrors().toArray();
			response.put(Message.KEY_ERRORS, Arrays.asList(errs));
			return;
		}
		Cab cab = mContext.createCab((int)num, whileWaiting);
		mStation.addCab(cab);
		response.put(Message.KEY_RESPONSE_STATUS, Message.STATUS_OK);
	}
	
}
