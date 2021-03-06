package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Abstract socket server implementation
 * @author alex
 */
abstract class AbstractServer implements Server {
	/**
	 * Socket server instance
	 */
	private ServerSocket mServer;
	private final Integer mPort;
	
	/**
	 * Flag 
	 */
	private boolean mAccepting = false;
	
	public AbstractServer(Integer port) {
		mPort = port;
	}
	
	@Override
	public boolean start() {
		try {
			mServer = new ServerSocket(mPort);
		} catch (IOException ex) {
			LoggerWrapper.logException(StationServer.class.getName() , ex);
			return false;
		}
		return true;
	}

	@Override
	public void accept() {
		mAccepting = true;
		while(mAccepting) {
			final Socket socket;	
			try {
				socket = mServer.accept();
			} catch (IOException ex) {
				LoggerWrapper.logException(StationServer.class.getName() , ex);
				break;
			}
			startWorker(socket);
		}
	}

	abstract public void startWorker(final Socket socket);
	
	@Override
	public void stop() {
		mAccepting = false;
		if (mServer == null) {
			throw new IllegalStateException("Server is not connected");
		}
		try {
			mServer.close();
		} catch (IOException ex) {
			LoggerWrapper.logException(StationServer.class.getName() , ex);
		}
	}
	
}
