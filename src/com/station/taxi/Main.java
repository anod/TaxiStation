package com.station.taxi;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.station.taxi.configuration.StationConfigLoader;
import com.station.taxi.configuration.StationConfigStorage;
import com.station.taxi.gui.StationFrame;


public class Main {
	
    public static void main(String[] args) {
    	//Create configuration loader
    	StationConfigLoader configLoader = new StationConfigLoader("configs/config1.xml");

    	final Station station;
    	try {
    		//Load station from configuration
			station = configLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
    	
    	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				StationFrame window = new StationFrame();
				station.registerStateListener(window);
		    	window.addWindowListener(new StationWindowAdapter(station));
			}
		});
    	
    }
    
    private static class StationWindowAdapter extends WindowAdapter {
    	private Station mStation;
    	
    	/* (non-Javadoc)
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		public StationWindowAdapter(Station station) {
			super();
			mStation = station;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			//Stop the thread
			mStation.interrupt();
			//mStation configuration
			StationConfigStorage configStorage = new StationConfigStorage("configs/config2.xml");
			configStorage.save(mStation);
			super.windowClosing(e);				
		}

		/* (non-Javadoc)
		 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowOpened(WindowEvent e) {
			super.windowOpened(e);
			//Start station thread
			mStation.start();
		}
		
    }
    
    
}
