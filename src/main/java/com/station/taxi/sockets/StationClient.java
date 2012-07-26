package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alex
 */
public class StationClient implements Client{
	private static final String USER_ACTION_EXIT = "exit";
	private static final String USER_ACTION_ADDCAB = "addcab";
	private static final String USER_ACTION_ADDPASSENGER = "addpassenger";

	private Socket mSocket;
	
	private final SocketStationContext mStationContext;
	private DataInputStream mFromNetInputStream;
	private PrintStream mToNetOutputStream;

	private StationClient(SocketStationContext context) {
		mStationContext = context;
	}
	
	@Override
	public boolean connect() {
		try {
			mSocket = new Socket("localhost", StationServer.PORT);
			mFromNetInputStream = new DataInputStream(mSocket.getInputStream());
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
			Logger.getLogger(StationClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		mSocket = null;
	}

	@Override
	public void communicate() {
		Scanner scan = new Scanner(System.in);

		while(true) {
			System.out.println("Please enter action [addcab,addpassenger,exit]: ");
			String input = scan.nextLine();
			if (input.equals(USER_ACTION_EXIT)) {
				break;
			}
			if (input.equals(USER_ACTION_ADDCAB)) {
				addCabRequest(scan);
			} else if (input.equals(USER_ACTION_ADDPASSENGER)) {
				//
			} else {
				System.out.println("Wrong input. Try again.");
			}
		}
	}

	private void addCabRequest(Scanner scan) {
		System.out.println("Please enter cab number: ");
		Integer number = Integer.valueOf(scan.nextLine());
		System.out.println("Please enter while waiting action: ");
		String whileWaiting = scan.nextLine();
		
		JSONObject json = new JSONObject();
		json.put(StationServer.KEY_ACTION, StationServer.ACTION_ADDCAB);
		json.put(StationServer.KEY_CABNUM, number);
		json.put(StationServer.KEY_CABWHILEWAITING, whileWaiting);
		
		mToNetOutputStream.println(json.toString());

	}
	
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
