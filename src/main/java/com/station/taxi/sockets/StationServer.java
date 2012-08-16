package com.station.taxi.sockets;

import com.station.taxi.model.TaxiStation;
import java.net.Socket;

/**
 * Station server implementation
 * @author alex
 */
public class StationServer extends AbstractServer{
	/**
	 * Port to listen for incoming connections
	 */
	public static final int PORT = 13000;
	/**
	 * Station instance
	 */
	private TaxiStation mStation;


	private final SocketStationContext mStationContext;

	@Override
	public void startWorker(Socket socket) {
		StationWorker w = mStationContext.createStationWorker(socket, mStation, mStationContext);
		new Thread(w).start();
	}

	public interface StationServerListener {
		public void onStart();
	}
	
	public StationServer(SocketStationContext context, TaxiStation station) {
		super(PORT);
		mStationContext = context;
		mStation = station;
	}

}
