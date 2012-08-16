package com.station.taxi.sockets;

import com.station.taxi.sockets.message.AbstractResponse;
import com.station.taxi.sockets.message.MessageFactory;
import com.station.taxi.sockets.message.Request;
import com.station.taxi.validator.CabValidator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

/**
 * Command line socket client for station
 * @author alex
 */
public class CLIStationClient {
	private static final String HOST = "localhost";

	private static final String USER_ACTION_EXIT = "exit";
	private static final String USER_ACTION_ADDCAB = "addcab";
	private static final String USER_ACTION_ADDPASSENGER = "addpassenger";
	private static final String USER_ACTION_LIST_WAITING_CABS= "list_waiting_cabs";
	private static final String USER_ACTION_LIST_PASSENGERS = "list_passengers";
	private static final String USER_ACTION_LIST_DRIVING = "list_driving";

	private static final String[] sUserActions = {
		USER_ACTION_ADDCAB,
		USER_ACTION_ADDPASSENGER,
		USER_ACTION_LIST_WAITING_CABS,
		USER_ACTION_LIST_PASSENGERS,
		USER_ACTION_LIST_DRIVING,
		USER_ACTION_EXIT
	}; 
	
	private final SocketStationContext mStationContext;
	private final Client mClient;

	/**
	 * 
	 * @param context 
	 */
	public CLIStationClient(SocketStationContext context) {
		mStationContext = context;
		mClient = context.createClient(HOST, StationServer.PORT);
	}

	/**
	 * Run client
	 */
	public void run() {
		if (!mClient.connect()) {
			return;
		}

		Scanner scan = new Scanner(System.in);

		boolean running = true;
		while(running) {
			StringBuilder sb = new StringBuilder();
			sb.append(sUserActions[0]);
			for(int i=1; i<sUserActions.length; i++) {
				sb.append(", ");
				sb.append(sUserActions[i]);
			}
			System.out.printf("Please enter action [%s]: ",sb.toString());
			String input = scan.nextLine();

			switch (input) {
				case USER_ACTION_EXIT:
					running = false;
					break;
				case USER_ACTION_ADDCAB:
					addCabRequest(scan);
					break;
				case USER_ACTION_ADDPASSENGER:
					//TODO
					break;
				case USER_ACTION_LIST_DRIVING:
					sendListRequest(MessageFactory.ACTION_LIST_DRIVING);
					break;
				case USER_ACTION_LIST_WAITING_CABS:
					sendListRequest(MessageFactory.ACTION_LIST_WAITING_CABS);
					break;
				case USER_ACTION_LIST_PASSENGERS:
					sendListRequest(MessageFactory.ACTION_LIST_WAITING_PASSENGERS);
					break;
				default:
					System.out.println("Wrong input. Try again.");
					break;
			}
		}
		
		Request msg = new Request(MessageFactory.ACTION_EXIT);
		mClient.sendRequest(msg.toJSON());
		
		mClient.close();
	}
	
	/**
	 * 
	 * @param scan 
	 */
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

		Request msg = MessageFactory.createAddCabRequest(Integer.valueOf(numberStr), whileWaiting);

		//wait for response
		JSONObject json = (JSONObject)mClient.sendAndReceive(msg.toJSON());
		AbstractResponse response = MessageFactory.parseResponse(json);
		if (response.isStatusOk()) {
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

	/**
	 * 
	 * @param action 
	 */
	private void sendListRequest(String action) {	
		Request msg = new Request(action);
		//wait for response
		JSONObject json = (JSONObject)mClient.sendAndReceive(msg.toJSON());
		AbstractResponse response = MessageFactory.parseResponse(json);
		if (response.isStatusOk()) {
			System.out.println("Response is ok");
		} else {
			System.out.println("Error occured");			
		}
	}

}
