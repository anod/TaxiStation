package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.model.TaxiStation;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author alex
 */
public class StationServer implements Server {
	/**
	 * Port to listen for incoming connections
	 */
	public static final int PORT = 13000;
	/**
	 * Station instance
	 */
	private TaxiStation mStation;
	/**
	 * Socket server instance
	 */
	private ServerSocket mServer;
	/**
	 * Flag 
	 */
	private boolean mAccepting = false;
	private final SocketStationContext mStationContext;

	public interface StationServerListener {
		public void onStart();
	}
	
	private StationServer(SocketStationContext context, TaxiStation station) {
		mStationContext = context;
		mStation = station;
	}
	
	@Override
	public void start() {
		try {
			mServer = new ServerSocket(PORT);
		} catch (IOException ex) {
			LoggerWrapper.logException(StationServer.class.getName() , ex);
		}
	}

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

	@Override
	public void accept() {
		mAccepting = true;
		while(mAccepting) {
			try {
				final Socket socket = mServer.accept();
				ServerWorker w = mStationContext.createWorker(socket, mStation, mStationContext);
				new Thread(w).start();
			} catch (IOException ex) {
				LoggerWrapper.logException(StationServer.class.getName() , ex);
				stop();
				break;
			}
			
		}
	}

}
