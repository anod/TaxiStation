
package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
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

	public JSONSocket(Socket socket) {
		mSocket = socket;
	}

	public void init() throws IOException {
		mFromNetInputStream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		mToNetOutputStream = new PrintStream(mSocket.getOutputStream());
	}
	
	public void sendMessage(Object message) {
		mToNetOutputStream.println(((JSONObject)message).toString());		
	}
	
	public Object receiveMessage() throws IOException {
		String string = mFromNetInputStream.readLine();
		if (string == null) {
			return null;
		}
		JSONObject message = (JSONObject)JSONValue.parse(string);
		return message;
	}

	public void close() throws IOException {
		mSocket.close();
	}

	public Socket getSocket() {
		return mSocket;
	}

	public boolean checkError() {
		return mToNetOutputStream.checkError();
	}
}
