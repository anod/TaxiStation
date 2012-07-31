package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.validator.CabValidator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

/**
 *
 * @author alex
 */
public class StationClient {
	private static final String HOST = "localhost";

	private static final String USER_ACTION_EXIT = "exit";
	private static final String USER_ACTION_ADDCAB = "addcab";
	private static final String USER_ACTION_ADDPASSENGER = "addpassenger";

	private final SocketStationContext mStationContext;
	private final Client mClient;

	public StationClient(SocketStationContext context) {
		mStationContext = context;
		mClient = context.createClient(HOST, StationServer.PORT);
	}

	public void run() {
		if (!mClient.connect()) {
			return;
		}

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
		
		RequestMessage msg = new RequestMessage(RequestMessage.ACTION_EXIT);
		mClient.sendRequest(msg.toJSON());
		
		mClient.close();
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

		RequestMessage msg = new RequestMessage(RequestMessage.ACTION_ADDCAB);
		msg.put(RequestMessage.KEY_CABNUM, Integer.valueOf(numberStr));
		msg.put(RequestMessage.KEY_CABWHILEWAITING, whileWaiting);

		mClient.sendRequest(msg.toJSON());
//		mClient.sendRequest(json);
		//wait for response
		JSONObject response = (JSONObject)mClient.receiveResponse();
		if (response != null && response.get(ResponseMessage.KEY_RESPONSE_STATUS).equals(ResponseMessage.STATUS_OK)) {
			System.out.println("New cab added!");
		} else {
			System.out.println("Error when adding a new cab");
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

}
