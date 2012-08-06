package com.station.taxi.gui;

import com.station.taxi.configuration.StationConfigLoader;
import com.station.taxi.configuration.StationConfigStorage;
import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.model.StationExecutor;
import com.station.taxi.model.TaxiStation;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

/**
 * StationFrame window adapter - initialize and stop station thread
 * 
 * @author alex
 * @author Eran Zimbler
 */
public class StationWindowAdapter extends WindowAdapter {
	
	private static final String CONFIG_XML = "configs/config1.xml";

	private TaxiStation mStation;
	private StationFrame mStationFrame;

	/* (non-Javadoc)
	 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
	 */
	public StationWindowAdapter(StationFrame frame) {
		super();
		mStationFrame = frame;
	}

	@Override
	public void windowClosing(WindowEvent event) {
		if (mStation == null) {
			return;
		}
		//Stop the thread
		mStation.interrupt();
		//mStation configuration
		StationConfigStorage configStorage = new StationConfigStorage(CONFIG_XML);
		configStorage.save(mStation);
		
		ClassPathXmlApplicationContext applicationContext = (ClassPathXmlApplicationContext)mStationFrame.getContext().getApplicationContext();
		applicationContext.close();
		
		super.windowClosing(event);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent event) {
		super.windowOpened(event);
		//Create configuration loader
		StationConfigLoader configLoader = new StationConfigLoader(CONFIG_XML, mStationFrame.getContext());

		//Load station from configuration
		mStation = configLoader.load();
		mStation.registerStateListener(mStationFrame);
		StationExecutor executor = new StationExecutor();
		//Start station thread
		executor.execute(mStation);
	}
	
}
