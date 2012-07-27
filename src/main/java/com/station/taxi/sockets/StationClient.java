package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.validator.CabValidator;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

/**
 *
 * @author alex
 */
public class StationClient implements Client{
	private static final String HOST = "localhost";

	private static final String USER_ACTION_EXIT = "exit";
	private static final String USER_ACTION_ADDCAB = "addcab";
	private static final String USER_ACTION_ADDPASSENGER = "addpassenger";

	private Socket mSocket;
	
	private final SocketStationContext mStationContext;
	private BufferedReader mFromNetInputStream;
	private PrintStream mToNetOutputStream;

	private StationClient(SocketStationContext context) {
		mStationContext = context;
	}
	
	@Override
	public boolean connect() {
		try {
			mSocket = new Socket(HOST, StationServer.PORT);
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
	public void communicate() {
		Scanner scan = new Scanner(System.in);

		boolean running = true;
		while(running) {
			System.out.println("Please enter action [addcab,addpassenger,exit]: ");
			String input = scan.nextLine();

			switch (input) {
				case USER_ACTION_EXIT:
					running = false;
					break;
				case USER_ACTION_ADDCAB:
					addCabRequest(scan);
					break;
				case USER_ACTION_ADDPASSENGER:
					break;
				default:
					System.out.println("Wrong input. Try again.");
					break;
			}
		}
		
		JSONObject json = new JSONObject();
		json.put(Message.KEY_ACTION, Message.ACTION_EXIT);
		LoggerWrapper.log(StationClient.class.getSimpleName(), json.toString());
		mToNetOutputStream.println(json.toString());
	}

	private void addCabRequest(Scanner scan) {
		System.out.println("Please enter cab number: ");
		String numberStr = scan.nextLine();
		if (!validateNumber(numberStr)) {
			return;
		}

		System.out.println("Please enter while waiting action: ");
		String whileWaiting = scan.nextLine();
		if (!validateWhileWaiting(whileWaiting)) {
			return;
		}
		
		JSONObject json = new JSONObject();
		json.put(Message.KEY_ACTION, Message.ACTION_ADDCAB);
		json.put(Message.KEY_CABNUM, Integer.valueOf(numberStr));
		json.put(Message.KEY_CABWHILEWAITING, whileWaiting);
		
		LoggerWrapper.log(StationClient.class.getName(), json.toString());
		mToNetOutputStream.println(json.toString());
		try {
			String response = mFromNetInputStream.readLine();
			LoggerWrapper.log(StationClient.class.getSimpleName(), response);
		} catch (IOException ex) {
			LoggerWrapper.logException(StationClient.class.getName(), ex);
		}
	}
	
	/**
	 * Validate number from user input and print error in case of error
	 * @param numberStr
	 * @return 
	 */
	private boolean validateNumber(String numberStr) {
		Map<String, String> map = new HashMap<>();
		MapBindingResult errors = new MapBindingResult(map, String.class.getName());
		CabValidator.getNumberStringValidator().validate(numberStr, errors);

		if (!errors.hasErrors()) {
			return true;
		}
		
		ObjectError error = errors.getGlobalError();
		System.out.println("Error: " + error.getDefaultMessage());
		return false;
	}
	
	/**
	 * Validate while waiting input from user
	 * @param whileWaiting
	 * @return 
	 */
	private boolean validateWhileWaiting(String whileWaiting) {
		Map<String, String> map = new HashMap<>();
		MapBindingResult errors = new MapBindingResult(map, String.class.getName());
		CabValidator.getWhileWaitingValidator().validate(whileWaiting, errors);

		if (!errors.hasErrors()) {
			return true;
		}
		
		ObjectError error = errors.getGlobalError();
		System.out.println("Error: " + error.getDefaultMessage());
		return false;
	}
	
	/**
	 * Start StationClient 
	 * @param args 
	 */
	public static void main(String[] args) {
		final SocketStationContext context = SocketStationContext.readFromXml();
		final Client client = context.createClient();
		boolean isConnected = client.connect();
		if (isConnected) {
			client.communicate();
			client.close();
		}
		
	}


}
