package com.station.taxi.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.station.taxi.Station;
import com.station.taxi.configuration.StationConfigLoader;
import com.station.taxi.configuration.StationConfigStorage;

/**
 * StationFrame window adapter - initialize and stop station thread
 * 
 * @author alex
 * @author Eran Zimbler
 */
public class StationWindowAdapter extends WindowAdapter {
	
	private static final String CONFIG_XML = "configs/config1.xml";

	private Station mStation;
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
		super.windowClosing(event);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent event) {
		super.windowOpened(event);
		//Create configuration loader
		StationConfigLoader configLoader = new StationConfigLoader(CONFIG_XML);

		try {
			//Load station from configuration
			mStation = configLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		mStation.registerStateListener(mStationFrame);
		//Start station thread
		mStation.start();
	}
	
}