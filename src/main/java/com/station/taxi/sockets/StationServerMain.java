package com.station.taxi.sockets;

import com.station.taxi.configuration.StationConfigLoader;
import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.model.Cab;
import com.station.taxi.model.Passenger;
import com.station.taxi.model.Station;
import com.station.taxi.model.StationExecutor;
import com.station.taxi.model.TaxiStation;
import com.station.taxi.model.TaxiStation.StateChangeListener;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author alex
 */
public class StationServerMain implements StateChangeListener {

	public static void main(String[] args) {
		final SocketStationContext context = SocketStationContext.readFromXml();
		final StationServerMain main = new StationServerMain(context);
		main.startStationAndServer();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				main.shutdown();
			}
	    }, "shutdown-thread"));
	}

	/**
	 * Station configuration path
	 */
	private static final String CONFIG_XML = "configs/config1.xml";
	
	private Server mServer;
	final private SocketStationContext mContext;
	
	public StationServerMain(SocketStationContext context) {
		mContext = context;
	}
	
	/**
	 * Read station configuration and start station thread
	 */
	public void startStationAndServer() {
		
		//Create configuration loader
		StationConfigLoader configLoader = new StationConfigLoader(CONFIG_XML, mContext);

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
	
	/**
	 * Run server when station thread is ready
	 * @param station 
	 */
	@Override
	public void onStationStart(TaxiStation station) {
		mServer = mContext.createServer(station);
		mServer.start();
		// loop
		mServer.accept();
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

	private void shutdown() {
		if (mServer != null) {
			mServer.stop();
		}
	}
}
