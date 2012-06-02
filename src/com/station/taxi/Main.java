package com.station.taxi;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.station.taxi.configuration.StationConfigLoader;
import com.station.taxi.configuration.StationConfigStorage;
import com.station.taxi.gui.MainFrame;


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
    	
    	MainFrame window = new MainFrame();
    	window.addWindowListener(new WindowAdapter() {


			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				//Stop the thread
				station.interrupt();
				//Save configuration
				StationConfigStorage configStorage = new StationConfigStorage("configs/config2.xml");
				configStorage.save(station);
				super.windowClosing(e);				
			}

			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				//Start station thread
				station.start();
			}
    		
		});
    	
		

    }
}
