package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import java.io.IOException;
import java.net.Socket;
import org.json.simple.JSONObject;

/**
 * Wrapper over socket for server worker thread
 * @author alex
 */
public class JSONWorker implements Worker {
	private final JSONSocket mJSONSocket;

	public JSONWorker(Socket socket) {
		mJSONSocket = new JSONSocket(socket);
	}
	
	@Override
	public boolean init() {
		try {
			mJSONSocket.init();
		} catch (IOException ex) {
			LoggerWrapper.logException(JSONWorker.class.getName(), ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean isSocketConnected() {
		return mJSONSocket.getSocket().isConnected();
	}

	@Override
	public void sendResponse(JSONObject response) {
		mJSONSocket.sendMessage(response);
	}

	@Override
	public Object readRequest() {
		try {
			return mJSONSocket.receiveMessage();
		} catch (IOException ex) {
			LoggerWrapper.logException(JSONWorker.class.getName(), ex);
			return null;
		}
	}
	
	
}
