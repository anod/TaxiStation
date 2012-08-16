
package com.station.taxi.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Socket wrapper
 * @author alex
 */
public class JSONSocket {
	
	private BufferedReader mFromNetInputStream;
	private PrintStream mToNetOutputStream;	
	private final Socket mSocket;

	/**
	 * 
	 * @param socket 
	 */
	public JSONSocket(Socket socket) {
		mSocket = socket;
	}

	/**
	 * Init streams
	 * @throws IOException 
	 */
	public void init() throws IOException {
		mFromNetInputStream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		mToNetOutputStream = new PrintStream(mSocket.getOutputStream());
	}
	
	/**
	 * Send json message
	 * @param message 
	 */
	public void sendMessage(Object message) {
		mToNetOutputStream.println(((JSONObject)message).toString());		
	}
	
	/**
	 * Receive JSON message
	 * @return
	 * @throws IOException 
	 */
	public Object receiveMessage() throws IOException {
		String string = mFromNetInputStream.readLine();
		if (string == null) {
			return null;
		}
		JSONObject message = (JSONObject)JSONValue.parse(string);
		return message;
	}

	/**
	 * Close connection
	 * @throws IOException 
	 */
	public void close() throws IOException {
		mSocket.close();
	}

	/**
	 * 
	 * @return wrapped socket
	 */
	public Socket getSocket() {
		return mSocket;
	}

	/**
	 * Check if stream has an error in connection
	 * @return 
	 */
	public boolean checkError() {
		return mToNetOutputStream.checkError();
	}
}
