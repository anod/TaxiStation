package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alex
 */
public class StationClient implements Client{
	private static final String ACTION_EXIT = "exit";
	private static final String ACTION_ADDCAB = "addcab";
	private static final String ACTION_ADDPASSENGER = "addpassenger";
	private Socket mSocket;
	
	private final SocketStationContext mStationContext;

	private StationClient(SocketStationContext context) {
		mStationContext = context;
	}
	
	@Override
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
	
	private static final String CONFIG_PATH = "SocketsXMLConfig.xml";

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_PATH, StationClient.class);
		final SocketStationContext context = new SocketStationContext(applicationContext);
		final Client client = context.createClient();
		boolean isConnected = client.connect();
		if (isConnected) {
			client.communicate();
			client.close();
		}
		
	}

	@Override
	public void communicate() {
		Scanner scan = new Scanner(System.in);

		while(true) {
			System.out.println("Please enter action [addcab,addpassenger,exit]: ");
			String input = scan.nextLine();
			if (input.equals(ACTION_EXIT)) {
				break;
			}
			if (input.equals(ACTION_ADDCAB)) {
				//
			} else if (input.equals(ACTION_ADDPASSENGER)) {
				//
			} else {
				System.out.println("Wrong input. Try again.");
			}
		}
	}
}
