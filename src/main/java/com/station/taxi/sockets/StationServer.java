package com.station.taxi.sockets;

import com.station.taxi.model.StationExecutor;
import com.station.taxi.configuration.StationConfigLoader;
import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.model.Cab;
import com.station.taxi.model.Passenger;
import com.station.taxi.model.Station;
import com.station.taxi.model.TaxiStation;
import com.station.taxi.model.TaxiStation.IStateChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author alex
 */
public class StationServer implements Server, IStateChangeListener {
	public static final int PORT = 13000;

	private static final String CONFIG_XML = "configs/config1.xml";

	private TaxiStation mStation;
	private ServerSocket mServer;
	private boolean mAccepting = false;
	private final SocketStationContext mStationContext;

	private StationServer(SocketStationContext context) {
		mStationContext = context;
	}
	
	@Override
	public void start() {
		try {
			mServer = new ServerSocket(PORT);
		} catch (IOException ex) {
			LoggerWrapper.logException(StationServer.class.getName() , ex);
		}
		startStation();
	}
	
	
	private void startStation() {
		
		//Create configuration loader
		StationConfigLoader configLoader = new StationConfigLoader(CONFIG_XML, mStationContext);

		Station station;
		try {
			//Load station from configuration
			station = configLoader.load();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			LoggerWrapper.logException(StationServer.class.getName(), e);
			return;
		}
		station.registerStateListener(this);
		StationExecutor executor = new StationExecutor();
		//Start station thread
		executor.execute(station);
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
	
	
	public static void main(String[] args) {
		final SocketStationContext context = SocketStationContext.readFromXml();
		final Server server = context.createServer();
		server.start();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				server.stop();
			}
	    }, "shutdown-thread"));
	}

	@Override
	public void onStationStart(TaxiStation station) {
		System.out.print("Station is running\n");
		mStation = station;
		System.out.print("Ready to accept connections\n");
		accept();
	}

	@Override
	public void onCabUpdate(Cab cab, int oldState) {
	}

	@Override
	public void onPassengerUpdate(Passenger p) {
	}

	@Override
	public void onCabAdd(Cab cab) {
	}

	@Override
	public void onPassengerAdd(Passenger p) {
	}
}
